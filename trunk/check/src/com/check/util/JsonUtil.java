package com.check.util;

import java.io.IOException;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

public class JsonUtil {
	
	private static final String JACKSON_OBJECT_MAPPER_BEAN_NAME = "jacksonObjectMapper";// jackson ObjectMapper Bean名称

	public static ObjectMapper getMapper() {
		return (ObjectMapper) SpringUtil.getBean(JACKSON_OBJECT_MAPPER_BEAN_NAME);
	}
	
	// 将对象转换为JSON字符串
	public static String toJson(Object object) {
		ObjectMapper mapper = getMapper();
		try {
			return mapper.writeValueAsString(object);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	// 将JSON字符串转换为对象
	public static <T> T toObject(String json, Class<T> clazz) {
		ObjectMapper mapper = getMapper();
		try {
			return mapper.readValue(json, clazz);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	// 将JSON字符串转换为对象
	@SuppressWarnings("unchecked")
	public static <T> T toObject(String json, TypeReference typeReference) throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper mapper = getMapper();
		return (T)mapper.readValue(json, typeReference);
	}

}