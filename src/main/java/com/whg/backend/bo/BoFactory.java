package com.whg.backend.bo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.whg.util.json.JsonDataQuerier;

/**
 * <p>
 * 基本常用的Repo都Autowired在BoFactory里面了,所以当需要注入(inject)
 * 到BO对象的Repo比较多的话,可以只注入BoFactory,然后通过BoFactory查找对应的Repo
 * </p>
 * @author wanghg
 * @date 2017年3月31日 上午11:43:00
 */
@Component
public class BoFactory {
	
	@Autowired
	private JsonDataQuerier jsonDataQuerier;

	public JsonDataQuerier getJsonDataQuerier() {
		return jsonDataQuerier;
	}
	
}
