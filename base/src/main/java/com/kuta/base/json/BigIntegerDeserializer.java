package com.kuta.base.json;

import java.lang.reflect.Type;
import java.math.BigInteger;

import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;

public class BigIntegerDeserializer implements ObjectDeserializer{

	@Override
	public BigInteger deserialze(DefaultJSONParser parser, Type type, Object fieldName) {
		// TODO Auto-generated method stub
		return new BigInteger(parser.lexer.stringVal());
	}

	@Override
	public int getFastMatchToken() {
		// TODO Auto-generated method stub
		return 0;
	}

}
