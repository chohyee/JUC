package cn.wells.producerAndConsumer;

import java.text.MessageFormat;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
/*
 * 消费者线程,处理和消费数据
 */
public class Consumer implements Runnable{
	private BlockingQueue<PCData> queue;//接收Main类中的缓冲区对象
	private static final int SLEEPTIME = 1000;
	
	public Consumer(BlockingQueue<PCData> queue) {
		this.queue = queue;
	}
	
	@Override
	public void run() {
		Random r = new Random();
		System.out.println("start Consumer id="+Thread.currentThread().getId());
		
		try{
			while(true){
				PCData data = queue.take();
				if(null!=data){
					int re = data.getData() * data.getData();//计算平方
					System.out.println(MessageFormat.format("{0}*{1}={2}", data.getData(),data.getData(),re));//按格式打印
					Thread.sleep(r.nextInt(SLEEPTIME));
				}
			}
		}catch(InterruptedException e){
			e.printStackTrace();
			Thread.currentThread().interrupt();
		}
	}
	
}
