
package com.lcw.herakles.platform.common.util;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.SerializerProvider;
import org.codehaus.jackson.map.ser.std.SerializerBase;

import com.lcw.herakles.platform.common.enums.DBStrEnum;

/**
 * Class Name: DBStrEnumSerializer Description: TODO
 * 
 * @author chenwulou
 * 
 */

public class DBStrEnumSerializer extends SerializerBase<DBStrEnum> {

    public DBStrEnumSerializer(){
        this(DBStrEnum.class);
    }
    
    protected DBStrEnumSerializer(Class<DBStrEnum> t) {
        super(t);
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
