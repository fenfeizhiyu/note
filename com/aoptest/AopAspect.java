package aoptest;


import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class AopAspect {

    @Pointcut("execution(* com.aoptest..(..)&& args(id))")
    public void pointCut(int id){

    }
}
