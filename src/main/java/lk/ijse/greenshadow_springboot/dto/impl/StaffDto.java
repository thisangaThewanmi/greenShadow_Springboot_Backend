package lk.ijse.greenshadow_springboot.dto.impl;

import lk.ijse.greenshadow_springboot.dto.StaffStatus;
import lk.ijse.greenshadow_springboot.entity.Field;
import lk.ijse.greenshadow_springboot.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class StaffDto implements StaffStatus {

    private String staffId;
    private String firstName;
    private String lastName;
    private String designation;
    private String gender;
    private LocalDate joinedDate;
    private LocalDate dob;
    private String addressLine1;
   /* private String addressLine2;
    private String addressLine3;
    private String addressLine4;
    private String addressLine5;*/
    private String contactNo;
    private String staffEmail;
    private Role role;

}
