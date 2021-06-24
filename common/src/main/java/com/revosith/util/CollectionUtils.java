package com.revosith.util;


import com.revosith.constant.NumConstant;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * @author 左轮(haiyong.he)
 * @desc
 * @name CollectionUtils
 * @date 2019/7/29
 **/
public class CollectionUtils {

    /**
     * 空集合判断
     *
     * @param collection 集合
     * @return 结果
     */
    public static boolean isEmpty(Collection collection) {
        return collection == null || collection.isEmpty();
    }

    /**
     * 非空集合判断
     *
     * @param collection 集合
     * @return 结果
     */
    public static boolean isNotEmpty(Collection collection) {
        return !isEmpty(collection);
    }

    /**
     * 获取第一个元素
     *
     * @param list
     * @param <T>
     * @return
     */
    public static <T> T getTop(List<T> list) {
        return isNotEmpty(list) ? list.get(NumConstant.NUM_0) : null;
    }

    public static <T, D> Map<T, D> getMap(List<D> list, Function<? super D, ? extends T> opt) {

        Map<T, D> result = new HashMap<>();

        if (isEmpty(list)) {
            return result;
        }

        for (D data : list) {
            result.put(opt.apply(data), data);
        }
        return result;
    }
}
