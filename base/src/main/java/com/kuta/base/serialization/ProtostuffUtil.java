package com.kuta.base.serialization;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.Stack;
import java.util.TreeMap;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import com.kuta.base.exception.KutaIllegalArgumentException;
import com.kuta.base.util.KutaUtil;

import akka.actor.ActorRef;
import akka.actor.ExtendedActorSystem;
import io.protostuff.LinkedBuffer;
import io.protostuff.ProtobufIOUtil;
import io.protostuff.ProtostuffIOUtil;
import io.protostuff.Schema;
import io.protostuff.runtime.DefaultIdStrategy;
import io.protostuff.runtime.Delegate;
import io.protostuff.runtime.RuntimeEnv;
import io.protostuff.runtime.RuntimeSchema;

/**
 * Protostuff序列化工具
 */
public class ProtostuffUtil {

	/**
	 * 缓存架构哈希Map
	 */
	private static Map<Class<?>, Schema<?>> cachedSchema = new ConcurrentHashMap<Class<?>, Schema<?>>();
	/**
	 * 运行时环境的ID策略
	 */
	private static final DefaultIdStrategy idStrategy = ((DefaultIdStrategy) RuntimeEnv.ID_STRATEGY);
	/**
	 * actor代理
	 */
	// private final static Delegate<ActorRef> ACTORREF_DELEGATE = new
	// ActorRefDelegate();
	/**
	 * actor system
	 */
	public static ExtendedActorSystem actorSystem;

	/**
	 * 序列化/反序列化包装类 Class 对象
	 */
	private static final Class<ProtostuffObjectWrapper> wrapperClass = ProtostuffObjectWrapper.class;

	/**
	 * 序列化/反序列化包装类 Schema 对象
	 */
	private static final Schema<ProtostuffObjectWrapper> wrapperSchema = RuntimeSchema.createFrom(wrapperClass);

	/**
	 * 需要使用包装类进行序列化/反序列化的class集合
	 */
	private static final Set<Class<?>> wrapperSet = new HashSet<>();

	static {
		wrapperSet.add(List.class);
		wrapperSet.add(ArrayList.class);
		wrapperSet.add(CopyOnWriteArrayList.class);
		wrapperSet.add(LinkedList.class);
		wrapperSet.add(Stack.class);
		wrapperSet.add(Vector.class);
		wrapperSet.add(Map.class);
		wrapperSet.add(HashMap.class);
		wrapperSet.add(TreeMap.class);
		wrapperSet.add(Hashtable.class);
		wrapperSet.add(SortedMap.class);
		wrapperSet.add(Map.class);
		wrapperSet.add(Object.class);
		// idStrategy.registerDelegate(ACTORREF_DELEGATE);
	}

	/**
	 * 注册需要使用包装类进行序列化/反序列化的 Class 对象
	 *
	 * @param clazz
	 *            需要包装的类型 Class 对象
	 */
	public static void registerWrapperClass(Class clazz) {
		wrapperSet.add(clazz);
	}

	/**
	 * 获取指定Java类型的架构
	 * 
	 * @param <T>
	 *            Java对象泛型
	 * @param clazz
	 *            Java对象的类型
	 * @return 指定泛型的架构信息
	 */
	private static <T> Schema<T> getSchema(Class<T> clazz) {
		@SuppressWarnings("unchecked")
		Schema<T> schema = (Schema<T>) cachedSchema.get(clazz);
		if (schema == null) {
			schema = RuntimeSchema.createFrom(clazz);
			cachedSchema.put(clazz, schema);
		}
		return schema;
	}

	/**
	 * 序列化
	 * 
	 * @param <T>
	 *            Java对象泛型
	 * @param obj
	 *            要序列化的Java对象
	 * @return byte数组
	 * @throws IllegalStateException
	 *             当出现异常时抛出
	 */
	@SuppressWarnings("unchecked")
	public static <T> byte[] serialize(T obj) {
		Class<T> clazz = (Class<T>) obj.getClass();
		LinkedBuffer buffer = LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE);
		try {
			Object serializeObject = obj;
			Schema schema = wrapperSchema;
			if (!wrapperSet.contains(clazz)) {
				schema = getSchema(clazz);
			} else {
				serializeObject = ProtostuffObjectWrapper.build(obj);
			}
			return ProtostuffIOUtil.toByteArray(serializeObject, schema, buffer);
		} catch (Exception e) {
			throw new IllegalStateException(e.getMessage(), e);
		} finally {
			buffer.clear();
		}
	}

	/**
	 * 序列化带Actor类型属性的java对象
	 * 
	 * @param <T>
	 *            Java对象泛型
	 * @param t
	 *            要序列化的Java对象
	 * @param extendedSystem
	 *            类型化的system
	 * @return byte数组
	 */
	public static <T> byte[] serialize(T t, final ExtendedActorSystem extendedSystem) {
		if (KutaUtil.isValueNull(actorSystem)) {
			actorSystem = extendedSystem;
		}
		return serialize(t);
	}

	/**
	 * 反序列化带Actor类型属性的byte数组
	 * 
	 * @param <T>
	 *            Java对象泛型
	 * @param data
	 *            protostuff的方式序列化的byte[]数据
	 * @param clazz
	 *            Java对象的类型
	 * @param extendedSystem
	 *            类型化的system
	 * @return Java对象
	 * @throws IllegalStateException
	 *             发生异常时抛出
	 */
	public static <T> T deserialize(byte[] data, Class<T> clazz, final ExtendedActorSystem extendedSystem)
			throws IllegalStateException {
		if (KutaUtil.isValueNull(actorSystem)) {
			actorSystem = extendedSystem;
		}
		return deserialize(data, clazz);
	}

	/**
	 * 反序列化byte数组
	 * 
	 * @param <T>
	 *            Java对象泛型
	 * @param data
	 *            protostuff的方式序列化的byte[]数据
	 * @param clazz
	 *            要进行反序列化成实体的Java类型
	 * @return Java对象
	 * @throws IllegalStateException
	 *             发生异常时抛出
	 */
	@SuppressWarnings("unchecked")
	public static <T> T deserialize(byte[] data, Class<T> clazz) throws IllegalStateException {
		try {
			if (!wrapperSet.contains(clazz)) {
				T message = clazz.newInstance();
				Schema<T> schema = getSchema(clazz);
				ProtostuffIOUtil.mergeFrom(data, message, schema);
				return message;
			} else {
				ProtostuffObjectWrapper<T> wrapper = new ProtostuffObjectWrapper<>();
				ProtostuffIOUtil.mergeFrom(data, wrapper, wrapperSchema);
				return wrapper.getData();
			}
		} catch (Exception e) {
			throw new IllegalStateException(e.getMessage(), e);
		}
	}

	/**
	 * 反序列化byte数组
	 * 
	 * @param data
	 *            protostuff的方式序列化的byte[]数据
	 * @param clazz
	 *            要进行反序列化成实体的Java类型
	 * @return Java对象
	 * @throws IllegalStateException
	 *             发生异常时抛出
	 */
	public static Object deserializeWithoutT(byte[] data, Class clazz) {
		try {
			// 判断是否是不可序列化对象，若是不能序列化对象，将对象进行包装
			if (wrapperSet.contains(clazz)) {
				// SerializeDeserializeWrapper<T> wrapper =
				// SerializeDeserializeWrapper.builder(clazz.newInstance());
				ProtostuffObjectWrapper wrapper = new ProtostuffObjectWrapper();
				ProtostuffIOUtil.mergeFrom(data, wrapper, wrapperSchema);
				return wrapper.getData();
			} else {
				Object message = clazz.newInstance();
				Schema<Object> schema = getSchema(clazz);
				ProtostuffIOUtil.mergeFrom(data, message, schema);
				return message;
			}
		} catch (Exception e) {
			throw new IllegalStateException(e.getMessage(), e);
		}
	}

	/**
	 * 序列化List集合
	 * 
	 * @param <T>
	 *            Java对象泛型
	 * @param list
	 *            需要序列化的List集合
	 * @return byte数组
	 * @throws IOException
	 *             发生IO异常时抛出
	 * @throws KutaIllegalArgumentException
	 *             参数错误时抛出
	 */

	public static <T> byte[] serializeList(List<T> list) throws KutaIllegalArgumentException, IOException {
		if (list == null || list.size() == 0) {
			throw new KutaIllegalArgumentException("集合不能为空!");
		}

		@SuppressWarnings("unchecked")
		Schema<T> schema = (Schema<T>) RuntimeSchema.getSchema(list.get(0).getClass());
		LinkedBuffer buffer = LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE);
		byte[] protostuff = null;
		ByteArrayOutputStream bos = null;
		try {
			bos = new ByteArrayOutputStream();
			ProtostuffIOUtil.writeListTo(bos, list, schema, buffer);
			protostuff = bos.toByteArray();
		} catch (Exception e) {
			throw new RuntimeException("序列化对象列表(" + list + ")发生异常!", e);
		} finally {
			buffer.clear();
			try {
				if (bos != null) {
					bos.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return protostuff;
	}

	/**
	 * 反序列化List集合
	 * 
	 * @param <T>
	 *            Java对象泛型
	 * @param data
	 *            需要反序列化的byte数组
	 * @param clazz
	 *            要进行反序列化成实体的Java类型
	 * @return List集合
	 * @throws IOException
	 *             当发生IO异常时抛出
	 * @throws KutaIllegalArgumentException
	 *             参数错误时抛出
	 */
	@SuppressWarnings("unchecked")
	public static <T> List<T> deserializeList(byte[] data, Class<T> targetClass)
			throws IOException, KutaIllegalArgumentException {
		if (data == null || data.length == 0) {
			throw new KutaIllegalArgumentException("byte数组不能为空!");
		}
		Schema<T> schema = RuntimeSchema.getSchema(targetClass);
		List<T> result = null;
		try {
			result = ProtostuffIOUtil.parseListFrom(new ByteArrayInputStream(data), schema);
		} catch (IOException e) {
			throw new RuntimeException("反序列化对象列表发生异常!", e);
		}
		return result;
	}

	/**
	 * 序列化Map结构数据
	 * 
	 * @param <K>
	 *            map-key泛型
	 * @param <V>
	 *            map-value泛型
	 * @param map
	 *            需要序列化为byte数组的哈希map
	 * @return key和value都为byte数组类型的哈希map
	 * @throws KutaIllegalArgumentException
	 *             参数错误时抛出
	 */
	public static <K, V> Map<byte[], byte[]> serializeMap(Map<K, V> map) throws KutaIllegalArgumentException {
		if (map == null || map.size() == 0) {
			throw new KutaIllegalArgumentException("集合不能为空!");
		}
		Map<byte[], byte[]> mapX = new HashMap<byte[], byte[]>();
		for (Map.Entry<K, V> entry : map.entrySet()) {
			mapX.put(serialize(entry.getKey()), serialize(entry.getValue()));
		}
		return mapX;
	}

	/**
	 * 反序列化Map
	 * 
	 * @param <K>
	 *            map-key泛型
	 * @param <V>
	 *            map-value泛型
	 * @param data
	 *            需要反序列化的byte数组
	 * @param kClass
	 *            map-key类型
	 * @param vClass
	 *            map-value类型
	 * @return 哈希map
	 * @throws KutaIllegalArgumentException
	 *             参数错误时抛出
	 */
	public static <K, V> Map<K, V> deserializerMap(Map<byte[], byte[]> data, Class<K> kClass, Class<V> vClass)
			throws KutaIllegalArgumentException {
		if (data == null || data.size() == 0) {
			throw new KutaIllegalArgumentException("集合不能为空!");
		}
		Map<K, V> map = new HashMap<>();
		for (Map.Entry<byte[], byte[]> entry : data.entrySet()) {
			map.put(deserialize(entry.getKey(), kClass), deserialize(entry.getValue(), vClass));
		}
		return map;
	}
}
