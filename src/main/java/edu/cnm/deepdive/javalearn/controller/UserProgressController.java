package edu.cnm.deepdive.javalearn.controller;

import edu.cnm.deepdive.javalearn.model.entity.Progress;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ExposesResourceFor(Progress.class)
@RequestMapping("/users/{userId}/progress")
public class UserProgressController {


}
