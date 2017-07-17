package com.whg.test;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.cglib.reflect.FastClass;
import org.springframework.cglib.reflect.FastConstructor;

import com.alibaba.fastjson.JSON;
import com.esotericsoftware.reflectasm.ConstructorAccess;

public class ConstructorTest {

	public static void main(String[] args) {
		String json = "['1',2.1,3,true]";
		
		List<String> argStrList = new ArrayList<String>();
		argStrList.add("1");
		argStrList.add("2.1");
		argStrList.add("3");
		argStrList.add("true");
		
		Object[] objs = null;
		
//		objs = jsonParse(json);
//		System.out.println(Arrays.toString(objs));
//		
//		objs = reflectParse(argStrList);
//		System.out.println(Arrays.toString(objs));
		
		long begin = System.currentTimeMillis();
		for(int i=0;i<1000;i++){
//			objs = jsonParse(json);
			objs = reflectParse(argStrList);
//			objs = asmParse(argStrList);
//			objs = fastParse(argStrList);
			//System.out.println(i+":"+Arrays.toString(objs));
		}
		System.out.println("consume "+(System.currentTimeMillis()-begin));
	}

	private static Object[] jsonParse(String json){
		return JSON.parseObject(json, Object[].class);
	}
	
	private static Map<Class<?>, Constructor<?>> constructorMap = new HashMap<Class<?>, Constructor<?>>();
	
	private static Object[] reflectParse(List<String> argStrList){
		Class<?>[] types = {String.class, Double.class, Integer.class, Boolean.class};
		Object[] objs = new Object[types.length];
		try {
			for(int i=0;i<types.length;i++){
				Class<?> clazz = types[i];
				Constructor<?> c = constructorMap.get(clazz);
				if(c == null){
					c = clazz.getConstructor(String.class);
					constructorMap.put(clazz, c);
				}
				//TODO 缓存基础对象类型的String构造器
				objs[i] = c.newInstance(argStrList.get(i));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return objs;
	}
	
	private static Object[] asmParse(List<String> argStrList){
		Class<?>[] types = {String.class, Double.class, Integer.class, Boolean.class};
		Object[] objs = new Object[types.length];
		try {
			for(int i=0;i<types.length;i++){
				Class<?> clazz = types[i];
				//TODO 缓存基础对象类型的String构造器
				objs[i] = ConstructorAccess.get(clazz).newInstance();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return objs;
	}
	
	private static Map<Class<?>, FastConstructor> fastConstructorMap = new HashMap<Class<?>, FastConstructor>();
	
	private static Object[] fastParse(List<String> argStrList){
		Class<?>[] types = {String.class, Double.class, Integer.class, Boolean.class};
		Object[] objs = new Object[types.length];
		try {
			for(int i=0;i<types.length;i++){
				Class<?> clazz = types[i];
				FastConstructor c = fastConstructorMap.get(clazz);
				if(c == null){
					//TODO 缓存基础对象类型的String构造器
					FastClass fastClass = FastClass.create(clazz);
					c = fastClass.getConstructor(new Class[]{String.class});
					fastConstructorMap.put(clazz, c);
				}
				
				objs[i] = c.newInstance(new Object[]{argStrList.get(i)});
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return objs;
	}
	
}
