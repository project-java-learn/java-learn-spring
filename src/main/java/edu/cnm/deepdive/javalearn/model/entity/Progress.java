package edu.cnm.deepdive.javalearn.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
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
  private UUID id;

  @NonNull
  @UpdateTimestamp
  @Temporal(TemporalType.TIMESTAMP)
  @Column(nullable = false, updatable = true)
  private Date updated;

  @ElementCollection
  private List<String> levels;

  @NonNull
  @OneToOne
  @JoinColumn(name = "user_field", nullable = false, updatable = false)
  @OnDelete(action = OnDeleteAction.CASCADE)
  private User user;

  private int score;

  public UUID getId() {
    return id;
  }

  public Date getUpdated() {
    return updated;
  }

  public List<String> getLevels() {
    return levels;
  }

  public User getUser() {
    return user;
  }

  public int getScore() {
    return score;
  }


}
