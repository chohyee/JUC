package cn.wells.threadPool;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/*
 * ScheduledExecutorService����
 */
public class ScheduleExecutorServiceDemo {
	public static void main(String[] args) {
		ScheduledExecutorService ses = Executors.newScheduledThreadPool(10);//10������
		ses.scheduleAtFixedRate(new Runnable(){//�������ӳ�һ��ʱ���������

			@Override
			public void run() {
				try {
					Thread.sleep(8000);
					System.out.println(System.currentTimeMillis()/1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			
		}, 0, 2, TimeUnit.SECONDS);
	}
}
