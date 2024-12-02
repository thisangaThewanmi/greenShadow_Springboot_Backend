package lk.ijse.greenshadow_springboot.service;


import lk.ijse.greenshadow_springboot.dao.StaffDao;
import lk.ijse.greenshadow_springboot.dao.UserDao;
import lk.ijse.greenshadow_springboot.dto.impl.UserDto;
import lk.ijse.greenshadow_springboot.entity.Role;
import lk.ijse.greenshadow_springboot.entity.Staff;
import lk.ijse.greenshadow_springboot.entity.User;
import lk.ijse.greenshadow_springboot.entity.Vehicle;
import lk.ijse.greenshadow_springboot.exception.DataPersistException;
import lk.ijse.greenshadow_springboot.exception.StaffNotFoundException;
import lk.ijse.greenshadow_springboot.util.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional

public class UserServiceImpl implements UserService {

    @Autowired
    UserDao userDao;

    @Autowired
    StaffDao staffDao;


    @Autowired
    Mapping userMapping;

  /*  @Override
    public void save(UserDto userDto) {

        User savedUser = userDao.save(userMapping.toUserEntity(userDto));

        if (userDto.getStaff() != null) {
            Staff staff = staffDao.findById(userDto.getStaff())
                    .orElseThrow(() -> new StaffNotFoundException("Staff not found with ID: " + userDto.getStaff()));
            staff.setStaffEmail(userDto.getEmail());
            staff.setRole(Role.valueOf(userDto.getRole()));

            staff = staffDao.save(staff);
            savedUser.setStaff(staff);



            // Assign Staff to the User entity


        }
        if(savedUser != null) {
            throw new DataPersistException("Saving user failed");
        }


    }*/


    @Override
    public void save(UserDto userDto) {
        // Convert DTO to User entity
        User userEntity = userMapping.toUserEntity(userDto);

        Staff staff = null;

        // Check if Staff ID is provided in the DTO
        if (userDto.getStaff() != null) {
            // Try to find the associated Staff entity
            staff = staffDao.findById(userDto.getStaff()).orElse(null);

            if (staff == null) {
                // Create a new Staff entity if it does not exist
                staff = new Staff();
                staff.setStaffId(userDto.getStaff());
            }

            // Update Staff entity details
            staff.setStaffEmail(userDto.getEmail());
            staff.setRole(Role.valueOf(userDto.getRole()));

            // Save Staff entity
            staff = staffDao.save(staff);

            // Assign Staff to the User entity
            userEntity.setStaff(staff);
        } else {
            // If no Staff ID is provided, create a new Staff entity
            staff = new Staff();
            staff.setStaffEmail(userDto.getEmail());
            staff.setRole(Role.valueOf(userDto.getRole()));

            // Save Staff entity
            staff = staffDao.save(staff);

            // Assign Staff to the User entity
            userEntity.setStaff(staff);
        }

        // Save User entity
        User savedUser = userDao.save(userEntity);

        // Validate if the User entity was saved successfully
        if (savedUser == null) {
            throw new DataPersistException("Saving user failed");
        }
    }
}
