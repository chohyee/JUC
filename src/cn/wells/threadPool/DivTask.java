package cn.wells.threadPool;

import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/*
 * 线程池偷吃异常
 */
public class DivTask implements Runnable{

	int a,b;
	public DivTask(int a,int b) {
		this.a = a;
		this.b = b;
	}
	@Override
	public void run() {
		double re = a/b;
		System.out.println(re);
	}
	
	public static void main(String[] args) {
		ThreadPoolExecutor pools = new ThreadPoolExecutor(0,Integer.MAX_VALUE,0L,TimeUnit.SECONDS,
				new SynchronousQueue<Runnable>()); //线程池
		for(int i=0;i<5;i++){
			//pools.submit(new DivTask(100,i));//将丢失100/0的异常
			pools.execute(new DivTask(100,i));//将显示异常信息
		}
	}
	
}
