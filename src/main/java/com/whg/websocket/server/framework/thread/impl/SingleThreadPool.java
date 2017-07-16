package com.whg.websocket.server.framework.thread.impl;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import com.whg.util.collection.map.IntHashMap;
import com.whg.websocket.server.framework.thread.OrderedExecutor;
import com.whg.websocket.server.framework.thread.ThreadNameFactory;

public class SingleThreadPool implements OrderedExecutor {
	
	private final IntHashMap<Executor> poolMap;
	
	public SingleThreadPool(int[] keys) {
		this(keys, "SingleThreadPool");
	}

	public SingleThreadPool(int[] keys, String name) {
		if(keys.length<= 0){
			throw new IllegalArgumentException("thread pool num is zero !?");
		}
		
		poolMap = new IntHashMap<Executor>(keys.length);
		for (int key:keys) {
			poolMap.put(key, Executors.newSingleThreadExecutor(new ThreadNameFactory(name)));
		}
	}

	@Override
	public void execute(int key, Runnable task) {
		Executor executor = selectExecutor(key);
		executor.execute(task);
	}
	
	private Executor selectExecutor(int key){
		Executor executor = poolMap.get(key);
		if(executor == null){
			throw new NullPointerException("key="+key+" not found SingleThreadPool");
		}
		return executor;
	}
	
}
