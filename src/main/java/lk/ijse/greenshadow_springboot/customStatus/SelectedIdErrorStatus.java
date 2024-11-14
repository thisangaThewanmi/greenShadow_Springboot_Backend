package lk.ijse.greenshadow_springboot.customStatus;

import lk.ijse.greenshadow_springboot.dto.StaffStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SelectedIdErrorStatus implements StaffStatus {
    private int status;
    private String statusMessage;
}
