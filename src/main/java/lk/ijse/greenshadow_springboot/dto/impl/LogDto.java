package lk.ijse.greenshadow_springboot.dto.impl;

import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import lk.ijse.greenshadow_springboot.entity.Crop;
import lk.ijse.greenshadow_springboot.entity.Field;
import lk.ijse.greenshadow_springboot.entity.Staff;

import java.sql.Date;
import java.util.List;

public class LogDto {
    private String logId;
    private String logDetails;
    private Date date;
    private String image2;
    private List<Staff> staffIds ;
    private List<Field> fieldIds;
    private List<Crop> cropIds;

}

