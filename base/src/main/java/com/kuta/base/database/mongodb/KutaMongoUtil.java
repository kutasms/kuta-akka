package com.kuta.base.database.mongodb;

import java.lang.reflect.Type;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.bson.Document;
import org.bson.json.Converter;
import org.bson.json.JsonWriterSettings;
import org.bson.json.StrictJsonWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.kuta.base.json.BsonLong2JSONDeserializer;
import com.kuta.base.json.DateWithMills2JSONDeserializer;
import com.kuta.base.json.JSON2BsonLongSerializer;
import com.kuta.base.json.JSONDateWithMillsSerializer;
import com.kuta.base.util.KSFUtil;
import com.kuta.common.config.utils.PropertyUtils;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;

/**
 * mongodb工具箱
 */
public class KutaMongoUtil {
	/**
	 * 日志接口
	 * */
	private final static Logger logger = LoggerFactory.getLogger(KutaMongoUtil.class);
	/**
	 * mongodb-host
	 * */
	private static final String MONGO_HOST = PropertyUtils.getProperty("mongo", "mongo.host");
	/**
	 * mongodb-user
	 * */
	private static final String MONGO_USER = PropertyUtils.getProperty("mongo", "mongo.user");
	/**
	 * mongodb-password
	 * */
	private static final String MONGO_PSWD = PropertyUtils.getProperty("mongo", "mongo.pswd");
	/**
	 * mongodb-port
	 * */
	private static final Integer MONGO_PORT = PropertyUtils.getInteger("mongo", "mongo.port");
	/**
	 * mongodb-dbname
	 * */
	private static final String MONGO_DBNM = PropertyUtils.getProperty("mongo", "mongo.dbname");
	/**
	 * alibaba fastjson的序列化相关配置
	 * <p>用于bson与fastjson的兼容处理</p>
	 * */
	private static final SerializeConfig fastJsonConfig;
	/**
	 * alibaba fastjson的转换相关配置
	 * <p>用于bson与fastjson的兼容处理</p>
	 * */
	private static final ParserConfig fastjsonParserConfig;
	/**
	 * mongodb客户端代理对象
	 * */
	private static final MongoClient client;
	static {
		logger.info("启动MongoDB连接,host:{},port:{},user:{}:pswd:{},dbnm:{}", MONGO_HOST, MONGO_PORT, MONGO_USER,
				"***", MONGO_DBNM);
		ServerAddress serverAddress = new ServerAddress(MONGO_HOST, MONGO_PORT);
		List<ServerAddress> addrs = new ArrayList<ServerAddress>();
		addrs.add(serverAddress);

		List<MongoCredential> credentials = new ArrayList<MongoCredential>();
		MongoCredential credential = MongoCredential.createCredential(MONGO_USER, MONGO_DBNM, MONGO_PSWD.toCharArray());
		credentials.add(credential);
		client = new MongoClient(serverAddress, credential, MongoClientOptions.builder().build());
	
		fastJsonConfig = new SerializeConfig();
		fastJsonConfig.put(BigInteger.class, JSON2BsonLongSerializer.getInstance());
		fastJsonConfig.put(Long.class, JSON2BsonLongSerializer.getInstance());
		fastJsonConfig.put(Long.TYPE, JSON2BsonLongSerializer.getInstance());
		fastJsonConfig.put(Date.class, JSONDateWithMillsSerializer.getInstance());
		fastjsonParserConfig = new ParserConfig();
		fastjsonParserConfig.putDeserializer(BigInteger.class, BsonLong2JSONDeserializer.getInstance());
		fastjsonParserConfig.putDeserializer(Long.class, BsonLong2JSONDeserializer.getInstance());
		fastjsonParserConfig.putDeserializer(Long.TYPE, BsonLong2JSONDeserializer.getInstance());
		fastjsonParserConfig.putDeserializer(Date.class, DateWithMills2JSONDeserializer.getInstance());
	}
	
	/**
	 * json写入器设置
	 * */
	private static final JsonWriterSettings settings = JsonWriterSettings.builder().int64Converter(new Converter<Long>() {
		public void convert(Long value, StrictJsonWriter writer) {
			writer.writeNumber(value.toString());
		}
	}).build();
	
	/**
	 * 获取mongodb的客户端代理对象
	 * @return mongodb客户端代理
	 * */
	public static MongoClient getClient() {
		return client;
	}

	/**
	 * 关闭客户端代理
	 * */
	public static void close(MongoClient client) {
		client.close();
	}

	/**
	 * 获取集合
	 * @param collName 集合名称
	 * @return 集合
	 * @see MongoCollection
	 * @see Document
	 */
	public static MongoCollection<Document> getColl(String collName) {
		return client.getDatabase(MONGO_DBNM).getCollection(collName);
	}

	/**
	 * 获取继承自Document的集合
	 * @param collName 集合名称
	 * @param clazz 文档类型
	 * @return mongodb集合
	 * @see MongoCollection
	 */
	public static <TDoc extends Document> MongoCollection<TDoc> getColl(String collName,
			Class<TDoc> clazz) {
		return client.getDatabase(MONGO_DBNM).getCollection(collName, clazz);
	}

	/**
	 * 创建集合
	 * @param collName 集合名称
	 * */
	public static void createColl(String collName) {
		client.getDatabase(MONGO_DBNM).createCollection(collName);
	}

	/**
	 * 实体集合转换为文档集合
	 * @param list 实体集合
	 * @return 文档集合
	 * */
	public static <T> List<Document> toDocList(List<T> list){
		if(KSFUtil.isEmptyColl(list)) {
			return null;
		}
		List<Document> docs = new ArrayList<>();
		list.forEach(t->{
			docs.add(toDocument(t));
		});
		return docs;
	}
	
	/**
	 * 实体转换为文档
	 * @param entity 实体
	 * @return 文档
	 * */
	public static <T> Document toDocument(T entity) {
		
		String json = JSONObject.toJSONString(entity,fastJsonConfig);
		return Document.parse(json);
	}
	
	/**
	 * 文档转换为实体
	 * @param document 文档
	 * @param clazz 实体类型
	 * @return 实体对象
	 * */
	public static <T> T toBean(Document document, Class<T> clazz) {
		return JSONObject.parseObject(document.toJson(settings), clazz);
	}
	
	/**
	 * 将查询出的游标数据转换为实体集合
	 * @param iterator 文档游标
	 * @param clazz 实体类型
	 * @return 实体集合
	 * */
	public static <T> List<T> toBean(MongoCursor<Document> iterator, Class<T> clazz) {
		List<T> list = new ArrayList<>();
		while (iterator.hasNext()) {
			String json = iterator.next().toJson(settings);
			T t = JSONObject.parseObject(json, (Type)clazz, fastjsonParserConfig, JSONObject.DEFAULT_PARSER_FEATURE);
			list.add(t);
		}
		return list;
	}
}
