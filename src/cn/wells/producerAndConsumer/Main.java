package cn.wells.producerAndConsumer;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;


/*
 * Main类，里面含有缓冲区queue,存储数据
 */
public class Main {
	public static void main(String[] args) throws InterruptedException {
		BlockingQueue<PCData> queue = new LinkedBlockingQueue<PCData>(10);//缓冲区大小为10
		//生产者
		Producer po1 = new Producer(queue);
		Producer po2 = new Producer(queue);
		Producer po3 = new Producer(queue);
		//消费者
		Consumer co1 = new Consumer(queue);
		Consumer co2 = new Consumer(queue);
		Consumer co3 = new Consumer(queue);
		
		ExecutorService service = Executors.newCachedThreadPool();//根据实际情况数量可变的线程池
		//提交任务
		service.execute(po1);
		service.execute(po2);
		service.execute(po3);
		service.execute(co1);
		service.execute(co2);
		service.execute(co3);
		
		Thread.sleep(10*1000);//让本缓冲模拟10秒的运行
		//停止生产（客户端请求）
		po1.stop();
		po2.stop();
		po3.stop();
		
		Thread.sleep(3000);//等待消费者（服务器）完成剩下的任务
		service.shutdown();//关闭线程池
	}
}
