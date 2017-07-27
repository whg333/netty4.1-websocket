package com.whg.web.filter;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.whg.util.string.StringUtil;

/**
 * <p>JSON头部过滤器：客户端传递过来的请求头header中可能没有标识内容Content-Type为JSON类型。</p>
 * <p>所以需要我们帮忙添加一下Content-Type为application/json;charset=utf-8，达到无缝整合Spring MVC的JSON转换器</p>
 * @author wanghg
 * @date 2017年3月29日 下午6:58:54
 */
public class JsonHeaderFilter implements Filter {
	
	private static final Logger logger = LoggerFactory.getLogger(JsonHeaderFilter.class);

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		chain.doFilter(new JsonHeaderRequest((HttpServletRequest) request), response);
	}

	private static class JsonHeaderRequest extends HttpServletRequestWrapper {

		private static final Set<String> contentTypeNames = new HashSet<String>(4);
		static {
			contentTypeNames.add("Content-Type");
			contentTypeNames.add("content-type");
			contentTypeNames.add("ContentType");
			contentTypeNames.add("contenttype");
		};

		public JsonHeaderRequest(HttpServletRequest request) {
			super(request);
		}

		@SuppressWarnings("rawtypes")
		@Override
		public Enumeration getHeaders(String name) {
			//logger.info("getHeaders:"+name);
			if(!isContentTypeHeader(name)){
				return super.getHeaders(name);
			}
			return new Enumeration<String>() {
				private boolean hadGetted = false;
				@Override
				public String nextElement() {
					if (hadGetted) {
						throw new NoSuchElementException("hadGetted element of 'Content-Type'");
					} else {
						hadGetted = true;
						return "application/json;charset=utf-8";
					}
				}
				@Override
				public boolean hasMoreElements() {
					return !hadGetted;
				}
			};
		}
		
		@Override
		public String getHeader(String name) {
			//logger.info("getHeader:"+name);
			if(!isContentTypeHeader(name)){
				return super.getHeader(name);
			}
			return "application/json;charset=utf-8";
		}
		
		private boolean isContentTypeHeader(String name){
			return !StringUtil.isEmpty(name) && contentTypeNames.contains(name);
		}
	}

	@Override
	public void destroy() {

	}

}
