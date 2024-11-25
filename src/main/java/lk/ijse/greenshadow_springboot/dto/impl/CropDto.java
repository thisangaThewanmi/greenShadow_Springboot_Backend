package lk.ijse.greenshadow_springboot.dto.impl;
import lk.ijse.greenshadow_springboot.dto.CropStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data

public class CropDto implements CropStatus {
    private String cropId;
    private String commonName;
    private String specificName;
    private String category;
    private String season;
    private String image1;
    private String fieldId;
}
