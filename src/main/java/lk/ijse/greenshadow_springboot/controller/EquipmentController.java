package lk.ijse.greenshadow_springboot.controller;


import lk.ijse.greenshadow_springboot.customStatus.SelectedIdErrorStatus;
import lk.ijse.greenshadow_springboot.dto.EquipmentStatus;
import lk.ijse.greenshadow_springboot.dto.impl.EquipmentDto;
import lk.ijse.greenshadow_springboot.exception.DataPersistException;
import lk.ijse.greenshadow_springboot.exception.EquipmentNotFoundException;
import lk.ijse.greenshadow_springboot.exception.VehicleNotFoundException;
import lk.ijse.greenshadow_springboot.service.EquipmentService;
import lk.ijse.greenshadow_springboot.util.AppUtil;
import lk.ijse.greenshadow_springboot.util.Regex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("api/v1/equipment")
@CrossOrigin(origins = "http://localhost:63342")
public class EquipmentController {

    @Autowired
    private EquipmentService equipmentService;

    private static final Logger logger = LoggerFactory.getLogger(EquipmentController.class);


    @CrossOrigin(origins = "http://localhost:63342")
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HashMap> addEquipmnet(@RequestBody EquipmentDto equipmentDto) {
        try {
            equipmentDto.setEquipmentId(AppUtil.generateEquipmentId());
            logger.info("equipment id assigned");
            equipmentService.saveEquipment(equipmentDto);
            logger.info("equipment saved");
            return new ResponseEntity<>(new HashMap<String, String>() {{
                put("message", "Equipment was added successfully");}}, HttpStatus.CREATED);
        } catch (DataPersistException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
            return new ResponseEntity<>(new HashMap<String, String>() {{
                put("message", "Error occoured while saving the equipment");}}, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();

            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @CrossOrigin(origins = "http://localhost:63342")
    @GetMapping(value = "/{equipmentId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public EquipmentStatus getEquipment(@PathVariable("equipmentId") String equipmentId) {


            return equipmentService.getSelectedEquipment(equipmentId);

    }

    @CrossOrigin(origins = "http://localhost:63342")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<EquipmentDto> getAllEquipment() {

        return equipmentService.getAllEquipment();
    }


    @CrossOrigin(origins = "http://localhost:63342")
    @DeleteMapping(value = "/{equipmentId}")
    public ResponseEntity<HashMap> deleteEquipment(@PathVariable("equipmentId") String equipmentId) {

        try {equipmentService.deleteEquipment(equipmentId);
            logger.info("equipment deleted successfully");
            return new ResponseEntity<>(new HashMap<String, String>() {{
                put("message", "Equipment deleted successfully");}}, HttpStatus.NO_CONTENT);

        } catch (EquipmentNotFoundException e) {
            logger.error("Id was not found");
            return new ResponseEntity<>(new HashMap<String, String>() {{
                put("message", "Equipment was not found");}}, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }


    @CrossOrigin(origins = "http://localhost:63342")
    @PutMapping(value = "/{equipmentId}")
    public ResponseEntity<HashMap> updateEquipment(@PathVariable("equipmentId") String equipmentId, @RequestBody EquipmentDto equipmentDto) {
        try {

            System.out.println(equipmentDto.getEquipmentId()+"DTO EKA ATHULE TIYENA ID EKA");
            equipmentService.updateEquipment(equipmentId, equipmentDto);
            logger.info("equipment updated successfully");
            return new ResponseEntity<>(new HashMap<String, String>() {{
                put("message", "Equipment updated successfully");}}, HttpStatus.OK);
        } catch (EquipmentNotFoundException e) {
            logger.error("EquipmentId was not found to update");
            e.printStackTrace();
            return new ResponseEntity<>(new HashMap<String, String>() {{
                put("message", "ERROR occoured while updating");}}, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
