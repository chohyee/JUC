package cn.wells.producerAndConsumer;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;


/*
 * Main�࣬���溬�л�����queue,�洢����
 */
public class Main {
	public static void main(String[] args) throws InterruptedException {
		BlockingQueue<PCData> queue = new LinkedBlockingQueue<PCData>(10);//��������СΪ10
		//������
		Producer po1 = new Producer(queue);
		Producer po2 = new Producer(queue);
		Producer po3 = new Producer(queue);
		//������
		Consumer co1 = new Consumer(queue);
		Consumer co2 = new Consumer(queue);
		Consumer co3 = new Consumer(queue);
		
		ExecutorService service = Executors.newCachedThreadPool();//����ʵ����������ɱ���̳߳�
		//�ύ����
		service.execute(po1);
		service.execute(po2);
		service.execute(po3);
		service.execute(co1);
		service.execute(co2);
		service.execute(co3);
		
		Thread.sleep(10*1000);//�ñ�����ģ��10�������
		//ֹͣ�������ͻ�������
		po1.stop();
		po2.stop();
		po3.stop();
		
		Thread.sleep(3000);//�ȴ������ߣ������������ʣ�µ�����
		service.shutdown();//�ر��̳߳�
	}
}
