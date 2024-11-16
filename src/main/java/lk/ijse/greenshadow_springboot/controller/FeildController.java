package lk.ijse.greenshadow_springboot.controller;


import lk.ijse.greenshadow_springboot.annotation.RequestPartContentType;
import lk.ijse.greenshadow_springboot.dto.impl.FieldDto;
import lk.ijse.greenshadow_springboot.entity.Field;
import lk.ijse.greenshadow_springboot.exception.DataPersistException;
import lk.ijse.greenshadow_springboot.service.FieldService;
import lk.ijse.greenshadow_springboot.util.AppUtil;
import lk.ijse.greenshadow_springboot.util.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Point;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("api/v1/field")
public class FeildController {


    @Autowired
    private FieldService fieldService;

    @Autowired
    Mapping fieldMapping;

    @PostMapping(produces = MediaType.MULTIPART_FORM_DATA_VALUE, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> addField(
            @RequestPart("fieldId") String fieldId,
            @RequestPart("name") String name,
            @RequestPart("location") String location, // Parse into Point later
            @RequestPart("size") String size,
            @RequestPart("image1") MultipartFile image1,
            @RequestPart("image2") MultipartFile image2,
            @RequestPart("staffIds") List<String> staffIds
    ){


        String base64Pimg1 = "";
        String base64Pimg2 = "";
        try {
            byte [] bytesImg1= image1.getBytes();
            base64Pimg1 = AppUtil.imageToBase64(bytesImg1);

            byte [] bytesImg2= image2.getBytes();
            base64Pimg2 = AppUtil.imageToBase64(bytesImg2);



            FieldDto fieldDto = new FieldDto();
            fieldDto.setFieldId(fieldId);
            fieldDto.setName(name);
            fieldDto.setLocation(location);
            fieldDto.setSize(Double.parseDouble(size));
            fieldDto.setImage1(base64Pimg1);
            fieldDto.setImage2(base64Pimg2);
            fieldDto.setStaffIds(staffIds);

            // Save the field entity (Service call)
            fieldService.addField(fieldDto);
            return new ResponseEntity<>(HttpStatus.CREATED);

        }catch (DataPersistException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
