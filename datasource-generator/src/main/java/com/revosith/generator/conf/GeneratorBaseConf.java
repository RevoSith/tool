package com.revosith.generator.conf;

import lombok.Data;
import org.mybatis.generator.api.PluginAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Revosith
 * @date 2020/11/16.
 */
@Data
public class GeneratorBaseConf {

    /**
     * 默认驱动  mysql
     */
    private String driver = "com.mysql.cj.jdbc.Driver";
    private String connectionURL;
    private String userId;
    private String passWord;
    private String driverClassPath;
    private String xmlPackage;
    private String domainPackage;
    private String mapperPackage;
    private String dtoPackage;
    private String module;
    private String dalModule;
    private String dtoModule;
    private List<PluginAdapter> plugins = new ArrayList<>();
}
