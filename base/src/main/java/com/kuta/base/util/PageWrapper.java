package com.kuta.base.util;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import com.alibaba.fastjson.serializer.SerializeWriter;
import com.github.pagehelper.PageInfo;
import com.kuta.base.exception.KutaIllegalArgumentException;

public class PageWrapper<T> extends PageInfo<T>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 788980498583334261L;
	
	private JSONObject json;
	
	public JSONObject getJson() {
		return json;
	}

	public class JSONDateSerializer implements ObjectSerializer{

	    public void write(JSONSerializer serializer, Object object,
	            Object fieldName, Type fieldType) throws IOException {
	        
	    }

		@Override
		public void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType, int features)
				throws IOException {
			// TODO Auto-generated method stub
			SerializeWriter out = serializer.getWriter();
	         if (object == null) {
	                serializer.getWriter().writeNull();
	                return;
	         }
	         out.write(KutaTimeUtil.formatWithMill((Date)object));
		}
	}
	
	public PageWrapper(List<T> list){
		super(list);
		json = new JSONObject();
//		ParserConfig config = new ParserConfig();
//		
//		config.putDeserializer(BigDecimal.class, new BigDecimalDeserializer());
//		config.putDeserializer(BigInteger.class, new BigIntegerDeserializer());
		
		json.put("list", JSONArray.toJSON(this.list));
		json.put("total_page", this.getPages());
		json.put("total", this.total);
		
	}

	public boolean empty() {
		return KutaUtil.isEmptyColl(this.list);
	}
	
	public T pop() {
		if(this.list!=null) {
			if(this.list.size() <= 0) {
				throw new KutaIllegalArgumentException("列表数据为空,不能执行Pop操作");
			}
			T item = this.list.get(0);
			this.list.remove(0);
			return item;
		}
		return null;
	}
	
	public List<T>pop(int count){
		if(this.list!=null) {
			if(this.list.size() < count) {
				throw new KutaIllegalArgumentException("列表数据不足" + count);
			}
			List<T> sub = this.list.subList(0, count -1);
			this.list.removeAll(sub);
			return sub;
		}
		return null;
	}
	
	public String toJSONString() {
		return json.toJSONString();
	}
	public String toJSONString(boolean prettyFormat) {
		return JSONObject.toJSONString(json, prettyFormat);
	}
}
