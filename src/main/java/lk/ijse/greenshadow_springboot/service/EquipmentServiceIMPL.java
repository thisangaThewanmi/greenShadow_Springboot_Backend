package lk.ijse.greenshadow_springboot.service;

import jakarta.transaction.Transactional;
import lk.ijse.greenshadow_springboot.dao.EquipmentDao;
import lk.ijse.greenshadow_springboot.dto.EquipmentStatus;
import lk.ijse.greenshadow_springboot.dto.impl.EquipmentDto;
import lk.ijse.greenshadow_springboot.entity.Equipment;
import lk.ijse.greenshadow_springboot.exception.DataPersistException;
import lk.ijse.greenshadow_springboot.exception.EquipmentNotFoundException;
import lk.ijse.greenshadow_springboot.util.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class EquipmentServiceIMPL implements EquipmentService {

    @Autowired
     private Mapping equipmentMapping;

    @Autowired
    private EquipmentDao equipmentDao;

    @Override
    public void saveEquipment(EquipmentDto equipmentDto) {
        Equipment savedEquipment = equipmentDao.save(equipmentMapping.toEquipmentEntity(equipmentDto));
        if(savedEquipment == null){
            throw new DataPersistException("Failed to save equipment");
        }
    }

    @Override
    public void deleteEquipment(String equipmentId) {
        Optional<Equipment> exsistedEquipment = equipmentDao.findById(equipmentId);

        if(exsistedEquipment.isPresent()){
            equipmentDao.delete(exsistedEquipment.get());
        }else{
            throw new EquipmentNotFoundException("Failed to delete equipment");
        }
    }

    @Override
    public void updateEquipment(String equipmentId, EquipmentDto equipmentDto) {

        Optional<Equipment> exsistedEquipment = equipmentDao.findById(equipmentId);

        if(exsistedEquipment.isPresent()){
            Equipment equipment = exsistedEquipment.get();
            equipment.setEquipmentId(equipmentDto.getEquipmentId());
            equipment.setType(equipmentDto.getType());
            equipment.setName(equipmentDto.getName());
            equipment.setStatus(equipmentDto.getStatus());

        }else{
            throw new EquipmentNotFoundException("Failed to update equipment");
        }
    }

    @Override
    public List<EquipmentDto> getAllEquipment() {
        List<Equipment> eqipmentList = equipmentDao.findAll();
        return equipmentMapping.asEquipmentDtoList(eqipmentList);
    }

    @Override
    public EquipmentStatus getSelectedEquipment(String equipmentId) {
        if(equipmentDao.existsById(equipmentId)){
            var selectedEquipment = equipmentDao.getReferenceById(equipmentId);
            return equipmentMapping.toEquipmentDto(selectedEquipment);
        }else{
            throw new EquipmentNotFoundException("Failed to select equipment");
        }
    }
}
