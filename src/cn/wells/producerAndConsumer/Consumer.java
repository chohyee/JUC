package cn.wells.producerAndConsumer;

import java.text.MessageFormat;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
/*
 * �������߳�,�������������
 */
public class Consumer implements Runnable{
	private BlockingQueue<PCData> queue;//����Main���еĻ���������
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
					int re = data.getData() * data.getData();//����ƽ��
					System.out.println(MessageFormat.format("{0}*{1}={2}", data.getData(),data.getData(),re));//����ʽ��ӡ
					Thread.sleep(r.nextInt(SLEEPTIME));
				}
			}
		}catch(InterruptedException e){
			e.printStackTrace();
			Thread.currentThread().interrupt();
		}
	}
	
}
