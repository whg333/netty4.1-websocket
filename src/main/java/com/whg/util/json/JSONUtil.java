package com.whg.util.json;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

/**
 * <p>jackson json 实践可参考 http://zuoge85.iteye.com/blog/1890895</p>
 * @author wanghg
 * @date 2017年5月5日 下午5:32:57
 */
public class JSONUtil {
	
	private static final ObjectMapper om = new ObjectMapper();
	private static final ObjectMapper ignoreUnknownPropertiesObjectMapper = new ObjectMapper();
	static{
		ignoreUnknownPropertiesObjectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		ignoreUnknownPropertiesObjectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS,false);
		ignoreUnknownPropertiesObjectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
	}
	
	private static final ObjectMapper ignoreAnnotationsPropertiesObjectMapper = new ObjectMapper();
	static{
		ignoreAnnotationsPropertiesObjectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		ignoreAnnotationsPropertiesObjectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS,false);
		ignoreAnnotationsPropertiesObjectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		ignoreAnnotationsPropertiesObjectMapper.configure(MapperFeature.USE_ANNOTATIONS, false);
	}
	
	public static <T> String toJSONignoreAnnotations(T object){
		try {
			return ignoreAnnotationsPropertiesObjectMapper.writeValueAsString(object);
		} catch (JsonGenerationException e) {
			throw new RuntimeException("JsonGenerationException",e);
		} catch (JsonMappingException e) {
			throw new RuntimeException("JsonMappingException",e);
		} catch (IOException e) {
			throw new RuntimeException("IOException",e);
		}
	}
	
	public static <T> String toJSONwithOutNullProp(T object) {
		try {
			return ignoreUnknownPropertiesObjectMapper.writeValueAsString(object);
		} catch (JsonGenerationException e) {
			throw new RuntimeException("JsonGenerationException",e);
		} catch (JsonMappingException e) {
			throw new RuntimeException("JsonMappingException",e);
		} catch (IOException e) {
			throw new RuntimeException("IOException",e);
		}
	}
	
	public static <T> String toJSON(T object) {
		ObjectMapper mapper = getObjectMapper(false);
		try {
			String jsonStr = mapper.writeValueAsString(object);
			return jsonStr;
		} catch (JsonGenerationException e) {
			throw new RuntimeException("JsonGenerationException",e);
		} catch (JsonMappingException e) {
			throw new RuntimeException("JsonMappingException",e);
		} catch (IOException e) {
			throw new RuntimeException("IOException",e);
		}
	}
	
	//可以使用 键 支持 不带双引号：mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
	@Deprecated
	public static <T> String toJSONWithQuoteFieldName(T object) {
		return removeQuoteFromFieldName(toJSON(object));
	}
	public static String removeQuoteFromFieldName(String jsonStr) {
		
		char[] charArray = jsonStr.toCharArray();
		char[] result = new char[charArray.length];
		int index = 0;
		char c;
		int leftQ = -1;
		int rightQ = -1;
		boolean findingLeftQ = true;
		for (int i = 0; i < charArray.length; i++) {
			c = charArray[i];
			if (':'==c){
				//删掉leftQ和rightQ所在字符
				//倒数第几个位置
				int leftQ4Result = index - (i - leftQ);
				int rightQ4Result = index - (i - rightQ);
				index = removeChar(result, leftQ4Result, index);
				index = removeChar(result, rightQ4Result, index);
				
			}else if ('\"'==c){
				//计算leftQ和rightQ所在位置
				if(findingLeftQ){
					leftQ = i;
					findingLeftQ = false;
				}else{
					rightQ = i;
					findingLeftQ = true;
				}
			}
			//拷贝当前字符
			result[index++] = charArray[i];
		}
		
		return String.valueOf(result).substring(0, index);
	}
	public static int removeChar4Test(char[] result, int position, int index) {
		return removeChar(result, position, index);
	}
	private static int removeChar(char[] result, int position, int index) {
		for (int i = position; i < index; i++) {
			result[i] = result[i+1];
		}
		result[index] = ' ';
		return index - 1;
	}

	/**
	 * 
	 * @param <T>
	 * @param jsonStr
	 * @param clazz
	 * @return
	 */
	public static <T> T fromJSON(String jsonStr, Class<T> clazz) {
		
		ObjectMapper mapper = getObjectMapper();
		T object = null;
		try {
			object = mapper.readValue(jsonStr, clazz);
		} catch (JsonGenerationException e) {
			throw new RuntimeException("JsonGenerationException",e);
		} catch (JsonMappingException e) {
			throw new RuntimeException("JsonMappingException",e);
		} catch (IOException e) {
			throw new RuntimeException("IOException",e);
		}
		return object;
	}

	
	public static <T> T fromJSON(String jsonStr, Class<T> clazz,boolean isIgnoreUnknownProperties) {
		ObjectMapper mapper = getObjectMapper(isIgnoreUnknownProperties);
		T object = null;
		try {
			object = mapper.readValue(jsonStr, clazz);
		} catch (JsonGenerationException e) {
			throw new RuntimeException("JsonGenerationException",e);
		} catch (JsonMappingException e) {
			throw new RuntimeException("JsonMappingException",e);
		} catch (IOException e) {
			throw new RuntimeException("IOException",e);
		}
		return object;
	}
	
	public static <T> T fromJSON(String jsonStr, TypeReference<T> typeReference, boolean isIgnoreUnknownProperties) {
		ObjectMapper mapper = getObjectMapper(isIgnoreUnknownProperties);
		T object = null;
		try {
			object = mapper.readValue(jsonStr, typeReference);
		} catch (JsonGenerationException e) {
			throw new RuntimeException("JsonGenerationException",e);
		} catch (JsonMappingException e) {
			throw new RuntimeException("JsonMappingException",e);
		} catch (IOException e) {
			throw new RuntimeException("IOException",e);
		}
		return object;
	}
	
	public static <T> List<T> fromJSONToList(String jsonStr, Class<T> clazz) {
		return fromJSONToList(jsonStr,clazz,false);
	}
	
	
	
	public static <T> List<T> fromJSONToList(String jsonStr, Class<T> clazz,boolean isIgnoreUnknownProperties) {
		
		ObjectMapper mapper = getObjectMapper();
		try {
			 JsonNode  rootNode = mapper.readTree(jsonStr);
			 Iterator<JsonNode> it=  rootNode.elements();
			 List<T> list =  new ArrayList<T>();
			 while(it.hasNext()){
				 T t = fromJSON(it.next().toString(),clazz);  
				 list.add(t);
			 }
			 return list;
		}catch (JsonGenerationException e) {
			throw new RuntimeException("JsonGenerationException",e);
		} catch (JsonMappingException e) {
			throw new RuntimeException("JsonMappingException",e);
		} catch (IOException e) {
			throw new RuntimeException("IOException",e);
		}
	}
	/**
	 * FIXME: share the object.
	 * @return
	 */
	public static ObjectMapper getObjectMapper() {
		return  getObjectMapper(true) ;
	}
	
	public static ObjectMapper getObjectMapper(boolean isIgnoreUnknownProperties) {
		return isIgnoreUnknownProperties   ?  ignoreUnknownPropertiesObjectMapper : om;
	}

}
