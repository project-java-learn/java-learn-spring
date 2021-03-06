package edu.cnm.deepdive.javalearn.controller;

import edu.cnm.deepdive.javalearn.model.dao.ProgressRepository;
import edu.cnm.deepdive.javalearn.model.entity.Progress;
import edu.cnm.deepdive.javalearn.model.entity.User;
import edu.cnm.deepdive.javalearn.service.UserService;
import java.security.Principal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ExposesResourceFor(Progress.class)
@RequestMapping("/progress")
public class ProgressController {

  private ProgressRepository progressRepository;
  private UserService userService;

  @Autowired
  public ProgressController(ProgressRepository progressRepository, UserService userService) {
    this.progressRepository = progressRepository;
    this.userService = userService;
  }

  @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Progress> post(@RequestBody Progress progress, Principal principal) {
    User user = userService.createOrLoad(principal);
    if(user.getProgress() == null) {
      progress.setUser(user);
      progressRepository.save(progress);
      return ResponseEntity.created(progress.getHref()).body(progress);
    } else {
      return ResponseEntity.ok().body(user.getProgress());
    }
  }

  @PatchMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Progress> updateProgress(@RequestBody Progress progress, Principal principal) {
    User user = userService.createOrLoad(principal);

    Progress savedProgress = user.getProgress();

    savedProgress.setScore(progress.getScore());
    savedProgress.setLevels(progress.getLevels());

    progressRepository.save(savedProgress);

    return ResponseEntity.created(progress.getHref()).body(progress);
  }

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public Progress getProgress(Principal principal) {
    User user = userService.createOrLoad(principal);

    return user.getProgress();
  }

  @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
  public List<Progress> list() {
    return progressRepository.findAllByOrderByUserAsc();
  }

  @DeleteMapping(value = "{progressId}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable ("progressId") UUID progressId) {
    progressRepository.deleteById(progressId);
  }

  @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Progress not found")
  @ExceptionHandler(NoSuchElementException.class)
  public void notFound() {
  }

}
