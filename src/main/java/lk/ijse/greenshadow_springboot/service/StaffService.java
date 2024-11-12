package lk.ijse.greenshadow_springboot.service;

import lk.ijse.greenshadow_springboot.dto.StaffStatus;
import lk.ijse.greenshadow_springboot.dto.impl.StaffDto;

import java.util.List;

public interface StaffService {
    void saveStaff(StaffDto staffDto);
    List<StaffDto> getAllStaff();
    StaffStatus getStaff(String staffId);
    void deleteStaff(String staffId);
    void updateStaff(String staffId, StaffDto staffDto);
}
