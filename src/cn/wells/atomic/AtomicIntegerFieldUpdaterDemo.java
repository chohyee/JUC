package cn.wells.atomic;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

public class AtomicIntegerFieldUpdaterDemo {
	//定义一个类，该类中的score字段需要被升级为原子操作
	public static class Candidate{
		int id;
		volatile int score; 
	}
	
	public final static AtomicIntegerFieldUpdater<Candidate> scoreUpdater
						= AtomicIntegerFieldUpdater.newUpdater(Candidate.class, "score");
	//检查Updater是否工作正确,建一个CAS操作的AtomicInteger
	public static AtomicInteger allScore = new AtomicInteger(0);
	
	//mian函数
	public static void main(String[] args) throws InterruptedException {
		final Candidate stu = new Candidate();
		Thread[] t = new Thread[10000];
		for(int i=0;i<10000;++i){
			t[i] = new Thread(new Runnable(){

				@Override
				public void run() {
					if(Math.random()>0.6){
						//被增强的类Candidate中score：++score
						scoreUpdater.incrementAndGet(stu);
						//用包含CAS操作的原子类做对比
						allScore.incrementAndGet();
					}
				}
				
			});
			t[i].start();
		}
		//每个当前线程需要返回结果，因此每个参与投票的线程需要用join()提示主线程等待
		for(int i=0;i<10000;++i){
			t[i].join();
		}
		
		//比较包含CAS的原子类与被加入CAS操作的类的计算结果
		System.out.println("score=" + stu.score);
		System.out.println("allScore=" + allScore);
	}
	
}
