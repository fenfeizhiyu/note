package data.structure;

import model.BinaryNode;

/**
 * @Author yu.yang
 * @Date 2018/10/16 14:23
 */
public class BinaryTree<T extends Comparable<T>> {

    private BinaryNode<T> root;


    private int size;

    private   StringBuilder sb;

    public void insert(T data){
        doInsert(data, root);
    }


    /**
     * 插入一个节点
     * 第一个数插入根节点，后面的数，依次插入左右节点，如果第一个数最小，则会造成根节点只有右子树
     *
     * @param data
     * @param currNode
     */
    public void doInsert(T data,BinaryNode<T> currNode){
        if (currNode == null) {
            //根节点
            this.root=new BinaryNode<T>(data, null);
            this.size++;
            return;
        }else {
            //不是根节点
            if (data.compareTo(currNode.getData())>0) {
                //插入节点大于当前节点，放入右节点
                if (currNode.hasRightChild()) {
                    doInsert(data, currNode.getRight());
                }else {
                    //右节点为空
                    currNode.setRight(new BinaryNode<T>(data, currNode));
                    this.size++;
                    return;
                }
            }else {
                //插入节点小与等于当前节点，放入左节点
                if (currNode.hasLeftChild()) {
                    doInsert(data, currNode.getLeft());
                }else {
                    currNode.setLeft(new BinaryNode<T>(data, currNode));
                    this.size++;
                    return;
                }
            }
        }

    }


    /**
     * 计算树的深度
     * @return
     */
    public int getTreeHeight(){
        return root==null?0:root.getMaxHeight();
    }


    /**
     * 按前序遍历顺序打印
     */
    public void printPreOrderTree(BinaryNode<T> currNode,String spe){
        if (sb == null) {
            sb= new StringBuilder();
        }
        if (currNode == null||currNode.getData()==null) {
            System.out.println("no dataNode");
        }
        if (currNode.hasLeftChild()){
            if (currNode.getLeft().getData() != null) {
                sb.append(currNode.getData() + spe);
            }else {
                System.out.println("currNode no data");
            }
            printPreOrderTree(currNode.getLeft(), spe);
        }else {
            //没有左节点了，再打印右节点
            if (currNode.hasRightChild()) {
                if (currNode.getRight().getData() != null) {
                    sb.append(currNode.getRight().getData() + spe);
                }else {
                    System.out.println("currNode no data");
                }
                printPreOrderTree(currNode.getRight(), spe);
            }
        }
    }











    public BinaryNode<T> getRoot() {
        return root;
    }

    public void setRoot(BinaryNode<T> root) {
        this.root = root;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }


    public StringBuilder getSb() {
        return sb;
    }

    public void setSb(StringBuilder sb) {
        this.sb = sb;
    }
}
