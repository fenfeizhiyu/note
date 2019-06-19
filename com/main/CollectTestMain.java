package main;

import model.Product;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.BiFunction;

public class CollectTestMain {

    public static void main(String[] args){

        BiFunction<String,Double,Product> productBiFunction= Product::new;

        List<Product> list=new ArrayList<Product>();

        list.add(productBiFunction.apply("苹果", 33.00));
        list.add(productBiFunction.apply("香蕉", 45.00));
        list.add(productBiFunction.apply("橘子", 25.00));


        list.sort(Comparator.comparing(Product::getPrice).reversed());

        list.forEach((p)->{
            System.out.printf(p.getName()+":"+p.getPrice());
        });
    }



}
