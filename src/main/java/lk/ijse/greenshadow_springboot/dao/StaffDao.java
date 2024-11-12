package lk.ijse.greenshadow_springboot.dao;

import lk.ijse.greenshadow_springboot.entity.Staff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StaffDao extends JpaRepository<Staff, String> {

}
