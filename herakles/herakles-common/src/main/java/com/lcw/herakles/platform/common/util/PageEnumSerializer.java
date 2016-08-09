
package com.lcw.herakles.platform.common.util;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.SerializerProvider;
import org.codehaus.jackson.map.ser.std.SerializerBase;

import com.lcw.herakles.platform.common.enums.PageEnum;

/**
 * Class Name: PageEnumSerializer Description: TODO
 * 
 * @author chenwulou
 * 
 */

public class PageEnumSerializer extends SerializerBase<PageEnum> {

    public PageEnumSerializer(){
        this(PageEnum.class);
    }
    
    protected PageEnumSerializer(Class<PageEnum> t) {
        super(t);
    }

    @Override
    public void serialize(PageEnum value, JsonGenerator generator, SerializerProvider provider) throws IOException {

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
