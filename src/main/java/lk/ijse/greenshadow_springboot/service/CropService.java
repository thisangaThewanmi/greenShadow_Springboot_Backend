package lk.ijse.greenshadow_springboot.service;

import lk.ijse.greenshadow_springboot.dto.CropStatus;
import lk.ijse.greenshadow_springboot.dto.EquipmentStatus;
import lk.ijse.greenshadow_springboot.dto.impl.CropDto;
import lk.ijse.greenshadow_springboot.dto.impl.EquipmentDto;

import java.util.List;

public interface CropService {

    void saveCrop(CropDto cropDto);
    void deleteCrop(String cropId);
    void updateCrop(CropDto cropDto,String cropId);
    List<CropDto> getAllCrop();
    CropStatus getSelectedCrop(String cropId);
}
