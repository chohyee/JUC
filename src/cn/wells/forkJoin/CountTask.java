package cn.wells.forkJoin;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

public class CountTask extends RecursiveTask<Long>{//这里必须给出计算结果的类型（包装类）
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final int THRESHOLD = 1000;
	private long start;
	private long end;
	
	public CountTask(long start,long end) {
		this.start = start;
		this.end = end;
	}
	@Override
	protected Long compute() {
		long sum = 0;
		boolean canCompute = THRESHOLD<(end-start);
		if(canCompute){
			for(long i=start;i<=end;++i){
				sum +=i;
			}
		}else{
			long step = (start+end)/10;
			ArrayList<CountTask> subTasks = new ArrayList<>();
			long tag = start;
			for(int j=0;j<10;++j){
				long subEnd = tag+step;
				if(tag+step>end)subEnd = end;
				CountTask subTask = new CountTask(tag,subEnd);
				tag += step+1;
				subTasks.add(subTask);//为了能够获取结果，先将提交的子任务保存
				subTask.fork();//提交子任务
			}
			//从subTasks中获取返回的结果
			for(CountTask ct:subTasks){
				sum+=ct.join();
			}
		}
		
		return sum;
	}
	//测试
	public static void main(String[] args) {
		ForkJoinPool fjp = new ForkJoinPool();
		CountTask task = new CountTask(0L, 20000L);
		ForkJoinTask<Long> result = fjp.submit(task);//提交任务,execute方法无返回值
		try {
			Long long1 = result.get();
			System.out.println(long1);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
