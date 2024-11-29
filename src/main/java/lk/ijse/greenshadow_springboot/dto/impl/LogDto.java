package lk.ijse.greenshadow_springboot.dto.impl;

import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import lk.ijse.greenshadow_springboot.dto.LogStatus;
import lk.ijse.greenshadow_springboot.entity.Crop;
import lk.ijse.greenshadow_springboot.entity.Field;
import lk.ijse.greenshadow_springboot.entity.Staff;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Data

public class LogDto implements LogStatus {
    private String logId;
    private String logDetails;
    private Date date;
    private String image2;
    private List<String > staffIds ;
    private List<String> fieldIds;
    private List<String> cropIds;

}

