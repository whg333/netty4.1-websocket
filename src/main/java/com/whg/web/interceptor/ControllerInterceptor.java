package com.whg.web.interceptor;

import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.whg.util.reflect.ReflectUtil;
import com.whg.util.string.StringUtil;

public class ControllerInterceptor implements HandlerInterceptor {

	private static final Logger logger = LoggerFactory.getLogger(ControllerInterceptor.class);
	
    /** 
     * 在业务处理器处理请求之前被调用<br>
     * <br>
     * 如果返回false<br>
     *     从当前的拦截器往回执行所有拦截器的afterCompletion(),再退出拦截器链 <br>
     * <br>
     * 如果返回true<br>
     *    执行下一个拦截器,直到所有的拦截器都执行完毕
     *    再执行被拦截的Controller
     *    然后进入拦截器链,
     *    从最后一个拦截器往回执行所有的postHandle()
     *    接着再从最后一个拦截器往回执行所有的afterCompletion()
     */  
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    	//logger.info("preHandle...");
    	
    	//HttpHeaderContext.addHttp(request, headerMap(request));
    	//logger.info(headerMap(request).toString());
    	String queryStr = request.getQueryString();
    	logger.info(ReflectUtil.getField(handler, "bean").getClass().getSimpleName()+", "+request.getRequestURI()+", "+request.getMethod()
    		+", "+paramMap(request)+(queryStr == null ? "" : ", "+queryStr));
    	
    	//返回json格式，其实mvc.xml的mappingJacksonHttpMessageConverter会自动处理了
    	//response.setContentType("application/json;charset=UTF-8");
    	
    	return true;
    }
    
    @SuppressWarnings("unchecked")
	private Map<String, String> headerMap(HttpServletRequest request){
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
	private Map<String, String> paramMap(HttpServletRequest request){
    	Map<String, String> paramMap = new HashMap<String, String>();
    	Map<String, String[]> map = request.getParameterMap();
    	for(Object key:map.keySet()){
    		paramMap.put(key.toString(), Arrays.toString(map.get(key)));
    	}
    	return paramMap;
    }

    //在业务处理器处理请求执行完成后,生成视图之前执行的动作
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		//logger.info("postHandle...");
    }

    /** 
     * 在DispatcherServlet完全处理完请求后被调用<br>
     * <br>
     * 当有拦截器抛出异常时,会从当前拦截器往回执行所有的拦截器的afterCompletion() 
     */  
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    	//logger.info("afterCompletion...");
    	//TODO 想办法捕获到JSON解析字段所报的异常信息
    	try{
    		//response.coyoResponse.status是tomcat的Response类中的字段
    		Object innerResponse = ReflectUtil.getField(response, "response");
    		Object coyoteResponse = ReflectUtil.getField(innerResponse, "coyoteResponse");
			String statusStr = ReflectUtil.getField(coyoteResponse, "status").toString();
			logHttpErrorIfNeed(handler, statusStr, "tomcat");
    	}catch(Exception e){
    		try {
				//_status是Jetty的Response类中的字段
				String statusStr = ReflectUtil.getField(response, "_status").toString();
				logHttpErrorIfNeed(handler, statusStr, "jetty");
			} catch (Exception e2) {
				e2.printStackTrace();
			}
    	}
    	if(ex != null){
    		logger.error("afterCompletion error:"+ex.getMessage());
    	}
    }
    
    private void logHttpErrorIfNeed(Object handler, String statusStr, String serverName){
    	if(!StringUtil.isNumber(statusStr)){
    		return;
    	}
    	
    	int status = Integer.parseInt(statusStr);
    	if(status == HttpServletResponse.SC_MOVED_TEMPORARILY){
    		logger.info(serverName+" : "+handler+" : "+statusStr+" REDIRECT");
    	}else if(status != HttpServletResponse.SC_OK){
			logger.error(serverName+"\n"+handler+"\n"+statusStr+" ERROR");
		}
    }

}
