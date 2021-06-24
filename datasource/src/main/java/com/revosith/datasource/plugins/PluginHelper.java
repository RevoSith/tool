package com.revosith.datasource.plugins;

import com.github.pagehelper.PageInterceptor;
import com.revosith.constant.NumConstant;
import com.revosith.util.StringUtils;
import org.apache.ibatis.plugin.Interceptor;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * @author Revosith
 * @description 插件辅助器
 * @date 2020/12/3.
 */
public class PluginHelper {


    public static List<Interceptor> getAllPlugins(String plugins) {
        List<Interceptor> interceptors = new ArrayList<>();
        interceptors.add(getPageInterceptor());
        interceptors.add(new MybatisInterceptor());
        interceptors.addAll(getPlugins(plugins));

        return interceptors;
    }

    public static PageInterceptor getPageInterceptor() {
        PageInterceptor pageInterceptor = new PageInterceptor();
        Properties properties = new Properties();
        properties.setProperty("autoRuntimeDialect", "true");
        pageInterceptor.setProperties(properties);
        return pageInterceptor;
    }

    private static List<Interceptor> getPlugins(String pName) {

        if (StringUtils.isBlank(pName)) {
            return new ArrayList<>(NumConstant.NUM_0);
        }
        List<Interceptor> result = new ArrayList<>(NumConstant.NUM_4);
        for (String clazz : pName.split(",")) {

            try {
                Class type = Class.forName(clazz);
                result.add((Interceptor) type.newInstance());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }
}
