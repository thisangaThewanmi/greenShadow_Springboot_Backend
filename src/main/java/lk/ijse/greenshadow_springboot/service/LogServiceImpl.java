package lk.ijse.greenshadow_springboot.service;

import jakarta.transaction.Transactional;
import lk.ijse.greenshadow_springboot.dao.CropDao;
import lk.ijse.greenshadow_springboot.dao.FieldDao;
import lk.ijse.greenshadow_springboot.dao.LogDao;
import lk.ijse.greenshadow_springboot.dao.StaffDao;
import lk.ijse.greenshadow_springboot.dto.LogStatus;
import lk.ijse.greenshadow_springboot.dto.impl.LogDto;
import lk.ijse.greenshadow_springboot.entity.Crop;
import lk.ijse.greenshadow_springboot.entity.Field;
import lk.ijse.greenshadow_springboot.entity.Log;
import lk.ijse.greenshadow_springboot.entity.Staff;
import lk.ijse.greenshadow_springboot.exception.DataPersistException;
import lk.ijse.greenshadow_springboot.exception.FeildNotFoundException;
import lk.ijse.greenshadow_springboot.exception.StaffNotFoundException;
import lk.ijse.greenshadow_springboot.util.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Service
@Transactional

public class LogServiceImpl implements LogService{

    @Autowired
    LogDao logDao;

    @Autowired
    Mapping logmapping;

    @Autowired
    StaffDao staffDao;
    @Autowired
    private FieldDao fieldDao;
    @Autowired
    private CropDao cropDao;


    @Override
    public void saveLog(LogDto logDto) {

        Log savedLog = logDao.save(logmapping.toLogEntity(logDto));

        List<Staff> staffEntiyList = new ArrayList<>();


        if (logDto.getStaffIds() != null && !logDto.getStaffIds().isEmpty()) {

            for (String staffId : logDto.getStaffIds()) {
                Staff staff = staffDao.findById(staffId)
                        .orElseThrow(() -> new StaffNotFoundException("Staff not found with ID: " + staffId));

                // Add the saved Staff to the Log's staff list
                staff.getLogs().add(savedLog);

                // Add the Staff to the staffEntities list
                staffEntiyList.add(staff);
            }
            savedLog.setStaffIds(staffEntiyList);
        }


        List<Field> fieldEntiyList = new ArrayList<>();


        if (logDto.getFieldIds() != null && !logDto.getFieldIds().isEmpty()) {

            for (String fieldId : logDto.getFieldIds()) {
                Field field = fieldDao.findById(fieldId)
                        .orElseThrow(() -> new FeildNotFoundException("Field not found with ID: " + fieldId));

                // Add the saved Staff to the Log's staff list
                field.getLogs().add(savedLog);

                // Add the Staff to the staffEntities list
                fieldEntiyList.add(field);
            }
            savedLog.setFieldIds(fieldEntiyList);
        }


        List<Crop>cropEntiyList = new ArrayList<>();


        if (logDto.getCropIds() != null && !logDto.getCropIds().isEmpty()) {

            for (String cropId : logDto.getCropIds()) {
                Crop crop = cropDao.findById(cropId)
                        .orElseThrow(() -> new FeildNotFoundException("Crop not found with ID: " + cropId));

                // Add the saved Staff to the Log's staff list
                crop.getLogs().add(savedLog);

                // Add the Staff to the staffEntities list
                cropEntiyList.add(crop);
            }
            savedLog.setCropIds(cropEntiyList);
        }


    }

    @Override
    public void deleteLog(String logId) {

    }

    @Override
    public void updateLog(String logId, LogDto logDto) {

    }

    @Override
    public List<LogDto> getAllLogs() {
        return List.of();
    }

    @Override
    public LogStatus getSelectedLog(String logId) {
        return null;
    }
}
