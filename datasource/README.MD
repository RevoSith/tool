数据源包.

版本1_多数据源.
nacos_配置demo

1.开启配置, 剔除自动化配置
@EnableMulitDataSource
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})

2.nacos配置

Mybatis:
  Database[0]:
    datasource: mainDatasource
    sqlSessionFactory: mainfactory
    transactionManager: maintm
    driverClassName: com.mysql.cj.jdbc.Driver
    jdbcUrl: xxx
    username: xxx
    password: xxx
    connectionTestQuery: select 1 from dual
    connectionTimeout: 3000
    validationTimeout: 3000
    idleTimeout: 1200000
    maxLifetime: 3600000
    maximumPoolSize: 10
    minimumIdle: 1
    mapperLocations: classpath*:com/revosith/*.xml
    typeAliasesPackage: com.revosith
    mapper: com.revosith
  Database[1]:
    datasource: couponDatasource
    sqlSessionFactory: couponfactory
    transactionManager: coupontm
    driverClassName: com.mysql.cj.jdbc.Driver
    jdbcUrl: xxx
    username: xxx
    password: xxx
    connectionTestQuery: select 1 from dual
    connectionTimeout: 3000
    validationTimeout: 3000
    idleTimeout: 1200000
    maxLifetime: 3600000
    maximumPoolSize: 10
    minimumIdle: 1
    mapperLocations: classpath*:com/revosith/*.xml
    typeAliasesPackage: com.revosith
    mapper: com.revosith