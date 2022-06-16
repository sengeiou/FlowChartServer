package com.flowchart.util;

import java.util.UUID;

/**
 * @className: UuidGeneratorUtil
 * @Description: TODO
 * @author: ct
 * @date: 2022/3/7 10:15
 */
public class UuidGeneratorUtil {

    //得到指定数量的UUID，以数组的形式返回
    public static String[] getUUID(int num){
        if( num <= 0) {
            return null;
        }

        String[] uuidArr = new String[num];

        for (int i = 0; i < uuidArr.length; i++) {
            uuidArr[i] = getuuid32();
        }

        return uuidArr;
    }

    //得到32位的uuid
    public static String getuuid32(){
        return UUID.randomUUID().toString().replace("-", "").toLowerCase();
    }
}
