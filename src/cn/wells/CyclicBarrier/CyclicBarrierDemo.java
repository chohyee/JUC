package cn.wells.CyclicBarrier;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierDemo {
	//士兵线程
	public static class Soldier implements Runnable{

		private String soldier;
		private final CyclicBarrier cyclic;//循环栅栏
		
		Soldier(CyclicBarrier cyclic,String soldierName){
			this.soldier = soldierName;
			this.cyclic = cyclic;
		}
		
		@Override
		public void run() {
			try {
				//等待士兵到齐
				cyclic.await();
				doWork();
				//等待士兵完成任务
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
			System.out.println(soldier + ": 任务完成");
		}
		
	}
	
	public static class BarrierRun implements Runnable{
		boolean flag;
		int N;
		
		public BarrierRun(boolean flag, int N) {
			this.flag = flag;
			this.N = N;
			
		}
		//每次计数完成时要执行的操作
		@Override
		public void run() {
			if(flag){
				System.out.println("司令：[士兵" + N + "个，任务完成！]");
			}else{
				System.out.println("司令：[士兵" + N + "个，集合完成！]");
				flag = true;
			}
		}
		
	}
	
	public static void main(String[] args) {
		final int N = 10;
		Thread[] allSoldier = new Thread[N];
		boolean flag = false;
		CyclicBarrier cyclic = new CyclicBarrier(N,new BarrierRun(flag,N));
		System.out.println("集合队伍！");
		for(int i=0;i<N;++i){
			System.out.println("士兵 "+i+" 报道！");
			allSoldier[i] = new Thread(new Soldier(cyclic, "士兵 "+i));
			allSoldier[i].start();
		}
	}
}
