package com.spaking.boot.starter.cas.utils;

import java.util.Random;

public class BalanceUtil {
    public static Integer pos = 0;
    public final static String SERVER_IP = "127.0.0.1:1234";

    //随机
    public static String getRandomIp(String[] ips) {
        if (ips.length > 0) {
            Random random = new Random();
            int n = random.nextInt(ips.length);
            return ips[n];
        } else {
            return SERVER_IP;
        }
    }

    //轮询
    public static String getRoundRobinIp(String[] ips) {
        String serverIp = SERVER_IP;
        if (ips.length > 0) {
            synchronized (pos) {
                if (pos >= ips.length) {
                    pos = 0;
                }
                serverIp = ips[pos];
                //轮询+1
                pos++;
            }
        }
        return serverIp;
    }
}
