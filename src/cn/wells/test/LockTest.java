package cn.wells.test;

import java.util.concurrent.locks.ReentrantLock;

//���ԡ�û�л�ȡ����ȥ�ͷ��������ʲô�����
public class LockTest implements Runnable{
	ReentrantLock lock = new ReentrantLock();
	@Override
	public void run() {
		System.out.println();
	}
	
}
