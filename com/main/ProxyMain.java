package main;

import proxy.JDKDynamicProxy;
import proxy.RealSubject;
import proxy.RealSubject2;
import proxy.Subject;
import proxy.Subject2;
import proxy.Subject3;

/**
 * @Author yu.yang
 * @Date 2019/2/22 13:29
 */
public class ProxyMain {

    public static void main(String[] args) {
        // 保存生成的代理类的字节码文件
        System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");

        // jdk动态代理测试
        Subject subject = new JDKDynamicProxy(new RealSubject()).getProxy();
        Subject3 sub = new JDKDynamicProxy(new RealSubject2()).getProxy();
       // subject.doSomething();
        subject.doDrive();
        sub.jump();
    }
}
