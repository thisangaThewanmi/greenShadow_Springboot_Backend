package lk.ijse.greenshadow_springboot.service;

import lk.ijse.greenshadow_springboot.dao.FieldDao;
import lk.ijse.greenshadow_springboot.dto.FieldStatus;
import lk.ijse.greenshadow_springboot.dto.impl.FieldDto;

import java.util.List;

public interface FieldService {

    void addField(FieldDto fieldDto);
    void updateField(String fieldId, FieldDto fieldDto);
    void deleteField(String fieldId);
    FieldStatus getSelectedField(String fieldId);
    List<FieldDto> getAllFields();
}
