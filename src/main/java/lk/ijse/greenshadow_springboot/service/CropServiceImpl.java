package lk.ijse.greenshadow_springboot.service;

import lk.ijse.greenshadow_springboot.customStatus.SelectedIdErrorStatus;
import lk.ijse.greenshadow_springboot.dao.CropDao;
import lk.ijse.greenshadow_springboot.dao.FieldDao;
import lk.ijse.greenshadow_springboot.dto.CropStatus;
import lk.ijse.greenshadow_springboot.dto.impl.CropDto;
import lk.ijse.greenshadow_springboot.dto.impl.VehicleDto;
import lk.ijse.greenshadow_springboot.entity.*;
import lk.ijse.greenshadow_springboot.exception.CropNotFoundException;
import lk.ijse.greenshadow_springboot.exception.DataPersistException;
import lk.ijse.greenshadow_springboot.exception.EquipmentNotFoundException;
import lk.ijse.greenshadow_springboot.exception.StaffNotFoundException;
import lk.ijse.greenshadow_springboot.util.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional


public class CropServiceImpl implements CropService {

    @Autowired
    CropDao cropDao;

    @Autowired
    Mapping cropMapping;

    @Autowired
    private FieldDao fieldDao;

    @Override
    public void saveCrop(CropDto cropDto) {

        System.out.println(cropDto+"cropDto");
        Crop savedCrop = cropDao.save(cropMapping.toCropEntity(cropDto));

        if (savedCrop == null) {
            throw new DataPersistException("Failed  to save crop");
        }
    }

    @Override
    public void deleteCrop(String cropId) {
        Optional<Crop> exsistedCrop= cropDao.findById(cropId);

        if(exsistedCrop.isPresent()){
            Crop crop = cropDao.findById(cropId)
                    .orElseThrow(() -> new CropNotFoundException("Crop not found: "+cropId));

            crop.getLogs().forEach(log -> log.getCropIds().remove(cropId));
            crop.getLogs().clear();

            cropDao.deleteById(cropId);


        }else{
            throw new EquipmentNotFoundException("Failed to delete crop");
        }

    }

    @Override
    public void updateCrop(CropDto cropDto, String cropId) {
        Optional<Crop> exsistedCrop = cropDao.findById(cropId);

        if(exsistedCrop.isPresent()){
            Crop crop = exsistedCrop.get();
            crop.setCropId(cropDto.getCropId());
            crop.setCommonName(cropDto.getCommonName());
            crop.setSpecificName(cropDto.getSpecificName());
            crop.setCategory(cropDto.getCategory());
            crop.setSeason(cropDto.getSeason());
            crop.setImage1(cropDto.getImage1());

            if (cropDto.getFieldId() != null) {
                Field field = fieldDao.findById(cropDto.getFieldId())
                        .orElseThrow(() -> new CropNotFoundException("Field not found with ID: " + cropDto.getFieldId()));
                crop.setField(field);
            } else {
                crop.setField(null); // Clear staff if not provided
            }

        }else{
            throw new EquipmentNotFoundException("Failed to update crop");
        }

    }

    @Override
    public List<CropDto> getAllCrop() {
        List<Crop> allCrops = cropDao.findAll();
        return cropMapping.toCropDtoList(allCrops);
    }

    @Override
    public CropStatus getSelectedCrop(String cropId) {
        if(cropDao.existsById(cropId)){
            Crop crop= cropDao.getReferenceById(cropId);
            CropDto cropDto = cropMapping.toCropDto(crop);
            return cropDto;
        }else
            return new SelectedIdErrorStatus(1,"Crop id not found");

    }
}
