package com.database.util;

import com.database.dto.AlertDto;
import com.database.dto.AlertIdLevelDto;
import com.database.model.Alert;
import com.database.model.Sensor;
import com.database.model.User;
import com.database.service.SensorService;
import com.database.service.UserService;

import java.util.ArrayList;
import java.util.List;

public class AlertUtil {

    public static Alert addAlert(AlertDto alertDto) {
        User user = UserService.findUserById(alertDto.getUserId());
        Sensor sensor = SensorService.findSensorById(alertDto.getSensorId());
        if (user != null && sensor != null) {
            Alert alert = new Alert();
            alert.setUser(user);
            alert.setSensor(sensor);
            alert.setGreaterLower(alertDto.getGreaterLower());
            alert.setValue(alertDto.getValue());
            alert.setActive(true);
            return alert;
        } else {
            return null;
        }
    }

    public static Alert updateAlert(Alert alert, AlertDto alertUpdate) {
        alert.setGreaterLower(NotNullUtil.setNotNull(alert.getGreaterLower(), alertUpdate.getGreaterLower()));
        alert.setValue(NotNullUtil.setNotNull(alert.getValue(), alertUpdate.getValue()));
        alert.setActive(NotNullUtil.setNotNull(alert.getActive(), alertUpdate.getActive()));
        return alert;
    }

    public static Alert updateAlertActive(Alert alert) {
        alert.setActive(!alert.getActive());
        if (!alert.getActive()) {
            KapacitorUtils.executeCommand(KapacitorUtils.disableTaskCommand(alert.getId()));
        } else {
            KapacitorUtils.executeCommand(KapacitorUtils.enableTaskCommand(alert.getId()));
        }
        return alert;
    }

    public static AlertDto convertToDto(Alert alert) {
        AlertDto alertDto = new AlertDto();
        alertDto.setValue(alert.getValue());
        alertDto.setGreaterLower(alert.getGreaterLower());
        alertDto.setSensorId(alert.getSensor().getId());
        alertDto.setUserId(alert.getUser().getId());
        alertDto.setActive(alert.getActive());
        return alertDto;
    }

    public static AlertIdLevelDto convertToDtoId(Alert alert) {
        AlertIdLevelDto alertDto = new AlertIdLevelDto();
        alertDto.setValue(alert.getValue());
        alertDto.setGreaterLower(alert.getGreaterLower());
        alertDto.setSensorId(alert.getSensor().getId());
        alertDto.setUserId(alert.getUser().getId());
        alertDto.setId(alert.getId());
        alertDto.setActive(alert.getActive());
        return alertDto;
    }

    public static List<AlertDto> convertToDtos(List<Alert> alerts) {
        List<AlertDto> alertDtos = new ArrayList<>();
        for (Alert alert : alerts) {
            alertDtos.add(convertToDto(alert));
        }
        return alertDtos;
    }

    public static List<AlertIdLevelDto> convertToDtosId(List<Alert> alerts) {
        List<AlertIdLevelDto> alertDtos = new ArrayList<>();
        for (Alert alert : alerts) {
            alertDtos.add(convertToDtoId(alert));
        }
        return alertDtos;
    }
}
