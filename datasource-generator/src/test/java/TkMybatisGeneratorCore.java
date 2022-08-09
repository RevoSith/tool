
import com.revosith.generator.GeneratorV2;
import com.revosith.generator.conf.GeneratorBaseConf;
import com.revosith.generator.conf.TableConf;
import com.revosith.generator.plugin.LombokPlugin;
import com.revosith.generator.plugin.OverwriteXmlPlugin;
import com.revosith.generator.plugin.SimpleDTOFilePlugin;
import org.mybatis.generator.plugins.SerializablePlugin;
import tk.mybatis.mapper.generator.MapperPlugin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

/**
 * @author Revosith
 * @date 2020/11/16..
 */
public class TkMybatisGeneratorCore {


    public static void main(String[] args) throws Exception {
        GeneratorBaseConf conf = new GeneratorBaseConf();
        //连接配置
        databaseConf(conf);
        //文件路径配置
        classConf(conf);
        //插件配置
        pluginConf(conf);
        //表配置 & 执行
        GeneratorV2.execute(conf, getTableConfList());
    }

    private static List<TableConf> getTableConfList() {

        List<TableConf> tableConfList = new ArrayList<>();
        List<String> tables = Arrays.asList(
                "xxxxxxx");

        for (String str : tables) {
            TableConf tableConf = new TableConf();
            tableConf.setTableName(str);
            tableConf.setPk("id");
            //selectKey的读取. pre or post
            tableConf.setType("pre");
            tableConfList.add(tableConf);
        }
        return tableConfList;
    }

    /**
     * 数据库连接配置
     *
     * @param conf
     */
    private static void databaseConf(GeneratorBaseConf conf) {
        conf.setConnectionURL("jdbc:mysql://xxxxxxxxxxxxxxxx:3306/xxxxxxxx?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B8&rewriteBatchedStatements=true&useServerPreparedStmts=true");
        conf.setUserId("xxxxx");
        conf.setPassWord("xxxxxx");
    }

    /**
     * 文件路径配置
     *
     * @param conf
     */
    private static void classConf(GeneratorBaseConf conf) {
        conf.setDomainPackage("com.revosith.test.domain");
        conf.setMapperPackage("com.revosith.test.mapper");
        conf.setModule("test-core");
    }

    /**
     * 插件配置
     *
     * @param conf
     */
    private static void pluginConf(GeneratorBaseConf conf) {
        //序列化插件
        conf.getPlugins().add(new SerializablePlugin());
        //xml 覆写插件
        conf.getPlugins().add(new OverwriteXmlPlugin());
        //lombok 插件
        conf.getPlugins().add(new LombokPlugin());
        //tk Mapper 插件
        tkPlugin(conf);
        //dto 文件插件
        simpleDTOFilePlugin(conf);
    }

    private static void tkPlugin(GeneratorBaseConf conf){
        //tk Mapper 插件
        MapperPlugin mapperPlugin = new MapperPlugin();
        Properties properties = mapperPlugin.getProperties();
        properties.setProperty("mappers", "tk.mybatis.mapper.common.Mapper");
        conf.getPlugins().add(mapperPlugin);
    }

    private static void simpleDTOFilePlugin(GeneratorBaseConf conf){
        SimpleDTOFilePlugin simpleDTOFilePlugin = new SimpleDTOFilePlugin();
        Properties simpleDTOFilePluginProperties = simpleDTOFilePlugin.getProperties();
        simpleDTOFilePluginProperties.setProperty(SimpleDTOFilePlugin.PACKAGE,"com.revosith.test.dto");
        simpleDTOFilePluginProperties.setProperty(SimpleDTOFilePlugin.MODULE,"test-dal");;
        //是否开启 swagger配置
//        simpleDTOFilePluginProperties.setProperty(SimpleDTOFilePlugin.ADD_SWAGGER,"00");;
        conf.getPlugins().add(simpleDTOFilePlugin);
    }
}
