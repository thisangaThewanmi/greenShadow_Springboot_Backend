package lk.ijse.greenshadow_springboot.controller;


import lk.ijse.greenshadow_springboot.customStatus.SelectedIdErrorStatus;
import lk.ijse.greenshadow_springboot.dto.VehicleStatus;
import lk.ijse.greenshadow_springboot.dto.impl.VehicleDto;
import lk.ijse.greenshadow_springboot.exception.DataPersistException;
import lk.ijse.greenshadow_springboot.exception.VehicleNotFoundException;
import lk.ijse.greenshadow_springboot.exception.StaffNotFoundException;
import lk.ijse.greenshadow_springboot.service.VehicleService;
import lk.ijse.greenshadow_springboot.util.AppUtil;
import lk.ijse.greenshadow_springboot.util.Regex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/v1/vehicle")
@CrossOrigin(origins = "http://localhost:63342")
public class VehicleController {
    @Autowired
    VehicleService vehicleService;

    private static final Logger logger = LoggerFactory.getLogger(VehicleController.class);

    @CrossOrigin(origins = "http://localhost:63342")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)

    public ResponseEntity<HashMap> saveVehicle(@RequestBody VehicleDto vehicleDto) {
        System.out.println("enter the saveVehicle method"+vehicleDto);
        try {
            vehicleDto.setVehicleId(AppUtil.generateVehicleId());
            vehicleService.addVehicle(vehicleDto);
            logger.info("vehicle added successfully");
            return new ResponseEntity<>(new HashMap<String, String>() {{
                put("message", "Vehicle added successfully");}}, HttpStatus.CREATED);
        } catch (DataPersistException e) {
            logger.error("Data persistance"+e.getMessage());
            return new ResponseEntity<>(new HashMap<String, String>() {{
                put("message", "Error occurred while saving a vehicle");}}, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            logger.error("Error occurred while saving a vehicle"+e.getMessage());
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @CrossOrigin(origins = "http://localhost:63342")
    @DeleteMapping(value = "/{vehicleId}")
    public ResponseEntity<HashMap> deleteVehicle(@PathVariable("vehicleId") String vehicleId) {

        try {vehicleService.deleteVehicle(vehicleId);
            logger.info("vehicle deleted successfully");
            return new ResponseEntity<>(new HashMap<String, String>() {{
                put("message", "Vehicle deleted successfully");}}, HttpStatus.NO_CONTENT);

        } catch (VehicleNotFoundException e) {
            logger.error("Vehicle id not found"+e.getMessage());
            return new ResponseEntity<>(new HashMap<String, String>() {{
                put("message", "VehicleId was not found");}}, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            logger.error("Error occurred while deleting a vehicle"+e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @CrossOrigin(origins = "http://localhost:63342")
    @GetMapping(value = "/{vehicleId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public VehicleStatus getVehicle(@PathVariable("vehicleId") String vehicleId) {


      return   vehicleService.getSelectedVehicle(vehicleId);
    }



    @CrossOrigin(origins = "http://localhost:63342")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<VehicleDto> getVehicles() {
        return vehicleService.getAllVehicles();
    }



    @CrossOrigin(origins = "http://localhost:63342")
    @PutMapping(value = "/{vehicleId}")
    public ResponseEntity<HashMap> updateVehicle(@PathVariable("vehicleId") String vehicleId, @RequestBody VehicleDto vehicleDto) {
        try {
            vehicleService.updateVehicle(vehicleId, vehicleDto);
            logger.info("vehicle updated successfully");
            return new ResponseEntity<>(new HashMap<String, String>() {{
                put("message", "Vehicle updated successfully");}}, HttpStatus.OK);
        } catch (StaffNotFoundException e) {
            logger.error("Staff not found"+e.getMessage());
            e.printStackTrace();
            return new ResponseEntity<>(new HashMap<String, String>() {{
                put("message", "VehicleId was not found");}}, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            logger.error("Error occurred while updating a vehicle"+e.getMessage());
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}


