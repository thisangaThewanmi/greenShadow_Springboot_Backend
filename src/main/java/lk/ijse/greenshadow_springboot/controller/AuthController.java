package lk.ijse.greenshadow_springboot.controller;




import lk.ijse.greenshadow_springboot.Secure.JwtAuthResponse;
import lk.ijse.greenshadow_springboot.Secure.SignIn;
import lk.ijse.greenshadow_springboot.dto.impl.StaffDto;
import lk.ijse.greenshadow_springboot.dto.impl.UserDto;
import lk.ijse.greenshadow_springboot.entity.Role;
import lk.ijse.greenshadow_springboot.service.AuthService;
import lk.ijse.greenshadow_springboot.service.StaffService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:63342")
public class AuthController {

    private final StaffService staffService;
    private final AuthService authService;

    // user register
    @CrossOrigin(origins = "http://localhost:63342")
    @PostMapping(value = "signup", consumes = MediaType.APPLICATION_JSON_VALUE)
    //@PreAuthorize("hasRole('MANAGER') or hasRole('ADMINISTRATOR') or hasRole('SCIENTIST')")
    public ResponseEntity<JwtAuthResponse> createUser(@RequestBody UserDto userDTO) {
        try {

            // Check if a staff member exists with the given email
            Optional<StaffDto> existingStaff = staffService.findByEmail(userDTO.getEmail());
            System.out.println("exsistingStaff: " + existingStaff.isPresent()+existingStaff);

            if (!existingStaff.isPresent()) {

                // Save new staff member if none exists
                StaffDto newStaffDto = new StaffDto();

                newStaffDto.setStaffEmail(userDTO.getEmail());
                newStaffDto.setRole(userDTO.getRole());

                // get saved staff dto
                StaffDto staffDTO = staffService.saveStaff(newStaffDto);

                // Set the saved staff ID to the user DTO
                userDTO.setStaff(staffDTO.getStaffId());
            } else {
                // Link to the existing staff member
                userDTO.setStaff(existingStaff.get().getStaffId());
            }

            // Save the user
            return ResponseEntity.ok(authService.signUp(userDTO)); // don't pass direct to the database.Pass to auth service

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // log in user
    @CrossOrigin(origins = "http://localhost:63342")
    @PostMapping(value = "signIn", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<JwtAuthResponse> signIn(@RequestBody SignIn signIn){
        return ResponseEntity.ok(authService.signIn(signIn));
    }

    // refresh token
    @CrossOrigin(origins = "http://localhost:63342")
    @PostMapping("refresh")
    public ResponseEntity<JwtAuthResponse> refreshToken(@RequestParam("existingToken") String existingToken) {
        return ResponseEntity.ok(authService.refreshToken(existingToken));
    }

}


























