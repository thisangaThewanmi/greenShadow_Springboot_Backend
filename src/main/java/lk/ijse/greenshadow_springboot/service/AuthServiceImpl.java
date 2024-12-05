package lk.ijse.greenshadow_springboot.service;

import lk.ijse.greenshadow_springboot.Secure.JwtAuthResponse;
import lk.ijse.greenshadow_springboot.Secure.SignIn;
import lk.ijse.greenshadow_springboot.dao.StaffDao;
import lk.ijse.greenshadow_springboot.dao.UserDao;
import lk.ijse.greenshadow_springboot.dto.impl.UserDto;
import lk.ijse.greenshadow_springboot.entity.Staff;
import lk.ijse.greenshadow_springboot.entity.User;
import lk.ijse.greenshadow_springboot.exception.UserNotFoundException;
import lk.ijse.greenshadow_springboot.util.AppUtil;
import lk.ijse.greenshadow_springboot.util.Mapping;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserDao userDAO;
    private final Mapping mapping;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final StaffDao staffDao;

    // user log in
    @Override
    public JwtAuthResponse signIn(SignIn signIn) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signIn.getEmail(),signIn.getPassword())); // get email and pw when user log in
        var user = userDAO.findByEmail(signIn.getEmail())
                .orElseThrow(() -> new UserNotFoundException("User Not found"));
        var generatedToken = jwtService.generateToken(user);

        return JwtAuthResponse.builder().token(generatedToken).build();
    }

    // save user in database and issue a token
    @Override
    public JwtAuthResponse signUp(UserDto userDTO) {
        System.out.println("sign up ekata ena userDto: " + userDTO);
        /*userDTO.setUserId(AppUtil.generateUserId());
        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        // save user

        *//*mn dameeeee*//*
        Staff staff = staffDao.findById(userDTO.getStaff())
                .orElseGet(() -> staffDao.save(new Staff()));
        userDTO.setStaff((mapping.toStaffDto(staff).getStaffId()));
        *//*------------------*//*

        User savedUser = userDAO.save(mapping.toUserEntity(userDTO));
        System.out.println("saved user: " + savedUser);
        // generate a token and return it
        var token = jwtService.generateToken(savedUser);

        return JwtAuthResponse.builder().token(token).build();*/

        Staff staff = staffDao.findById(userDTO.getStaff())
                .orElseThrow(() -> new IllegalArgumentException("Staff not found for ID: " + userDTO.getStaff()));

        // Map UserDto to User entity
        User user = new User();
        user.setUserId(AppUtil.generateUserId());
        user.setEmail(userDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setRole(userDTO.getRole());
        user.setStaff(staff); // Assign the fetched Staff entity

        // Save User entity
        User savedUser = userDAO.save(user);

        // Generate a token and return it
        var token = jwtService.generateToken(savedUser);

        return JwtAuthResponse.builder().token(token).build();

    }

    // refresh token
    @Override
    public JwtAuthResponse refreshToken(String accessToken) {
        //extract username from existing token
        var userName = jwtService.extractUserName(accessToken);
        //check the user availability in the db
        var findUser=  userDAO.findByEmail(userName)
                .orElseThrow(() -> new UserNotFoundException("User Not found"));
        var refreshedToken = jwtService.refreshToken(findUser);

        return JwtAuthResponse.builder().token(refreshedToken).build();
    }
}

