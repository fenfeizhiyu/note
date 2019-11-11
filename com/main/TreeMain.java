package main;

import data.structure.BinaryTree;
import freemarker.template.utility.NumberUtil;
import model.BinaryNode;
import org.apache.commons.lang.math.NumberUtils;

/**
 * @Author yu.yang
 * @Date 2018/10/15 14:10
 */
public class TreeMain {

    public static void main(String[] args) {
        double k=1.0;
        double res =0.0;
       do{
           res=k;
           for(int i=0;i<5;i++){
               res=calc(res);
           }
           k++;
       }while (res%1!=0);
        System.out.println(k);
        System.out.println(res);
        //testNodeHeiht();
    }

    public static double calc(double n){
        return (n+1)*1.25;
    }


    /**
     * 测试对树插入节点
     */
    public static void testInsertNodeInTree(){
        BinaryTree<Integer> tree = new BinaryTree<Integer>();
        int root=15;
        tree.insert(root);
        for(int i=0;i<30;i++){
            if (i == root) {
                continue;
            }
            tree.insert(i);
        }
        System.out.println("size:"+tree.getSize()+";height:"+tree.getTreeHeight());
        tree.printPreOrderTree(tree.getRoot(), ",");
        System.out.println(tree.getSb()==null?"null":tree.getSb().toString());
    }


    /**
     * 测试节点的高度
     */
    public static void testNodeHeiht(){
        BinaryNode root = new BinaryNode(0, null);
        BinaryNode currNode=root;
        for (int i = 1; i<= 10; i++) {
            currNode.setLeft(new BinaryNode(i, currNode));
            currNode=currNode.getLeft();
        }
        System.out.println(root.getMaxHeight()+"");
    }
}
