package com.revosith.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

/**
 * @author Revosith
 * @date 2020/6/19
 * @description
 **/
public class BatchListUtils {

    /**
     * 批处理执行工具
     *
     * @param dataList
     * @param size
     * @param function
     * @param <T,R>
     */
    public static <T, R> List<R> doBatchFunction(List<T> dataList, int size, Function<List<T>, List<R>> function) {

        if (CollectionUtils.isEmpty(dataList)) {
            return Collections.emptyList();
        }
        List<R> cashList = new ArrayList<>(size);

        for (int i = 0; i < dataList.size(); ) {

            List<R> subList = function.apply(dataList.subList(i, Math.min(i + size, dataList.size())));

            if (!CollectionUtils.isEmpty(subList)) {
                cashList.addAll(subList);
            }
            i += size;
        }
        return cashList;
    }

}