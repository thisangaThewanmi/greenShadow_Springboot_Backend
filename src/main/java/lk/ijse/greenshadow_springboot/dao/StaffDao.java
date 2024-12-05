package lk.ijse.greenshadow_springboot.dao;

import lk.ijse.greenshadow_springboot.entity.Staff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StaffDao extends JpaRepository<Staff, String> {

    Optional<Staff> findByStaffEmail(String email);

}
