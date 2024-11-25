package lk.ijse.greenshadow_springboot.service;

import lk.ijse.greenshadow_springboot.dto.EquipmentStatus;
import lk.ijse.greenshadow_springboot.dto.LogStatus;
import lk.ijse.greenshadow_springboot.dto.impl.EquipmentDto;
import lk.ijse.greenshadow_springboot.dto.impl.LogDto;

import java.util.List;

public interface LogService {

    void saveLog(LogDto logDto);
    void deleteLog(String logId);
    void updateLog(String logId,LogDto logDto);
    List<LogDto> getAllLogs();
    LogStatus getSelectedLog(String logId);

}
