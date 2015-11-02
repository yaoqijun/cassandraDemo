package org.yqj.cassandra.demo;

import java.util.function.Function;

/**
 * Created by yaoqijun.
 * Date:2015-10-27
 * Email:yaoqj@terminus.io
 * Descirbe:
 */
public class Main {
    public static void main(String []args){
        Function<String,Integer> function = (t) -> Integer.valueOf(t);
        System.out.println(function.apply("123"));
        System.out.println("test");
    }
}
