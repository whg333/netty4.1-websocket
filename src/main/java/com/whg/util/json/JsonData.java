package com.whg.util.json;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import com.whg.util.string.StringUtil;

public abstract class JsonData<T>{
	
	protected static final String BASE_PATH = "com/skylinematrix/dragonComming/json/";

	protected static final Logger logger = LoggerFactory.getLogger(JsonData.class);
	
	public T data;
	
	public abstract void init();
	
	protected void initData(String path, Class<T> clazz) throws IOException {
		path = BASE_PATH + path;
    	ClassLoader loader = Thread.currentThread().getContextClassLoader();
    	InputStream input = loader.getResourceAsStream(path);
		if (input == null) {
			throw new IllegalArgumentException("找不到配置文件"+path);
		}
		
		String jsonStr = IOUtils.toString(input);
		Assert.isTrue(!StringUtil.isEmpty(jsonStr));
		//logger.info(jsonStr);
		data = JsonUtil.fromJSON(jsonStr, clazz);
		initForEach();
    }
	
	public void initForEach(){
		
	}
	
}
