package main;

import model.Product;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CollectTestMain {

    public static void main(String[] args){

        testMethod_3();
    }

    public static void testMethod_3(){
        Stream<Integer> stream= Arrays.asList(1,2,3,4,5,6).stream();
        List<Integer> numbers=stream.reduce(new ArrayList<Integer>(),(List<Integer> list_1,Integer e)->{
            list_1.add(e);
            System.out.println("****");
            return list_1;
        },(List<Integer> l1,List<Integer> l2)->{
            l1.addAll(l2);
            System.out.println("----");
            return l1;
        });
        numbers.forEach((Integer i)->{
            System.out.print(i+";");
        });
    }


    public static void testMethod_2() {
        List<Integer> list = Stream.iterate(0, (Integer i) -> {
            return i + 1;
        }).limit(20).collect(Collectors.toList());
        list.stream().forEach((Integer i)->{
            System.out.println(i);
        });
        System.out.println("***********");
        list.parallelStream().forEachOrdered((Integer i)->{
            System.out.println(i);
        });

    }


    public static void testMethod_1(){
        BiFunction<String,Double,Product> productBiFunction= Product::new;

        List<Product> list=new ArrayList<Product>();

        list.add(productBiFunction.apply("苹果", 33.00));
        list.add(productBiFunction.apply("香蕉", 45.00));
        list.add(productBiFunction.apply("橘子", 25.00));
        list.add(new Product("汽车", 202223.00, 2));
        list.add(new Product("手机", 4442.00, 2));
        list.add(new Product("水", 2.00, 3));
        list.add(new Product("饮料", 6.00, 3));

        list.sort(Comparator.comparing(Product::getPrice).reversed());


        list.forEach((p)->{
            System.out.printf(p.getName()+":"+p.getPrice());
        });
    }



}
