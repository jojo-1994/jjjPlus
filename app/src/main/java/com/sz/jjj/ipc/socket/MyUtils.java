package com.sz.jjj.ipc.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author:jjj
 * @data:2018/7/10
 * @description:
 */

public class MyUtils {
    public static void close(PrintWriter out){
        if(out != null){
            out.close();
        }
    }
    public static void close(BufferedReader in){
        if(in != null){
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
