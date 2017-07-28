package com.whg.web.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.whg.websocket.server.framework.request.JsonRequest;

@Controller
@ResponseBody
@RequestMapping("/serviceController")
public class ServiceController {

	@RequestMapping(value="/dispatch")
	public Map<String, Object> dispatch(@RequestBody JsonRequest request){
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("request", request);
		result.put("status", 200);
		return result;
	}
	
	@RequestMapping(value="/dispatch2")
	public Map<String, Object> dispatch2(@RequestParam String request){
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("request", JSON.parseObject(request, JsonRequest.class));
		result.put("status", 200);
		return result;
	}
	
}
