package lk.ijse.greenshadow_springboot.service;

import jakarta.transaction.Transactional;
import lk.ijse.greenshadow_springboot.customStatus.SelectedIdErrorStatus;
import lk.ijse.greenshadow_springboot.dao.StaffDao;
import lk.ijse.greenshadow_springboot.dao.VehicleDao;
import lk.ijse.greenshadow_springboot.dto.VehicleStatus;
import lk.ijse.greenshadow_springboot.dto.impl.VehicleDto;
import lk.ijse.greenshadow_springboot.entity.Staff;
import lk.ijse.greenshadow_springboot.entity.Vehicle;
import lk.ijse.greenshadow_springboot.exception.DataPersistException;
import lk.ijse.greenshadow_springboot.exception.NotFoundException;
import lk.ijse.greenshadow_springboot.exception.StaffNotFoundException;
import lk.ijse.greenshadow_springboot.util.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class VehicleServiceIMPL implements VehicleService {

    @Autowired
    Mapping vehicleMapping;

    @Autowired
    VehicleDao vehicleDao;

    @Autowired
    private StaffDao staffDao;

    @Override
    public void addVehicle(VehicleDto vehicleDto) {
        Vehicle savedVehicle = vehicleDao.save(vehicleMapping.toVehicleEntity(vehicleDto));
        if (vehicleDto.getStaffId() != null) {
            Staff staff = staffDao.findById(vehicleDto.getStaffId())
                    .orElseThrow(() -> new StaffNotFoundException("Staff not found with ID: " + vehicleDto.getStaffId()));
            savedVehicle.setStaff(staff);
        }

        if(savedVehicle == null) {
            //If not saved
            throw new DataPersistException("Failed to save staff data");
        }
    }

    @Override
    public void updateVehicle(String vehicleId, VehicleDto vehicleDto) {


            Optional<Vehicle> exsistedVehicle = vehicleDao.findById(vehicleId);
            if(!exsistedVehicle.isPresent()) {
                throw new NotFoundException("Vehicle id not found");

            }else{
                Vehicle vehicle = exsistedVehicle.get();
                vehicle.setVehicleId(vehicleId);
                vehicle.setCategory(vehicleDto.getCategory());
                vehicle.setStatus(vehicleDto.getStatus());
                vehicle.setFuelType(vehicleDto.getFuelType());
                vehicle.setPlateNumber(vehicleDto.getPlateNumber());
                vehicle.setRemarks(vehicleDto.getRemarks());

                if (vehicleDto.getStaffId() != null) {
                    Staff staff = staffDao.findById(vehicleDto.getStaffId())
                            .orElseThrow(() -> new StaffNotFoundException("Staff not found with ID: " + vehicleDto.getStaffId()));
                    vehicle.setStaff(staff);
                } else {
                    vehicle.setStaff(null); // Clear staff if not provided
                }
            }
    }


    @Override
    public VehicleStatus getSelectedVehicle(String vehicleId) {

        if(vehicleDao.existsById(vehicleId)){
            Vehicle vehicle = vehicleDao.getReferenceById(vehicleId);
            VehicleDto vehicleDto = vehicleMapping.toVehicleDto(vehicle);
            return vehicleDto;
        }else
        return new SelectedIdErrorStatus(1,"Vehicle id not found");
    }

    @Override
    public List<VehicleDto> getAllVehicles() {
        List<Vehicle> allVehicles = vehicleDao.findAll();
        return vehicleMapping.asVehicleDtoList(allVehicles);
    }

    @Override
    public void deleteVehicle(String vehicleId) {
        Optional<Vehicle> exsistedVehicle = vehicleDao.findById(vehicleId);
        if(!exsistedVehicle.isPresent()) {
            throw new NotFoundException("Vehicle id not found");
        }else{
            vehicleDao.deleteById(vehicleId);
        }

    }
}
