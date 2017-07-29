package com.whg.web.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.whg.websocket.server.framework.request.JsonRequest;

@Controller
@ResponseBody
@RequestMapping("/httpController")
public class HttpController {
	
	@Autowired
	private HttpDispatcher dispatcher;

	@RequestMapping(value="/dispatch")
	public Map<String, Object> dispatch(@RequestBody JsonRequest request){
		return dispatcher.dispatch(request);
	}
	
	@RequestMapping(value="/dispatch2")
	public Map<String, Object> dispatch2(@RequestParam String request){
		return dispatcher.dispatch(JSON.parseObject(request, JsonRequest.class));
	}
	
}
