package main;

import model.MyForkJoinTask;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;

public class ForkJoinMain {



    public static void main(String[] args){
        testMethod();
    }


    public static void  testMethod(){
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        ForkJoinTask<Integer> taskFuture = forkJoinPool.submit(new MyForkJoinTask(1, 10001));
        try {
            Integer result = taskFuture.get();
            System.out.println("result = " + result);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace(System.out);
        }

    }

}
