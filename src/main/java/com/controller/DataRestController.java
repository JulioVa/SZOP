package com.controller;

import com.model.Temperature;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/data")
public class DataRestController {

    private Temperature temperature;

    @RequestMapping(method = RequestMethod.POST)
    ResponseEntity<?> add(@RequestBody Float temp) {

        if(temp != null){
            temperature.setTemperature(temp);
            return ResponseEntity.ok().build();
        }
        return  ResponseEntity.noContent().build();
    }

}
