/**
 * 根节点的值大于它的左子树的值，小于它的右子树的值
 * 相等的话-->直接替换
 * */
public class BST<E extends Comparable<E>> {

	private class Node{
		private E e;
		private Node left,right;
		
		public Node(E e) {
			this.e = e;
			left = null;
			right = null;
		}
	}
	
	
	private Node root;
	
	private int size;
	
	
	public BST() {
		this.root = null;
		size = 0;
	}
	/**
	 * 得到结点个数
	 * */
	public int size() {
		return size;
	}
	
	/**
	 * 判断其是否为空
	 * */
	public boolean isEmpty(){
		return size == 0;
	}
	
	/**
	 * 添加节点
	 * */
	public void add(E e) {
		root = add(root,e);
	}
	
	/**
	 * 递归语义：向以node为根的二分搜索树中添加元素e，返回插入后BSF的根
	 * */
	private Node add(Node node,E e) {
		if(node == null) {
			size++;
			return new Node(e);
		}
		
		if(e.compareTo(node.e)<0)
			node.left = add(node.left,e);
		else if(e.compareTo(node.e)>0)
			node.right = add(node.right,e);
		return node;
	}
	
	/**
	 * 看BFS中是否包含元素e
	 * */
	public boolean contains(E e) {
		return contains(root,e);
	}
	
	private boolean contains(Node node,E e) {
		if(node == null)
			return false;
		if(node.e.compareTo(e)==0)
			return true;
		else if(node.e.compareTo(e)<0) 
			return contains(node.left,e);
		else
			return contains(node.right,e);
	}

	
	
	/**
	 * BFS的前序遍历
	 * */
	public void preOrder() {
		preOrder(root);
	}
	
	//前序遍历以node为跟的BFS的结点
	private void preOrder(Node node) {
		if(node == null)
			return;
		Visited(node.e);
		preOrder(node.left);
		preOrder(node.right);
	}
	
	public void Visited(E e) {
		System.out.println(e);
	}
	
	/**
	 * BFS的中序遍历
	 * */
	public void InOrder() {
		InOrder(root);
	}
	
	//中序遍历以node为跟的BFS的结点
	private void InOrder(Node node) {
		if(node == null)
			return;
		InOrder(node.left);
		Visited(node.e);
		InOrder(node.right);
	}
	

	/**
	 * BFS的后序遍历(例如内存释放问题，必须把跟它相关联的结点都释放完，才能释放它)
	 * */
	public void PostOrder() {
		PostOrder(root);
	}
	
	//后序遍历以node为跟的BFS的结点
	private void PostOrder(Node node) {
		if(node == null)
			return;
		PostOrder(node.left);
		PostOrder(node.right);
		Visited(node.e);
	}
	
	/**
	 * 寻找最小值
	 * */
	public E minimum() {
		if(size == 0)throw new IllegalArgumentException("BST is empty!");
		return minimum(root).e;
		
	}
	
	
	private Node minimum(Node node) {
		if(node.left == null)
			return node;
		return minimum(node.left);
	}
	
	
	/**
	 * 寻找最大值
	 * */
	public E maxnum() {
		if(size == 0)throw new IllegalArgumentException("BST is empty!");
		return maxnum(root).e;
		
	}
	
	
	private Node maxnum(Node node) {
		if(node.right == null)
			return node;
		return maxnum(node.right);
	}
	
	
	
	
	/**
	 * 删除最小的结点
	 * */
	public E removeMin() {
		E ret = minimum();
		root = removeMin(root);
		return ret;
	}
	
	/**
	 * 删除node为根的BFT的最小结点，返回删除后BST的跟
	 * */
	private Node removeMin(Node node) {
		if(node.left == null) {
			Node rightNode = node.right;
			node.right = null;
			size--;
			return rightNode;
		}
		node.left = removeMin(node.left);
		return node;
		
	}
	
	
	
	/**
	 * 删除最大的结点
	 * */
	public E removeMax() {
		E ret = maxnum();
		root = removeMax(root);
		return ret;
	}
	
	/**
	 * 删除node为根的BFT的最大结点，返回删除后BST的根
	 * */
	private Node removeMax(Node node) {
		if(node.right == null) {
			Node leftNode = node.left;
			node.left = null;
			size--;
			return leftNode;
		}
		node.right = removeMax(node.right);
		return node;
		
	}
	
	//从二分搜索树种删除元素为e的结点
	public void remove(E e) {
		root = remove(root,e);
	}
	//删除node为根的BFT的元素e结点，返回删除后BST的根
	private Node remove(Node node,E e) {
		if(node == null)
			return null;
		if(e.compareTo(node.e)<0) {
			node.left = remove(node.left,e);
			return node;
		}else if(e.compareTo(node.e)>0) {
			node.right = remove(node.right,e);
			return node;
		}else {
			// e.compareTo(node.e) == 0
			if(node.left == null) {
				Node rightNode = node.right;
				node.right = null;//断掉其余右子树的联系
				size--;
				return rightNode;
			}else if(node.right == null) {
				Node leftNode = node.left;
				node.left = null;
				size--;
				return leftNode;
			}else {
				//后继successor
				Node successor = minimum(node.right);
				successor.right = removeMin(node.right);//这个操作已经删除了结点
				successor.left = node.left;
				node.left = node.right = null;
				return successor;
			}
			
			
		}
		
	}
	
	
	
	
	
	
	/**
	 * 重写父类的toString();方法
	 * 前序遍历BST
	 * */
	@Override
	public String toString() {
		StringBuilder res = new StringBuilder();
		generateBSTString(root,0,res);
		return res.toString();
	}
	/**
	 * 打印字符串  深度+值
	 * */
	private void generateBSTString(Node node, int depth, StringBuilder res) {
		if(node == null) {
			res.append(generateDepthString(depth)+"null\n");
			return;
		}
		res.append(generateDepthString(depth)+node.e+"\n");
		generateBSTString(node.left,depth+1,res);
		generateBSTString(node.right,depth+1,res);
		
	}
	/**
	 * 打印深度字符串
	 * 例如  ：  -------
	 * */
	private String generateDepthString(int depth) {
		StringBuilder res = new StringBuilder();
		for(int i=0;i<depth;i++) {
			res.append("--");
		}
		return res.toString();
	}
	
}
