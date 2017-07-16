package com.whg.websocket.server.framework.thread.impl;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import com.whg.util.collection.map.IntHashMap;
import com.whg.websocket.server.framework.thread.OrderedExecutor;
import com.whg.websocket.server.framework.thread.ThreadNameFactory;

/**
 * <p>有序线程池，调用execute(key, task)执行</p>
 * <p>内部结构为key --> SingleThreadExecutor</p>
 * <p>单例线程池确保了按顺序进入队列的任务必须等待前一个任务执行完毕后才轮到自己执行</p>
 * <p>int类型的key是为了方便定义业务模块划分，例如用户user操作，房间room操作</p>
 * @author wanghg
 * @date 2017年7月16日 下午5:00:39
 */
public class OrderedThreadPool implements OrderedExecutor {
	
	private final IntHashMap<Executor> poolMap;
	
	public OrderedThreadPool(int[] keys) {
		this(keys, "OrderedThreadPool");
	}

	public OrderedThreadPool(int[] keys, String name) {
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
			throw new NullPointerException("key="+key+" not found OrderedThreadPool");
		}
		return executor;
	}
	
}
