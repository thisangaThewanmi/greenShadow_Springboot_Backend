package lk.ijse.greenshadow_springboot.service;

import jakarta.transaction.Transactional;
import lk.ijse.greenshadow_springboot.dao.FieldDao;
import lk.ijse.greenshadow_springboot.dao.StaffDao;
import lk.ijse.greenshadow_springboot.dto.FieldStatus;
import lk.ijse.greenshadow_springboot.dto.impl.FieldDto;
import lk.ijse.greenshadow_springboot.dto.impl.StaffDto;
import lk.ijse.greenshadow_springboot.entity.Field;
import lk.ijse.greenshadow_springboot.entity.Staff;
import lk.ijse.greenshadow_springboot.exception.DataPersistException;
import lk.ijse.greenshadow_springboot.exception.StaffNotFoundException;
import lk.ijse.greenshadow_springboot.util.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class FieldServiceIMPL implements FieldService {

    @Autowired
    private FieldDao fieldDao;

    @Autowired
    private StaffDao staffDao;

    @Autowired
    Mapping fieldMapping;

    @Autowired
    Mapping staffMapping;

    @Override
    public void addField(FieldDto fieldDto) {

        Field savedField = fieldDao.save(fieldMapping.toFieldEntity(fieldDto));

        if (savedField == null) {
            throw new DataPersistException("Failed  to save field");
        } else {

            String location = fieldDto.getLocation();

            String[] coordinates = location.split(",");
            Point point = new Point(Double.parseDouble(coordinates[0]), Double.parseDouble(coordinates[1]));


            List<String> staffIds = fieldDto.getStaffIds();

            List<Staff> staffEntities = new ArrayList<>();


            if (staffIds != null && staffIds.size() > 0) {
                for (String staffId : fieldDto.getStaffIds()) {
                    Staff staff = staffDao.findById(staffId)
                            .orElseThrow(() -> new StaffNotFoundException("Staff not found with ID: " + staffId));
                    staffEntities.add(staff);
                }

                savedField.setStaffMembers(staffEntities);
                savedField.setLocation(point);
            }
        }

    }

    @Override
    public void updateField(String fieldId, FieldDto fieldDto) {

    }

    @Override
    public void deleteField(String fieldId) {

    }

    @Override
    public FieldStatus getSelectedField(String fieldId) {
        return null;
    }

    @Override
    public List<FieldDto> getAllFields() {
        return List.of();
    }
}
