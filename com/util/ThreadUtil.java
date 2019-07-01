package util;

public class ThreadUtil {

    public static void threadSleep(long time){
        try {
            Thread.sleep(time);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
