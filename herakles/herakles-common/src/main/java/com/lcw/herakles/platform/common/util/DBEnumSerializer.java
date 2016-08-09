
package com.lcw.herakles.platform.common.util;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.SerializerProvider;
import org.codehaus.jackson.map.ser.std.SerializerBase;

import com.lcw.herakles.platform.common.enums.DBEnum;

/**
 * Class Name: PageEnumSerializer Description: TODO
 * 
 * @author chenwulou
 * 
 */

public class DBEnumSerializer extends SerializerBase<DBEnum> {

    public DBEnumSerializer(){
        this(DBEnum.class);
    }
    
    protected DBEnumSerializer(Class<DBEnum> t) {
        super(t);
    }

    @Override
    public void serialize(DBEnum value, JsonGenerator generator, SerializerProvider provider) throws IOException {
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
