package model;

import java.util.concurrent.RecursiveTask;

public class MyForkJoinTask extends RecursiveTask<Integer> {


    public static final Integer MAX = 200;

    /**
     * 子任务开始值
     */
    private Integer startValue;

    /**
     * 子任务结束值
     */
    private Integer endValue;


    public MyForkJoinTask(Integer startValue, Integer endValue) {
        this.startValue = startValue;
        this.endValue = endValue;
    }



    @Override
    protected Integer compute() {
        // 如果条件成立，说明这个任务所需要计算的数值分为足够小了
        // 可以正式进行累加计算了
        if(startValue==6409){
            System.out.println("here");
        }
        if(endValue - startValue < MAX) {
            System.out.println("开始计算的部分：startValue = " + startValue + ";endValue = " + endValue);
            Integer totalValue = 0;
            for(int index = this.startValue ; index <= this.endValue  ; index++) {
                totalValue += index;
            }
            System.out.println("Task:"+totalValue);
            return totalValue;
        }
        // 否则再进行任务拆分，拆分成两个任务
        else {
            MyForkJoinTask subTask1 = new MyForkJoinTask(startValue, (startValue + endValue) / 2);
            subTask1.fork();
            MyForkJoinTask subTask2 = new MyForkJoinTask((startValue + endValue) / 2 + 1 , endValue);
            subTask2.fork();
            return subTask1.join() + subTask2.join();
        }
    }



}
