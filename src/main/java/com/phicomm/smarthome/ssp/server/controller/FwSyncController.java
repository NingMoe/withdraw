package com.phicomm.smarthome.ssp.server.controller;

import com.phicomm.smarthome.model.SmartHomeResponse;
import com.phicomm.smarthome.ssp.server.consts.Const;
import com.phicomm.smarthome.ssp.server.model.BaseResponseModel;
import com.phicomm.smarthome.ssp.server.model.SwRouterConfigModel;
import com.phicomm.smarthome.ssp.server.model.dao.SwRouterConfigDaoModel;
import com.phicomm.smarthome.ssp.server.model.request.RequestRouterConfSettingModel;
import com.phicomm.smarthome.ssp.server.model.request.RouterConfSettingMockModel;
import com.phicomm.smarthome.ssp.server.service.RouterConfigService;
import com.phicomm.smarthome.ssp.server.service.impl.RouterConfigServiceImpl;
import com.phicomm.smarthome.ssp.server.service.impl.SwitchServiceImpl;
import com.phicomm.smarthome.util.StringUtil;
import io.swagger.annotations.ApiOperation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


/**
 * PROJECT_NAME: SspBusServer PACKAGE_NAME:
 * com.phicomm.ssp.server.service.sharedwifi.controller DESCRIPTION: AUTHOR:
 * xiangrong.ke DATE: 2018/01/03
 */

@RestController
@ComponentScan
@EnableSwagger2
@PropertySource("classpath:application.properties")
public class FwSyncController extends BaseController {

    private static final Logger logger = LogManager.getLogger(FwSyncController.class);

    @Autowired
    private RouterConfigServiceImpl routerConfigServiceImpl;

    @ApiOperation(value = "固件版本设置", httpMethod = "POST", notes = "固件版本设置")
    @RequestMapping(value = "/router_config_setting", method = RequestMethod.POST, produces = { "application/json" })
    public SmartHomeResponse<Object> routerConfSetting(@RequestBody RequestRouterConfSettingModel reqParam) {
        BaseResponseModel rspObj = new BaseResponseModel();
        //判断基本参数合法性
        if(reqParam == null || StringUtil.isNullOrEmpty(reqParam.getModel()) ||
                StringUtil.isNullOrEmpty(reqParam.getRomVersion()) ||
                StringUtil.isNullOrEmpty(reqParam.getSign())){
            logger.warn("router_config_setting,param error,return");
            rspObj.setRetCode(Const.ResponseStatus.STATUS_COMMON_NULL);
            rspObj.setRetMsg(Const.ResponseStatus.STATUS_COMMON_NULL_STR);
            return successResponse(rspObj);
        }
        logger.info("router_config_setting,model[{}],rom_version[{}],sign[{}]",reqParam.getModel(),reqParam.getRomVersion(),reqParam.getSign());

        //判断签名合法性
        RouterConfSettingMockModel mockModel = new RouterConfSettingMockModel(reqParam);
        if(!mockModel.getSign().equalsIgnoreCase(reqParam.getSign())){
            logger.warn("router_config_setting sign err,expect sign[{}],req sign[{}]",mockModel.getSign(),reqParam.getSign());
            rspObj.setRetCode(Const.ResponseStatus.STATUS_SIGN_ERR);
            rspObj.setRetMsg("签名错误");
            return successResponse(rspObj);
        }

        //入库
        SwRouterConfigDaoModel model = routerConfigServiceImpl.queryRouterConfig();
        //key:rom_version model status
        model.setRomVersion(reqParam.getRomVersion());
        model.setModel(reqParam.getModel());

        long ctime = System.currentTimeMillis()/1000l;
        model.setCreateTime(ctime);
        model.setUpdateTime(ctime);

        routerConfigServiceImpl.updateRouterConfig(model);

        return successResponse(rspObj);
    }

}
