package lk.ijse.greenshadow_springboot.dto.impl;


import lk.ijse.greenshadow_springboot.dto.SuperDto;
import lk.ijse.greenshadow_springboot.dto.VehicleStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class VehicleDto implements VehicleStatus {
    private String vehicleId;
    private String plateNumber;
    private String category;
    private String fuelType;
    private String status;
    private String remarks;
    private String staffId;
}
