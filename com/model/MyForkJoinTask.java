package model;

import sun.misc.InnocuousThread;
import sun.misc.ThreadGroupUtils;
import util.ThreadUtil;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.atomic.AtomicInteger;

public class MyForkJoinTask extends RecursiveTask<Integer> {


    public static final Integer MAX = 10;

    public static AtomicInteger taskNum = new AtomicInteger(0);

    public static ConcurrentHashMap hashMap = new ConcurrentHashMap<String,String>();

    /**
     * 子任务开始值
     */
    private Integer startValue;

    /**
     * 子任务结束值
     */
    private Integer endValue;

    private Integer number;

    public MyForkJoinTask() {
        this.number = taskNum.addAndGet(1);
    }

    public String getTaskName(){
        return "task_" + number + ":" + startValue + "_" + endValue + ";";
    }


    public MyForkJoinTask(Integer startValue, Integer endValue) {
        this.startValue = startValue;
        this.endValue = endValue;
        this.number = taskNum.addAndGet(1);
       // System.out.println(getTaskName()+"create;");
    }



    @Override
    protected Integer compute() {
        // 如果条件成立，说明这个任务所需要计算的数值分为足够小了
        // 可以正式进行累加计算了
        //hashMap.put(getTaskName())
        if(endValue - startValue < MAX) {
            System.out.println("startTask:"+getTaskName()+";"+Thread.currentThread().getName()+";开始计算的部分：startValue = " + startValue + ";endValue = " + endValue);
            Integer totalValue = 0;
            for(int index = this.startValue ; index <= this.endValue  ; index++) {
                totalValue += index;
            }
            ThreadUtil.threadSleep(2000L);

            System.out.println(Thread.currentThread().getName()+":"+getTaskName()+";TaskTotal:"+totalValue);
            return totalValue;
        }
        // 否则再进行任务拆分，拆分成两个任务
        else {
            MyForkJoinTask subTask1 = new MyForkJoinTask(startValue, (startValue + endValue) / 2);
            subTask1.fork();
            MyForkJoinTask subTask2 = new MyForkJoinTask((startValue + endValue) / 2 + 1 , endValue);
            subTask2.fork();
            System.out.println("forkTask:"+Thread.currentThread().getName()+";taskList:"+subTask1.getTaskName()+";"+subTask2.getTaskName());
            Integer num1= subTask1.join();
            System.out.println("info:task1"+Thread.currentThread().getName()+";"+getTaskName()+";"+"执行完成"+subTask1.getTaskName());
            Integer num2=subTask2.join();
            System.out.println("info:task2"+Thread.currentThread().getName()+";"+getTaskName()+";"+"执行完成"+subTask2.getTaskName());
            return num1+num2;
        }
    }




}
