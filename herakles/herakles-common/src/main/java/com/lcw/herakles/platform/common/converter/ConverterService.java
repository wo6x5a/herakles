package com.lcw.herakles.platform.common.converter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;

import com.lcw.herakles.platform.common.util.ApplicationContextUtil;

import net.sf.cglib.beans.BeanCopier;
import net.sf.cglib.core.Converter;

/**
 * Class Name: ConverterService
 * Description: provides a conversion utility in converting between PO and DTO back and forth.
 * 
 * @author chenwulou
 * 
 */
@SuppressWarnings("rawtypes")
public final class ConverterService {

    private static final Map<String, BeanCopier> cachedCopierMap = new ConcurrentHashMap<String, BeanCopier>();
    private static final Map<String, ObjectConverter> cachedCustomConverterMap = new ConcurrentHashMap<String, ObjectConverter>();
    private static final String PO = "Po";
    private static final String DTO = "Dto";
    private static final Logger LOGGER = LoggerFactory.getLogger(ConverterService.class);
    

    public static <T, F> F convert(T source, F target, Converter converter,
            Class<? extends ObjectConverter> customConverterClass) {
        if (source == null || target == null) {
            return null;
        }
        ObjectConverter customConverter = getCustomConverterInstance(customConverterClass);
        copy(source, target, converter, customConverter);
        return target;
    }

    public static <T, F> F convert(T source, Class<F> targetClass) {
    	F target = initializeTarget(targetClass);
        return convert(source, target, new DeepConverter(), null);
    }

    public static <T, F> F convert(T source, F target) {
        return convert(source, target, new DeepConverter(), null);
    }

    public static <T, F> F convert(T source, Class<F> targetClass,
            Class<? extends ObjectConverter> customConverterClass) {
    	F target = initializeTarget(targetClass);
        return convert(source, target, new DeepConverter(), customConverterClass);
    }

    public static <T, F> F convert(T source, F target,
            Class<? extends ObjectConverter> customConverterClass) {
        return convert(source, target, new DeepConverter(), customConverterClass);
    }
    
	public static <T, F, C extends ObjectConverter> F convert(T source, Class<F> targetClass,
            C customConverter) {
    	F target = initializeTarget(targetClass);
    	copy(source, target, new DeepConverter(), customConverter);
        return target;
    }

    @SuppressWarnings("unchecked")
    private static <T, F, C extends ObjectConverter> void copy(T source, F target, Converter converter,
    		C customConverter) {
        BeanCopier beanCopier = getBeanCopierInstance(source, target.getClass(), converter);
        beanCopier.copy(source, target, converter);
        if(customConverter != null){
	        if (target.getClass().getName().endsWith(PO) || target.getClass().getName().endsWith(DTO)) {
	            customConverter.convertFromDomain(source, target);
	        } else if (source.getClass().getName().endsWith(PO) || source.getClass().getName().endsWith(DTO)) {
	            customConverter.convertToDomain(source, target);
	        }
        }
    }

    private static <T, F> BeanCopier getBeanCopierInstance(T source, Class<F> targetClass, Converter converter) {
        String key = source.getClass().getName() + "#" + targetClass.getName();
        BeanCopier beanCopier = cachedCopierMap.get(key);
        if (beanCopier == null) {
            synchronized (cachedCopierMap) {
                beanCopier = cachedCopierMap.get(key);
                if (beanCopier == null) {
                    beanCopier = TypeAwareBeanCopier.instantiate(source.getClass(), targetClass, converter != null);
                    cachedCopierMap.put(key, beanCopier);
                }
            }
        }
        return beanCopier;
    }

    private static <T, F> ObjectConverter getCustomConverterInstance(
            Class<? extends ObjectConverter> customConverterClass) {
        if (customConverterClass == null) {
            return null;
        }
        String key = customConverterClass.getName();
        ObjectConverter converter = cachedCustomConverterMap.get(key);
        if (converter == null) {
            synchronized (cachedCustomConverterMap) {
                try {
                    converter = ApplicationContextUtil.getBean(customConverterClass);
                } catch (BeansException e) {
                    LOGGER.info(customConverterClass.getName() + " is not a component, need new instance.");
                }
                if (converter == null) {
                    try {
                        converter = customConverterClass.newInstance();
                        cachedCustomConverterMap.put(key, converter);
                    } catch (InstantiationException e) {
                        return null;
                    } catch (IllegalAccessException e) {
                        return null;
                    }
                }
            }
        }
        return converter;
    }
    
    /**
	 * @param targetClass
	 * @return
	 */
	private static <F> F initializeTarget(Class<F> targetClass) {
		F target = null;
		try {
			target = targetClass.newInstance();
		} catch (InstantiationException e) {
			LOGGER.error("InstantiationException occured, {}", e);
		} catch (IllegalAccessException e) {
			LOGGER.error("IllegalAccessException occured, {}", e);
		}
		return target;
	}

}
