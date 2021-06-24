package com.revosith.generator.conf;

import lombok.Data;

/**
 * @author Revosith
 * @date 2020/11/16.
 */
@Data
public class TableConf {

    private String tableName;
    private String domainName;
    private String mapperName;
    private String pk;
    private String type;
    private String needDto;
}
