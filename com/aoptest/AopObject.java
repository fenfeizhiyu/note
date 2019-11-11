package aoptest;

import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
public class AopObject {


    public void doSomeSth(int id){
        System.out.println("do some thing:"+id);
    }
}
