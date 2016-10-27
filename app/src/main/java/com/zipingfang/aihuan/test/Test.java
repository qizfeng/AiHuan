package com.zipingfang.aihuan.test;

/**
 * Created by 峰 on 2016/9/28.
 */
public class Test {
    public static void main(String[] args) {
        int i, j;
        for (i = 1; i <= 9; i++) {
            for (j = 1; j <= 9 - i; j++)
                System.out.print(" ");
            for (j = 1; j <= 2 * i - 1; j++)
                System.out.print("*");
            System.out.println();
        }
    }
}
