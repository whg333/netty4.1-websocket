package com.whg.util.reflect;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

//import com.whalin.MemCached.MemCachedClient;

public class ReflectUtil {
	
//	public static void printMemcachedServer(String repoName, ExtendedMemcachedClient client){
//		MemCachedClient mcc = (MemCachedClient)ReflectUtil.getField(client, "mcc");
//		HoolaiAscIIClient c = (HoolaiAscIIClient)ReflectUtil.getField(mcc, "client");
//		HoolaiSockIOPool pool = (HoolaiSockIOPool)ReflectUtil.getField(c, "pool");
//		String[] servers = (String[])ReflectUtil.getField(pool, "servers");
//		System.out.println(repoName+"="+servers[0]+", "+client);
//	}

    public static Object newInstance(Class<?> clazz){
        try {
            return clazz.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    
    public static Object invokeMethod(Object obj, Method method, Object... args){
        method.setAccessible(true);
        try {
            return method.invoke(obj, args);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    
    public static void setField(Object obj, Field field, Object value){
        field.setAccessible(true);
        try {
            field.set(obj, value);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    
    public static Object getField(Object obj, String fieldName){
        try {
        	Field field = obj.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
        	return field.get(obj);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    
}
