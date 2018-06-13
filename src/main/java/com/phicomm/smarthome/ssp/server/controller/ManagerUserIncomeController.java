package com.phicomm.smarthome.ssp.server.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.ibatis.annotations.Param;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.phicomm.smarthome.model.SmartHomeResponse;
import com.phicomm.smarthome.model.ldapsys.common.BaseResponseModel;
import com.phicomm.smarthome.ssp.server.consts.Const;
import com.phicomm.smarthome.ssp.server.model.request.RequestIncomeRecordModel;
import com.phicomm.smarthome.ssp.server.model.request.RequestSwUserModel;
import com.phicomm.smarthome.ssp.server.model.response.ResponseIncomeRecordListModel;
import com.phicomm.smarthome.ssp.server.model.response.ResponseUserInfoListModel;
import com.phicomm.smarthome.ssp.server.service.CloudService;
import com.phicomm.smarthome.ssp.server.service.ManagerUserIncomeService;

import net.sf.json.JSONObject;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * 用户收益controller
 * 
 * @author fujiang.mao
 *
 */
@RestController
@ComponentScan
@EnableSwagger2
@PropertySource("classpath:application.properties")
@RequestMapping("/manager")
public class ManagerUserIncomeController extends BaseController {

    private static final Logger LOGGER = LogManager.getLogger(ManagerUserIncomeController.class);

    @Autowired
    private ManagerUserIncomeService managerUserIncomeService;

    @Autowired
    private CloudService cloudService;

    /**
     * 用户 列表
     * 
     * @param request
     * @return
     */
    @RequestMapping(value = "/query_user_list", method = RequestMethod.POST, produces = { "application/json" })
    public SmartHomeResponse<Object> queryUserList(HttpServletRequest request, @RequestBody RequestSwUserModel model) {
        LOGGER.debug("queryUserList start");
        int errCode = Const.ResponseStatus.STATUS_OK;
        String errMsg = Const.ResponseStatus.STATUS_OK_STR;
        ResponseUserInfoListModel rspObj = new ResponseUserInfoListModel();
        if (model == null || model.getPageSize() < 1 || model.getCurPage() < 1) {
            errCode = Const.ResponseStatus.STATUS_COMMON_NULL;
            errMsg = Const.ResponseStatus.STATUS_COMMON_NULL_STR;
            rspObj.setRetCode(errCode);
            rspObj.setRetMsg(errMsg);
            return successResponse(rspObj);
        }
        rspObj = managerUserIncomeService.getUserInfoList(model);
        LOGGER.debug("queryUserList end");
        return successResponse(rspObj);
    }

    /**
     * 收益记录列表
     * 
     * @param request
     * @return
     */
    @RequestMapping(value = "/query_income_record_list", method = RequestMethod.POST, produces = { "application/json" })
    public SmartHomeResponse<Object> queryIncomeRecordList(HttpServletRequest request,
            @RequestBody RequestIncomeRecordModel model) {
        JSONObject jsonObject = JSONObject.fromObject(model);
        LOGGER.info("queryIncomeRecordList start model[{}]", jsonObject.toString());
        int errCode = Const.ResponseStatus.STATUS_OK;
        String errMsg = Const.ResponseStatus.STATUS_OK_STR;
        ResponseIncomeRecordListModel rspObj = new ResponseIncomeRecordListModel();
        if (model == null || model.getPageSize() < 1 || model.getCurPage() < 1) {
            errCode = Const.ResponseStatus.STATUS_COMMON_NULL;
            errMsg = Const.ResponseStatus.STATUS_COMMON_NULL_STR;
            rspObj.setRetCode(errCode);
            rspObj.setRetMsg(errMsg);
            return successResponse(rspObj);
        }
        rspObj.setIncomeRecordList(managerUserIncomeService.getIncomeRecordList(model));
        rspObj.setTotalCount(managerUserIncomeService.getIncomeRecordListCount(model));
        LOGGER.debug("queryIncomeRecordList end");
        return successResponse(rspObj);
    }

    @RequestMapping(value = "/test", method = RequestMethod.GET, produces = { "application/json" })
    public SmartHomeResponse<Object> test(HttpServletRequest request, @Param("uid") String uid) {
        LOGGER.debug("queryIncomeRecordList start");
        BaseResponseModel rspObj = new BaseResponseModel();
        cloudService.getUserMacByUid(NumberUtils.toLong(uid + ""));
        LOGGER.debug("queryIncomeRecordList end");
        return successResponse(rspObj);
    }

}