package lk.ijse.greenshadow_springboot.dao;

import lk.ijse.greenshadow_springboot.entity.Crop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface CropDao extends JpaRepository<Crop,String> {}
