package main;

import model.ZskiplistNode;

/**
 * @Author yu.yang
 * @Date 2018/10/26 10:40
 */
public class ZlistTestMain {



    public static void main(String[] args) {
        ZskiplistNode  head = new ZskiplistNode<Integer>(32, 0.0, null);
        for(int i=0;i<30;i++){
            System.out.println(head.randRomLevel());
        }


    }

}
