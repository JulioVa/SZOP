package com;

import com.database.dto.*;
import com.database.model.*;
import com.database.model.System;
import com.database.service.*;
import com.database.util.*;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class SzopRestController {

    private static final Logger LOGGER = LogManager.getLogger(SzopRestController.class);

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
    public ResponseEntity<List<SensorDto>> getSensorsBySystemId(@PathVariable int systemId) {
        List<Sensor> sensors = SensorService.findAllBySystem(systemId);
        List<SensorDto> sensorDtos = SensorUtil.convertToDtos(sensors);
        if (sensors.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(sensorDtos);
    }

    @RequestMapping(value = "/schema/{schemaId}/sensors", method = RequestMethod.GET)
    public ResponseEntity<List<SensorDto>> getSensorsBySchemaId(@PathVariable int schemaId) {
        List<Sensor> sensors = SensorService.findAllBySchema(schemaId);
        List<SensorDto> sensorDtos = SensorUtil.convertToDtos(sensors);
        if (sensors.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(sensorDtos);
    }

    @RequestMapping(value = "/sensors/temperature", method = RequestMethod.GET)
    public ResponseEntity<List<SensorDto>> getTemperatureSensors() {
        List<Sensor> sensors = SensorService.findAllByType(1);
        List<SensorDto> sensorDtos = SensorUtil.convertToDtos(sensors);
        if (sensors.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(sensorDtos);
    }

    @RequestMapping(value = "/sensors/humidity", method = RequestMethod.GET)
    public ResponseEntity<List<SensorDto>> getHumiditySensors() {
        List<Sensor> sensors = SensorService.findAllByType(2);
        List<SensorDto> sensorDtos = SensorUtil.convertToDtos(sensors);
        if (sensors.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(sensorDtos);
    }

    @RequestMapping(value = "/sensor/{sensorId}", method = RequestMethod.GET)
    public ResponseEntity<SensorDto> getSensorById(@PathVariable int sensorId) {
        Sensor sensor = SensorService.findSensorById(sensorId);
        SensorDto sensorDto = SensorUtil.convertToDto(sensor);
        if (sensor == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(sensorDto);
    }

    @RequestMapping(value = "/sensors/schema/{schemaId}/unbind", method = RequestMethod.PUT)
    public ResponseEntity<Void> unbindAllSensorsFromSchema(@PathVariable int schemaId) {
        List<Sensor> sensors = SensorService.findAllBySchema(schemaId);
        SensorUtil.unbindFromSchema(sensors);
        return ResponseEntity.ok().build();
    }

    @RequestMapping(value = "/sensors/{sensorId}/unbind", method = RequestMethod.PUT)
    public ResponseEntity<Void> unbindSensorFromSchema(@PathVariable String sensorId) {
        Sensor sensor = SensorService.findSensorBySensorId(sensorId);
        SensorUtil.unbindSingleFromSchema(sensor);
        return ResponseEntity.ok().build();
    }

    @RequestMapping(value = "/system/{systemId}/sensors/{sensorId}", method = RequestMethod.PUT)
    public ResponseEntity<SensorDto> updateSensor(@PathVariable int systemId, @PathVariable String sensorId, @RequestBody SensorDto sensorDto) {
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
    public ResponseEntity<SchemaIdLevelDto> createSchema(@RequestBody SchemaDto schemaDto) {
        Schema schema = SchemaUtil.addSchema(schemaDto);
        SchemaService.save(schema);
        SchemaIdLevelDto schemaIdLevelDto = new SchemaIdLevelDto(schema.getName(), schema.getImg(), schema.getId(), schema.getUser().getId());
        return ResponseEntity.ok().body(schemaIdLevelDto);
    }

    @RequestMapping(value = "/schema/{schemaId}", method = RequestMethod.GET)
    public ResponseEntity<SchemaDto> getSchemaById(@PathVariable int schemaId) {
        Schema schema = SchemaService.findSchemaById(schemaId);
        if (schema == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(SchemaUtil.convertToDto(schema));
    }

    @RequestMapping(value = "/schemas", method = RequestMethod.GET)
    public ResponseEntity<List<SchemaDto>> getSchemas() {
        List<Schema> schemas = SchemaService.findAll();
        List<SchemaDto> schemaDtos = SchemaUtil.convertToDtos(schemas);
        if (schemas.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(schemaDtos);
    }

    @RequestMapping(value = "/schemas/id", method = RequestMethod.GET)
    public ResponseEntity<List<SchemaIdLevelDto>> getSchemasId() {
        List<Schema> schemas = SchemaService.findAll();
        List<SchemaIdLevelDto> schemaDtos = SchemaUtil.convertToDtosId(schemas);
        if (schemas.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(schemaDtos);
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
    public ResponseEntity<UserDto> getUserById(@PathVariable int userId) {
        User user = UserService.findUserById(userId);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(UserUtil.convertToDto(user));
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
    public ResponseEntity<AlertDto> getAlertById(@PathVariable int alertId) {
        Alert alert = AlertService.findAlertById(alertId);
        if (alert == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(AlertUtil.convertToDto(alert));
    }

    @RequestMapping(value = "/user/{userId}/alerts", method = RequestMethod.GET)
    public ResponseEntity<List<AlertDto>> getAlertsByUserId(@PathVariable int userId) {
        List<Alert> alerts = AlertService.findAllForUser(userId);
        List<AlertDto> alertDtos = AlertUtil.convertToDtos(alerts);
        if (alerts.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(alertDtos);
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

    // system
    @RequestMapping(value = "/system", method = RequestMethod.POST)
    public ResponseEntity<SystemDto> createSystem(@RequestBody SystemDto systemDto) {
        System system = SystemUtil.addSystem(systemDto);
        if (system != null) {
            SystemService.save(system);
            return ResponseEntity.ok().body(systemDto);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @RequestMapping(value = "/system/{systemId}", method = RequestMethod.GET)
    public ResponseEntity<SystemDto> getSystemById(@PathVariable int systemId) {
        System system = SystemService.findSystemById(systemId);
        if (system == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(SystemUtil.convertToDto(system));
    }

    @RequestMapping(value = "/system/user/{userId}", method = RequestMethod.GET)
    public ResponseEntity<List<SystemDto>> getSystemsByUserId(@PathVariable int userId) {
        List<System> systems = SystemService.findAllByUser(userId);
        List<SystemDto> systemDtos = SystemUtil.convertToDtos(systems);
        if (systems.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(systemDtos);
    }

    @RequestMapping(value = "/system/{systemId}", method = RequestMethod.PUT)
    public ResponseEntity<SystemDto> updateSystem(@PathVariable int systemId, @RequestBody SystemDto systemDto) {
        System system = SystemService.findSystemById(systemId);
        if (system == null) {
            return ResponseEntity.notFound().build();
        }
        system = SystemUtil.updateSystem(system, systemDto);
        SystemService.update(system);
        return ResponseEntity.ok().build();
    }

    @RequestMapping(value = "/system/{systemId}", method = RequestMethod.DELETE)
    public ResponseEntity<SystemDto> deleteSystem(@PathVariable int systemId) {
        System system = SystemService.findSystemById(systemId);
        if (system == null) {
            return ResponseEntity.notFound().build();
        }
        SystemService.delete(system);
        return ResponseEntity.ok().build();
    }

    @RequestMapping(value = "/sensors/{sensorId}/user/{userId}/system/{systemId}/data", method = RequestMethod.GET)
    public ResponseEntity<List<Temperature>> getSensorsData(@PathVariable int userId, @PathVariable int systemId, @PathVariable String sensorId) {
        List<Temperature> data = InfluxService.getDataForSensor(userId, systemId, sensorId);
        LOGGER.info("data from sensor: " + data);
        if (data.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(data);
    }

    @RequestMapping(value = "/sensors/user/{userId}/data/temp", method = RequestMethod.GET)
    public ResponseEntity<List<SensorTempData>> getSensorsDataTemp(@PathVariable int userId) {
        List<SensorTempData> data = InfluxService.getDataForUser(userId, "temp");
        LOGGER.info("data from sensor: " + data);
        if (data.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(data);
    }

    @RequestMapping(value = "/sensors/user/{userId}/data/humid", method = RequestMethod.GET)
    public ResponseEntity<List<SensorTempData>> getSensorsDataHumid(@PathVariable int userId) {
        List<SensorTempData> data = InfluxService.getDataForUser(userId, "humid");
        LOGGER.info("data from sensor: " + data);
        if (data.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(data);
    }

    @RequestMapping(value = "/sensors/data", method = RequestMethod.POST)
    ResponseEntity<?> addData(@RequestBody Map<String, Object> data) {
        if (data != null) {
            LOGGER.error(data.toString());
            String userId = (String) data.get("user_id");
            String systemName = (String) data.get("system_id");
            List<TemperatureData> temps = TemperatureDataUtil.convertToDtos((List<Map<String, Object>>) data.get("sensors"));
            InfluxService.writeData(userId,systemName,temps);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.noContent().build();
    }
}
