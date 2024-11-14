package lk.ijse.greenshadow_springboot.service;

import lk.ijse.greenshadow_springboot.dto.EquipmentStatus;
import lk.ijse.greenshadow_springboot.dto.StaffStatus;
import lk.ijse.greenshadow_springboot.dto.impl.EquipmentDto;
import lk.ijse.greenshadow_springboot.dto.impl.StaffDto;

import java.util.List;

public interface EquipmentService {

    void saveEquipment(EquipmentDto equipmentDto);
    void deleteEquipment(String equipmentId);
    void updateEquipment(String equipmentId,EquipmentDto equipmentDto);
    List<EquipmentDto> getAllEquipment();
    EquipmentStatus getSelectedEquipment(String equipmentId);


}
