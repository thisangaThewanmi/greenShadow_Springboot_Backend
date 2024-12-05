package lk.ijse.greenshadow_springboot.service;

import lk.ijse.greenshadow_springboot.Secure.JwtAuthResponse;
import lk.ijse.greenshadow_springboot.Secure.SignIn;
import lk.ijse.greenshadow_springboot.dto.impl.UserDto;

public interface AuthService {
    JwtAuthResponse signIn(SignIn signIn);
    JwtAuthResponse signUp(UserDto userDTO);
    JwtAuthResponse refreshToken(String accessToken);
}
