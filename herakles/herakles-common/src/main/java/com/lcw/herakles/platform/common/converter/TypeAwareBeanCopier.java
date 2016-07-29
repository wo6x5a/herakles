package com.lcw.herakles.platform.common.converter;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import net.sf.cglib.beans.BeanCopier;
import net.sf.cglib.core.ClassEmitter;
import net.sf.cglib.core.CodeEmitter;
import net.sf.cglib.core.Constants;
import net.sf.cglib.core.EmitUtils;
import net.sf.cglib.core.Local;
import net.sf.cglib.core.MethodInfo;
import net.sf.cglib.core.ReflectUtils;
import net.sf.cglib.core.Signature;
import net.sf.cglib.core.TypeUtils;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Type;

/**
 * Enhance {@link BeanCopier} to allow customized {@link net.sf.cglib.core.Converter} to visit
 * field level information via {@code context} param.
 * 
 * @author chenwulou
 * 
 */
public abstract class TypeAwareBeanCopier extends BeanCopier { // NOSONAR

	private static final Type BEAN_COPIER = TypeUtils
			.parseType("net.sf.cglib.beans.BeanCopier");
	private static final Type CONVERTER = TypeUtils
			.parseType("net.sf.cglib.core.Converter");
	private static final Signature COPY = new Signature("copy", Type.VOID_TYPE,
			new Type[] { Constants.TYPE_OBJECT, Constants.TYPE_OBJECT,
					CONVERTER });
	private static final Signature CONVERT = TypeUtils
			.parseSignature("Object convert(Object, Class, Object)");

	
	/**
	* Description: instantiates a {@link BeanCopier}.
	*
	* @param source
	* @param target
	* @param useConverter
	* @return
	*/
	public static BeanCopier instantiate(Class<?> source, Class<?> target,
			boolean useConverter) {
		TypeAwareGenerator gen = new TypeAwareGenerator();
		gen.setSource(source);
		gen.setTarget(target);
		gen.setUseConverter(useConverter);
		return gen.create();
	}

	private static Method getDeclaredMethod(Class<?> clz, String method,
			Class<?>... params) {
		try {
			return clz.getDeclaredMethod(method, params);
		} catch (Exception e) {
			return null;
		}
	}

	private static Field getDeclaredField(Class<?> clz, String field) {
		try {
			return clz.getDeclaredField(field);
		} catch (Exception e) {
			return null;
		}
	}

	public static Field getField(Class<?> classType, String field) {
	    Class<?> clz = classType;
        while (clz != Object.class) {
            Field f = getDeclaredField(clz, field);
            if (f != null) {
                return f;
            }
            clz = clz.getSuperclass();
        }
        return null;
    }

    /**
    * Class Name: TypeAwareGenerator
    * Description: TODO
    * @author chenwulou
    *
    */
    protected static class TypeAwareGenerator extends Generator {
		private static Field SOURCE = null; // NOSONAR
		private static Field TARGET = null; // NOSONAR
		private static Field USECONVERTER = null; // NOSONAR
		private static Method GET_FIELD_METHOD = null; // NOSONAR
		private static Signature GET_FIELD_METHOD_SIG = null; // NOSONAR
		private static Type GET_FIELD_METHOD_TYPE = null; // NOSONAR
		static {
			GET_FIELD_METHOD = getDeclaredMethod(TypeAwareBeanCopier.class,
					"getField", Class.class, String.class);
			GET_FIELD_METHOD_SIG = new Signature(GET_FIELD_METHOD.getName(),
					Type.getMethodDescriptor(GET_FIELD_METHOD));
			GET_FIELD_METHOD_TYPE = Type.getType(TypeAwareBeanCopier.class);
			SOURCE = getDeclaredField(Generator.class, "source");
			SOURCE.setAccessible(true);
			TARGET = getDeclaredField(Generator.class, "target");
			TARGET.setAccessible(true);
			USECONVERTER = getDeclaredField(Generator.class, "useConverter");
			USECONVERTER.setAccessible(true);
		}

		public void generateClass(ClassVisitor v) {
			Class<?> source = null;
			Class<?> target = null;
			Boolean useConverter = null;
			try {
				source = (Class<?>) SOURCE.get(this);
				target = (Class<?>) TARGET.get(this);
				useConverter = (Boolean) USECONVERTER.get(this);
			} catch (Exception e) {
			    throw new RuntimeException("exception caught during TypeAwareGenerator.generateClass()", e);
			}
			Type sourceType = Type.getType(source);
			Type targetType = Type.getType(target);
			ClassEmitter ce = new ClassEmitter(v);
			ce.begin_class(Constants.V1_2, Constants.ACC_PUBLIC,
					getClassName(), BEAN_COPIER, null, Constants.SOURCE_FILE);

			EmitUtils.null_constructor(ce);
			CodeEmitter e = ce.begin_method(Constants.ACC_PUBLIC, COPY, null);
			PropertyDescriptor[] getters = ReflectUtils.getBeanGetters(source);
			PropertyDescriptor[] setters = ReflectUtils.getBeanGetters(target);

			Map<Object, Object> names = new HashMap<Object, Object>();
			for (int i = 0; i < getters.length; i++) {
				names.put(getters[i].getName(), getters[i]);
			}
			Local targetLocal = e.make_local();
			Local sourceLocal = e.make_local();
			if (useConverter) {
				e.load_arg(1);
				e.checkcast(targetType);
				e.store_local(targetLocal);
				e.load_arg(0);
				e.checkcast(sourceType);
				e.store_local(sourceLocal);
			} else {
				e.load_arg(1);
				e.checkcast(targetType);
				e.load_arg(0);
				e.checkcast(sourceType);
			}
			for (int i = 0; i < setters.length; i++) {
				PropertyDescriptor setter = setters[i];
				PropertyDescriptor getter = (PropertyDescriptor) names
						.get(setter.getName());
				if (getter != null) {
					MethodInfo read = ReflectUtils.getMethodInfo(getter
							.getReadMethod());
					MethodInfo write = ReflectUtils.getMethodInfo(setter
							.getWriteMethod());
					if (useConverter) {
						Type setterType = write.getSignature()
								.getArgumentTypes()[0];
						e.load_local(targetLocal);
						e.load_arg(2);
						e.load_local(sourceLocal);
						e.invoke(read);
						e.box(read.getSignature().getReturnType());
						EmitUtils.load_class(e, setterType);

						/**
						 * populates the target Field as context param.
						 */
						EmitUtils.load_class(e, targetType);
						e.push(setter.getName());
						e.invoke_static(GET_FIELD_METHOD_TYPE,
								GET_FIELD_METHOD_SIG);

						e.invoke_interface(CONVERTER, CONVERT);
						e.unbox_or_zero(setterType);
						e.invoke(write);
					} else if (compatible(getter, setter)) {
						e.dup2();
						e.invoke(read);
						e.invoke(write);
					}
				}
			}
			e.return_value();
			e.end_method();
			ce.end_class();
		}

		private static boolean compatible(PropertyDescriptor getter,
				PropertyDescriptor setter) {
			return setter.getPropertyType().isAssignableFrom(
					getter.getPropertyType());
		}
	}

}
