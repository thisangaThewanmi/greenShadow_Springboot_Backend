package lk.ijse.greenshadow_springboot.service;

import lk.ijse.greenshadow_springboot.dto.CropStatus;
import lk.ijse.greenshadow_springboot.dto.impl.CropDto;

import java.util.List;

public class CropServiceImpl implements CropService {
    @Override
    public void saveCrop(CropDto cropDto) {

    }

    @Override
    public void deleteCrop(String cropId) {

    }

    @Override
    public void updateCrop(CropDto cropDto, String cropId) {

    }

    @Override
    public List<CropDto> getAllCrop() {
        return List.of();
    }

    @Override
    public CropStatus getSelectedCrop(String cropId) {
        return null;
    }
}
