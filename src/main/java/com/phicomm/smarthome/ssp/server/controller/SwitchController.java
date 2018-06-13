package com.phicomm.smarthome.ssp.server.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.phicomm.smarthome.ssp.server.consts.Const;
import com.phicomm.smarthome.ssp.server.model.SmartHomeResponse;
import com.phicomm.smarthome.ssp.server.model.SwRouterConfigModel;
import com.phicomm.smarthome.ssp.server.model.response.ResponseQuerySwitch;
import com.phicomm.smarthome.ssp.server.model.response.ResponseSwitchUpdateModel;
import com.phicomm.smarthome.ssp.server.service.impl.SwitchServiceImpl;

import io.swagger.annotations.ApiOperation;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * PROJECT_NAME: SspBusServer PACKAGE_NAME:
 * com.phicomm.ssp.server.service.sharedwifi.controller DESCRIPTION: AUTHOR:
 * liang04.zhang DATE: 2017/6/19
 */

@RestController
@ComponentScan
@EnableSwagger2
@PropertySource("classpath:application.properties")
public class SwitchController extends BaseController {

    private static final Logger LOGGER = LogManager.getLogger(SwitchController.class);

    @Value("${super.admin.name}")
    private String superAdminName;

    @Value("${super.admin.password}")
    private String superAdminPassword;

    @Autowired
    private SwitchServiceImpl switchServiceImpl;

    @ApiOperation(value = "更新switch", httpMethod = "POST", notes = "更新switch")
    @RequestMapping(value = "/switch/query", method = RequestMethod.POST, produces = { "application/json" })
    public SmartHomeResponse<ResponseQuerySwitch> querySwitch(HttpServletRequest request) {
        LOGGER.info("querySwitch begin");
        int errCode = Const.ResponseStatus.STATUS_OK;
        String errMsg = Const.ResponseStatus.STATUS_OK_STR;
        ResponseQuerySwitch rspObj = new ResponseQuerySwitch();
        List<SwRouterConfigModel> list = new ArrayList<SwRouterConfigModel>();
        list = switchServiceImpl.querySwitch();
        rspObj.setList(list);
        LOGGER.info("querySwitch end");

        return new SmartHomeResponse<ResponseQuerySwitch>(errCode, errMsg, rspObj);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @ApiOperation(value = "更新switch", httpMethod = "POST", notes = "更新switch")
    @RequestMapping(value = "/switch/update", method = RequestMethod.POST, produces = { "application/json" })
    public SmartHomeResponse<ResponseSwitchUpdateModel> updateSwitch(HttpServletRequest request,
            @RequestBody SwRouterConfigModel switchModel) {
        LOGGER.info("updateSwitch begin");

        int errCode = Const.ResponseStatus.STATUS_OK;
        String errMsg = Const.ResponseStatus.STATUS_OK_STR;

        int affectRow = switchServiceImpl.updateSwitch(switchModel);
        ResponseSwitchUpdateModel rspObj = new ResponseSwitchUpdateModel();
        rspObj.setAffectRow(affectRow);
        LOGGER.info("updateSwitch end,update affect row=" + affectRow);
        return new SmartHomeResponse<ResponseSwitchUpdateModel>(errCode, errMsg, rspObj);

    }

}
