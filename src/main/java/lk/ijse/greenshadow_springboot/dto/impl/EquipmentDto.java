package lk.ijse.greenshadow_springboot.dto.impl;


import lk.ijse.greenshadow_springboot.dto.EquipmentStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class EquipmentDto  implements EquipmentStatus {
    private String equipmentId;
    private String type;
    private String name;
    private String status;
    private String fieldId;
    private String staffId;
}
