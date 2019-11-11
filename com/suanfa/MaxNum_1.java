package suanfa;

import java.util.concurrent.RecursiveTask;

public class MaxNum_1 extends RecursiveTask<Integer> {

    private  int[] sourceData={2,3,5,7,9,2,1};

    private int leftNum=0;

    private int childNum=0;

    public MaxNum_1(int[] data,int leftNum,int childNum) {
        super();
        sourceData=data;
        this.childNum=childNum;
        this.leftNum=leftNum;
    }



    @Override
    protected Integer compute() {
        return null;
    }
}
