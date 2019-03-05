package proxy;

/**
 * @Author yu.yang
 * @Date 2019/2/22 13:24
 */
public class RealSubject implements Subject{


    public void doSomething() {
        System.out.println("RealSubject do something");
    }

    public void doDrive() {
        System.out.println("RealSubject do drive");
    }
}
