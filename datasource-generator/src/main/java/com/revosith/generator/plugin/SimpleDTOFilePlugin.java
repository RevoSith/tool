package com.revosith.generator.plugin;

import com.revosith.util.StringUtils;
import org.mybatis.generator.api.GeneratedJavaFile;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.config.Context;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author Revosith
 * @description 基于swagger注释的DTO
 * @date 2020/11/17.
 */
public class SimpleDTOFilePlugin extends PluginAdapter {

    public static String PACKAGE = "package";
    public static String MODULE = "module";
    public static String ADD_SWAGGER = "addSwagger";

    @Override
    public boolean validate(List<String> warnings) {
        return true;
    }

    /**
     * 生成额外java文件
     */
    @Override
    public List<GeneratedJavaFile> contextGenerateAdditionalJavaFiles(IntrospectedTable introspectedTable) {
        String filePackage = this.getProperties().getProperty(PACKAGE);
        String path = this.getProperties().getProperty(MODULE) + "/src/main/java";
        List<GeneratedJavaFile> list = new ArrayList<>();
        CompilationUnit addDTOs = generateReqUnit(introspectedTable, filePackage);
        list.add(new GeneratedJavaFile(addDTOs, path, this.context.getProperty("javaFileEncoding"), this.context.getJavaFormatter()));
        return list;
    }


    private CompilationUnit generateReqUnit(IntrospectedTable introspectedTable, String basePackage) {
        String domainObjectName = introspectedTable.getFullyQualifiedTable().getDomainObjectName();
        StringBuilder builder = new StringBuilder();
        boolean addSwagger = StringUtils.isNotBlank(this.getProperties().getProperty(ADD_SWAGGER));
        TopLevelClass dto = new TopLevelClass(
                builder.delete(0, builder.length())
                        .append(basePackage)
                        .append(".")
                        .append(domainObjectName)
                        .append("DTO")
                        .toString()
        );
        dto.setVisibility(JavaVisibility.PUBLIC);
        Iterator<IntrospectedColumn> var9 = introspectedTable.getAllColumns().iterator();
        //添加序列化
        addSerializable(dto);
        //添加lombok
        dto.addImportedType("lombok.Data");
        dto.addImportedType("lombok.NoArgsConstructor");
        dto.addAnnotation("@Data");
        dto.addAnnotation("@NoArgsConstructor");

        if (addSwagger) {
            //swagger注释.
            dto.addAnnotation("@ApiModel(\"" + introspectedTable.getRemarks() + "\")");
            dto.addImportedType("io.swagger.annotations.ApiModel");
            dto.addImportedType("io.swagger.annotations.ApiModelProperty");
        }

        while (var9.hasNext()) {
            IntrospectedColumn introspectedColumn = var9.next();
            Field field = getField(introspectedColumn, this.context, introspectedTable);
            dto.addField(field);
            dto.addImportedType(field.getType());

            if (addSwagger) {
                field.addAnnotation("@ApiModelProperty(\"" + introspectedColumn.getRemarks() + "\")");
            } else if (StringUtils.isNotBlank(introspectedColumn.getRemarks())) {
                field.addJavaDocLine("/**");
                field.addJavaDocLine(" * " + introspectedColumn.getRemarks());
                field.addJavaDocLine(" */");
            }
        }
        return dto;
    }

    /**
     * 获取field
     *
     * @param introspectedColumn
     * @param context
     * @param introspectedTable
     * @return
     */
    private Field getField(IntrospectedColumn introspectedColumn, Context context, IntrospectedTable introspectedTable) {
        FullyQualifiedJavaType fqjt = introspectedColumn.getFullyQualifiedJavaType();
        String property = introspectedColumn.getJavaProperty();
        Field field = new Field();
        field.setVisibility(JavaVisibility.PRIVATE);
        field.setType(fqjt);
        field.setName(property);
        return field;
    }

    /**
     * 添加序列化
     *
     * @param dto
     */
    private void addSerializable(TopLevelClass dto) {
        //序列化
        dto.addImportedType(new FullyQualifiedJavaType("java.io.Serializable"));
        dto.addSuperInterface(new FullyQualifiedJavaType("java.io.Serializable"));
        Field field1 = new Field();
        field1.setFinal(true);
        field1.setInitializationString("1L");
        field1.setName("serialVersionUID");
        field1.setStatic(true);
        field1.setType(new FullyQualifiedJavaType("long"));
        field1.setVisibility(JavaVisibility.PRIVATE);
        dto.addField(field1);
    }
}
