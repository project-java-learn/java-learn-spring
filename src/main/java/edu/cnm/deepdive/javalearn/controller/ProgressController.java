package edu.cnm.deepdive.javalearn.controller;

import edu.cnm.deepdive.javalearn.model.dao.ProgressRepository;
import edu.cnm.deepdive.javalearn.model.entity.Progress;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ExposesResourceFor(Progress.class)
@RequestMapping("/progress")
public class ProgressController {

  private ProgressRepository progressRepository;

  @Autowired
  public ProgressController(ProgressRepository progressRepository) {
    this.progressRepository = progressRepository;
  }

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public List<Progress> list() {
    return progressRepository.findAllByOrderByUserAsc();
  }

  @GetMapping(value = "/{progressId}")
  public Progress get(@PathVariable("progressId") UUID progressId){
    return progressRepository.findById(progressId).get();
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
