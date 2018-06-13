package com.phicomm.smarthome.ssp.server.controller.status;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jcabi.manifests.Manifests;
import com.phicomm.smarthome.model.SmartHomeResponse;
import com.phicomm.smarthome.model.status.StatusResponse;

import io.swagger.annotations.ApiOperation;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * PROJECT_NAME: liang04-smarthome-sharedwifi PACKAGE_NAME:
 * com.phicomm.smarthome.sharedwifi.controller.status DESCRIPTION: AUTHOR:
 * liang04.zhang DATE: 2017/6/20
 */

@RestController
@ComponentScan
@EnableSwagger2
public class ServiceMonitorController {
    private static Logger logger = LogManager.getLogger(ServiceMonitorController.class);

    @ApiOperation(value = "服务运行状态监控", httpMethod = "POST", notes = "SSP<->Service:服务运行状态监控，获取当前服务状态及对应数据库连接状态")
    @RequestMapping(value = "/monitor/sharedwifi_withdraw", produces = { "application/json" })
    public SmartHomeResponse<Object> getServiceMonitorStatus() {

        StatusResponse statusResponse = new StatusResponse();
        try {
            statusResponse.setGroupId(Manifests.read("Implementation-Vendor-Id"));
            statusResponse.setArtifactId(Manifests.read("Implementation-Title"));
            statusResponse.setBuildNum(Manifests.read("buildNumber"));
            statusResponse.setGitBranch(Manifests.read("gitBranch"));
            statusResponse.setGitCommit(Manifests.read("gitCommit"));
        } catch (Exception e) {
            logger.error(e);
        }
        return new SmartHomeResponse<Object>(0, "MonitorMessage", statusResponse);
    }
}
