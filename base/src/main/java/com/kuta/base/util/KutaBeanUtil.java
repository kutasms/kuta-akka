package com.kuta.base.util;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;

import akka.protobufv3.internal.Value;

/**
 * Java entity objects serialization and deserialization tool
 * */
public class KutaBeanUtil {
	/**
	 * logger
	 * */
	private static final Logger logger = LoggerFactory.getLogger(KutaBeanUtil.class);
	
	/**
	 * Get all fields of the object, including all upper parent classes
	 * @param o Entity object
	 * @return Field wrapper array
	 * */
	public static Field[] getAllFields(Object o){
	    return getAllFields(o.getClass());
	}
	public static Field[] getAllFields(Class<?> clazz){
	    List<Field> fieldList = new ArrayList<>();
	    while (clazz!= null){
	        fieldList.addAll(new ArrayList<>(Arrays.asList(clazz.getDeclaredFields())));
	        clazz= clazz.getSuperclass();
	    }
	    Field[] fields = new Field[fieldList.size()];
	    fieldList.toArray(fields);
	    return fields;
	}
	
	/**
	 * Convert entity object to map
	 *
	 * @param obj Entity object
	 * @return Map object
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
				if(fieldObj == null) {
					continue;
				}
				if(field.getName().equals("serialVersionUID")) {
					logger.debug("dump serialVersionUID");
					continue;
				}
				Type declareType = field.getGenericType();
				if (declareType.equals(Date.class)) {

					map.put(field.getName(), KutaTimeUtil.formatWithMill(field.get(obj)));
				} 
				else if(declareType.equals(String.class)||
						declareType.equals(Integer.class) ||
						declareType.equals(Boolean.class) ||
						declareType.equals(Float.class) ||
						declareType.equals(Double.class)||
						declareType.equals(BigDecimal.class) ||
						declareType.equals(BigInteger.class) ||
						declareType.equals(Long.class)||
						declareType.equals(Byte.class) ||
						declareType.equals(Short.class)) {
					map.put(field.getName(), field.get(obj).toString());
				}
				else if(field.getType().isPrimitive()) {
					map.put(field.getName(), field.get(obj).toString());
				}
//				else if(field.getType().isEnum()) {
//					logger.info("field:{}, 枚举", field.getName());
//					map.put(field.getName(), field.get(obj).toString());
//				}
				else {
					map.put(field.getName(), JSONObject.toJSONString(fieldObj));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}

	/**
	 * Convert map to entity object
	 * @param <T> Entity generics
	 * @param map Attribute Collection map
	 * @param Clazz Entity Java type
	 * @return Entity object
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

				Class<?> type = field.getType();
				
				if(!map.containsKey(field.getName())) {
					continue;
				}
				
				String val = map.get(field.getName());
				if(val == null || val.equals("null")) {
					continue;
				}
				if(val.startsWith("\"") && val.endsWith("\"")) {
					Object ins = JSONObject.parseObject(val,field.getType());
					field.set(obj, ins);
					continue;
				}
				if (type.equals(java.lang.Integer.class)
						|| type.equals(int.class)) {
					field.set(obj, Integer.parseInt(val));
				} else if (type.equals(java.lang.Long.class)
						|| type.equals(long.class)) {
					field.set(obj, Long.parseLong(val));
				} else if (type.equals(java.lang.Boolean.class)
						|| type.equals(boolean.class)) {
					field.set(obj, Boolean.parseBoolean(val));
				} else if (type.equals(java.lang.Short.class)
						|| type.equals(short.class)) {
					field.set(obj, Short.parseShort(val));
				} else if (type.equals(java.lang.Byte.class)
						|| type.equals(byte.class)) {
					field.set(obj, Byte.parseByte(val));
				} else if (type.equals(java.math.BigDecimal.class)) {
					field.set(obj, new BigDecimal(val));
				} else if (type.equals(BigInteger.class)) {
					field.set(obj, new BigInteger(val));
				} else if (type.equals(Date.class)) {
					if(val.matches("^[0-9]+$")) {
						field.set(obj, new Date(Long.parseLong(val)));
					} else {
					field.set(obj, KutaTimeUtil.parseWithMill(val));
					}
				} else if(type.equals(Double.class)
						|| type.equals(double.class)) {
					field.set(obj, Double.parseDouble(val));
				} else if(type.equals(String.class)) {
					field.set(obj, val);
				} else {
					Object ins = JSONObject.parseObject(val,field.getType());
					field.set(obj, ins);
					continue;
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return obj;
	}
}
