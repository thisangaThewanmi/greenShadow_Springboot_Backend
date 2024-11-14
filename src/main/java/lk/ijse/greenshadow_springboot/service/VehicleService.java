package lk.ijse.greenshadow_springboot.service;

import lk.ijse.greenshadow_springboot.dto.VehicleStatus;
import lk.ijse.greenshadow_springboot.dto.impl.VehicleDto;
import lk.ijse.greenshadow_springboot.entity.Vehicle;

import java.util.List;

public interface VehicleService {
    void addVehicle(VehicleDto vehicleDto);
    void updateVehicle(String vehicleId, VehicleDto vehicleDto);
    VehicleStatus getSelectedVehicle(String vehicleId);
    List<VehicleDto> getAllVehicles();
    void deleteVehicle(String vehicleId);

}
