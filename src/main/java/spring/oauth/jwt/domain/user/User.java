package spring.oauth.jwt.domain.user;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String userName;
  @Setter
  private String name;
  @Setter
  private String email;
  private String role;

  @Builder
  public User(Long id, String userName, String name, String email, String role) {
    this.id = id;
    this.userName = userName;
    this.name = name;
    this.email = email;
    this.role = role;
  }
}
