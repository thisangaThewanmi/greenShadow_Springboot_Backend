package lk.ijse.greenshadow_springboot.dto.impl;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class UserDto {

    private String email;
    private String password; // Use cautiously; you might not want to expose this directly.
    private String role;     // Enum can be represented as a string in the DTO.

    private String staff;
}
