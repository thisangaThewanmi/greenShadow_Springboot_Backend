package lk.ijse.greenshadow_springboot.dao;

import lk.ijse.greenshadow_springboot.entity.Field;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FieldDao extends JpaRepository<Field,String> {
}
