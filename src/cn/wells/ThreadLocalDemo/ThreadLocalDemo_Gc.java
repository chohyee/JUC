package cn.wells.ThreadLocalDemo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadLocalDemo_Gc {
	static volatile ThreadLocal<SimpleDateFormat> t1 = new ThreadLocal<SimpleDateFormat>(){
		protected void finalize() throws Throwable{
			//对象t1及其内部的SimpleDateFormat对象被回收时会显示
			System.out.println(this.toString()+" is gc");
		}
	};
	
	static volatile CountDownLatch cd = new CountDownLatch(100);
	public static class ParseDate implements Runnable{
		int i = 0;
		public ParseDate(int i) {
			this.i = i;
		}
		
		@SuppressWarnings("serial")
		@Override
		public void run() {
			try {
				if(t1.get()==null){//通过线程名获取ThreadLocal.ThreadLocalMap.Entry里的值
					t1.set(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"){
						protected void finalize() throws Throwable{
							//对象SimpleDateFormat被回收时会显示
							System.out.println(this.toString()+" is gc");
						}
					});
					System.out.println(Thread.currentThread().getId() + ":createSimpleDateFormat");
				}
				Date t = t1.get().parse("2015-03-29 19:29:" + i%60);//获取Simple对象并转换为指定格式的Date类型
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				cd.countDown();//计数器减1
			}
		}
		
	}
	public static void main(String[] args) throws InterruptedException {
		ExecutorService es = Executors.newFixedThreadPool(10);
		for(int i=0;i<100;++i){
			es.execute(new ParseDate(i));
		}
		cd.await();
		System.out.println("mission complete!!");
		t1 = null;
		System.gc();//第一次显示GC
		System.out.println("first GC complete!!");
		t1 = new ThreadLocal<SimpleDateFormat>();
		cd = new CountDownLatch(100);
		for(int i=0;i<100;++i){
			es.execute(new ParseDate(i));
		}
		cd.await();
		Thread.sleep(1000);
		System.gc();
		System.out.println("second GC complete!!");
	}
}
