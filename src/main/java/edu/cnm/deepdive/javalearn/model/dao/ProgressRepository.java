package edu.cnm.deepdive.javalearn.model.dao;

import edu.cnm.deepdive.javalearn.model.entity.Progress;
import java.util.List;
import java.util.UUID;
import org.springframework.data.repository.CrudRepository;

public interface ProgressRepository extends CrudRepository<Progress, UUID> {

  List<Progress> findAllByOrderByUserAsc();
}
