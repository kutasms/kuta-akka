package com.kuta.base.json;

import java.io.IOException;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import com.alibaba.fastjson.serializer.SerializeWriter;
import com.kuta.base.util.KSFTimeUtil;

public class JSONDateWithMillsSerializer implements ObjectSerializer {

public static JSONDateWithMillsSerializer instance;
	
	public static JSONDateWithMillsSerializer getInstance() {
		if(instance == null) {
			instance = new JSONDateWithMillsSerializer();
		}
		return instance;
	}
	
	@Override
	public void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType, int features)
			throws IOException {
		// TODO Auto-generated method stub
		SerializeWriter out = serializer.out;
		if(object == null) {
			out.writeNull();
			return;
		}
		SimpleDateFormat fomatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSSSS");
		out.writeString(fomatter.format(object));
	}

}
