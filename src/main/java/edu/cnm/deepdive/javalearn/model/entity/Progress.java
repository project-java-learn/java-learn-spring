package edu.cnm.deepdive.javalearn.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.net.URI;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import javax.annotation.PostConstruct;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityLinks;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
public class Progress {

  private static EntityLinks entityLinks;

  @Id
  @GeneratedValue(generator = "uuid2")
  @GenericGenerator(name = "uuid2", strategy = "uuid2")
  @Column(name = "progress_id", nullable = false, updatable = false)
  private UUID progressId;

  @NonNull
  @UpdateTimestamp
  @Temporal(TemporalType.TIMESTAMP)
  @Column(nullable = false, updatable = true)
  private Date updated;

  @ElementCollection
  private List<String> levels;

  @NonNull
  @OneToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "user_field", nullable = false, updatable = false)
  @OnDelete(action = OnDeleteAction.CASCADE)
  private User user;

  private int score;

  @PostConstruct
  private void initEntityLinks() {
    String ignore = entityLinks.toString();
  }

  @Autowired
  private void setEntityLinks(EntityLinks entityLinks) {
    Progress.entityLinks = entityLinks;
  }

  public UUID getProgressId() {
    return progressId;
  }

  public Date getUpdated() {
    return updated;
  }

  public List<String> getLevels() {
    return levels;
  }

  @JsonIgnore
  public User getUser() {
    return user;
  }

  public int getScore() {
    return score;
  }

  public void setScore(int score) {
    this.score = score;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public URI getHref() {
    return entityLinks.linkForSingleResource(Progress.class, progressId).toUri();
  }
}
