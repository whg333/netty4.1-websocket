package com.whg.util.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import com.whg.util.operate.HttpContext;
import com.whg.util.string.StringUtil;

/**
 * 网络工具类
 */
public class NetUtil {

    /**
     * 查找客户端真实IP并转化为long类型
     * 
     * @return
     */
    public static long findClientIP() {
        String ipStr = findClientIPStr();
        return findAndconvertIpToInt(ipStr);
    }

    /**
     * 正常获取服务器IP地址
     * 
     * @return
     */
    public static long findServerIP() {
        String ipStr = findServerIPStr();
        if (ipStr != null) {
            return findAndconvertIpToInt(ipStr);
        }
        return 0;
    }

    /**
     * 获取腾讯服务器IP地址
     * 
     * @return
     */
    public static long findServerIP2() {
        String ipStr = findServerIPStr2();
        if (ipStr != null) {
            return findAndconvertIpToInt(ipStr);
        }
        return 0;
    }
    
    /**
     * 正常获取服务器IP地址
     * 
     * @return
     */
    public static String findServerIPStr4() {
    	 String ipStr = findServerIPStr();
    	if (ipStr != null) {
    		return ipStr;
    	}
    	return "";
    }

    /**
     * 获取linux对应设备上的IP地址
     * 
     * @return
     */
    public static long findServerIP3() {
        String ipStr = findServerIPStr3();
        if (ipStr != null) {
            return findAndconvertIpToInt(ipStr);
        }
        return 0;
    }

    /**
     * 查找IP并把IP地址转化为一个整数
     * 
     * @param ip
     *            IP地址
     * @return ip地址转化为的整形数
     */
    private static long findAndconvertIpToInt(String uiClientIP) {
        long ip = 0;
        if (uiClientIP == null || uiClientIP.length() < 7) {
            return ip;
        }
        String[] ips = uiClientIP.split("\\.");
        ip += Long.parseLong(ips[0]) << 24;
        ip += Long.parseLong(ips[1]) << 16;
        ip += Long.parseLong(ips[2]) << 8;
        ip += Long.parseLong(ips[3]);
        return ip;
    }

    /**
     * 获取客户端的IP地址
     * 
     * @return
     */
    public static String findClientIPStr() {
    	//基于AMF协议的胡莱三国是这样
    	//HttpServletRequest request = FlexContext.getHttpRequest();
    	//HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
    	
    	//基于HTTP协议的花藤是下面这样
    	HttpServletRequest request = HttpContext.getRequest();
    	
        if(request == null) return "127.0.0.1";
        
        String ip = request.getHeader("X-Real-IP");	//在Nginx里面设置的proxy_set_header  X-Real-IP  $remote_addr;
        
        if (StringUtil.isEmpty(ip)) {
            ip = request.getHeader("x-forwarded-for");
        } 
        if (StringUtil.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        } 
        if (StringUtil.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        } 
        if (StringUtil.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        
        String[] ips = ip.split(",");
        
        ip = ips[ips.length - 1].trim();
        return ip;
    }

    /**
     * 获取服务器端IP地址(windows下通用，linux下获取/etc/hosts中配置的localhost的ip地址)
     * 
     * @return
     */
    public static String findServerIPStr() {
        String ip = null;
        InetAddress inet;
        try {
            inet = InetAddress.getLocalHost();
            ip = inet.getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return ip;
    }

    /**
     * 获取服务器IP地址，获取网卡绑定的IP地址(腾讯服务器适用的获取IP的方式)
     * 
     * @return
     */
    @SuppressWarnings({ "unchecked" })
    private static String findServerIPStr2() {
        InetAddress ip = null;
        try {
            Enumeration netInterfaces = NetworkInterface.getNetworkInterfaces();
            while (netInterfaces.hasMoreElements()) {
                NetworkInterface ni = (NetworkInterface) netInterfaces.nextElement();
                ip = (InetAddress) ni.getInetAddresses().nextElement();
                if (!ip.isSiteLocalAddress() && !ip.isLoopbackAddress() && ip.getHostAddress().indexOf(":") == -1) {
                    return ip.getHostAddress();
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String findServerIPStr3() {
        String ip = null;
        BufferedReader bufferedReader = null;
        Process process = null;
        try {
            process = Runtime.getRuntime().exec("ifconfig eth0");
            bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = null;
            int index = -1;
            while ((line = bufferedReader.readLine()) != null) {
                index = line.toLowerCase().indexOf("inet addr");
                if (index >= 0) {
                    ip = findFirstIP(line);
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            bufferedReader = null;
            process = null;
        }

        return ip;
    }

    /**
     * 获取字符串中出现的第一个IP地址
     * 
     * @param info
     * @return
     */
    private static String findFirstIP(String info) {
        String ip = null;
        String regEx = "(((2[0-4]\\d)|(25[0-5]))|(1\\d{2})|([1-9]\\d)|(\\d))[.](((2[0-4]\\d)|(25[0-5]))|(1\\d{2})|([1-9]\\d)|(\\d))[.](((2[0-4]\\d)|(25[0-5]))|(1\\d{2})|([1-9]\\d)|(\\d))[.](((2[0-4]\\d)|(25[0-5]))|(1\\d{2})|([1-9]\\d)|(\\d))";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(info);
        if (m.find()) {
            ip = m.group();
        }
        return ip;
    }

}
