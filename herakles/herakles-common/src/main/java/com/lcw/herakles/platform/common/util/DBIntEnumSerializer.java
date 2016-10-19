
package com.lcw.herakles.platform.common.util;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.lcw.herakles.platform.common.enums.DBIntEnum;

/**
 * Class Name: DBIntEnumSerializer Description: TODO
 * 
 * @author chenwulou
 * 
 */
public class DBIntEnumSerializer extends JsonSerializer<DBIntEnum> {


    @Override
    public Class<DBIntEnum> handledType() {
        return DBIntEnum.class;
    }
    @Override
    public void serialize(DBIntEnum value, JsonGenerator generator, SerializerProvider provider) throws IOException {
        generator.writeStartObject();
        generator.writeFieldName("code");
        generator.writeString(value.getCode().toString());
        generator.writeFieldName("text");
        generator.writeString(value.getText());
        generator.writeFieldName("name");
        generator.writeString(value.name());
        generator.writeEndObject();
    }
}
