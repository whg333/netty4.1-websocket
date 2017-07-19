package com.whg.web;

import java.io.IOException;
import java.text.ParseException;

import org.dom4j.DocumentException;
import org.springframework.context.ApplicationContext;

import com.whg.backend.bo.Base;
import com.whg.backend.bo.BoFactory;
import com.whg.util.Constant;
import com.whg.util.Log4jProperties;
import com.whg.util.exception.ErrorCode;
import com.whg.websocket.server.WebSocketServer;

/**
 * <p>后端业务逻辑静态初始化启动类,包括一些静态资源检测加载,定时任务等</p>
 * @author wanghg
 * @date 2017年3月31日 下午5:50:14
 */
public class BootStrap {
	
    private final ApplicationContext ac;

    private final String contextPath;

    public BootStrap(ApplicationContext ac, String contextPath) {
        this.ac = ac;
        this.contextPath = contextPath;
    }

    public void startServer() {
        try {
            loadStaticData();
            startWebSocketServer();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }
    
	private void loadStaticData() throws DocumentException, ParseException, IOException, ClassNotFoundException {
        //ClassLoader loader = Thread.currentThread().getContextClassLoader();
        //InputStream officerDataInput = loader.getResourceAsStream("com/hoolai/huaTeng/flower/flowers.xml");

        //FlowerXmlData flowerXmlData = (FlowerXmlData) ac.getBean("flowerXmlData");
        //FlowerJsonData flowerJsonData = (FlowerJsonData) ac.getBean("flowerJsonData");
        //officerData.init(officerDataInput);
        //System.out.println(flowerData.getFlowerMap().size());
        
    	Log4jProperties.init();
//        BrowserInfoUtil.init();
        BoFactory boFactory = (BoFactory) ac.getBean("boFactory");
        Base.init(boFactory);
        
        loadNativeComponent();
        
        ErrorCode.init();
        
        if(Constant.isLocalPlatform()){
        	//
        }
    }
    
    private void loadNativeComponent() {
        if (Constant.LOAD_PLATFORM_INFO && Constant.ANTI_PLUG_OPEN) {
            try {
                System.load(contextPath + "WEB-INF/jni_lib/libantiPlugin.so");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    private void startWebSocketServer() {
    	new WebSocketServerThread().start();
	}
    
    private class WebSocketServerThread extends Thread{
    	@Override
    	public void run() {
    		try {
				new WebSocketServer(ac).start();
			} catch (Exception e) {
				e.printStackTrace();
			}
    	}
    }

}
