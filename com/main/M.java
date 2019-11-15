package main;

import model.CalcNum;



public class M {
    public static void main(String[] args){
        InheritableThreadLocal<CalcNum> local_1 = new InheritableThreadLocal<CalcNum>();
        CalcNum num = new CalcNum(50);
        local_1.set(num);
        Runnable task=new Runnable() {
            @Override
            public void run() {
               CalcNum num=local_1.get();
               num.setNum(num.getNum()+1);
                System.out.println(Thread.currentThread().getName()+":"+local_1.get().getNum());
            }

        };
        Thread[] threads=new Thread[5];
        for(int i=0;i<threads.length;i++){
            threads[i] = new Thread(task);
            threads[i].setName("threadName_" + i);
        }
        for (Thread t : threads) {
            t.start();
        }

    }
}
