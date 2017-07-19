package com.whg.util.operate;

import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>描述：为每个Http工作线程绑定对应的Http Header内容，用于验证Http请求是否包含token</p>
 * @author whg
 * @date 2016-3-18 下午07:24:40
 */
public class HttpContext {

	private static ThreadLocal<HttpServletRequest> httpRequests = new ThreadLocal<HttpServletRequest>();
	private static ThreadLocal<Map<String, String>> httpHeaders = new ThreadLocal<Map<String, String>>();
	
	public static HttpServletRequest getRequest() {
		HttpServletRequest request = httpRequests.get();
		if(request == null){
			return null;
		}
		return request;
	}
	
	public static String getHeader(String key) {
		Map<String, String> httpHeaderMap = httpHeaders.get();
		if(httpHeaderMap == null){
			return null;
		}
		return httpHeaderMap.get(key);
	}
	
	public static void addHttp(HttpServletRequest request){
		//System.out.println(Thread.currentThread().getName()+" addHeaders...");
		httpRequests.set(request);
		httpHeaders.set(headerMap(request));
	}
	
	@SuppressWarnings("unchecked")
	public static Map<String, String> headerMap(HttpServletRequest request){
    	Enumeration<String> names = request.getHeaderNames();
    	Map<String, String> headerMap = new HashMap<String, String>();
    	while(names.hasMoreElements()){
    		String name = names.nextElement();
    		headerMap.put(name, request.getHeader(name));
    	}
    	//logger.info("header : "+headerMap.toString());
    	return headerMap;
    }
	
	@SuppressWarnings("unchecked")
	public Map<String, String> paramMap(HttpServletRequest request){
    	Map<String, String> paramMap = new HashMap<String, String>();
    	Map<String, String[]> map = request.getParameterMap();
    	for(Object key:map.keySet()){
    		paramMap.put(key.toString(), Arrays.toString(map.get(key)));
    	}
    	return paramMap;
    }
	
	public static void removeHttp(){
		httpRequests.remove();
		httpHeaders.remove();
		//System.out.println(Thread.currentThread().getName()+" removeHeaders...");
	}
	
}
