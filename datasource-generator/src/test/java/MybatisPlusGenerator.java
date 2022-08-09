import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.ConstVal;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Revosith
 * @description
 * @date 2021/9/2
 **/
public class MybatisPlusGenerator {

    /**
     * 数据库配置
     */
    private static final String DATA_BASE_URL = "jdbc:mysql://xxx:3306/xxxx?useUnicode=true&useSSL=false&characterEncoding=utf8";
    private static final String USER_NAME = "xxx";
    private static final String PASSWORD = "xxx";
    /**
     * 表名  ,分割
     */
    private static final String TABLES = "xxxxxx";
    /**
     * 文件&包名配置
     */
    private static final String MODULE_DAL = "ai-user-dal";
    private static final String MODULE_SERVICE = "ai-user-biz";
    private static final String MODULE_CONTROLLER = "ai-user-web";
    private static final String PACKAGE = "com.newhope.pig.user.dal";


    public static void main(String[] args) {
        AutoGenerator mpg = new AutoGenerator();
        mpg.setGlobalConfig(getGlobalConfig());
        mpg.setDataSource(getDs());
        mpg.setPackageInfo(getPackageConfig());
        mpg.setStrategy(getStrategyConfig());
        mpg.setTemplateEngine(new FreemarkerTemplateEngine());
        mpg.execute();
    }


    private static GlobalConfig getGlobalConfig() {
        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        gc.setAuthor("Revosith");
        gc.setOpen(false);
        gc.setFileOverride(true);
        gc.setBaseResultMap(true);// XML ResultMap
        gc.setBaseColumnList(true);// XML columList
//        gc.setSwagger2(true);
        return gc;
    }

    /**
     * 数据源配置
     *
     * @return
     */
    private static DataSourceConfig getDs() {
        // 数据源配置
        DataSourceConfig ds = new DataSourceConfig();
        ds.setUrl(DATA_BASE_URL);
        ds.setDriverName("com.mysql.jdbc.Driver");
        ds.setUsername(USER_NAME);
        ds.setPassword(PASSWORD);
        return ds;
    }

    private static PackageConfig getPackageConfig() {
        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setParent(PACKAGE);
        pc.setEntity("model");
        pc.setMapper("mysql");
        pc.setXml("mysql");
        String src = "/src/main/java/";
        String resources = "/src/main/resources/";
        Map<String, String> pathInfo = new HashMap<>();
        pathInfo.put(ConstVal.ENTITY_PATH, MODULE_DAL + src + (PACKAGE + "." + pc.getEntity()).replace(".", "/"));
        pathInfo.put(ConstVal.MAPPER_PATH, MODULE_DAL + src + (PACKAGE + "." + pc.getMapper()).replace(".", "/"));
        pathInfo.put(ConstVal.SERVICE_PATH, MODULE_SERVICE + src + (PACKAGE + "." + pc.getService()).replace(".", "/"));
        pathInfo.put(ConstVal.CONTROLLER_PATH, MODULE_CONTROLLER + src + PACKAGE + "." + pc.getController().replace(".", "/"));
        pathInfo.put(ConstVal.SERVICE_IMPL_PATH, MODULE_SERVICE + src + (PACKAGE + "." + pc.getServiceImpl()).replace(".", "/"));
        pathInfo.put(ConstVal.XML_PATH, MODULE_DAL + resources + (PACKAGE + "." + pc.getXml()).replace(".", "/"));
        pc.setPathInfo(pathInfo);
        return pc;
    }


    private static StrategyConfig getStrategyConfig() {
        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        strategy.setEntityLombokModel(true);
        strategy.setRestControllerStyle(true);
        strategy.setEntityColumnConstant(true);
        strategy.setInclude(TABLES.split(","));
        strategy.setControllerMappingHyphenStyle(true);
        return strategy;
    }
}
