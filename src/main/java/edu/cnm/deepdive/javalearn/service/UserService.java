package edu.cnm.deepdive.javalearn.service;

import edu.cnm.deepdive.javalearn.model.dao.UserRepository;
import edu.cnm.deepdive.javalearn.model.entity.User;
import java.security.Principal;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  private UserRepository userRepository;

  @Autowired
  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public User createOrLoad(Principal principal) {
    Map<String, String> map = (Map<String, String>)((OAuth2AuthenticationDetails)((OAuth2Authentication) principal)
        .getDetails())
        .getDecodedDetails();
    String email = map.get("email");
    String oauthId = principal.getName();
    return userRepository.findFirstByOAuthId(oauthId).orElseGet(() -> {
      User user = new User();
      user.setOAuthId(oauthId);
      user.setUsername(email);
      userRepository.save(user);
      return user;
    });
  }
}
