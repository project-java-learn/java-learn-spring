package edu.cnm.deepdive.javalearn.controller;

import edu.cnm.deepdive.javalearn.model.dao.UserRepository;
import edu.cnm.deepdive.javalearn.model.entity.User;
import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ExposesResourceFor(User.class)
@RequestMapping("/users")
public class UserController {

  private UserRepository userRepository;

  @Autowired

  public UserController(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public List<User> list() {
    return userRepository.findAllByOrderByUserIdAsc();
  }

  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<User> post(@RequestBody User user) {
    userRepository.save(user);
    return ResponseEntity.created(user.getHref()).body(user);
  }


  @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "User not found")
  @ExceptionHandler(NoSuchElementException.class)
  public void notFound() {
  }
}