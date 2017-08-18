package cn.wells.producerAndConsumer;

import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
/*
 * 生产者线程，创建和提交数据
 */
public class Producer implements Runnable{
	private volatile boolean isRunning = true;
	private BlockingQueue<PCData> queue;//缓冲区Main类传过来的
	private static AtomicInteger count = new AtomicInteger();//总数，原子操作。
	private static final int SLEEPTIME = 1000;
	
	public Producer(BlockingQueue<PCData>queue) {
		this.queue = queue;
	}
	
	@Override
	public void run() {
		PCData data = null;
		Random r = new Random();
		System.out.println("start pproducer id = " + Thread.currentThread().getId());
		
		try {
			while(isRunning){
			
				Thread.sleep(r.nextInt(SLEEPTIME));
				data = new PCData(count.incrementAndGet());//构造任务数据
				System.out.println(data+" is put into queue");
				if(!queue.offer(data,2,TimeUnit.SECONDS)){
					System.err.println("failed to put data" + data);
				}
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
			Thread.currentThread().interrupt();//当前线程调用父类方法
		}
	}
	
	public void stop(){
		isRunning = false;
	}
}
