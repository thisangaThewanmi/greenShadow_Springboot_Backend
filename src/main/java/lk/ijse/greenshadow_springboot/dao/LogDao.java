package lk.ijse.greenshadow_springboot.dao;

import lk.ijse.greenshadow_springboot.entity.Log;
import org.springframework.data.jpa.repository.JpaRepository;


public interface LogDao extends JpaRepository<Log,String> {}

