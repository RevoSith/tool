package com.revosith.generator.resolver;

import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.internal.types.JavaTypeResolverDefaultImpl;

import java.sql.Types;

/**
 * @author Revosith
 * @description
 * @date 2020/11/18.
 */
public class MyTypeResolver extends JavaTypeResolverDefaultImpl {

    public MyTypeResolver() {
        super();
        typeMap.put(Types.TINYINT, new JdbcTypeInformation("TINYINT",
                new FullyQualifiedJavaType(Integer.class.getName())));
        typeMap.put(Types.BIT, new JdbcTypeInformation("BIT",
                new FullyQualifiedJavaType(Integer.class.getName())));
    }
}
