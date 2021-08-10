package com.kuta.base.json;

import java.lang.reflect.Type;
import java.math.BigDecimal;

import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;

public class BigDecimalDeserializer implements ObjectDeserializer {

	

	@Override
	public int getFastMatchToken() {
		// TODO Auto-generated method stub
		return 0;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T deserialze(DefaultJSONParser parser, Type type, Object fieldName) {
		// TODO Auto-generated method stub
		return (T) new BigDecimal(parser.getLexer().stringVal());
	}

}
