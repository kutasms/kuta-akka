package com.kuta.base.serialization;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.kuta.base.util.KutaConsoleUtil;

import junit.framework.TestCase;

public class ProtostuffUtilTest extends TestCase {
	
	public void testMapOrList(){
		Map<String, Object> map = new HashMap<>();
        map.put("key1", "butioy");
        map.put("key2", "protostuff");
        map.put("key3", "serialize");
        
        List<String> list = new ArrayList<>();
        list.add("fiallfewafew");
        list.add("biialzzz");
//        SerializeDeserializeWrapper wrapper = SerializeDeserializeWrapper.builder(list);

        byte[] serializeBytes = ProtostuffUtil.serialize(map);
        System.out.println("序列化map集合二进制数组长度 length=" + serializeBytes.length);
        System.out.println("bytes:" + Arrays.toString(serializeBytes));
        ProtostuffObjectWrapper deserializeWrapper = ProtostuffUtil.deserialize(serializeBytes, ProtostuffObjectWrapper.class);
        System.out.println("反序列化map对象：" + deserializeWrapper.getData());
	}
}
