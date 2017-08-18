package cn.wells.CyclicBarrier;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierDemo {
	//ʿ���߳�
	public static class Soldier implements Runnable{

		private String soldier;
		private final CyclicBarrier cyclic;//ѭ��դ��
		
		Soldier(CyclicBarrier cyclic,String soldierName){
			this.soldier = soldierName;
			this.cyclic = cyclic;
		}
		
		@Override
		public void run() {
			try {
				//�ȴ�ʿ������
				cyclic.await();
				doWork();
				//�ȴ�ʿ���������
				cyclic.await();
			} catch (InterruptedException | BrokenBarrierException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		void doWork() {
			try {
				Thread.sleep(Math.abs(new Random().nextInt()%10000));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println(soldier + ": �������");
		}
		
	}
	
	public static class BarrierRun implements Runnable{
		boolean flag;
		int N;
		
		public BarrierRun(boolean flag, int N) {
			this.flag = flag;
			this.N = N;
			
		}
		//ÿ�μ������ʱҪִ�еĲ���
		@Override
		public void run() {
			if(flag){
				System.out.println("˾�[ʿ��" + N + "����������ɣ�]");
			}else{
				System.out.println("˾�[ʿ��" + N + "����������ɣ�]");
				flag = true;
			}
		}
		
	}
	
	public static void main(String[] args) {
		final int N = 10;
		Thread[] allSoldier = new Thread[N];
		boolean flag = false;
		CyclicBarrier cyclic = new CyclicBarrier(N,new BarrierRun(flag,N));
		System.out.println("���϶��飡");
		for(int i=0;i<N;++i){
			System.out.println("ʿ�� "+i+" ������");
			allSoldier[i] = new Thread(new Soldier(cyclic, "ʿ�� "+i));
			allSoldier[i].start();
		}
	}
}
