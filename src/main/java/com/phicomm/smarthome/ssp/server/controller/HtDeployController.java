package com.phicomm.smarthome.ssp.server.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.phicomm.smarthome.model.SmartHomeResponse;
import com.phicomm.smarthome.model.cache.PreFallRatioModel;
import com.phicomm.smarthome.ssp.server.consts.Const;
import com.phicomm.smarthome.ssp.server.consts.Const.ResponseStatus;
import com.phicomm.smarthome.ssp.server.model.BaseResponseModel;
import com.phicomm.smarthome.ssp.server.service.HtDeployService;
import com.phicomm.smarthome.util.StringUtil;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author fujiang.mao
 *
 */
@RestController
@ComponentScan
@EnableSwagger2
@PropertySource("classpath:application.properties")
public class HtDeployController extends BaseController {

    private static final Logger LOGGER = LogManager.getLogger(HtDeployController.class);

    @Autowired
    private HtDeployService htDeployService;

    /**
     * 分成比例生效时间检测 每分钟执行一次
     */
    @Scheduled(cron = "0 0/1 * * * ?")
    public void fallRatioStatusCheck() {
        LOGGER.info("fallRatioStatusCheck 每分钟执行一次，开始。。。。。。");
        htDeployService.fallRatioStatusCheck();
        LOGGER.info("fallRatioStatusCheck 每分钟执行一次，结束！");
    }

    @RequestMapping(value = "/update_fall_ratio", method = RequestMethod.POST, produces = { "application/json" })
    public SmartHomeResponse<Object> uploadFallRatio(HttpServletRequest request, @RequestBody PreFallRatioModel model) {
        LOGGER.info("update_fall_ratio begin");
        BaseResponseModel rspObj = new BaseResponseModel();
        int errCode = Const.ResponseStatus.STATUS_OK;
        String errMsg = Const.ResponseStatus.STATUS_OK_STR;
        if (model == null || model.getFallRatio() == 0 || StringUtil.isNullOrEmpty(model.getFallRatioEftTime())) {
            errCode = ResponseStatus.STATUS_COMMON_NULL;
            errMsg = ResponseStatus.STATUS_COMMON_NULL_STR;
            rspObj.setRetCode(errCode);
            rspObj.setRetMsg(errMsg);
            return successResponse(rspObj);
        }
        htDeployService.uploadFallRatio(model);
        return successResponse(rspObj);
    }

    /**
     * 管理后台查询分成比例
     * 
     * @param request
     * @return
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @RequestMapping(value = "/query_fall_ratio", method = RequestMethod.POST, produces = { "application/json" })
    public SmartHomeResponse<Object> queryFallRatio(HttpServletRequest request) {
        int errCode = Const.ResponseStatus.STATUS_OK;
        String errMsg = Const.ResponseStatus.STATUS_OK_STR;

        PreFallRatioModel queryFallRatio = htDeployService.queryFallRatio();
        if (queryFallRatio == null) {
            queryFallRatio = new PreFallRatioModel();
        }
        return new SmartHomeResponse(errCode, errMsg, queryFallRatio);
    }

}