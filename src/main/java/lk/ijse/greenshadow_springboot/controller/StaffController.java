package lk.ijse.greenshadow_springboot.controller;


import lk.ijse.greenshadow_springboot.customStatus.SelectedIdErrorStatus;
import lk.ijse.greenshadow_springboot.dto.StaffStatus;
import lk.ijse.greenshadow_springboot.dto.impl.StaffDto;
import lk.ijse.greenshadow_springboot.entity.Staff;
import lk.ijse.greenshadow_springboot.exception.DataPersistException;
import lk.ijse.greenshadow_springboot.exception.StaffNotFoundException;
import lk.ijse.greenshadow_springboot.service.StaffService;
import lk.ijse.greenshadow_springboot.util.AppUtil;
import lk.ijse.greenshadow_springboot.util.Regex;
import org.hibernate.tool.schema.internal.exec.ScriptTargetOutputToFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("api/v1/staff")
@CrossOrigin(origins = "http://localhost:63342")
public class StaffController  {

    @Autowired
    private StaffService staffService;


    @CrossOrigin(origins = "http://localhost:63342")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HashMap> addStaff(@RequestBody StaffDto staffDto) {
        System.out.println("staffDto comes to: " + staffDto);

        try {

            staffDto.setStaffId(AppUtil.generateStaffId());
            staffService.saveStaff(staffDto);


            //if sucessfully saved
           /* return new ResponseEntity<>(HttpStatus.CREATED);*/

            return new ResponseEntity<>(new HashMap<String, String>() {{
                put("message", "Staff added successfully");}}, HttpStatus.CREATED);


            //catching the error which comes from service layer
        } catch (DataPersistException e) {
            e.printStackTrace();
            return new ResponseEntity<>(new HashMap<String, String>() {{
                put("message", "Error occured while saving");}}, HttpStatus.BAD_REQUEST);


            //if there is an error which dosent occour from service
        } catch (Exception e) {
            return new ResponseEntity<>(new HashMap<String, String>() {{
                put("message", "Change the email");}}, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @CrossOrigin(origins = "http://localhost:63342")
    @GetMapping(value = "/{staffID}",produces = MediaType.APPLICATION_JSON_VALUE)
    public StaffStatus getSelectedStaff(@PathVariable("staffID") String staffId){


        return staffService .getStaff(staffId); // Fetch and return the note
    }



    @CrossOrigin(origins = "http://localhost:63342")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<StaffDto> getAllStaff() {

        return staffService.getAllStaff();
    }


    @CrossOrigin(origins = "http://localhost:63342")
    @DeleteMapping(value = "/{staffId}")
    public ResponseEntity<Object> deleteStaff(@PathVariable("staffId") String staffId) {

        Regex regexValidator = new Regex(Regex.PatternType.STAFF);

        try {

            staffService.deleteStaff(staffId);
            return new ResponseEntity<>(new HashMap<String, String>() {{
                put("message", "Staff deleted successfully");}}, HttpStatus.NO_CONTENT);


        } catch (StaffNotFoundException e) {
            e.printStackTrace();
            return new ResponseEntity<>(new HashMap<String, String>() {{
                put("message", "Staff id was not found");}}, HttpStatus.NOT_FOUND);

        } catch (Exception e) {
            e.printStackTrace();
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }



         @CrossOrigin(origins = "http://localhost:63342")
        @PutMapping(value="/{staffId}")
        public ResponseEntity<HashMap> updateStaff(@PathVariable("staffId") String staffId,@RequestBody StaffDto staffDto) {

            System.out.println(staffDto);
            try {
                staffService.updateStaff(staffId, staffDto);
                return new ResponseEntity<>(new HashMap<String, String>() {{
                    put("message", "Staff updated Sucessfully");}}, HttpStatus.NO_CONTENT);

            }catch (StaffNotFoundException e) {
                e.printStackTrace();
                return new ResponseEntity<>(new HashMap<String, String>() {{
                    put("message", "Staff was not found");}}, HttpStatus.BAD_REQUEST);

            }catch (Exception e) {
                e.printStackTrace();
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

            }
        }

    }


