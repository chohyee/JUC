package cn.wells.threadPool;

import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/*
 * �̳߳�͵���쳣
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
				new SynchronousQueue<Runnable>()); //�̳߳�
		for(int i=0;i<5;i++){
			//pools.submit(new DivTask(100,i));//����ʧ100/0���쳣
			pools.execute(new DivTask(100,i));//����ʾ�쳣��Ϣ
		}
	}
	
}
