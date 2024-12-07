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
@RequestMapping("api/v1/crop")
@CrossOrigin(origins = "http://localhost:63342")

public class CropController {

    private static final Logger logger = LoggerFactory.getLogger(CropController.class);


    @Autowired
    private CropService cropService;

    @Autowired
    Mapping cropMapping;

    @PostMapping(produces = MediaType.MULTIPART_FORM_DATA_VALUE, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> addCrop(

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
            logger.info("generated the cropId: " + cropDto.getCropId());
            cropService.saveCrop(cropDto);
            logger.info("save the crop");
            return new ResponseEntity<>(HttpStatus.CREATED);

        }catch (DataPersistException e) {
            e.printStackTrace();
            logger.error("data persist exception");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }catch (Exception e) {
            e.printStackTrace();
            logger.error("500 exception "+e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @CrossOrigin(origins = "http://localhost:63342")

    @DeleteMapping(value="/{cropId}")
    public ResponseEntity<Void> deleteCrop(@PathVariable ("cropId")  String cropId) {
        try {

            cropService.deleteCrop(cropId);
            logger.info("delete the crop");

            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        } catch (FeildNotFoundException e) {
            logger.error("crop id not found");

            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("500 exception "+e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @CrossOrigin(origins = "http://localhost:63342")

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
            logger.info("updated the crop");
            return new ResponseEntity<>(HttpStatus.OK);





        } catch (FeildNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            logger.error("500 exception "+e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @CrossOrigin(origins = "http://localhost:63342")

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CropDto>>getAllCrops() {
        try {
            List<CropDto> crops = cropService.getAllCrop();
            logger.info("returned All crops");
            return new ResponseEntity<>(crops, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("500 exception "+e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }


    @CrossOrigin(origins = "http://localhost:63342")

    @GetMapping(value = "/{cropId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CropStatus getCrop(@PathVariable("cropId") String cropId) {
            return cropService.getSelectedCrop(cropId);
    }
    }







