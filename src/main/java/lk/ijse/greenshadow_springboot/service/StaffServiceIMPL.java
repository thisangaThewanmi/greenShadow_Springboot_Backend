package lk.ijse.greenshadow_springboot.service;

import jakarta.transaction.Transactional;
import lk.ijse.greenshadow_springboot.dao.StaffDao;
import lk.ijse.greenshadow_springboot.dto.StaffStatus;
import lk.ijse.greenshadow_springboot.dto.impl.StaffDto;
import lk.ijse.greenshadow_springboot.entity.Staff;
import lk.ijse.greenshadow_springboot.exception.DataPersistException;
import lk.ijse.greenshadow_springboot.exception.StaffNotFoundException;
import lk.ijse.greenshadow_springboot.util.Mapping;
import org.apache.catalina.mapper.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


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
       if(savedStaff == null) {
           throw new DataPersistException("Staff save sucessfully");

       }
    }



    @Override
    public List<StaffDto> getAllStaff() {
        return staffMapping.asStaffDtoList(staffDao.findAll());
    }

    @Override
    public StaffStatus getStaff(String staffId) {
      /*  if(staffDao.existsById(staffId)){
            var selectedStaff = staffDao.getReferenceById(staffId);
            return staffMapping.toStaffDto(selectedStaff);
        }else {
            return new StaffStatus(2, "Selected note not found") {
            };
        }*/
    }

    @Override
    public void deleteStaff(String staffId) {

        Optional<Staff> existedStaff = staffDao.findById(staffId);
        if (!existedStaff.isPresent()) {
            throw new StaffNotFoundException("Staff with id "+staffId+" not found!");
        }else {
            staffDao.deleteById(staffId);
        }
    }

    @Override
    public void updateStaff(String staffId, StaffDto staffDto) {

        Optional<Staff> findStaff = staffDao.findById(staffId);
        if (!findStaff.isPresent()) {
            throw new StaffNotFoundException("Staff  not found!");
        }else{
            findStaff.get().setStaffId(staffDto.getStaffId());
            findStaff.get().setAddress(staffDto.getAddress());
            findStaff.get().setContact(staffDto.getContact());
            findStaff.get().setEmail(staffDto.getEmail());
            findStaff.get().setDob(staffDto.getDob());
            findStaff.get().setFirstName(staffDto.getFirstName());
            findStaff.get().setLastName(staffDto.getLastName());
            findStaff.get().setRole(staffDto.getRole());
            findStaff.get().setJoinDate(staffDto.getJoinDate());
        }


    }
}
