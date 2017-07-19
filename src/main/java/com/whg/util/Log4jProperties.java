package com.whg.util;

import java.util.Properties;

import org.apache.log4j.PropertyConfigurator;

/**
 * <p>描述：可通过set252Log4j.jsp动态修改Constant.LOG_LEVEL的值来重新动态加载log4j配置</p>
 * <p>PS:在log4j.properties里面先设置为INFO是为了查看启动加载信息，然后在BootStrap里面再启用此类的init方法重新动态加载log4j配置</p>
 * @author whg
 * @date 2016-11-09 上午13:19:07
 */
public class Log4jProperties {
	
	public static void init(){
		Properties pro = new Properties();
		
		//Global logging configuration
		pro.put("log4j.rootLogger", Constant.LOG_LEVEL+", stdout");

		pro.put("log4j.logger.com.hoolai", Constant.LOG_LEVEL);
		pro.put("log4j.logger.com.hoolai.huaTeng.bo.battle", Constant.LOG_LEVEL);
		pro.put("log4j.logger.com.hoolai.keyvalue", "DEBUG");
		pro.put("log4j.logger.com.hoolai.exception.ExceptionHandler", "DEBUG");
		pro.put("log4j.logger.com.hoolai.huaTeng.bo.audit.BlackList", "INFO");
		pro.put("log4j.logger.com.hoolai.huaTeng.job", "INFO");

		pro.put("log4j.appender.empty", "org.apache.log4j.varia.NullAppender");

		//Standard Output
		pro.put("log4j.appender.stdout", "org.apache.log4j.ConsoleAppender");
		pro.put("log4j.appender.stdout.layout", "org.apache.log4j.PatternLayout");
		pro.put("log4j.appender.stdout.layout.ConversionPattern", "[%d{yyyy-MM-dd HH:mm:ss}] [%5p] [%c{1}:%L] [%m]%n");

		//File Output
		pro.put("log4j.appender.fileout", "org.apache.log4j.RollingFileAppender");
		pro.put("log4j.appender.fileout.File", "${catalina.home}/logs/huaTeng/fileout.log");
		pro.put("log4j.appender.fileout.MaxFileSize", "10000KB");
		pro.put("log4j.appender.fileout.layout", "org.apache.log4j.PatternLayout");
		pro.put("log4j.appender.fileout.layout.ConversionPattern", "[%d{yyyy-MM-dd HH:mm:ss}] [%5p] [%c{1}:%L] [%m]%n");

		PropertyConfigurator.configure(pro);
	}
	
}
