package lk.ijse.greenshadow_springboot.service;


/*
import lk.ijse.greenshadow_springboot.dao.StaffDao;
import lk.ijse.greenshadow_springboot.dao.UserDao;
import lk.ijse.greenshadow_springboot.dto.impl.UserDto;
import lk.ijse.greenshadow_springboot.entity.Role;
import lk.ijse.greenshadow_springboot.entity.Staff;
import lk.ijse.greenshadow_springboot.entity.User;
import lk.ijse.greenshadow_springboot.entity.Vehicle;
import lk.ijse.greenshadow_springboot.exception.DataPersistException;
import lk.ijse.greenshadow_springboot.exception.StaffNotFoundException;
import lk.ijse.greenshadow_springboot.exception.UserNotFoundException;
import lk.ijse.greenshadow_springboot.util.AppUtil;
import lk.ijse.greenshadow_springboot.util.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional

public class UserServiceImpl implements UserService {

    @Autowired
    UserDao userDao;

    @Autowired
    StaffDao staffDao;


    @Autowired
    Mapping userMapping;


    @Override
    public void save(UserDto userDto) {
       // userDto.setStaff(AppUtil.generateStaffId());
        // Convert DTO to User entity
        System.out.println("user service -> :"+userDto);
        User userEntity = userMapping.toUserEntity(userDto);
        User savedUser = userDao.save(userEntity);

        // Validate if the User entity was saved successfully
        if (savedUser == null) {
            throw new DataPersistException("Saving user failed");
        }
    }



    @Override
    public UserDetailsService userDetailService() {
        return userName ->
        userDao.findByEmail(userName)
                        .orElseThrow(()->new UserNotFoundException("User Not Found"));
    }

}
*/

import lk.ijse.greenshadow_springboot.customStatus.SelectedIdErrorStatus;
import lk.ijse.greenshadow_springboot.dao.UserDao;
import lk.ijse.greenshadow_springboot.dto.impl.UserDto;
import lk.ijse.greenshadow_springboot.entity.User;
import lk.ijse.greenshadow_springboot.exception.DataPersistException;
import lk.ijse.greenshadow_springboot.exception.UserNotFoundException;
import lk.ijse.greenshadow_springboot.util.AppUtil;
import lk.ijse.greenshadow_springboot.util.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDAO;

    @Autowired
    private Mapping mapping;


    // save user
    @Override
    public void saveUser(UserDto userDTO) {
        userDTO.setUserId(AppUtil.generateUserId());
        User savedUser = userDAO.save(mapping.toUserEntity(userDTO));
        if (savedUser == null) {
            throw new DataPersistException("User not saved");
        }
    }



    // get selected user
    @Override
    @PreAuthorize("hasRole('MANAGER') or hasRole('ADMINISTRATOR') or hasRole('SCIENTIST')")
    public UserDto getSelectedUser(String userId) {
        if(userDAO.existsById(userId)){
            User selectedUser = userDAO.getReferenceById(userId);
            return mapping.toUserDto(selectedUser);
        } else {
/*
            return new SelectedIdErrorStatus(2, "User with id " + userId + " not found");
*/
            return  null;
        }
    }

    // get all users
    @Override
    @PreAuthorize("hasRole('MANAGER') or hasRole('ADMINISTRATOR') or hasRole('SCIENTIST')")
    public List<UserDto> getAllUsers() {
        List<User> allUsers = userDAO.findAll();
        return mapping.toUserDtoList(allUsers);
    }

    // delete user
    @Override
    @PreAuthorize("hasRole('MANAGER') or hasRole('ADMINISTRATOR') or hasRole('SCIENTIST')")
    public void deleteUser(String userId) {
        Optional<User> foundUser = userDAO.findById(userId);
        if (!foundUser.isPresent()) {
            throw new UserNotFoundException("User with id " + userId + " not found");
        } else {
            userDAO.deleteById(userId);
        }
    }


    // update user
    @Override
    @PreAuthorize("hasRole('MANAGER') or hasRole('ADMINISTRATOR') or hasRole('SCIENTIST')")
    public void updateUser(String userId, UserDto updatedUserDTO) {
        Optional<User> foundUser = userDAO.findById(userId);
        if (foundUser.isPresent()) {
            foundUser.get().setEmail(updatedUserDTO.getEmail());
            foundUser.get().setPassword(updatedUserDTO.getPassword());
            foundUser.get().setRole(updatedUserDTO.getRole());
        }
    }

    @Override
    public Optional<UserDto> findByEmail(String email) {
        Optional<User> userByEmail = userDAO.findByEmail(email);
        return userByEmail.map(mapping::toUserDto);
    }

    @Override
    public UserDetailsService userDetailsService() {
        return userName ->
                userDAO.findByEmail(userName)
                        .orElseThrow(() -> new UserNotFoundException("User not found"));

    }

}











