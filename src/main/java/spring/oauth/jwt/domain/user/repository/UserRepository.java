package spring.oauth.jwt.domain.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spring.oauth.jwt.domain.user.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  User findByUserName(String userName);
}
