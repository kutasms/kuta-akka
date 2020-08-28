package com.kuta.base.database.mongodb;

import org.apache.commons.codec.DecoderException;
import org.bson.Document;
import org.bson.codecs.pojo.annotations.BsonIgnore;

import com.alibaba.fastjson.annotation.JSONField;
import com.kuta.base.util.KSFUtil;

/**
 * KSF框架Mongodb实体基类
 * */
public class KSFMongoEntity {

	/**
	 * 键字符串值
	 * */
	private String _id;
	
	/**
	 * 将实体转换为Document
	 * @return mongodb文档
	 * @see Document
	 * */
	public Document toDocument() {
		if(KSFUtil.isEmptyString(this._id)) {
			this._id = new KSFObjectId().getStringVal();
		}
		
		return KutaMongoUtil.toDocument(this);
	}

	/**
	 * 获取ID
	 * @return 键的字符串值
	 * */
	@JSONField(name="_id")
	public String getId() {
		return _id;
	}
	
	/**
	 * 获取KSFObjectId对象
	 * @return KSFObjectId对象
	 * */
	@BsonIgnore
	@JSONField(serialize = false)
	public KSFObjectId getKutaId() {
		try {
			return KSFObjectId.parse(this._id);
		} catch (DecoderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 设置id
	 * @param id 键的字符串值
	 * */
	@JSONField(name="_id")
	public void setId(String id) {
		this._id = id;
	}
}
