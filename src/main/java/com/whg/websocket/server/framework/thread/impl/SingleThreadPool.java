package com.whg.websocket.server.framework.thread.impl;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import com.whg.util.collection.map.IntHashMap;
import com.whg.websocket.server.framework.thread.OrderedThread;
import com.whg.websocket.server.framework.thread.ThreadNameFactory;

public class SingleThreadPool implements OrderedThread {
	
	private final IntHashMap<Executor> poolMap;
	
	public SingleThreadPool(int[] keys) {
		this(keys, "SequenceThreadPool");
	}

	public SingleThreadPool(int[] keys, String name) {
		if(keys.length<= 0){
			throw new IllegalArgumentException("intKeys.length=0");
		}
		
		poolMap = new IntHashMap<Executor>(keys.length);
		for (int key:keys) {
			poolMap.put(key, Executors.newSingleThreadExecutor(new ThreadNameFactory(name)));
		}
	}

	@Override
	public void execute(int key, Runnable task) {
		Executor executor = poolMap.get(key);
		if(executor == null){
			throw new NullPointerException("key="+key+" not found SequenceThread");
		}
		executor.execute(task);
	}
}
