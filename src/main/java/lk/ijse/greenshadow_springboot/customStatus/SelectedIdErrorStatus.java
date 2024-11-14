package lk.ijse.greenshadow_springboot.customStatus;

import lk.ijse.greenshadow_springboot.dto.EquipmentStatus;
import lk.ijse.greenshadow_springboot.dto.StaffStatus;
import lk.ijse.greenshadow_springboot.dto.VehicleStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SelectedIdErrorStatus extends Throwable implements StaffStatus, EquipmentStatus, VehicleStatus {
    private int status;
    private String statusMessage;
}
