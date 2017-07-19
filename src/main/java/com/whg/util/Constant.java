package com.whg.util;

import java.io.IOException;
import java.util.Arrays;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.whg.util.platform.Platform;

/**
 * <p>项目中用到的跟平台或环境相关的一些常量</p>
 * @author wanghg
 * @date 2017年3月31日 下午3:45:11
 */
public class Constant {
	
	private static final Logger logger = LoggerFactory.getLogger(Constant.class);
	
	public static final String separator = "_";
	
	public static final String PC_INDEX_PAGE = "/index.hlhtml?";
	
	public static final String H5_INDEX_PAGE = "/h5"+PC_INDEX_PAGE;
    
    public static boolean IS_STATISTIC = false;

    public static int SERVER_ID;
    
    public static String CURRENT_SERVER_URL;
    public static String[] SERVER_URL_LIST;
    
    /** 平台相关常量 */
    public static String API_KEY;

    public static String SECRET_KEY;

    public static String RESOURCE_URL;

    public static String CANVAS_URL;

    public static String BBS_URL;
    
    public static String REDIRECT_URL;
    public static String PC_REDIRECT_URL;
    
    public static String API_PREFIX;

    public static String API_URL;
    
    public static String PAY_URL;
    
    public static final int OPEN_ID_LENGTH = 32;

    public static boolean LOAD_PLATFORM_INFO = false;

    public static String PLATFORM_NAME;

    public static Platform platform;

    public static int PLATFORM_TYPE;
    
    public static int APP_ID;

    public static String APP_NAME;

//    public static boolean IS_DEPLOYING = false;
    
    public static String LOG_LEVEL = "WARN";

    public static String LAN_SUFFIX = "zh_cn";// 默认为中文

    /** 反外挂ID */
    public static boolean ANTI_PLUG_OPEN = false;
    
    public static long LOG_ID;

    public static long MODEL_LOG_ID;

    public static boolean MODEL_CALL_OPEN = false;

    public static boolean IS_TENCENT = false;

    public static boolean JSON_BROKER_ENABLE = false;
    
    public static boolean IS_TEST_SERVER = false;
    
    public static boolean IS_OPEN_UNIONTASK = true;
    
    public static boolean IS_OPEN_ROBMINE_TASK = true;
    
//    public static boolean CAN_CHOOSE_SERVER = false;
    
    public static String TRACK_SN_ID;
    public static String TRACK_CLIENT_ID;
    
    public static String PC_TRACK_GAME_ID;
    public static String IOS_TRACK_GAME_ID;
    public static String NONIOS_TRACK_GAME_ID;
    
    public static String TRACK_SCRIBED_HOST;
    public static int CORE_POOL_SIZE;
    public static int MAX_POOL_SIZE;
    public static int QUEUE_CAPACITY;
    
    public static boolean IS_OPEN_TRACKING = false;
    public static boolean IS_OPEN_MAP_FIGHT = false;
    public static boolean IS_OPEN_DRESS = false;
    
    /** 复杂模式代表的是之前诸多因素影响的那个版本，当前版本是修改数值后的化简模式 */
	public static boolean IS_OPEN_LANDS_SIMPLE_MODE = true;
    
    public static String TENCENT_ACTIVITY_ID;
    
    public static boolean IS_OPEN_EXPEDITIONROAD_TRACKING = false;
    
    public static final boolean IS_OPEN_MATRIX_DEVELOP = true;
    
    public static final boolean IS_OPEN_ARTIFACT = true;
    
    /**是否开启士兵的阵法功能*/
    public static boolean IS_OPEN_SOLDIER_MATRIX = true;
    
    /** 是否使用DB测试代理，即仅读取数据有效，修改数据无效 */
    public static boolean IS_DB_TEST_PROXY;
    
    static {
        try {
            Properties p = new Properties();
            loadPlatformProperties(p);
            loadDBProperties(p);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void loadPlatformProperties(Properties p) throws IOException{
    	p.load(Constant.class.getClassLoader().getResourceAsStream("constant_file.properties"));
        String filePath = p.getProperty("file_path");
        p.clear();
        p.load(Constant.class.getClassLoader().getResourceAsStream(filePath));

        // 调用API的接口前缀名
        API_PREFIX = p.getProperty("api_prefix");
        // 决定index.jsp include的目录 以及 platform_type
        PLATFORM_NAME = p.getProperty("platform_name");

        platform = Platform.valueOf(PLATFORM_NAME);
        LOG_LEVEL = isLocalPlatform() ? "INFO" : "WARN";
        PLATFORM_TYPE = platform.type;
        
        // 传递给前端 请求的服务器地址
        SERVER_URL_LIST = p.getProperty("server_urls").split(",");    
        SERVER_ID = Integer.parseInt(p.getProperty("server_id"));      
        CURRENT_SERVER_URL = SERVER_URL_LIST[SERVER_ID];
        
        // 下载前端文件和images的url
        RESOURCE_URL = p.getProperty("resource_url");
        REDIRECT_URL  = p.getProperty("redirect_url");
        PC_REDIRECT_URL = p.getProperty("pc_redirect_url");
        
        PAY_URL = p.getProperty("pay_url");
        
        // 是否开启维护 default=false
//        if (p.containsKey("deploy_env")) {
//            IS_DEPLOYING = Boolean.parseBoolean(p.getProperty("deploy_env"));
//        }

        if(p.containsKey("is_statistic")) {
            IS_STATISTIC = Boolean.parseBoolean(p.getProperty("is_statistic"));
        }
        
        if(p.containsKey("is_test_server")){
        	IS_TEST_SERVER = Boolean.parseBoolean(p.getProperty("is_test_server"));
        }
        
        // 是否同步平台信息 defalut=false
        if (p.containsKey("load_platform_info")) {
            LOAD_PLATFORM_INFO = Boolean.parseBoolean(p.getProperty("load_platform_info"));
        }

        if (p.containsKey("app_id")) {
            APP_ID = Integer.parseInt(p.getProperty("app_id"));
        }
        
        API_KEY = p.getProperty("api_key");
        SECRET_KEY = p.getProperty("secret_key");
        CANVAS_URL = p.getProperty("canvas_url");
        BBS_URL = p.getProperty("bbs_url");
        API_URL = p.getProperty("api_url");
        APP_NAME = p.getProperty("app_name");
        
//        CAN_CHOOSE_SERVER = Boolean.parseBoolean(p.getProperty("can_choose_server"));
        
        TRACK_SN_ID = p.getProperty("track_sn_id");
        TRACK_CLIENT_ID = p.getProperty("track_client_id");
        
        PC_TRACK_GAME_ID = p.getProperty("pc_track_game_id");
        IOS_TRACK_GAME_ID = p.getProperty("ios_track_game_id");
        NONIOS_TRACK_GAME_ID = p.getProperty("nonios_track_game_id");
        
        TRACK_SCRIBED_HOST = p.getProperty("track_scribed_host");
        CORE_POOL_SIZE = Integer.valueOf(p.getProperty("core_pool_size"));
        MAX_POOL_SIZE = Integer.valueOf(p.getProperty("max_pool_size"));
        QUEUE_CAPACITY = Integer.valueOf(p.getProperty("queue_capacity"));

        // 是否报送模块调用 default=false
        if (p.containsKey("model_call_open")) {
            MODEL_CALL_OPEN = Boolean.parseBoolean(p.getProperty("model_call_open"));
        }

        if (p.containsKey("log_id")) {
            LOG_ID = Long.parseLong(p.getProperty("log_id"));
        }

        if (p.containsKey("model_call_log_id")) {
            MODEL_LOG_ID = Long.parseLong(p.getProperty("model_call_log_id"));
        }

        if (p.containsKey("lan_suffix")) {
            LAN_SUFFIX = p.getProperty("lan_suffix");
        }
        
        if (p.containsKey("is_open_map_fight")) {
        	IS_OPEN_MAP_FIGHT = Boolean.valueOf(p.getProperty("is_open_map_fight"));
        }
        if (p.containsKey("is_open_dress")) {
        	IS_OPEN_DRESS = Boolean.valueOf(p.getProperty("is_open_dress"));
        }
        if (p.containsKey("is_open_lands_simple_mode")) {
        	IS_OPEN_LANDS_SIMPLE_MODE = Boolean.valueOf(p.getProperty("is_open_lands_simple_mode"));
        }
        
        if (p.containsKey("is_open_tracking")) {
        	IS_OPEN_TRACKING = Boolean.valueOf(p.getProperty("is_open_tracking"));
        }

        IS_TENCENT = "qzone".equals(PLATFORM_NAME) || "pengyou".equals(PLATFORM_NAME) || "kaixin".equals(PLATFORM_NAME) || "manyou".equals(PLATFORM_NAME)|| "qplus".equals(PLATFORM_NAME) || "android".equals(PLATFORM_NAME);

        logger.info("SERVER_ID="+SERVER_ID+", SERVER_URL_LIST="+Arrays.toString(SERVER_URL_LIST));
    }
    
	public static void loadDBProperties(Properties p) throws IOException{
    	p.load(Constant.class.getClassLoader().getResourceAsStream("constant_file.properties"));
        String dbFilePath = p.getProperty("db_file_path");
    	p.clear();
    	p.load(Constant.class.getClassLoader().getResourceAsStream(dbFilePath));
    	String dbClient = p.getProperty("db_client");
    	IS_DB_TEST_PROXY = "com.hoolai.sango.repo.ExtendedMemcachedTestClientImpl".equals(dbClient);
    }
    
    public static boolean isUsedCMEMStorage(){
    	return Constant.SERVER_ID == 0 || Constant.SERVER_ID == 1;
    }
    
	public static boolean isLocalPlatform() {
		return platform == Platform.local;
	}

	public static boolean isCurrentServer(int serverId) {
		logger.info("serverId="+serverId+", SERVER_ID="+SERVER_ID);
		return serverId == SERVER_ID;
	}

}
