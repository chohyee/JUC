package cn.wells.producerAndConsumer;

public final class PCData {
	private final int intData;//�����ȹ����� 
	public PCData (int d){
		intData = d;
	}
	public PCData(String d){
		intData = Integer.valueOf(d);
	}
	public int getData(){
		return intData;
	}
	@Override
	public String toString(){
		return "data"+intData;
	}
}
