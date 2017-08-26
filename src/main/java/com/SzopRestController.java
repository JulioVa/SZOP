package com;

import com.database.dto.AlertDto;
import com.database.dto.SchemaDto;
import com.database.dto.SensorDto;
import com.database.dto.UserDto;
import com.database.model.*;
import com.database.service.AlertService;
import com.database.service.SchemaService;
import com.database.service.SensorService;
import com.database.service.UserService;
import com.database.util.AlertUtil;
import com.database.util.SchemaUtil;
import com.database.util.SensorUtil;
import com.database.util.UserUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
public class SzopRestController {

    private Temperature temperature = new Temperature();

    // sensors
    @RequestMapping(value = "/sensors", method = RequestMethod.POST)
    public ResponseEntity<SensorDto> createSensor(@RequestBody SensorDto sensorDto) {
        Sensor sensor = SensorUtil.addSensor(sensorDto);
        if (sensor != null) {
            SensorService.save(sensor);
            return ResponseEntity.ok().body(sensorDto);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @RequestMapping(value = "/system/{systemId}/sensors", method = RequestMethod.GET)
    public ResponseEntity<List<Sensor>> getSensorsBySystemId(@PathVariable int systemId) {
        List<Sensor> sensors = SensorService.findAllBySystem(systemId);
        if (sensors.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(sensors);
    }

    @RequestMapping(value = "/schema/{schemaId}/sensors", method = RequestMethod.GET)
    public ResponseEntity<List<Sensor>> getSensorsBySchemaId(@PathVariable int schemaId) {
        List<Sensor> sensors = SensorService.findAllBySchema(schemaId);
        if (sensors.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(sensors);
    }

    @RequestMapping(value = "/system/{systemId}/sensors/{sensorId}", method = RequestMethod.PUT)
    public ResponseEntity<SensorDto> updateSensor(@PathVariable int systemId, @PathVariable int sensorId, @RequestBody SensorDto sensorDto) {
        Sensor sensor = SensorService.findBySensorIdAndSystemId(sensorId, systemId);
        if (sensor == null) {
            return ResponseEntity.notFound().build();
        }
        sensor = SensorUtil.updateSensor(sensor, sensorDto);
        SensorService.update(sensor);
        return ResponseEntity.ok().build();
    }

    // schema
    @RequestMapping(value = "/schema", method = RequestMethod.POST)
    public ResponseEntity<SchemaDto> createSchema(@RequestBody SchemaDto schemaDto) {
        Schema schema = SchemaUtil.addSchema(schemaDto);
        SchemaService.save(schema);
        return ResponseEntity.ok().body(schemaDto);
    }

    @RequestMapping(value = "/schema/{schemaId}", method = RequestMethod.GET)
    public ResponseEntity<Schema> getSchemaById(@PathVariable int schemaId) {
        Schema schema = SchemaService.findSchemaById(schemaId);
        if (schema == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(schema);
    }

    @RequestMapping(value = "/schema/{schemaId}", method = RequestMethod.PUT)
    public ResponseEntity<SchemaDto> updateSchema(@PathVariable int schemaId, @RequestBody SchemaDto schemaDto) {
        Schema schema = SchemaService.findSchemaById(schemaId);
        if (schema == null) {
            return ResponseEntity.notFound().build();
        }
        schema = SchemaUtil.updateSchema(schema, schemaDto);
        SchemaService.update(schema);
        return ResponseEntity.ok().build();
    }


    @RequestMapping(value = "/schema/{schemaId}", method = RequestMethod.DELETE)
    public ResponseEntity<SchemaDto> deleteSchema(@PathVariable int schemaId) {
        Schema schema = SchemaService.findSchemaById(schemaId);
        if (schema == null) {
            return ResponseEntity.notFound().build();
        }
        SchemaService.delete(schema);
        return ResponseEntity.ok().build();
    }

    // user
    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto) {
        User user = UserUtil.addUser(userDto);
        UserService.save(user);
        return ResponseEntity.ok().body(userDto);
    }

    @RequestMapping(value = "/user/{userId}", method = RequestMethod.GET)
    public ResponseEntity<User> getUserById(@PathVariable int userId) {
        User user = UserService.findUserById(userId);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(user);
    }

    @RequestMapping(value = "/user/{userId}", method = RequestMethod.PUT)
    public ResponseEntity<UserDto> updateUser(@PathVariable int userId, @RequestBody UserDto userDto) {
        User user = UserService.findUserById(userId);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        user = UserUtil.updateUser(user, userDto);
        UserService.update(user);
        return ResponseEntity.ok().build();
    }


    @RequestMapping(value = "/user/{userId}", method = RequestMethod.DELETE)
    public ResponseEntity<UserDto> deleteUser(@PathVariable int userId) {
        User user = UserService.findUserById(userId);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        UserService.delete(user);
        return ResponseEntity.ok().build();
    }

    // alert
    @RequestMapping(value = "/alert", method = RequestMethod.POST)
    public ResponseEntity<AlertDto> createAlert(@RequestBody AlertDto alertDto) {
        Alert alert = AlertUtil.addAlert(alertDto);
        if (alert != null) {
            AlertService.save(alert);
            return ResponseEntity.ok().body(alertDto);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @RequestMapping(value = "/alert/{alertId}", method = RequestMethod.GET)
    public ResponseEntity<Alert> getAlertById(@PathVariable int alertId) {
        Alert alert = AlertService.findAlertById(alertId);
        if (alert == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(alert);
    }

    @RequestMapping(value = "/alert/{alertId}", method = RequestMethod.PUT)
    public ResponseEntity<AlertDto> updateAlert(@PathVariable int alertId, @RequestBody AlertDto alertDto) {
        Alert alert = AlertService.findAlertById(alertId);
        if (alert == null) {
            return ResponseEntity.notFound().build();
        }
        alert = AlertUtil.updateAlert(alert, alertDto);
        AlertService.update(alert);
        return ResponseEntity.ok().build();
    }


    @RequestMapping(value = "/alert/{alertId}", method = RequestMethod.DELETE)
    public ResponseEntity<AlertDto> deleteAlert(@PathVariable int alertId) {
        Alert alert = AlertService.findAlertById(alertId);
        if (alert == null) {
            return ResponseEntity.notFound().build();
        }
        AlertService.delete(alert);
        return ResponseEntity.ok().build();
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
