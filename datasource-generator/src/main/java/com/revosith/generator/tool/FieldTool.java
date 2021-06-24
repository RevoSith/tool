package com.revosith.generator.tool;

import com.revosith.constant.NumConstant;

/**
 * @author:
 * @data: 2021/6/23
 * @Description: 特殊工具
 **/
public class FieldTool {


    /**
     * 转驼峰
     *
     * @param field
     * @return
     */
    public static String changeToJavaFiled(String field) {
        String[] fields = field.toLowerCase().split("_");
        StringBuilder sbuilder = new StringBuilder();
        for (int i = 0; i < fields.length; i++) {
            char[] cs = fields[i].toCharArray();
            cs[0] -= NumConstant.NUM_32;
            sbuilder.append(String.valueOf(cs));
        }
        return sbuilder.toString();
    }
}
