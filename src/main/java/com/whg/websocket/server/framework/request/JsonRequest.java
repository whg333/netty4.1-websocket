package com.whg.websocket.server.framework.request;

import java.lang.reflect.Constructor;

public class JsonRequest extends DefaultRequest{

	@Override
	public Object[] methodArgs(Class<?>[] argTypes) throws Exception{
		Object[] methodArgs = new Object[argTypes.length];
		
		for(int i=0;i<argTypes.length;i++){
			Class<?> clazz = argTypes[i];
			if(clazz == String.class){
				methodArgs[i] = args[i];
			}else{
				Constructor<?> constructor = constructorMap.get(clazz);
				if(constructor == null){
					constructor = clazz.getConstructor(String.class);
					constructorMap.put(clazz, constructor);
				}
				methodArgs[i] = constructor.newInstance(args[i]);
			}
			
		}
		
		return methodArgs;
	}
	
}
