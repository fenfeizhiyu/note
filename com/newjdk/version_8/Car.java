package newjdk.version_8;

import java.util.function.Supplier;

/**
 * @Author yu.yang
 * @Date 2019/5/10 10:43
 */
public class Car {

    public static Car create(final Supplier<Car> supplier) {
        return supplier.get();
    }

    public static void collide(final Car car) {
        System.out.println("Collided " + car.toString());
    }

    public void follow(final Car another) {
        System.out.println("Following the " + another.toString());
    }

    public void repair() {
        System.out.println("Repaired " + this.toString());
    }
}
