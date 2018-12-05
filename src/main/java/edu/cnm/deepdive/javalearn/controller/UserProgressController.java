package edu.cnm.deepdive.javalearn.controller;

import edu.cnm.deepdive.javalearn.model.dao.ProgressRepository;
import edu.cnm.deepdive.javalearn.model.dao.UserRepository;
import edu.cnm.deepdive.javalearn.model.entity.Progress;
import edu.cnm.deepdive.javalearn.model.entity.User;
import java.util.NoSuchElementException;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users/{userId}/progress")
public class UserProgressController {

  private UserRepository userRepository;
  private ProgressRepository progressRepository;

  @Autowired
  public UserProgressController(UserRepository userRepository,
      ProgressRepository progressRepository) {
    this.userRepository = userRepository;
    this.progressRepository = progressRepository;
  }

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public Progress list(@PathVariable("userId") UUID userId) {
    User user = userRepository.findById(userId).get();
    return progressRepository.findByUser(user);
  }

  @GetMapping(value = "{progressId}", produces = MediaType.APPLICATION_JSON_VALUE)
  public Progress get(@PathVariable("userId") UUID userId,
      @PathVariable("progressId") UUID progressId) {
    User user = userRepository.findById(userId).get();
    return progressRepository.findFirstByUserAndProgressId(user, progressId).get();
  }

  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Progress> post(
      @PathVariable("userId") UUID userId, @RequestBody Progress progress) {
    User user = userRepository.findById(userId).get();
    user.setProgress(progress);
    progress.setUser(user);
    progressRepository.save(progress);
    userRepository.save(user);
    return ResponseEntity.created(progress.getHref()).body(progress);
  }

  @PostMapping(value = "{progressId}/levels", consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Progress> updateLevels(@PathVariable("progressId")UUID progressId,
      @RequestBody String level) {
    Progress progress = progressRepository.findById(progressId).get();
    progress.getLevels().add(level);
    progressRepository.save(progress);
    return ResponseEntity.created(progress.getHref()).body(progress);
  }

  @PostMapping(value = "{progressId}/score", consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Progress> updateScore(@PathVariable("progressId")UUID progressId,
      @RequestBody int score) {
    Progress progress = progressRepository.findById(progressId).get();
    progress.setScore(progress.getScore() + score);
    progressRepository.save(progress);
    return ResponseEntity.created(progress.getHref()).body(progress);
  }

  @DeleteMapping(value = "{progressId}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable("userId") UUID userId, @PathVariable("progressId") UUID progressId) {
    progressRepository.delete(get(userId, progressId));
  }

  @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "User or progress not found.")
  @ExceptionHandler(NoSuchElementException.class)
  public void notFound() {
  }
}
