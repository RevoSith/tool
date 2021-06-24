package com.revosith.util;


import com.revosith.constant.NumConstant;

/**
 * @author Revosith
 * @date 2020/11/16.
 */
public class StringUtils {

    /**
     * 空字符串校验
     *
     * @param str 字符
     * @return result
     */
    public static boolean isBlank(String str) {
        int strLen;
        if (str == null || (strLen = str.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if ((!Character.isWhitespace(str.charAt(i)))) {
                return false;
            }
        }
        return true;
    }

    /**
     * 非空判断
     *
     * @param str 字符
     * @return 结果
     */
    public static boolean isNotBlank(String str) {
        return !isBlank(str);
    }

    /**
     * 获取非空类型
     *
     * @param args
     * @return
     */
    public static String getStrWithRank(String... args) {

        if (args == null) {
            return null;
        }

        for (String str : args) {

            if (isNotBlank(str)) {
                return str;
            }
        }
        return null;
    }

    /**
     * 分隔符 连接生成字符
     *
     * @param tag
     * @param strArrays
     * @return
     */
    public static String contact(String tag, Object... strArrays) {

        StringBuilder sb = new StringBuilder(strArrays[NumConstant.NUM_0].toString());

        for (int i = 1; i < strArrays.length; i++) {
            sb.append(tag).append(strArrays[i]);
        }
        return sb.toString();
    }
}
