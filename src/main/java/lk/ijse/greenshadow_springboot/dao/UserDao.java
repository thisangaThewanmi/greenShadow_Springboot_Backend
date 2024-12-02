package lk.ijse.greenshadow_springboot.dao;

import lk.ijse.greenshadow_springboot.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserDao extends JpaRepository<User, String> {
}
