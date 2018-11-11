import java.util.Stack;

import org.junit.Test;

public class BT {
	
	private BinaryTreeNode root;
	private int count;
	
	public BT(){
		this.count = 0;
		this.root = null;
	}
	
	/**
	 * 
	 * 二叉树的创建（递归）
	 * 前序字符串
	 * */
	
	public BinaryTreeNode creatTree(String s,int index  ,int i) {
		if(index==s.length()) {
			return null;
		}
		
		if(s.charAt(index)=='#') {
			return null;
		}
		BinaryTreeNode root = new BinaryTreeNode(s.charAt(index),i);
		root.left = creatTree(s,++index,i+1);
		root.right = creatTree(s,++index,i+1);
		return root;
	}
	
	/**
	 * 二叉树的前序遍历
	 * */
	public void preOrder(BinaryTreeNode root) {
		
		Stack<BinaryTreeNode> s = new Stack<BinaryTreeNode>();
		BinaryTreeNode q = root;
		
		while(!s.isEmpty()||q!=null) {
			if(q!=null) {
				System.out.println(q.data);
				q = q.getLeft();
			}else {
				q = s.pop();
				q = q.getRight();	
			}
		
		}
		
		
	}
	
	
	
	/**
	 * 二叉树的创建（递归创建）
	 * @return 根节点
	 * */
	public BinaryTreeNode creatTree(String s){
		StringBuilder sb = new StringBuilder(s);
		return creatTree(sb,sb.length());
	}
	
	private BinaryTreeNode creatTree(StringBuilder sb,int size){
		if(sb.length() == 0)
			return null;
		String data = sb.charAt(0)+"";
		int index = size - sb.length();//计算结点的编号
		if(data.equals("#")) {
			sb.deleteCharAt(0);
			return null;
		}
		BinaryTreeNode<String> node = new BinaryTreeNode<>(data,index);
		count++;
		//如果编号为0表明创建根节点
 		if(index == 0) {
			root =  node;
		}
		//此结点已经创建完毕，开始下一个结点
		sb.deleteCharAt(0);
		node.left = creatTree(sb,size);
		node.right = creatTree(sb,size);
		return node;
	}
	
	
	/**
	 * 返回二叉树的结点个数（计数法）
	 * */
	public int size() {
		return count;
	}
	/**
	 * 返回二叉树的结点个数（遍历法）
	 * */
	public int getSize() {
		return getSize(root);
	}
	
	private int getSize(BinaryTreeNode node) {
		if(node == null) {
			return 0;
		}else {
			int leftCount = getSize(node.getLeft());
			int rightCount = getSize(node.getRight());
			//左子树的数量 + 右子树的数量 + 根节点（1个）
			return leftCount + rightCount + 1;
		}
	}
	
	
	/**
	 * 判断二叉树是否为空树
	 * */
	public boolean isEmpty() {
		return count == 0;
	}
	
	/**
	 * 返回二叉树的高度
	 * */
	public int getHight() {
		return getHight(root);
	}
	
	public int getHight(BinaryTreeNode node) {
		if(node == null) {
			return 0;
		}else {
			int leftHight = getHight(node.getLeft());
			int rightHight = getHight(node.getRight());
			return leftHight > rightHight ? leftHight + 1 : rightHight+1;
		}
	
	}
	
	
	
	
	
	/**
	 * 二叉树的遍历(前序)
	 * */
	public void PreOrder() {
		PreOrder(root);
	}
	
	private void PreOrder(BinaryTreeNode node) {
		if(node == null) {
			return;
		}else {
			System.out.println(node.getData());
			PreOrder(node.getLeft());
			PreOrder(node.getRight());
		}
	}
	
	/**
	 * 二叉树的遍历前序（非递归）
	 * 
	 *注意：前序遍历必须得先让右子树先入栈，因为每次得先访问左子树
	 *
	 * */
	public void PreOrderImpByStack() {
		PreOrderImpByStack(root);
	}
	
	private void PreOrderImpByStack(BinaryTreeNode node) {
		if(node == null) {
			return;
		}
		Stack<BinaryTreeNode> stack = new Stack<>();
		//跟结点入栈
		stack.add(node);
		while(!stack.isEmpty()) {
			BinaryTreeNode curNode = stack.pop();
			System.out.println(curNode.data);
			//注意得先让右结点进栈，因为后访问右结点（左，中，右）
			if(curNode.getRight()!=null) {
				stack.push(curNode.right);
			}
			
			if(curNode.getLeft()!=null) {
				stack.push(curNode.left);
			}
			
		}

	}
	
	
	
	/**
	 * 二叉树的遍历(中序)
	 * */
	
	public void midOrder() {
		midOrder(root);
	}
	
	private void midOrder(BinaryTreeNode node) {
		if(node==null) {
			return;
		}else {
			midOrder(node.getLeft());
			System.out.println(node.getData());
			midOrder(node.getRight());
		}
		
	}
	/**
	 * 中序遍历（非递归）
	 * */
	
	public void MidOrderImpByStack() {
		MidOrderImpByStack(root);
	}
	
	private void MidOrderImpByStack(BinaryTreeNode node) {
	
		Stack<BinaryTreeNode> stack = new Stack<>();
		BinaryTreeNode p = node;
		
		while(p!=null || !stack.isEmpty()) {	
			while(p!=null) {
				stack.push(p);
				p = p.left;
			}
			if(!stack.isEmpty()) {
				p = stack.pop();
				System.out.println(p.data);
				p = p.right;		
			}
			
			
		}

	}
	
	
	
	
	
	/**
	 * 二叉树的遍历(后序)
	 * */
	public void lastOrder() {
		lastOrder(root);
	}
	
	private void lastOrder(BinaryTreeNode node) {
		if(node==null) {
			return;
		}else {
			midOrder(node.getLeft());
			midOrder(node.getRight());
			System.out.println(node.getData());
		}
		
	}
	@Test
	public void test01() {
		
	}
	
	
	@Test
	public void test() {
//		BT bt = new BT();
//		String s = "abd##e##c#f##";
//		bt.creatTree(s);
//		bt.PreOrder();
//		System.out.println("hight="+bt.getHight());
//		bt.PreOrderImpByStack();
//		System.out.println("*******************");
//		bt.midOrder();
//		System.out.println("*******************");
//		bt.MidOrderImpByStack();
		
		BT bt = new BT();
		String s = "abd##e##c#f##";
		BinaryTreeNode root = bt.creatTree(s,0,1);
		bt.PreOrder(root);
		
	}
	
	/**
	 * 得到根节点
	 * */
	public BinaryTreeNode getRoot() {
		return root;
	}
	



	private class BinaryTreeNode<T>{
		private T data;
		private int index; //当前结点的下标
		private BinaryTreeNode<T> left;
		private BinaryTreeNode<T> right;
		
		public BinaryTreeNode(){
		
		}
		public BinaryTreeNode(T data,int index){
			this.data = data;
			this.index = index;
			this.left = null;
			this.right = null;
		}
		public T getData() {
			return data;
		}
		public int getIndex() {
			return index;
		}
		public void setIndex(int index) {
			this.index = index;
		}
		public void setData(T data) {
			this.data = data;
		}
		public BinaryTreeNode<T> getLeft() {
			return left;
		}
		public void setLeft(BinaryTreeNode<T> left) {
			this.left = left;
		}
		public BinaryTreeNode<T> getRight() {
			return right;
		}
		public void setRight(BinaryTreeNode<T> right) {
			this.right = right;
		}
		
	}
	
	
	
}
