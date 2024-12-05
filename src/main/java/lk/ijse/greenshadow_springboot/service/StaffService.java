package lk.ijse.greenshadow_springboot.service;

import lk.ijse.greenshadow_springboot.dto.StaffStatus;
import lk.ijse.greenshadow_springboot.dto.impl.StaffDto;

import java.util.List;
import java.util.Optional;

public interface StaffService {
    StaffDto saveStaff(StaffDto staffDto);
    List<StaffDto> getAllStaff();
    StaffStatus getStaff(String staffId);
    void deleteStaff(String staffId);
    void updateStaff(String staffId, StaffDto staffDto);
    Optional findByEmail(String email);

    /*--------new--------*/

}
