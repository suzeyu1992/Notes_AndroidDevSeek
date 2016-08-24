package com.szysky.note.androiddevseek_12.util;

import java.util.Hashtable;
import java.util.Set;

import static android.R.attr.y;

/**
 * Author :  suzeyu
 * Time   :  2016-08-08  下午3:19
 * Blog   :  http://szysky.com
 * GitHub :  https://github.com/suzeyu1992
 * ClassDescription :
 */

public class test {
    private static short Y = 0;

    public static void main (String args[]){
        
        byte[]  testData = new byte[]{126,-112,0,0,0,37,97,117,116,104,82,101
                ,115,112,67,111,100,101,0,48,48,0,
                111,110,108,105,110,101,68,97,116,97,0,57,70,51,54,48,50,48,48,66
                ,69,0,-1,-112,0};
        
        
        
        byte[] bytes = new byte[]{0,-1,-112,0};
        setRspData(bytes);

        System.out.println(Y);

        String respDataStr = new String(testData);
        Hashtable items = parseKeyValue(respDataStr);
        if(items.size() != 0 && items.containsKey("iccData")) {
            System.out.println("wo shi zhen");

        } else {
            System.out.println("wo shi jia");

        }
    }


    public static void setRspData(byte[] dataAndStatus) {
        if(dataAndStatus != null && dataAndStatus.length >= 2) {
            Y = byteArray2Short(dataAndStatus, dataAndStatus.length - 2);
        }
    }


    public static short byteArray2Short(byte[] from, int offset) {
        return (short)(from[offset] << 8 & '\uff00' | from[offset + 1] & 255);
    }

    private static Hashtable<String, String> parseKeyValue(String data) {
        Hashtable items = new Hashtable();
        String[] temp1 = data.split("\\|");

        for(int i = 0; i < temp1.length; ++i) {
            if(!temp1[i].contains(".")) {
                return new Hashtable();
            }

            int position = temp1[i].indexOf("=");
            String key = temp1[i].substring(0, position);
            String value = temp1[i].substring(position + 1);
            items.put(key, value);
        }

        return items;
    }

}
