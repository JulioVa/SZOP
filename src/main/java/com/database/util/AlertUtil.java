package com.database.util;

import com.database.dto.AlertDto;
import com.database.model.Alert;
import com.database.model.Sensor;
import com.database.model.User;
import com.database.service.SensorService;
import com.database.service.UserService;

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
            return alert;
        } else {
            return null;
        }
    }

    public static Alert updateAlert(Alert alert, AlertDto alertUpdate) {
        alert.setGreaterLower(NotNullUtil.setNotNull(alert.getGreaterLower(), alertUpdate.getGreaterLower()));
        alert.setValue(NotNullUtil.setNotNull(alert.getValue(), alertUpdate.getValue()));
        return alert;
    }
}
