package com.revosith.generator;

import com.revosith.generator.conf.GeneratorBaseConf;
import com.revosith.generator.conf.TableConf;
import com.revosith.generator.conf.ClassConf;
import com.revosith.generator.tool.FieldTool;
import com.revosith.util.CollectionUtils;
import com.revosith.util.StringUtils;
import lombok.Data;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.config.*;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.util.List;

/**
 * @author Revosith
 */
@Data
public class GeneratorV2 {

    private static Context CONTEXT = new Context(ModelType.CONDITIONAL);

    /**
     * 执行生成
     *
     * @throws Exception
     */
    public static void execute(GeneratorBaseConf dataBaseConf, List<TableConf> tableConfList) throws Exception {
        Configuration config = new Configuration();
        config.addContext(CONTEXT);
        //基础参数配置
        propertyConf(dataBaseConf);
        //数据库连接配置
        jdbcConnectionConfiguration(dataBaseConf);
        //基础文件配置  dao entity  xmlMapper
        baseClassGenerator(dataBaseConf);
        //表配置载入
        loadTableConf(tableConfList);
        //插件配置
        addPlugin(dataBaseConf);
        //创建 MBG
        MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, new DefaultShellCallback(true), null);
        //执行生成代码
        myBatisGenerator.generate(null);
    }

    /**
     * 基础参数配置
     * @param dataBaseConf 数据库配置
     */
    private static void propertyConf(GeneratorBaseConf dataBaseConf) {
        //context id  一个库一个id
        CONTEXT.setId("default");
        CONTEXT.setTargetRuntime("MyBatis3Simple");
        //分割符  ORACLE是双引号，MYSQL默认是`反引号
        CONTEXT.addProperty("beginningDelimiter", "`");
        CONTEXT.addProperty("endingDelimiter", "`");
        //格式化代码
        CONTEXT.addProperty("javaFormatter", "org.mybatis.generator.api.dom.DefaultJavaFormatter");
        CONTEXT.addProperty("xmlFormatter", "org.mybatis.generator.api.dom.DefaultXmlFormatter");
        CONTEXT.addProperty("javaFileEncoding", "utf-8");
        CONTEXT.addProperty("autoDelimitKeywords", "true");
    }

    /**
     * dao,entity, xmlMapper
     * @param dataBaseConf 数据库配置
     */
    private static void baseClassGenerator(GeneratorBaseConf dataBaseConf) {
        //dao 配置
        CONTEXT.setJavaClientGeneratorConfiguration(ClassConf.clientGenerator(dataBaseConf));
        //entity 配置
        CONTEXT.setJavaModelGeneratorConfiguration(ClassConf.modelGenerator(dataBaseConf));
        //entity字段的类型特殊转换配置
        CONTEXT.setJavaTypeResolverConfiguration(ClassConf.javaTypeResolverConfiguration());
        //xml 配置
        CONTEXT.setSqlMapGeneratorConfiguration(ClassConf.sqlMapGeneratorConfiguration(dataBaseConf));

    }
    /**
     * 数据库配置
     */
    private static void jdbcConnectionConfiguration(GeneratorBaseConf dataBaseConf) {
        JDBCConnectionConfiguration config = new JDBCConnectionConfiguration();
        config.setConnectionURL(dataBaseConf.getConnectionURL());
        config.setDriverClass(dataBaseConf.getDriver());
        config.setUserId(dataBaseConf.getUserId());
        config.setPassword(dataBaseConf.getPassWord());
        CONTEXT.setJdbcConnectionConfiguration(config);
    }
    /**
     * 添加插件
     */
    private static void addPlugin(GeneratorBaseConf dataBaseConf) {

        if (CollectionUtils.isEmpty(dataBaseConf.getPlugins())) {
            return;
        }

        for (PluginAdapter plugin : dataBaseConf.getPlugins()) {
            PluginConfiguration configSerialize = new PluginConfiguration();
            configSerialize.setConfigurationType(plugin.getClass().getName());

            for (String name : plugin.getProperties().stringPropertyNames()) {
                configSerialize.addProperty(name, plugin.getProperties().getProperty(name));
            }
            CONTEXT.addPluginConfiguration(configSerialize);
        }
    }

    /**
     * 载入表配置
     */
    private static void loadTableConf(List<TableConf> tableConfList) {

        for (TableConf conf : tableConfList) {
            TableConfiguration tempConfig = new TableConfiguration(CONTEXT);
            //设置表名
            tempConfig.setTableName(conf.getTableName());
            //对象名
            tempConfig.setDomainObjectName(StringUtils.getStrWithRank(conf.getDomainName(), FieldTool.changeToJavaFiled(conf.getTableName())));
            //mapper
            tempConfig.setMapperName(StringUtils.getStrWithRank(conf.getMapperName(), FieldTool.changeToJavaFiled(conf.getTableName()) + "Mapper"));
            //主键
            GeneratedKey generatedKey = new GeneratedKey(conf.getPk(), "Mysql", false, conf.getType());
            //主键
            tempConfig.setGeneratedKey(generatedKey);
            tempConfig.addProperty("useActualColumnNames", "false");
            CONTEXT.addTableConfiguration(tempConfig);
        }
    }
}