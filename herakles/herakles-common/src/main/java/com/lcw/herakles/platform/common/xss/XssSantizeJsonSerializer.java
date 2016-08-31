	
package com.lcw.herakles.platform.common.xss;

import java.io.IOException;

import org.owasp.encoder.Encode;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

/**
 * Class Name: XssSantizeJsonSerializer
 * Description: Sanitize String type fields in object for json serialization.   
 * @author chenwulou
 *
 */
public class XssSantizeJsonSerializer extends JsonSerializer<String> {

    @Override
    public void serialize(String value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        if(value!=null){
            String encoded = Encode.forHtml(value);
            jgen.writeString(encoded);
        }
        
    }
    @Override
    public Class<String> handledType() {
        return String.class;
    }
}
