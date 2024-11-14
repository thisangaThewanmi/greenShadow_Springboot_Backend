package lk.ijse.greenshadow_springboot.dao;

import lk.ijse.greenshadow_springboot.entity.Equipment;
import org.springframework.data.jpa.repository.JpaRepository;


public interface EquipmentDao extends JpaRepository<Equipment, String> {
}
