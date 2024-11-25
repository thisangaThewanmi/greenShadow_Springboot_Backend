package lk.ijse.greenshadow_springboot.util;


import lk.ijse.greenshadow_springboot.dto.impl.*;
import lk.ijse.greenshadow_springboot.entity.*;
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
    public Staff toStaffEntity(StaffDto staffDto ) {return modelMapper.map(staffDto, Staff.class);}
    public StaffDto toStaffDto(Staff staff) {
        return modelMapper.map(staff, StaffDto.class);
    }
    public List<StaffDto> asStaffDtoList(List<Staff> staff) {
        return modelMapper.map(staff, new TypeToken<List<StaffDto>>(){}.getType());
    }

    //for Equipment

    public Equipment toEquipmentEntity(EquipmentDto equipmentDto ) {return modelMapper.map(equipmentDto, Equipment.class);}
    public EquipmentDto toEquipmentDto(Equipment equipment) {
        return modelMapper.map(equipment, EquipmentDto.class);
    }
    public List<EquipmentDto> asEquipmentDtoList(List<Equipment> equipments) {
        return modelMapper.map(equipments, new TypeToken<List<EquipmentDto>>(){}.getType());
    }


    //for Vehicle

    public Vehicle toVehicleEntity(VehicleDto vehicleDto ) {return modelMapper.map(vehicleDto,Vehicle.class);}
    public VehicleDto toVehicleDto(Vehicle vehicle) {
        return modelMapper.map(vehicle, VehicleDto.class);
    }
    public List<VehicleDto> asVehicleDtoList(List<Vehicle> vehicles) {
        return modelMapper.map(vehicles, new TypeToken<List<VehicleDto>>(){}.getType());
    }

    //for Field

    public Field toFieldEntity(FieldDto fieldDto) {return modelMapper.map(fieldDto,Field.class);}
    public FieldDto toFieldDto(Field field) {
        return modelMapper.map(field, FieldDto.class);
    }
    public List<FieldDto> toFieldDtoList(List<Field> fields) {
        return modelMapper.map(fields, new TypeToken<List<FieldDto>>(){}.getType());
    }


    //forCrop
    public Crop toCropEntity(CropDto cropDto) {return modelMapper.map(cropDto,Crop.class);}
    public CropDto toCropDto(Crop crop) {
        return modelMapper.map(crop, CropDto.class);
    }
    public List<CropDto> toCropDtoList(List<Crop> crops) {
        return modelMapper.map(crops, new TypeToken<List<CropDto>>(){}.getType());
    }


    //forLog

    public Log toLogEntity(LogDto logDto) {return modelMapper.map(logDto,Log.class);}
    public LogDto toLogDto(Log log) {
        return modelMapper.map(log, LogDto.class);
    }
    public List<LogDto> toLogDtoList(List<Log> logs) {
        return modelMapper.map(logs, new TypeToken<List<LogDto>>(){}.getType());
    }


}
