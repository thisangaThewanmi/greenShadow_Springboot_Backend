package lk.ijse.greenshadow_springboot.controller;


import lk.ijse.greenshadow_springboot.customStatus.SelectedIdErrorStatus;
import lk.ijse.greenshadow_springboot.dto.StaffStatus;
import lk.ijse.greenshadow_springboot.dto.impl.StaffDto;
import lk.ijse.greenshadow_springboot.entity.Staff;
import lk.ijse.greenshadow_springboot.exception.DataPersistException;
import lk.ijse.greenshadow_springboot.exception.StaffNotFoundException;
import lk.ijse.greenshadow_springboot.service.StaffService;
import lk.ijse.greenshadow_springboot.util.Regex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("api/v1/staff")
public class StaffController  {

    @Autowired
    private StaffService staffService;



    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> addStaff(@RequestBody StaffDto staffDto) {

        try {


            staffService.saveStaff(staffDto);


            //if sucessfully saved
            return new ResponseEntity<>(HttpStatus.CREATED);

            //catching the error which comes from service layer
        } catch (DataPersistException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

            //if there is an error which dosent occour from service
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/{staffID}",produces = MediaType.APPLICATION_JSON_VALUE)
    public StaffStatus getSelectedStaff(@PathVariable("staffID") String staffId){
        // Create a Regex instance for the NOTE_ID pattern
        Regex regexValidator = new Regex(Regex.PatternType.STAFF);

        // Use the Regex class to validate the noteId
        if (!regexValidator.matches(staffId)) {
            return new SelectedIdErrorStatus(1, "Staff ID is not valid");
        }

        return staffService .getStaff(staffId); // Fetch and return the note
    }




    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<StaffDto> getAllStaff() {

        return staffService.getAllStaff();
    }

    @DeleteMapping(value = "/{staffId}")
    public ResponseEntity<Object> deleteStaff(@PathVariable("staffId") String staffId) {

        Regex regexValidator = new Regex(Regex.PatternType.STAFF);

        try {
            if (!regexValidator.matches(staffId)) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

            staffService.deleteStaff(staffId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        } catch (StaffNotFoundException e) {
            e.printStackTrace();
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } catch (Exception e) {
            e.printStackTrace();
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }


        @PutMapping(value="/{staffId}")
        public ResponseEntity<Void> updateStaff(@PathVariable("staffId") String staffId,@RequestBody StaffDto staffDto) {

            System.out.println(staffDto);
            try {
                Regex regexValidator = new Regex(Regex.PatternType.STAFF);
                if (!regexValidator.matches(staffId) || staffDto == null) {
                    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                }
                staffService.updateStaff(staffId, staffDto);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }catch (StaffNotFoundException e) {
                e.printStackTrace();
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }catch (Exception e) {
                e.printStackTrace();
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

            }
        }

    }


