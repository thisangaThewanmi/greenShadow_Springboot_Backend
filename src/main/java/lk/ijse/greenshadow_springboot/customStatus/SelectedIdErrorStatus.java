package lk.ijse.greenshadow_springboot.customStatus;

import lk.ijse.greenshadow_springboot.dto.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SelectedIdErrorStatus extends Throwable implements StaffStatus, EquipmentStatus, VehicleStatus, FieldStatus, CropStatus,LogStatus {
    private int status;
    private String statusMessage;
}
