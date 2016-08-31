package com.lcw.herakles.platform.common.converter;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;

import org.apache.commons.lang3.time.DateUtils;

import net.sf.cglib.core.Converter;

/**
 * Class Name: DeepConverter
 * Description: customized {@link Converter}
 * 
 * @author chenwulou
 * 
 */
public class DeepConverter implements Converter {

    private final SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
    private final SimpleDateFormat timeStampFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public Object convert(Object value, Class targetClass, Object context) {
        if (targetClass != null && value != null) {
            Class<? extends Object> sourceClass = value.getClass();
            if (targetClass.equals(sourceClass)) {
                return value;
            }
            // primitive type
            if (sourceClass.isPrimitive() || targetClass.isPrimitive()) {
                return value;
            }
            // collection
            if (Collection.class.isAssignableFrom(sourceClass) && Collection.class.isAssignableFrom(targetClass)) {
                // not supported
                return null;
            } else if (Timestamp.class.isAssignableFrom(sourceClass) && targetClass.isAssignableFrom(String.class)) {
                return timeStampFormatter.format((Timestamp) value);
            } else if (Date.class.isAssignableFrom(sourceClass) && targetClass.isAssignableFrom(String.class)) {
                return dateFormatter.format((Date) value);
            } else if (java.util.Date.class.isAssignableFrom(targetClass) && sourceClass.isAssignableFrom(String.class)) {
                try {
                    return DateUtils.parseDate(value.toString(), new String[] { "yyyy-MM-dd" });
                } catch (ParseException e) {
                    return null;
                }
            } else if (targetClass.isAssignableFrom(sourceClass)) {
                return value;
            }
            // complex type
            if (targetClass.getClassLoader() != null && sourceClass.getClassLoader() != null) {
                return ConverterService.convert(value, targetClass);
            }

        }
        return null;
    }

}
