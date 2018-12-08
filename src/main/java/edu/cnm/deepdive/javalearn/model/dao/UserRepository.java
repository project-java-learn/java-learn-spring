package edu.cnm.deepdive.javalearn.model.dao;

import edu.cnm.deepdive.javalearn.model.entity.User;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, UUID> {

  List<User> findAllByOrderByUserIdAsc();

  Optional<User> findFirstByOAuthId(String oAuthId);

}
