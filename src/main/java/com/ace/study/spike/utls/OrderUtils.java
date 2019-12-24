package com.ace.study.spike.utls;

import java.util.Date;

public class OrderUtils {

    public static String generateOrderNumber(String type) {
        return type + "" + new Date().getTime();
    }

}
