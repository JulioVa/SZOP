package com.database.util;

import com.database.dto.SchemaDto;
import com.database.dto.SchemaIdLevelDto;
import com.database.model.Schema;
import com.database.model.User;
import com.database.service.UserService;

import java.util.ArrayList;
import java.util.List;

public class SchemaUtil {

    public static Schema addSchema(SchemaDto schemaDto) {
        User user = UserService.findUserById(schemaDto.getUserId());

        Schema schema = new Schema();
        schema.setName(schemaDto.getName());
        schema.setImg(schemaDto.getImg());
        schema.setUser(user);
        return schema;
    }

    public static Schema updateSchema(Schema schema, SchemaDto schemaUpdate) {
        schema.setName(NotNullUtil.setNotNull(schema.getName(), schemaUpdate.getName()));
        schema.setImg(NotNullUtil.setNotNull(schema.getImg(), schemaUpdate.getImg()));
        return schema;
    }

    public static SchemaDto convertToDto(Schema schema) {
        SchemaDto schemaDto = new SchemaDto();
        schemaDto.setName(schema.getName());
        schemaDto.setImg(schema.getImg());
        schemaDto.setUserId(schema.getUser().getId());
        return schemaDto;
    }

    public static List<SchemaDto> convertToDtos(List<Schema> schemas) {
        List<SchemaDto> schemaDtos = new ArrayList<>();
        for (Schema schema : schemas) {
            schemaDtos.add(convertToDto(schema));
        }
        return schemaDtos;
    }

    public static SchemaIdLevelDto convertToDtoId(Schema schema) {
        SchemaIdLevelDto schemaDto = new SchemaIdLevelDto();
        schemaDto.setName(schema.getName());
        schemaDto.setImg(schema.getImg());
        schemaDto.setId(schema.getId());
        return schemaDto;
    }

    public static List<SchemaIdLevelDto> convertToDtosId(List<Schema> schemas) {
        List<SchemaIdLevelDto> schemaDtos = new ArrayList<>();
        for (Schema schema : schemas) {
            schemaDtos.add(convertToDtoId(schema));
        }
        return schemaDtos;
    }
}
