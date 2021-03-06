package edu.cnm.deepdive.javalearn.controller;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.token.AccessTokenConverter;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.stereotype.Component;

@Component
public class GoogleTokenServices implements ResourceServerTokenServices {

  @Value("${oauth.clientId}")
  private String clientId;

  private final AccessTokenConverter converter = new DefaultAccessTokenConverter();

  @Override
  public OAuth2Authentication loadAuthentication(String idTokenString)
      throws AuthenticationException, InvalidTokenException {

    try {
      HttpTransport transport = new NetHttpTransport();
      JacksonFactory jsonFactory = new JacksonFactory();
      GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(transport, jsonFactory)
          .setAudience(Collections.singletonList(clientId))
          .build();
      GoogleIdToken idToken = verifier.verify(idTokenString);
      if (idToken != null) {
        Payload payload = idToken.getPayload();
        Collection<GrantedAuthority> grants = Collections
            .singleton(new SimpleGrantedAuthority("ROLE_USER"));
        Authentication base = new UsernamePasswordAuthenticationToken(payload.getSubject(),
            idTokenString, grants);
        OAuth2Request request = converter.extractAuthentication(payload).getOAuth2Request();
        OAuth2Authentication authentication = new OAuth2Authentication(request, base);
        Map<String, String> map = (Map<String, String>) authentication.getDetails();
        if (map == null) {
          map = new HashMap<>();
          authentication.setDetails(map);
        }
        map.put("email", payload.getEmail());
        return authentication;
      } else {
        throw new BadCredentialsException(idTokenString);
      }
    } catch (GeneralSecurityException | IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public OAuth2AccessToken readAccessToken(String accessToken) {
    return null;
  }
}
