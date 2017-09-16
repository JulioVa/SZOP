package com.database.util;

import com.database.dto.SchemaDto;
import com.database.model.Schema;

public class SchemaUtil {

    public static Schema addSchema(SchemaDto schemaDto) {
        Schema schema = new Schema();
        schema.setName(schemaDto.getName());
        schema.setImg(schemaDto.getImg());
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
        return schemaDto;
    }
}
