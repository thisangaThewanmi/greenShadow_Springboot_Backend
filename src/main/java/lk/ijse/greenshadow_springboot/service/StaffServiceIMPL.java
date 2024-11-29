package lk.ijse.greenshadow_springboot.service;

import jakarta.transaction.Transactional;
import lk.ijse.greenshadow_springboot.customStatus.SelectedIdErrorStatus;
import lk.ijse.greenshadow_springboot.dao.StaffDao;
import lk.ijse.greenshadow_springboot.dto.StaffStatus;
import lk.ijse.greenshadow_springboot.dto.impl.StaffDto;
import lk.ijse.greenshadow_springboot.entity.Field;
import lk.ijse.greenshadow_springboot.entity.Staff;
import lk.ijse.greenshadow_springboot.exception.DataPersistException;
import lk.ijse.greenshadow_springboot.exception.StaffNotFoundException;
import lk.ijse.greenshadow_springboot.util.Mapping;
import lk.ijse.greenshadow_springboot.util.Regex;
import org.apache.catalina.mapper.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;


@Service
@Transactional
public class StaffServiceIMPL implements StaffService {

    @Autowired
    private StaffDao staffDao;

    @Autowired
    private Mapping staffMapping;


    @Override
    public void saveStaff(StaffDto staffDto) {
       Staff savedStaff = staffDao.save(staffMapping.toStaffEntity(staffDto));
        System.out.println("savedStaff: " + savedStaff);
       if(savedStaff == null) {
           //If not saved
           throw new DataPersistException("Failed to save staff data");
       }
    }



    @Override
    public List<StaffDto> getAllStaff() {
        return staffMapping.asStaffDtoList(staffDao.findAll());
    }

    @Override
    public StaffStatus getStaff(String staffId) {

        if(staffDao.existsById(staffId)){
            var selectedStaff = staffDao.getReferenceById(staffId);
            return staffMapping.toStaffDto(selectedStaff);
        }else {
            return new SelectedIdErrorStatus(2, "Selected staff not found");
        }
    }

    @Override
    public void deleteStaff(String staffId) {
/*
        Optional<Staff> existedStaff = staffDao.findById(staffId);
        if (!existedStaff.isPresent()) {
            throw new StaffNotFoundException("Staff with id "+staffId+" not found!");
        }else {*/

            Staff staff = staffDao.findById(staffId)
                    .orElseThrow(() -> new StaffNotFoundException("Staff not found with ID: "+staffId ));

            // Remove associations with staff members
        staff.getFields().forEach(field -> field.getStaffMembers().remove(staff));
        staff.getFields().clear();
            staffDao.deleteById(staffId);
    }

    @Override
    public void updateStaff(String staffId, StaffDto staffDto) {
        Optional<Staff> findStaff = staffDao.findById(staffId);
        if (!findStaff.isPresent()) {
            throw new StaffNotFoundException("Staff not found!");
        } else {
            Staff staff = findStaff.get();
            staff.setFirstName(staffDto.getFirstName());
            staff.setLastName(staffDto.getLastName());
            staff.setDesignation(staffDto.getDesignation());
            staff.setGender(staffDto.getGender());
            staff.setJoinedDate(staffDto.getJoinedDate());
            staff.setDob(staffDto.getDob());
            staff.setAddressLine1(staffDto.getAddressLine1());
           /* staff.setAddressLine2(staffDto.getAddressLine2());
            staff.setAddressLine3(staffDto.getAddressLine3());
            staff.setAddressLine4(staffDto.getAddressLine4());
            staff.setAddressLine5(staffDto.getAddressLine5());*/
            staff.setContactNo(staffDto.getContactNo());
            staff.setStaffEmail(staffDto.getStaffEmail());
            staff.setRole(staffDto.getRole());

            // Save or update the staff entity
            staffDao.save(staff);
        }

    }




    }

