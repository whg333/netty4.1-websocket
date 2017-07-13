package com.whg.util.json.base;

import org.springframework.stereotype.Component;

@Component
public class BaseJsonData /*extends JsonData<ConfigBase>*/{

//	@PostConstruct
//	@Override
//	public void init() {
//		try {
//			initData("config_base.json", ConfigBase.class);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}

	public int getBaseIntValue(String key) {
//		Assert.isTrue(!StringUtil.isEmpty(key));
//		BaseProperty baseProperty = data.base_info.get(key);
//		if(baseProperty == null){
//			throw new IllegalArgumentException("未识别的基础key="+key+"值");
//		}
//		return baseProperty.value;
		return 1;
	}
	
}
