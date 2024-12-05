package lk.ijse.greenshadow_springboot.dao;

import lk.ijse.greenshadow_springboot.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UserDao extends JpaRepository<User, String> {
    Optional<User> findByEmail(String email);
}
