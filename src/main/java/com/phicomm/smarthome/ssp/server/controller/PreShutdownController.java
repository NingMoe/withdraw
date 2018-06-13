package com.phicomm.smarthome.ssp.server.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.phicomm.smarthome.model.cache.PreShutdownModel;
import com.phicomm.smarthome.ssp.server.consts.Const;
import com.phicomm.smarthome.ssp.server.model.SmartHomeResponse;
import com.phicomm.smarthome.ssp.server.model.response.ResponseSwitchUpdateModel;
import com.phicomm.smarthome.ssp.server.service.PreShutdownSettingService;

import io.swagger.annotations.ApiOperation;

/**
 * PROJECT_NAME: SspBusServer PACKAGE_NAME:
 * com.phicomm.ssp.server.service.sharedwifi.controller DESCRIPTION: AUTHOR:
 * xiangrong.ke DATE: 2017/8/23
 */

@RestController
@ComponentScan
@PropertySource("classpath:application.properties")
public class PreShutdownController extends BaseController {

    private static final Logger LOGGER = LogManager.getLogger(PreShutdownController.class);

    @Autowired
    private PreShutdownSettingService preShutdownSettingService;

    @RequestMapping(value = "/preshutdown/query", method = RequestMethod.POST, produces = { "application/json" })
    public SmartHomeResponse<PreShutdownModel> querySwitch(HttpServletRequest request) {
        LOGGER.info("querySwitch begin");
        int errCode = Const.ResponseStatus.STATUS_OK;
        String errMsg = Const.ResponseStatus.STATUS_OK_STR;
        PreShutdownModel model = preShutdownSettingService.querySwitch();
        if (model == null) {
            model = new PreShutdownModel();
        }
        LOGGER.info("querySwitch end");

        return new SmartHomeResponse<PreShutdownModel>(errCode, errMsg, model);
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @ApiOperation(value = "更新switch", httpMethod = "POST", notes = "更新switch")
    @RequestMapping(value = "/preshutdown/update", method = RequestMethod.POST, produces = { "application/json" })
    public SmartHomeResponse<Object> updateSwitch(HttpServletRequest request,
            @RequestBody PreShutdownModel switchModel) {
        LOGGER.info("updateSwitch begin");

        int errCode = Const.ResponseStatus.STATUS_OK;
        String errMsg = Const.ResponseStatus.STATUS_OK_STR;

        int affectRow = preShutdownSettingService.updateSwitch(switchModel);
        LOGGER.info("updateSwitch end,update affect row=" + affectRow);
        ResponseSwitchUpdateModel rspObj = new ResponseSwitchUpdateModel();
        rspObj.setAffectRow(affectRow);
        return new SmartHomeResponse(errCode, errMsg, rspObj);
    }
}
