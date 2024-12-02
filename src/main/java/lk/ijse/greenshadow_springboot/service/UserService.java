package lk.ijse.greenshadow_springboot.service;

import lk.ijse.greenshadow_springboot.dto.impl.UserDto;
import lk.ijse.greenshadow_springboot.entity.User;

public interface UserService {
    void save(UserDto userDto);
}
