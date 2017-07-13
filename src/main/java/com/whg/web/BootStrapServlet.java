package com.whg.web;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * <p>描述：提供给Web端初始化的加载类，在内部再启动BootStrap，配置在web.xml里面</p>
 * @author whg
 */
@SuppressWarnings("serial")
public class BootStrapServlet extends HttpServlet {

	@Override
    public void init() throws ServletException {
        String contextPath = getServletContext().getRealPath("/");
        ApplicationContext ac = WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext());
        BootStrap bootStrap = new BootStrap(ac, contextPath);
        bootStrap.startServer();
    }
}
