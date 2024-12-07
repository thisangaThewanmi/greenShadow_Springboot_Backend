package lk.ijse.greenshadow_springboot.controller;


import lk.ijse.greenshadow_springboot.customStatus.SelectedIdErrorStatus;
import lk.ijse.greenshadow_springboot.dto.FieldStatus;
import lk.ijse.greenshadow_springboot.dto.LogStatus;
import lk.ijse.greenshadow_springboot.dto.impl.FieldDto;
import lk.ijse.greenshadow_springboot.dto.impl.LogDto;
import lk.ijse.greenshadow_springboot.exception.DataPersistException;
import lk.ijse.greenshadow_springboot.exception.FeildNotFoundException;
import lk.ijse.greenshadow_springboot.exception.LogNotFoundException;
import lk.ijse.greenshadow_springboot.service.FieldServiceIMPL;
import lk.ijse.greenshadow_springboot.service.LogService;
import lk.ijse.greenshadow_springboot.util.AppUtil;
import lk.ijse.greenshadow_springboot.util.Mapping;
import lk.ijse.greenshadow_springboot.util.Regex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.security.auth.login.LoginContext;
import java.sql.Date;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("api/v1/log")
public class LogController {

    @Autowired
    private LogService logService;

    @Autowired
    Mapping logMapping;



    @PostMapping(produces = MediaType.MULTIPART_FORM_DATA_VALUE,consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> saveLog(
            @RequestPart ("logDetails")String logDetails,
            @RequestPart("date") String date,
            @RequestPart("image2")String image,
            @RequestPart("staffIds") String staffIds,
            @RequestPart("fieldIds")String fieldIds,
            @RequestPart("cropIds")String cropIds
            ) {

        List<String> staffIdList = Arrays.asList(staffIds.split(","));
        List<String> fieldIdList = Arrays.asList(fieldIds.split(","));
        List<String> cropIdList = Arrays.asList(cropIds.split(","));

        String base64Img = "";


        try {
            if (image != null && !image.isEmpty()) {
                byte[] bytesImg1 = image.getBytes();
                base64Img = AppUtil.imageToBase64(bytesImg1);
            } else {
                base64Img = null;
            }


            LogDto logDto = new LogDto();
            logDto.setLogId(AppUtil.generateLogId());
            logDto.setLogDetails(logDetails);
            logDto.setDate(Date.valueOf(date));
            logDto.setImage2(base64Img);
            logDto.setStaffIds(staffIdList);
            logDto.setFieldIds(fieldIdList);
            logDto.setCropIds(cropIdList);

            logService.saveLog(logDto);
            return new ResponseEntity<>(HttpStatus.CREATED);

        } catch (DataPersistException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }




    @DeleteMapping(value="/{logId}")
    public ResponseEntity<Void> deleteLog(@PathVariable String logId) {
        try {
            logService.deleteLog(logId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (LogNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/{logId}",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> updateLog(@RequestPart("logId") String logId,
                                          @RequestPart ("logDetails")String logDetails,
                                          @RequestPart("date") String date,
                                          @RequestPart("image2")String image,
                                          @RequestPart("staffIds") String staffIds,
                                          @RequestPart("fieldIds")String fieldIds,
                                          @RequestPart("cropIds")String cropIds
    ){


        try {

            Regex regexValidator = new Regex(Regex.PatternType.LOG);
            if (!regexValidator.matches(logId)) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            List<String> staffIdList = Arrays.asList(staffIds.split(","));
            List<String> fieldIdList = Arrays.asList(fieldIds.split(","));
            List<String> cropIdList = Arrays.asList(cropIds.split(","));

            String base64Img="";

            if (image != null && !image.isEmpty()) {
                byte[] bytesImg1 = image.getBytes();
                base64Img = AppUtil.imageToBase64(bytesImg1);
            } else {
                base64Img = null;
            }

            LogDto logDto = new LogDto();
            logDto.setLogId(logId);
            logDto.setLogDetails(logDetails);
            logDto.setDate(Date.valueOf(date));
            logDto.setImage2(base64Img);
            logDto.setStaffIds(staffIdList);
            logDto.setFieldIds(fieldIdList);
            logDto.setCropIds(cropIdList);

            logService.updateLog(logId, logDto);
            return new ResponseEntity<>(HttpStatus.OK);

        } catch (FeildNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<LogDto>> getAllLogs() {
        try {
            List<LogDto> logs = logService.getAllLogs();
            return new ResponseEntity<>(logs, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping(value = "/{logId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public LogStatus getLog(@PathVariable("logId") String logId) {

            return logService.getSelectedLog(logId);
        }
    }



