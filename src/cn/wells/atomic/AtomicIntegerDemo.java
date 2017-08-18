package cn.wells.atomic;

import java.util.concurrent.atomic.AtomicInteger;

public class AtomicIntegerDemo {
	static AtomicInteger i = new AtomicInteger();
	
	public static class AddThread implements Runnable{

		@Override
		public void run() {
			//每个线程
			for(int k=0;k<1000;k++){
				i.incrementAndGet();//++i
			}
		}
		
	} 
	public static void main(String[] args) {
		Thread[] ts = new Thread[10];
		for(int k=0;k<10;k++){
			ts[k] = new Thread(new AddThread());
		}
		
		for(int m=0;m<10;++m){
			ts[m].start();
		}
		for(int n=0;n<10;++n)
			try {
				ts[n].join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		System.out.println(i);
	}
}
