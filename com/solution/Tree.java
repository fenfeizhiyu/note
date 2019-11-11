package solution;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Tree {


    public static void main(String[] args){
        int[] nums = new int[]{7, 1, 3, 2, 4, 9,3, 8};
        Node tree = createNodeTree(nums);
        System.out.println(tree);
        Stack stack = new Stack();

    }



    public static Node createNodeTree(int[] nums){
        Node root=null;
        for (Integer i : nums) {
            root = addNode(i, root);
        }
        return root;
    }


    public static Node addNode(int num,Node node){
        if (node == null) {
            return new Node(num);
        }else {
            if (num > node.getData()) {
                if (node.hasRight()) {
                    addNode(num, node.right);
                }else {
                    node.right = new Node(num);
                }
            }else {
                if (node.hasLeft()) {
                    addNode(num, node.left);
                }else {
                    node.left = new Node(num);
                }
            }
        }
        return node;
    }



















    public static class Node {
        private Integer data;
        private Node left;
        private Node right;

        public Node(Integer data){
            this.data=data;
        }

        public boolean hasLeft(){
            return left!=null;
        }


        public boolean hasRight(){
            return right!=null;
        }

        public Integer getData() {
            return data;
        }

        public void setData(Integer data) {
            this.data = data;
        }

        public Node getLeft() {
            return left;
        }

        public void setLeft(Node left) {
            this.left = left;
        }

        public Node getRight() {
            return right;
        }

        public void setRight(Node right) {
            this.right = right;
        }
    }
}


