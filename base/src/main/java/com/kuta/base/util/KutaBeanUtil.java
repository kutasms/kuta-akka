package com.kuta.base.util;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Java实体对象序列化工具
 * */
public class KutaBeanUtil {
	/**
	 * 日志接口
	 * */
	private static final Logger logger = LoggerFactory.getLogger(KutaBeanUtil.class);
	
	/**
	 * 获取对象的所有字段，包括所有上层父类
	 * @param o 实体对象
	 * @return 字段包装数组
	 * */
	public static Field[] getAllFields(Object o){
	    Class<?> c= o.getClass();
	    List<Field> fieldList = new ArrayList<>();
	    while (c!= null){
	        fieldList.addAll(new ArrayList<>(Arrays.asList(c.getDeclaredFields())));
	        c= c.getSuperclass();
	    }
	    Field[] fields = new Field[fieldList.size()];
	    fieldList.toArray(fields);
	    return fields;
	}
	/**
	 * 实体对象转成Map
	 *
	 * @param obj 实体对象
	 * @return map对象
	 */
	public static Map<String, String> bean2Map(Object obj) {
		Map<String, String> map = new HashMap<>();
		if (obj == null) {
			return map;
		}
		Field[] fields = getAllFields(obj);
		try {
			for (Field field : fields) {
				field.setAccessible(true);
				Object fieldObj = field.get(obj);
				if(KutaUtil.isValueNull(fieldObj)) {
					continue;
				}
				if(field.getName().equals("serialVersionUID")) {
					continue;
				}
				if (field.getGenericType().getTypeName().equals(Date.class.getName())) {
					SimpleDateFormat fomatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
//					logger.info("时间转换:{}", fomatter.format(field.get(obj)));
//					
					map.put(field.getName(), fomatter.format(field.get(obj)));
				} else {
					map.put(field.getName(), field.get(obj).toString());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}

	/**
	 * Map转成实体对象
	 * @param <T> 实体泛型
	 * @param map
	 *            实体对象包含属性
	 * @param clazz
	 *            实体Java类型
	 * @return 实体对象
	 */
	public static <T> T map2Bean(Map<String, String> map, Class<T> clazz) {
		if (map == null) {
			return null;
		}
		T obj = null;
		try {
			obj = clazz.newInstance();

			Field[] fields = getAllFields(obj);
			for (Field field : fields) {
				int mod = field.getModifiers();
				if (Modifier.isStatic(mod) || Modifier.isFinal(mod)) {
					continue;
				}
				field.setAccessible(true);

				Type type = field.getGenericType();
				
				String val = map.get(field.getName());;
				if(KutaUtil.isEmptyString(val)) {
					continue;
				}
				if (type.getTypeName().equals(java.lang.Integer.class.getName())
						|| type.getTypeName().equals("int")) {
					field.set(obj, Integer.parseInt(val));
				} else if (type.getTypeName().equals(java.lang.Long.class.getName())
						|| type.getTypeName().equals("long")) {
					field.set(obj, Long.parseLong(val));
				} else if (type.getTypeName().equals(java.lang.Boolean.class.getName())
						|| type.getTypeName().equals("boolean")) {
					field.set(obj, Boolean.parseBoolean(val));
				} else if (type.getTypeName().equals(java.lang.Short.class.getName())
						|| type.getTypeName().equals("short")) {
					field.set(obj, Short.parseShort(val));
				} else if (type.getTypeName().equals(java.lang.Byte.class.getName())
						|| type.getTypeName().equals("byte")) {
					field.set(obj, Byte.parseByte(val));
				} else if (type.getTypeName().equals(java.math.BigDecimal.class.getName())) {
					field.set(obj, new BigDecimal(val));
				} else if (type.getTypeName().equals(Date.class.getName())) {
					field.set(obj, KutaTimeUtil.parseWithMill(val));
				} else if(type.getTypeName().equals(Double.class.getName())
						|| type.getTypeName().equals("double")) {
					field.set(obj, Double.parseDouble(val));
				} 
				else {
					field.set(obj, val);
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return obj;
	}
}
