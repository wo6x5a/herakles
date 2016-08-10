
package com.lcw.herakles.platform.common.util;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.SerializerProvider;
import org.codehaus.jackson.map.ser.std.SerializerBase;

import com.lcw.herakles.platform.common.enums.DBIntEnum;

/**
 * Class Name: DBIntEnumSerializer Description: TODO
 * 
 * @author chenwulou
 * 
 */

public class DBIntEnumSerializer extends SerializerBase<DBIntEnum> {

    public DBIntEnumSerializer(){
        this(DBIntEnum.class);
    }
    
    protected DBIntEnumSerializer(Class<DBIntEnum> t) {
        super(t);
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
