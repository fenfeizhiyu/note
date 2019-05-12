package main;

import com.sun.istack.internal.NotNull;
import newjdk.version_8.Car;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;


/**
 * @Author yu.yang
 * @Date 2019/5/10 9:20
 */
public class NewVersion {


    private @NotNull String s;

    public static void main(String[] args) {
         Car car = Car.create( Car::new );

    }


    /**
     * 测试lambad表达式
     */
    public static void test_one(){
        Integer[] array = {3, -4, 6, 4};

        Consumer<Integer[]> con = (x) -> {
            for(int a:array){
                System.out.println("i" + a);
            }
        };
        Function<Integer[],List<Integer>> arrayToList=(param)->{
            List<Integer> list = new ArrayList<>(param.length);
            for(Integer a:param){
                list.add(a);
            }
            return list;
        };
        con.accept(array);
        List<Integer> list =arrayToList.apply(array);
        System.out.println(list);
        Map<String , String> map = new HashMap<>();
        map.put("a","a");
        map.put("b","b");
        map.put("c","c");
        map.put("d","d");
        map.put("e","e");
        map.put("f","f");

        map.forEach((k,v)->System.out.println(k + ":" + v));

        List<String> strList= Arrays.asList("545", "66", "53222");
    }
}
