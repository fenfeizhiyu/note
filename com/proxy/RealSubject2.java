package proxy;

/**
 * @Author yu.yang
 * @Date 2019/2/22 16:28
 */
public class RealSubject2 implements Subject2 ,Subject3 {

    public void sing() {
        System.out.println("sub2 do sing");
    }

    public void attack() {
        System.out.println("sub2 do attack");
    }

    public void jump() {
        System.out.println("sub3 to jump");
    }
}
