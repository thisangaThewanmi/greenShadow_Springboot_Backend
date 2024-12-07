package lk.ijse.greenshadow_springboot.controller;


import lk.ijse.greenshadow_springboot.customStatus.SelectedIdErrorStatus;
import lk.ijse.greenshadow_springboot.dto.FieldStatus;
import lk.ijse.greenshadow_springboot.dto.impl.FieldDto;
import lk.ijse.greenshadow_springboot.exception.DataPersistException;
import lk.ijse.greenshadow_springboot.exception.FeildNotFoundException;
import lk.ijse.greenshadow_springboot.service.FieldService;
import lk.ijse.greenshadow_springboot.util.AppUtil;
import lk.ijse.greenshadow_springboot.util.Mapping;
import lk.ijse.greenshadow_springboot.util.Regex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("api/v1/field")
@CrossOrigin(origins = "http://localhost:63342")

public class FeildController {


    @Autowired
    private FieldService fieldService;

    @Autowired
    Mapping fieldMapping;

    private static final Logger logger = LoggerFactory.getLogger(FeildController.class);


    @PostMapping(produces = MediaType.MULTIPART_FORM_DATA_VALUE, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> addField(
            /*@RequestPart("fieldId") String fieldId,*/
            @RequestPart("name") String name,
            @RequestPart("location") String location, // Parse into Point later
            @RequestPart("size") String size,
            @RequestPart("image1") MultipartFile image1,
            @RequestPart("image2") MultipartFile image2,
            @RequestPart("staffIds") String staffIds
    ){


        List<String> staffIdList = Arrays.asList(staffIds.split(","));

        // You can now use staffIdList as a List of IDs
        System.out.println("Converted Staff IDs: " + staffIdList);


        String base64Pimg1 = "";
        String base64Pimg2 = "";
        try {
            byte [] bytesImg1= image1.getBytes();
            base64Pimg1 = AppUtil.imageToBase64(bytesImg1);

            byte [] bytesImg2= image2.getBytes();
            base64Pimg2 = AppUtil.imageToBase64(bytesImg2);



            FieldDto fieldDto = new FieldDto();
            fieldDto.setName(name);
            fieldDto.setLocation(location);
            fieldDto.setSize(Double.parseDouble(size));
            fieldDto.setImage1(base64Pimg1);
            fieldDto.setImage2(base64Pimg2);
            fieldDto.setStaffIds(staffIdList);

            // Save the field entity (Service call)
            fieldDto.setFieldId(AppUtil.generateFieldId());
            fieldService.addField(fieldDto);
            logger.info("field added successfully");
            return new ResponseEntity<>(HttpStatus.CREATED);

        }catch (DataPersistException e) {
            logger.error("data persistance error"+e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }catch (Exception e) {
            logger.error(e.getMessage());
             e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @CrossOrigin(origins = "http://localhost:63342")

    @DeleteMapping(value="/{fieldId}")
    public ResponseEntity<Void> deleteField(@PathVariable String fieldId) {
            try {
            fieldService.deleteField(fieldId);
            logger.info("field deleted successfully");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (FeildNotFoundException e) {
                logger.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
                logger.error(e.getMessage());
                e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @CrossOrigin(origins = "http://localhost:63342")

    @PutMapping(value = "/{fieldId}",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> updateField(@PathVariable("fieldId") String fieldId,
            @RequestPart("name") String name,
            @RequestPart("location") String location, // Parse into Point later
            @RequestPart("size") String size,
            @RequestPart("image1") MultipartFile image1,
            @RequestPart("image2") MultipartFile image2,
            @RequestPart("staffIds") String staffIds
    ){


        try {
            List<String> staffIdList = Arrays.asList(staffIds.split(","));

            String base64Pimg1 = AppUtil.imageToBase64(image1.getBytes());
            String base64Pimg2 = AppUtil.imageToBase64(image2.getBytes());

            FieldDto fieldDto = new FieldDto();
            fieldDto.setFieldId(fieldId);
            fieldDto.setName(name);
            fieldDto.setLocation(location);
            fieldDto.setSize(Double.parseDouble(size));
            fieldDto.setImage1(base64Pimg1);
            fieldDto.setImage2(base64Pimg2);
            fieldDto.setStaffIds(staffIdList);

            fieldService.updateField(fieldId, fieldDto);
            logger.info("field updated successfully");
            return new ResponseEntity<>(HttpStatus.OK);

        } catch (FeildNotFoundException e) {
            logger.error("Id not found"+e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @CrossOrigin(origins = "http://localhost:63342")

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<FieldDto>> getAllFields() {
        try {
            List<FieldDto> fields = fieldService.getAllFields();
            logger.info("getAllFields successfully");
            return new ResponseEntity<>(fields, HttpStatus.OK);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @CrossOrigin(origins = "http://localhost:63342")

    @GetMapping(value = "/{fieldId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public FieldStatus getField(@PathVariable("fieldId") String fieldId) {

                return fieldService.getSelectedField(fieldId);
            }
    }








