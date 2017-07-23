package com;

import com.database.model.Temperature;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
public class SzopRestController {

    private Temperature temperature = new Temperature();

    @RequestMapping(value = "/sensors", method = RequestMethod.POST)
    ResponseEntity<?> sendSensorsData(@RequestBody Map<String, Object> sensorsData) {
        return ResponseEntity.ok().build();
    }

    @RequestMapping(value = "/sensors/{system_id}", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getSensorsBySystemId(@PathVariable int systemId) {
        Map<String, Object> data = new HashMap<>();
        return data;
    }

    // TODO remove below methods, they were used for testing connection with raspberry pi
    @RequestMapping(value = "/temp")
    public Map<String, Object> temp() {
        Map<String, Object> temp = new HashMap<>();
        temp.put("id", UUID.randomUUID().toString());
        temp.put("content", temperature.getTemperature());
        return temp;
    }

    @RequestMapping(value = "/data", method = RequestMethod.POST)
    ResponseEntity<?> add(@RequestBody Map<String, Float> temp) {
        if (temp != null) {
            temperature.setTemperature(temp.get("temp"));
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.noContent().build();
    }
}
