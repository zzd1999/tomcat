package web;

import java.util.Arrays;

public class Test {

    public static void main(String[] args) throws Exception{
        String str="张";
       byte[] b= str.getBytes("utf-8");
        System.out.println(Arrays.toString(b));
        for(byte t:b){
            System.out.println(Integer.toHexString(t));
        }
    }
}
