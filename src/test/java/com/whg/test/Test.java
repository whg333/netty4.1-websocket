package com.whg.test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Test {

	public static void main(String[] args) {
		//ExecutorService executor = Executors.newFixedThreadPool(2);
		ExecutorService executor = Executors.newSingleThreadExecutor();
		
		executor.execute(new SlowThread("slow"));
		executor.execute(new FastThread("fast"));
		
		executor.shutdown();
	}
	
	private static class SlowThread extends Thread{
		public SlowThread(String name){
			super(name);
		}
		@Override
		public void run() {
			try {
				TimeUnit.SECONDS.sleep(3);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println(getName()+" Done!");
		}
	}
	
	private static class FastThread extends Thread{
		public FastThread(String name){
			super(name);
		}
		@Override
		public void run() {
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println(getName()+" Done!");
		}
	}
	
}
