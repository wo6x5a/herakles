	
package com.lcw.herakles.platform.common.xss;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.SerializerProvider;
import org.codehaus.jackson.map.ser.std.SerializerBase;
import org.owasp.encoder.Encode;

/**
 * Class Name: XssSantizeJsonSerializer
 * Description: Sanitize String type fields in object for json serialization.   
 * @author chenwulou
 *
 */
public class XssSantizeJsonSerializer extends SerializerBase<String> {

    public XssSantizeJsonSerializer(){
        this(String.class);
    }
    
    protected XssSantizeJsonSerializer(Class<String> t) {
        super(t);
    }

    @Override
    public void serialize(String value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        if(value!=null){
            String encoded = Encode.forHtml(value);
            jgen.writeString(encoded);
        }
        
    }

}
