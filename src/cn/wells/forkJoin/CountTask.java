package cn.wells.forkJoin;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

public class CountTask extends RecursiveTask<Long>{//���������������������ͣ���װ�ࣩ
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
				subTasks.add(subTask);//Ϊ���ܹ���ȡ������Ƚ��ύ�������񱣴�
				subTask.fork();//�ύ������
			}
			//��subTasks�л�ȡ���صĽ��
			for(CountTask ct:subTasks){
				sum+=ct.join();
			}
		}
		
		return sum;
	}
	//����
	public static void main(String[] args) {
		ForkJoinPool fjp = new ForkJoinPool();
		CountTask task = new CountTask(0L, 20000L);
		ForkJoinTask<Long> result = fjp.submit(task);//�ύ����,execute�����޷���ֵ
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
