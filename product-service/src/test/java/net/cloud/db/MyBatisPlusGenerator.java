package net.cloud.db;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

public class MyBatisPlusGenerator {
    public static void main(String[] args) {
        GlobalConfig config = new GlobalConfig();
        config.setActiveRecord(true)
                .setAuthor("Rongyi")
                .setOutputDir("/Users/yi/Desktop/demo/src/main/java")
                .setFileOverride(true)
                .setIdType(IdType.AUTO)
                .setDateType(DateType.ONLY_DATE)
                .setServiceName("%sService")
                .setEntityName("%sDO")
                .setBaseResultMap(true)
                .setActiveRecord(false)
                .setBaseColumnList(true);

        DataSourceConfig dsConfig = new DataSourceConfig();
        dsConfig.setDbType(DbType.MYSQL)
                .setDriverName("com.mysql.cj.jdbc.Driver")
                .setUrl("jdbc:mysql://111.229.165.247:3307/xdclass_product")
                .setUsername("root")
                .setPassword("Mysql666!!");

        StrategyConfig strategyConfig = new StrategyConfig();
        strategyConfig.setCapitalMode(true)
                .setNaming(NamingStrategy.underline_to_camel)
                .setEntityLombokModel(true)
                .setRestControllerStyle(true)
                .setInclude("banner","product");

        PackageConfig packageConfig = new PackageConfig();
        packageConfig.setParent("net.cloud")
                .setMapper("mapper")
                .setService("service")
                .setController("controller")
                .setEntity("model")
                .setXml("mapper");

        AutoGenerator autoGenerator = new AutoGenerator();
        autoGenerator.setGlobalConfig(config)
                .setDataSource(dsConfig)
                .setStrategy(strategyConfig)
                .setPackageInfo(packageConfig);

        autoGenerator.execute();
        System.out.println("=== generate complete ===");
    }
}
