package lk.ijse.greenshadow_springboot.controller;


import lk.ijse.greenshadow_springboot.dto.StaffStatus;
import lk.ijse.greenshadow_springboot.dto.impl.StaffDto;
import lk.ijse.greenshadow_springboot.exception.DataPersistException;
import lk.ijse.greenshadow_springboot.exception.StaffNotFoundException;
import lk.ijse.greenshadow_springboot.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/staff")
public class StaffController  {

    @Autowired
    private StaffService staffService;



    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> addStaff(@RequestBody StaffDto staffDto) {

        try {
            staffService.saveStaff(staffDto);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (DataPersistException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

   /* @GetMapping(value ="/{staffId}" ,MediaType.APPLICATION_JSON_VALUE)
    public StaffStatus getStaffById(@PathVariable("staffId") String staffId) {
        return staffService.getStaff(staffId);
    }*/


    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<StaffDto> getAllStaff() {
        return staffService.getAllStaff();
    }

    @DeleteMapping(value = "/{staffId}")
    public ResponseEntity<Object> deleteStaff(@PathVariable("staffId") String staffId) {

        try {
            staffService.deleteStaff(staffId);
        } catch (StaffNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
