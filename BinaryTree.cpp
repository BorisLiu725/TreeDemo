#include<stdio.h>
#include<stdlib.h>
#include<string.h>
#define ElemType char
#define QueueMax 100
#define StackMax 100
#define ele BiTree
//二叉树的结点构造 
typedef struct node{
	struct node *lChild;
	ElemType data;
	struct node *rChild;
}BiNode,*BiTree;
/*********************定义一个队列***********************/
typedef struct queue{
	BiTree data[QueueMax];
	int front;
	int rear;
}CseQueue;



void InitQueue(CseQueue **q){
	(*q) = (CseQueue*)malloc(sizeof(CseQueue));
	(*q)->rear = (*q)->front = QueueMax-1;
}

bool InSeQueue(CseQueue *q,BiTree data){
	if((q->rear+1)%QueueMax==q->front)return false;//判断队满 
	q->rear = (q->rear+1)%QueueMax;
	q->data[q->rear] = data;
	return true;
}

bool OutSeQueue(CseQueue *q,BiTree *x){
	if(q->front==q->rear)return false;	//判队空 
	q->front = (q->front+1)%QueueMax;
	*x = q->data[q->front];
	return true;
}

bool EmptySeQueue(CseQueue *q){
	return q->front == q->rear;
} 
/***************************************************/

/*************************定义一个栈***************************/

typedef struct stack{
	BiTree data[StackMax];
	int top;
}SeqStack; 

//初始化栈 
SeqStack *Init_SeqStack(){
	SeqStack *s;
	s = (SeqStack *)malloc(sizeof(SeqStack));
	s->top = -1;
	return s;
}
//判栈空 
bool Empty_SeqStack(SeqStack *s){
	return s->top == -1;
}
//入栈
bool Push_SeqStack(SeqStack *s,BiTree x){
	if(s->top == StackMax-1)return false;
	s->data[++(s->top)] = x;
	return true;
} 
//出栈
bool Pop_SeqStack(SeqStack *s,BiTree *x){
	if(Empty_SeqStack(s))return false;
	*x = s->data[(s->top)--];
	return true;
} 
//取栈顶元素 
BiTree Top_SeqStack(SeqStack *s){	
	if(Empty_SeqStack(s))return NULL;
	else return s->data[s->top];	
} 
 
/****************************************************************/



//二叉树的创建
void CreateBiTree(BiTree *root);
//前序遍历 
void preOrder(BiTree root);
//中序遍历 
void InOrder(BiTree root);
//后序遍历  
void PostOrder(BiTree root);
//层次遍历 
void LevelOrder(BiTree root);
/***************************/
 
 //遍历算法的应用 
 /***************************/
 //1.统计二叉树中的结点数 
 void preOrderCount(BiTree root);
 
 //2.输出二叉树中的叶子结点 
 void printLeafNode(BiTree root); 
 
 //3.统计二叉树中的叶子结点的数目 
 int LeafCounts(BiTree root);
 //4.求二叉树的高度
 int TreeDepth(BiTree root); 
 //5.求结点的双亲
 BiTree parent(BiTree root,BiTree current); 
 //6.二叉树的相似性判断
 bool like(BiTree t1,BiTree t2); 
 //7.按照树状打印二叉树
 void printTree(BiTree root,int h); 
/***************************/

//*******************************************以下是实现部分*********************************/ 

//二叉树的创建
int i = -1;
void CreateBiTree(BiTree *root,char s[]){
	int sLength = strlen(s);
	i++;
	if(i>=sLength)return;
	if(s[i]=='#') *root = NULL;
	else{
		(*root) = (BiTree)malloc(sizeof(BiNode));
		(*root)->data = s[i];
		CreateBiTree(&((*root)->lChild),s);
		CreateBiTree(&((*root)->rChild),s);
	}
	
}

void Visit(char data){
	putchar(data);
}
//前序遍历 
void preOrder(BiTree root){
	if(root){
		Visit(root->data);
		preOrder(root->lChild);
		preOrder(root->rChild);
 	}
	
}
//第一种代码 
void preOrder1ByStack(BiTree root){
	if(root==NULL)return;
	SeqStack *s;
	BiTree e = (BiTree)malloc(sizeof(BiNode));
	s = Init_SeqStack();
	
	Push_SeqStack(s,root);
	while(!Empty_SeqStack(s)){
		Pop_SeqStack(s,&e);
		Visit(e->data);
		if(e->rChild!=NULL)Push_SeqStack(s,e->rChild);
		if(e->lChild!=NULL)Push_SeqStack(s,e->lChild);
	}
}

//第二种代码 
void preOrder2ByStack(BiTree root){
	if(root==NULL)return;
	SeqStack *s;
	BiTree e = (BiTree)malloc(sizeof(BiNode));
	s = Init_SeqStack();
	e = root;
	while(!Empty_SeqStack(s) || e!=NULL){
		if(e!=NULL){
			Visit(e->data);
			Push_SeqStack(s,e);
			e = e->lChild;
		}else{
			Pop_SeqStack(s,&e);
			e = e->rChild;
		}
		
	}
}


//中序遍历 
void InOrder(BiTree root){
	if(root){
		InOrder(root->lChild);
		Visit(root->data);
		InOrder(root->rChild);
 	}
} 

//中序非递归遍历

void InOrderByStack(BiTree root){
	if(root==NULL)return;
	SeqStack *s;
	BiTree e = (BiTree)malloc(sizeof(BiNode));
	s = Init_SeqStack();
	e = root;
	while(!Empty_SeqStack(s) || e!=NULL){
		if(e!=NULL){
			Push_SeqStack(s,e);
			e = e->lChild;
		}else{
			Pop_SeqStack(s,&e);
			Visit(e->data);
			e = e->rChild;
		}
		
	}
	

} 

//后序非递归
void PostOrder(BiTree root){
	if(root){
		PostOrder(root->lChild);
		PostOrder(root->rChild);	
		Visit(root->data);
 	}
	
} 
 


//后序非递归遍历
void PostOrderByStack(BiTree root){
	if(root==NULL)return;
	SeqStack *s;
	BiTree p = (BiTree)malloc(sizeof(BiNode));
	s = Init_SeqStack();
	p = root;
	BiTree q = NULL; 
	while(!Empty_SeqStack(s) || p!=NULL){
		while(p!=NULL){
			Push_SeqStack(s,p);
			p = p->lChild;
		}
		if(!Empty_SeqStack(s)){
			p = Top_SeqStack(s);
			if(p->rChild == NULL || p->rChild==q){
				Pop_SeqStack(s,&p);
				Visit(p->data);
				//q为上一次访问的结点 
				q = p;
				p = NULL;	
			}else p = p->rChild; 
		}
		
	}
	
} 



//层次遍历 
 
void LevelOrder(BiTree root){
	CseQueue *q;
	BiTree p = root; 
	InitQueue(&q);
	if(p){InSeQueue(q,p);}
	while(!EmptySeQueue(q)){
		OutSeQueue(q,&p);
		Visit(p->data);	
		if(p->lChild!=NULL)InSeQueue(q,p->lChild);		
		if(p->rChild!=NULL)InSeQueue(q,p->rChild);
	}
}

 //1.统计二叉树中的结点数 
 int count = 0;
 void preOrderCount(BiTree root){
 	if(root){
 		count++;
	 	preOrderCount(root->lChild);
	 	preOrderCount(root->rChild);
	}
 
 }
 
 //2.输出二叉树中的叶子结点 
 void printLeafNode(BiTree root){
 	if(root){	
 		printLeafNode(root->lChild);
	 	if(root->lChild == NULL&&root->rChild == NULL){
	 		Visit(root->data);	
		}
	 	printLeafNode(root->rChild);
	 }
 } 
 
 //3.统计二叉树中的叶子结点的数目 
 int LeafCounts(BiTree root){
 	if(!root)return 0;
	if(root->lChild == NULL&&root->rChild == NULL)return 1;
 	return LeafCounts(root->lChild) + LeafCounts(root->rChild);
 }
 //4.求二叉树的高度
 int TreeDepth(BiTree root){
 	int hl,hr,h;
 	if(!root) return 0;
 	else{
 		hl = TreeDepth(root->lChild);	
 		hr = TreeDepth(root->rChild);
 		return hl>hr?hl+1:hr+1;
	}
 	
 }
 //5.求结点的双亲
 BiTree parent(BiTree root,BiTree current){
 	if(root == NULL)return NULL;
	BiTree p = NULL;

	if(root->lChild == current || root->rChild == current)
		return root; 
	p = parent(root->lChild,current);
	if(p!=NULL)return p;
	return parent(root->rChild,current);
	
 }
 
 
 
 //6.二叉树的相似性判断
 bool like(BiTree t1,BiTree t2){
 	bool like1,like2;	
 	if(t1 == NULL && t2 == NULL)return true;
 	else if(t1 == NULL || t2 == NULL)return false;
 	else{
 		like1 = like(t1->lChild,t2->lChild);
 		like2 = like(t1->rChild,t2->rChild);
 		return (like1&&like2);
	 }
 	
 }
 
 //7.按照树状打印二叉树
 void printTree(BiTree root,int h){
 	if(root){
 		printTree(root->rChild,h+1);	
 		for(int i=0;i<h;i++){
 			printf("	");
		 }
		 printf("%c\n",root->data);
		 printTree(root->lChild,h+1);	
	}
 	
 } 




int main()
{
	BiTree root;
	char s[50] = "ABD#G###CE#H##F##";
	CreateBiTree(&root,s);
	printf("二叉树的前序遍历:");
	preOrder(root);
	printf("\n二叉树的前序遍历1(非递归)");
	preOrder1ByStack(root);
	printf("\n二叉树的前序遍历2(非递归)");
	preOrder2ByStack(root);
	printf("\n二叉树的中序遍历:");
	InOrder(root);
	printf("\n二叉树的中序遍历(非递归)");
	InOrderByStack(root); 
	printf("\n二叉树的后序遍历:");
	PostOrder(root);
	printf("\n二叉树的后序(非递归)遍历:");
	PostOrderByStack(root);
	printf("\n二叉树的层次遍历"); 
	LevelOrder(root);
	printf("\n叶子结点为：");
	printLeafNode(root);
	printf("\n叶子结点的个数为：%d",LeafCounts(root));
	printf("\n二叉树的高度为：%d\n",TreeDepth(root));
	printf("\n打印之后的二叉树为：\n");
	printTree(root,0);
	//printf("\n二叉树的父亲结点是");
		
	return 0;
}
 
