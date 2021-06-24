package com.revosith.generator.conf;

import org.mybatis.generator.config.JavaClientGeneratorConfiguration;
import org.mybatis.generator.config.JavaModelGeneratorConfiguration;
import org.mybatis.generator.config.JavaTypeResolverConfiguration;
import org.mybatis.generator.config.SqlMapGeneratorConfiguration;

/**
 * @author:
 * @data: 2021/6/23
 * @Description:
 **/
public class ClassConf {

    /**
     * dao 层配置
     */
    public static JavaClientGeneratorConfiguration clientGenerator(GeneratorBaseConf dataBaseConf) {
        JavaClientGeneratorConfiguration config = new JavaClientGeneratorConfiguration();
        config.setTargetPackage(dataBaseConf.getMapperPackage());
        config.setTargetProject(dataBaseConf.getModule() + "/src/main/java");
        config.setConfigurationType("XMLMAPPER");
        config.addProperty("enableSubPackages", "false");
        return config;
    }

    /**
     * 设置实体
     */
    public static JavaModelGeneratorConfiguration modelGenerator(GeneratorBaseConf dataBaseConf) {
        JavaModelGeneratorConfiguration config = new JavaModelGeneratorConfiguration();
        config.setTargetPackage(dataBaseConf.getDomainPackage());
        config.setTargetProject(dataBaseConf.getModule() + "/src/main/java");
        config.addProperty("trimStrings", "true");
        config.addProperty("enableSubPackages", "false");
        return config;
    }

    /**
     * 类型转换
     */
    public static JavaTypeResolverConfiguration javaTypeResolverConfiguration() {
        JavaTypeResolverConfiguration config = new JavaTypeResolverConfiguration();
        config.setConfigurationType("com.revosith.generator.resolver.MyTypeResolver");
        config.addProperty("forceBigDecimals", "false");
        return config;
    }

    /**
     * sql mapper
     */
    public static SqlMapGeneratorConfiguration sqlMapGeneratorConfiguration(GeneratorBaseConf dataBaseConf) {
        SqlMapGeneratorConfiguration config = new SqlMapGeneratorConfiguration();
        config.setTargetPackage(dataBaseConf.getMapperPackage());
        config.setTargetProject(dataBaseConf.getModule() + "/src/main/resources");
        config.addProperty("enableSubPackages", "false");
        return config;
    }
}
