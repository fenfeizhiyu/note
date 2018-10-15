package main;

import model.BinaryNode;

/**
 * @Author yu.yang
 * @Date 2018/10/15 14:10
 */
public class Tmain {

    public static void main(String[] args) {
        BinaryNode root = new BinaryNode(0, null);
        BinaryNode currNode=root;
        for (int i = 1; i<= 10; i++) {
            currNode.setLeft(new BinaryNode(i, currNode));
            currNode=currNode.getLeft();
        }
        System.out.println(root.getMaxHeight()+"");
    }
}
