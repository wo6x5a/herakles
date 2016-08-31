
package com.lcw.herakles.platform.common.util;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.lcw.herakles.platform.common.enums.DBStrEnum;

/**
 * Class Name: DBStrEnumSerializer Description: TODO
 * 
 * @author chenwulou
 * 
 */

public class DBStrEnumSerializer extends JsonSerializer<DBStrEnum> {

    @Override
    public Class<DBStrEnum> handledType() {
        return DBStrEnum.class;
    }

    @Override
    public void serialize(DBStrEnum value, JsonGenerator generator, SerializerProvider provider) throws IOException {

        generator.writeStartObject();
        generator.writeFieldName("code");
        generator.writeString(value.getCode());
        generator.writeFieldName("text");
        generator.writeString(value.getText());
        generator.writeFieldName("name");
        generator.writeString(value.name());
        generator.writeEndObject();
    }
}
