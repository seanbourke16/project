package project;

public class Memory {
	public static int DATA_SIZE=2048;
	private int[] array=new int[DATA_SIZE];
	private int changedIndex = -1;
	private int[] getArray(){
		return array;
	}
	public int getData(int index){
		return array[index];
	}
	public void setData(int index, int value){
	    array[index]=value;
		changedIndex=index;
	}
	public int getChangedIndex() {
		return changedIndex;
	}
	
	public void clear(int start, int end){
		for (int i=start; i<end; i++){
			array[i]=0;
			changedIndex=-1;
		}
	}
    public int[] getData(){
	return array;
    }
}
