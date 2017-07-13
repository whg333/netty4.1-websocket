package com.whg.util.json;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.whg.util.json.base.BaseJsonData;

/**
 * <p>JSON配置表查询者，基本所有读取的JSON都应该能在这里查询到</p>
 * @author wanghg
 * @date 2017年4月5日 下午3:08:56
 */
@Component
public class JsonDataQuerier {
	
	@Autowired
	private BaseJsonData baseJsonData;

	public int getBaseIntValue(String key){
		return baseJsonData.getBaseIntValue(key);
	}
	
}
