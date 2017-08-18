package cn.wells.test;

import java.util.concurrent.locks.ReentrantLock;

//测试“没有获取锁而去释放锁会出现什么情况”
public class LockTest implements Runnable{
	ReentrantLock lock = new ReentrantLock();
	@Override
	public void run() {
		System.out.println();
	}
	
}
