package com;

import com.model.Temperature;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@SpringBootApplication
@RestController
public class SzopRestController {

    private Temperature temperature = new Temperature();

    @RequestMapping(value="/temp")
    public Map<String,Object> temp() {
        Map<String,Object> temp = new HashMap<>();
        temp.put("id", UUID.randomUUID().toString());
        temp.put("content", temperature.getTemperature());
        return temp;
    }

    @RequestMapping(value="/data", method = RequestMethod.POST)
    ResponseEntity<?> add(@RequestBody Float temp) {
        if(temp != null){
            temperature.setTemperature(temp);
            return ResponseEntity.ok().build();
        }
        return  ResponseEntity.noContent().build();
    }
}
