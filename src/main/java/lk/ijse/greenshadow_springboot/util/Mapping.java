package lk.ijse.greenshadow_springboot.util;


import lk.ijse.greenshadow_springboot.dto.impl.StaffDto;
import lk.ijse.greenshadow_springboot.entity.Staff;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Mapping {

    @Autowired
    private ModelMapper modelMapper;
    // for staff mapping
    public Staff toStaffEntity(StaffDto staffDto ) {

        return modelMapper.map(staffDto, Staff.class);
    }

    public StaffDto toStaffDto(Staff staff) {
        return modelMapper.map(staff, StaffDto.class);
    }
    public List<StaffDto> asStaffDtoList(List<Staff> staff) {
        return modelMapper.map(staff, new TypeToken<List<StaffDto>>(){}.getType());
    }

}
