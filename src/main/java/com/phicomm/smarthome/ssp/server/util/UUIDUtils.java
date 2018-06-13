package com.phicomm.smarthome.ssp.server.util;

import java.util.UUID;

public class UUIDUtils {

    public static String create() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    public static String create16() {
        return UUID.randomUUID().toString().replace("-", "").substring(0, 16);
    }

    public static void main(String[] args) {
        System.out.println(create());
        System.out.println(create16());
    }
}
