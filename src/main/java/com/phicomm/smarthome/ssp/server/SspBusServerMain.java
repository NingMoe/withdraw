package com.phicomm.smarthome.ssp.server;

import com.alibaba.fastjson.parser.ParserConfig;
import com.phicomm.smarthome.ssp.server.common.ds.DynamicDataSourceRegister;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

/**
 * PROJECT_NAME: SspBusServer PACKAGE_NAME:
 * com.phicomm.ssp.server.service.sharedwifi DESCRIPTION: AUTHOR: liang04.zhang
 * DATE: 2017/6/19
 */

@SpringBootApplication(scanBasePackages = { "com.phicomm.smarthome.**" })
@PropertySources({ @PropertySource(value = "classpath:application.properties", ignoreResourceNotFound = true),
        @PropertySource(value = "classpath:server.properties", ignoreResourceNotFound = true) })
@MapperScan("com.phicomm.smarthome.ssp.server.dao.**")
// 启动注册动态数据源been
@Import({ DynamicDataSourceRegister.class })
public class SspBusServerMain {

    static {
        ParserConfig.getGlobalInstance().addAccept("com.phicomm.smarthome.model.cache.");
        ParserConfig.getGlobalInstance().addAccept("com.phicomm.smarthome.ssp.server.model.");
    }

    public static void main(String[] args) {
        SpringApplication.run(SspBusServerMain.class, args);
    }
}
