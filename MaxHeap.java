/**
 *堆：（1）是一棵完全二叉树
 * 	  （2）堆中某个节点的值总是不大于其父亲节点的值
 * */
public class MaxHeap<E extends Comparable<E>>{
	
	private Array<E> data;
	
	public MaxHeap(int capacity) {
		data = new Array<>(capacity);
	}
	
	public MaxHeap() {
		data = new Array<>();
	}
	
	public MaxHeap(E[] arr) {
		data = new Array<>(arr);
		for(int i=parent(arr.length-1);i>=0;i--) 
			siftDown(i);
	}
	
	public int size() {
		return data.getSize();
	}
	
	public boolean isEmpty() {
		return data.isEmpty();
	}
	
	
	private int parent(int index) {
		if(index == 0)
			return -1;
		else return (index-1)/2;//此处我们是从0开始存储的
	}
	
	private int leftChild(int index) {
		return index*2+1;
	}
	private int rightChild(int index) {
		return index*2+2;
	}
	
	//向堆中添加元素
	public void add(E e) {
		data.addLast(e);
		siftUp(data.getSize()-1);
		
	}
	//上浮
	private void siftUp(int index) {
		while(index > 0 && data.get(index).compareTo(data.get(parent(index))) > 0) {
			data.swap(index, parent(index));
			index = parent(index);
		}
	}
	
	//看一下堆中的最大元素
	public E findMax() {
		if(data.getSize() == 0)
			throw new IllegalArgumentException("heap is empty!");
		return data.get(0);
	}
	
	//取出堆中的最大元素
	
	public E extractMax() {
		E ret = findMax();
		data.swap(0, data.getSize()-1);
		//因为已经交换，所以最大值在数组的末尾
		data.removeLast();
		siftDown(0);
		
		return ret;
	}

	private void siftDown(int index) {
		while(leftChild(index)<data.getSize()) {
			int j = leftChild(index);
			if(j+1 < data.getSize() && data.get(j+1).compareTo(data.get(j))>0) {
				j = rightChild(index);
			}
			//此时j为左右孩子中最大值的索引
			//如果满足堆的性质，则跳出循环
			if(data.get(j).compareTo(data.get(index))<=0) break;
			//否则
			data.swap(j, index);
			index = j;
		}
		
	}
	
	//取出堆中最大元素，并替换成e
	public E replace(E e) {
		E ret = findMax();
		data.set(0, e);
		siftDown(0);
		return ret;
	}
	
	 
	
}
