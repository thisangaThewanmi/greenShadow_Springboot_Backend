package lk.ijse.greenshadow_springboot.dto.impl;

import lk.ijse.greenshadow_springboot.dto.FieldStatus;
import lk.ijse.greenshadow_springboot.entity.Staff;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.geo.Point;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class FieldDto implements FieldStatus {
    private String fieldId;
    private String name;
    private String location;
    private Double size;
    private String image1;
    private String image2;
    private List<String> staffIds;

}
