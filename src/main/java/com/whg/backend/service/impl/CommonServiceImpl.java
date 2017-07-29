package com.whg.backend.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.whg.backend.bo.server.ServerInfo;
import com.whg.backend.service.CommonService;
import com.whg.util.annotation.HttpService;

@HttpService
@Service("commonService")
public class CommonServiceImpl implements CommonService {

	@Override
	public Map<String, Object> getServerList(String token) {
		Map<String, Object> result = new HashMap<String, Object>();
		List<ServerInfo> serverList = new ArrayList<ServerInfo>();
		serverList.add(new ServerInfo("外网测试1服", "ws://120.132.48.219:8080/websocket"));
		serverList.add(new ServerInfo("内网测试1服", "ws://127.0.0.1:8080/websocket"));
		result.put("serverList", serverList);
		return result;
	}

}
