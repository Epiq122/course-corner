package dev.robglason.admin.dao;

import dev.robglason.admin.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDao extends JpaRepository<User, Long> {


    User findByEmail(String email);

}
