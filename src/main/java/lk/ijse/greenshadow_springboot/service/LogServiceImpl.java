package lk.ijse.greenshadow_springboot.service;

import lk.ijse.greenshadow_springboot.dto.LogStatus;
import lk.ijse.greenshadow_springboot.dto.impl.LogDto;

import java.util.List;

public class LogServiceImpl implements LogService{
    @Override
    public void saveLog(LogDto logDto) {

    }

    @Override
    public void deleteLog(String logId) {

    }

    @Override
    public void updateLog(String logId, LogDto logDto) {

    }

    @Override
    public List<LogDto> getAllLogs() {
        return List.of();
    }

    @Override
    public LogStatus getSelectedLog(String logId) {
        return null;
    }
}
