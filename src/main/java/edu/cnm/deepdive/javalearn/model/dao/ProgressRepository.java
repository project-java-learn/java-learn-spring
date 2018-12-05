package edu.cnm.deepdive.javalearn.model.dao;

import edu.cnm.deepdive.javalearn.model.entity.Progress;
import edu.cnm.deepdive.javalearn.model.entity.User;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.repository.CrudRepository;

public interface ProgressRepository extends CrudRepository<Progress, UUID> {

  List<Progress> findAllByOrderByUserAsc();

  Progress findByUser(User user);

  Optional<Progress> findFirstByUserAndProgressId(User user, UUID progressId);
}
