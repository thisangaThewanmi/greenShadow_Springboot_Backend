package lk.ijse.greenshadow_springboot.service;

/*
import lk.ijse.greenshadow_springboot.dto.impl.UserDto;
import lk.ijse.greenshadow_springboot.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService {
    void save(UserDto userDto);

    UserDetailsService userDetailService();
}
*/


import lk.ijse.greenshadow_springboot.dto.impl.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Optional;

public interface UserService {

    void saveUser(UserDto userDTO);
    UserDto getSelectedUser(String userId);
    List<UserDto> getAllUsers();
    void deleteUser(String userId);
    void updateUser(String userId, UserDto updatedUserDTO);

    Optional<UserDto> findByEmail(String email);
    UserDetailsService userDetailsService(); // can fetch a user easily from the database
}





