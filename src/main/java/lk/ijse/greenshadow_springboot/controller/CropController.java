package lk.ijse.greenshadow_springboot.controller;


import lk.ijse.greenshadow_springboot.customStatus.SelectedIdErrorStatus;
import lk.ijse.greenshadow_springboot.dto.CropStatus;
import lk.ijse.greenshadow_springboot.dto.FieldStatus;
import lk.ijse.greenshadow_springboot.dto.impl.CropDto;
import lk.ijse.greenshadow_springboot.dto.impl.FieldDto;
import lk.ijse.greenshadow_springboot.exception.DataPersistException;
import lk.ijse.greenshadow_springboot.exception.FeildNotFoundException;
import lk.ijse.greenshadow_springboot.service.CropService;
import lk.ijse.greenshadow_springboot.service.FieldService;
import lk.ijse.greenshadow_springboot.util.AppUtil;
import lk.ijse.greenshadow_springboot.util.Mapping;
import lk.ijse.greenshadow_springboot.util.Regex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("api/v1/crop")
public class CropController {


    @Autowired
    private CropService cropService;

    @Autowired
    Mapping cropMapping;

    @PostMapping(produces = MediaType.MULTIPART_FORM_DATA_VALUE, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> addCrop(
            /*@RequestPart("cropId") String cropId,*/
            @RequestPart("commonName") String commonName,
            @RequestPart("specificName") String specificName,
            @RequestPart("category") String category,
            @RequestPart("season") String season,
            @RequestPart("image1") MultipartFile image1,
            @RequestPart("fieldId") String fieldId
    ){

        System.out.println("cropControler");

        String cropImg = "";

        try {
            byte [] bytesImg1= image1.getBytes();
            cropImg = AppUtil.imageToBase64(bytesImg1);


            CropDto cropDto = new CropDto();
            cropDto.setCommonName(commonName);
            cropDto.setSpecificName(specificName);
            cropDto.setCategory(category);
            cropDto.setSeason(season);
            cropDto.setImage1(cropImg);
            cropDto.setFieldId(fieldId);

            // Save the crop entity
            cropDto.setCropId(AppUtil.generateCropId());
            cropService.saveCrop(cropDto);
            return new ResponseEntity<>(HttpStatus.CREATED);

        }catch (DataPersistException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value="/{cropId}")
    public ResponseEntity<Void> deleteCrop(@PathVariable ("cropId")  String cropId) {
        try {

            Regex regexChecker = new Regex(Regex.PatternType.CROP);
            if (!regexChecker.matches(cropId)) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            cropService.deleteCrop(cropId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (FeildNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/{cropId}",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> updateCrop(@PathVariable("cropId") String cropId,
            @RequestPart("commonName") String commonName,
            @RequestPart("specificName") String specificName,
            @RequestPart("category") String category,
            @RequestPart("season")String season,
            @RequestPart("image1") MultipartFile image1,
            @RequestPart("fieldId") String fieldId
    ){
        try {

            Regex regexValidator = new Regex(Regex.PatternType.CROP);
            if (!regexValidator.matches(cropId)) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }


            String cropImg = "";

            byte [] bytesImg1= image1.getBytes();
            cropImg = AppUtil.imageToBase64(bytesImg1);


            CropDto cropDto = new CropDto();
            cropDto.setCropId(cropId);
            cropDto.setCommonName(commonName);
            cropDto.setSpecificName(specificName);
            cropDto.setCategory(category);
            cropDto.setSeason(season);
            cropDto.setImage1(cropImg);
            cropDto.setFieldId(fieldId);

            // Save the crop entity
            cropService.updateCrop(cropDto,cropId);
            return new ResponseEntity<>(HttpStatus.OK);





        } catch (FeildNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CropDto>>getAllCrops() {
        try {
            List<CropDto> crops = cropService.getAllCrop();
            return new ResponseEntity<>(crops, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }


    @GetMapping(value = "/{cropId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CropStatus getCrop(@PathVariable("cropId") String cropId) {

        Regex regexValidator = new Regex(Regex.PatternType.CROP);
        if (!regexValidator.matches(cropId)) {
            return new SelectedIdErrorStatus(1, "Crop ID is not valid");
        } else{
            return cropService.getSelectedCrop(cropId);
        }
    }







}
