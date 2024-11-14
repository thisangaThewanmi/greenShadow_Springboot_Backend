package lk.ijse.greenshadow_springboot.controller;


import lk.ijse.greenshadow_springboot.customStatus.SelectedIdErrorStatus;
import lk.ijse.greenshadow_springboot.dto.EquipmentStatus;
import lk.ijse.greenshadow_springboot.dto.impl.EquipmentDto;
import lk.ijse.greenshadow_springboot.exception.DataPersistException;
import lk.ijse.greenshadow_springboot.exception.EquipmentNotFoundException;
import lk.ijse.greenshadow_springboot.service.EquipmentService;
import lk.ijse.greenshadow_springboot.util.Regex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/equipment")
public class EquipmentController {

    @Autowired
    private EquipmentService equipmentService;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> addEquipmnet(@RequestBody EquipmentDto equipmentDto) {
        try {
            equipmentService.saveEquipment(equipmentDto);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (DataPersistException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping(value = "/{equipmentId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public EquipmentStatus getEquipment(@PathVariable("equipmentId") String equipmentId) {

        Regex regexValidator = new Regex(Regex.PatternType.EQUIPMENT);

        if (regexValidator.matches(equipmentId)) {
            return equipmentService.getSelectedEquipment(equipmentId);
        } else {
            return new SelectedIdErrorStatus(1, "Equipment ID is not valid");
        }
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<EquipmentDto> getAllEquipment() {
        return equipmentService.getAllEquipment();
    }


    @DeleteMapping(value = "/{euipmentId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> deleteEquipment(@PathVariable("euipmentId") String equipmentId) {

        try {

            Regex regexValidator = new Regex(Regex.PatternType.EQUIPMENT);

            if (regexValidator.matches(equipmentId)) {

                equipmentService.deleteEquipment(equipmentId);
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PutMapping(value = "/{equipmentId}")
    public ResponseEntity<Void> updateEquipment(@PathVariable("equipmentId") String equipmentId, @RequestBody EquipmentDto equipmentDto) {
        try {
            Regex regexValidator = new Regex(Regex.PatternType.EQUIPMENT);
            if (!regexValidator.matches(equipmentId)) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

            equipmentService.updateEquipment(equipmentId, equipmentDto);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (EquipmentNotFoundException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
