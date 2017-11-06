package com.database.util;

import com.database.model.TemperatureData;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TemperatureDataUtil {

    public static TemperatureData convertToDto(Map<String, Object> data) {
        TemperatureData tempData = new TemperatureData();
        tempData.setValue((float) data.get("value"));
        tempData.setDate((long) data.get("date"));
        tempData.setSensorId((String) data.get("id"));
        tempData.setType((String) data.get("type"));
        return tempData;
    }

    public static List<TemperatureData> convertToDtos(List<Map<String, Object>> data) {
        List<TemperatureData> temperaturesData = new ArrayList<>();
        for (Map<String, Object> d: data) {
            temperaturesData.add(convertToDto(d));
        }
        return temperaturesData;
    }
}
