package lk.ijse.greenshadow_springboot.dto.impl;

import lk.ijse.greenshadow_springboot.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class StaffDto{
    private String staffId;
    private String address;
    private String contact;
    private Date dob;
    private String email;
    private String firstName;
    private Date joinDate;
    private String lastName;
    private Role role;  // Could also be an Enum

    // Getters and Setters
}
