package cn.wells.atomic;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

public class AtomicIntegerFieldUpdaterDemo {
	//����һ���࣬�����е�score�ֶ���Ҫ������Ϊԭ�Ӳ���
	public static class Candidate{
		int id;
		volatile int score; 
	}
	
	public final static AtomicIntegerFieldUpdater<Candidate> scoreUpdater
						= AtomicIntegerFieldUpdater.newUpdater(Candidate.class, "score");
	//���Updater�Ƿ�����ȷ,��һ��CAS������AtomicInteger
	public static AtomicInteger allScore = new AtomicInteger(0);
	
	//mian����
	public static void main(String[] args) throws InterruptedException {
		final Candidate stu = new Candidate();
		Thread[] t = new Thread[10000];
		for(int i=0;i<10000;++i){
			t[i] = new Thread(new Runnable(){

				@Override
				public void run() {
					if(Math.random()>0.6){
						//����ǿ����Candidate��score��++score
						scoreUpdater.incrementAndGet(stu);
						//�ð���CAS������ԭ�������Ա�
						allScore.incrementAndGet();
					}
				}
				
			});
			t[i].start();
		}
		//ÿ����ǰ�߳���Ҫ���ؽ�������ÿ������ͶƱ���߳���Ҫ��join()��ʾ���̵߳ȴ�
		for(int i=0;i<10000;++i){
			t[i].join();
		}
		
		//�Ƚϰ���CAS��ԭ�����뱻����CAS��������ļ�����
		System.out.println("score=" + stu.score);
		System.out.println("allScore=" + allScore);
	}
	
}
