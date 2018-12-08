package edu.cnm.deepdive.javalearn.service;

import edu.cnm.deepdive.javalearn.model.dao.ProgressRepository;
import edu.cnm.deepdive.javalearn.model.entity.Progress;
import java.security.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProgressService {

  private ProgressRepository progressRepository;

  @Autowired
  public ProgressService(ProgressRepository progressRepository) {
    this.progressRepository = progressRepository;
  }

  public Progress checkProgress(Principal principal) {

    return null;
  }
}
