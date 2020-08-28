package com.kuta.base.database.mongodb;

import java.lang.reflect.ParameterizedType;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.kuta.base.util.KutaByteUtil;
import com.kuta.base.util.KutaUtil;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.UpdateResult;

/**
 * mongodb数据操作基类
 * */
public abstract class KutaMongoDao<T extends KutaMongoEntity> {
	
	/**
	 * 集合名称
	 * */
	protected final String collName;
	/**
	 * mongodb集合
	 * */
	private final MongoCollection<Document> coll;
	/**
	 * 数据实体类型
	 * */
	private final Class<T> clazz;
	/**
	 * 构造函数
	 * */
	@SuppressWarnings("unchecked")
	public KutaMongoDao() {
		clazz = (Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
		collName = clazz.getSimpleName();
		
		coll = KutaMongoUtil.getColl(collName);
	}
	
	/**
	 * 插入一个实体对象
	 * @param record 将被插入的实体对象
	 * */
	public void insert(T record) {
		Document document = record.toDocument();
		coll.insertOne(document);
	}
	
	/**
	 * 插入多个实体对象
	 * @param list 实体对象集合
	 * */
	public void insertMany(List<T> list) {
		List<Document> docs = KutaMongoUtil.toDocList(list);
		coll.insertMany(docs);
	}
	
	/**
	 * 以ObjectId时间范围获取查询条件
	 * @param start 起始时间
	 * @param end 结束时间
	 * @return 查询条件的Bson值
	 * */
	public Bson getFiltersByIdRange(Date start, Date end){
		ByteBuffer bufferStart = ByteBuffer.allocate(12);
		bufferStart.put(KutaByteUtil.longHex(start.getTime() + KutaObjectId.INCREMENT, 6));
		bufferStart.putInt(0);
		bufferStart.putShort((short)0);
		bufferStart.flip();
		String startHex = KutaByteUtil.bytesToHexString(bufferStart.array());
		ByteBuffer bufferEnd = ByteBuffer.allocate(12);
		bufferEnd.put(KutaByteUtil.longHex(end.getTime() + KutaObjectId.INCREMENT, 6));
		bufferEnd.putInt(0);
		bufferEnd.putShort((short)0);
		bufferEnd.flip();
		String endHex = KutaByteUtil.bytesToHexString(bufferEnd.array());
		return Filters.and(Filters.gte("_id", startHex), Filters.lte("_id", endHex));
	}
	
	
	
	/**
	 * 根据mongodb集合中的ObjectId更新数据
	 * @param record 需要更新的对象
	 * @return 数据更新结果
	 * @see UpdateResult
	 * */
	public UpdateResult updateByKutaObjectId(T record) {
		Document document = KutaMongoUtil.toDocument(record);
		document.remove("_id");
		return coll.updateOne(Filters.eq(record.getId()), new Document("$set",document));
	}
	
	
	/**
	 * <p>按条件搜索数据</p>
	 * <p>{@code 示例代码:this.find(Filters.eq("age",2));}</p>
	 * @param filter 搜索条件
	 * @return 查询结果
	 * @see Filters
	 * */
	public List<T> find(Bson filter) {
		MongoCursor<Document> iterator = coll.find(filter).iterator();
		List<T> list = KutaMongoUtil.toBean(iterator, clazz);
		return list;
	}
	
	/**
	 * 从mongodb查询单条数据
	 * @param id 数据id
	 * @return 实体对象
	 * */
	public T get(String id) {
		Document document = coll.find(Filters.eq(id)).first();
		if(KutaUtil.isValueNull(document)) {
			return null;
		}
		return KutaMongoUtil.toBean(document, clazz);
	}
	
	/**
	 * <p>查找结果并返回{@code List<Document>}</p>
	 * <p>示例代码:{@code this.findWithDoc(Filters.eq("age",2));}</p>
	 * @param filter 查询条件
	 * @return Document集合
	 * @see MongoCursor
	 * @see Document
	 * */
	public List<Document> findWithDoc(Bson filter){
		MongoCursor<Document> iterator = coll.find(filter).iterator();
		if(!iterator.hasNext()) {
			return null;
		}
		List<Document> list = new ArrayList<Document>();
		iterator.forEachRemaining(doc->{
			list.add(doc);
		});
		return list;
	}
}
