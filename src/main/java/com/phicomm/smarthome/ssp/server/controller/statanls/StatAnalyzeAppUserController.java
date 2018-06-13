package com.phicomm.smarthome.ssp.server.controller.statanls;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.phicomm.smarthome.model.SmartHomeResponse;
import com.phicomm.smarthome.ssp.server.consts.Const;
import com.phicomm.smarthome.ssp.server.consts.Const.StatAnalyzeDataVar;
import com.phicomm.smarthome.ssp.server.controller.BaseController;
import com.phicomm.smarthome.ssp.server.model.request.RequestStatAppModel;
import com.phicomm.smarthome.ssp.server.model.response.ResponseQueryStatAppInfoModel;
import com.phicomm.smarthome.ssp.server.model.response.ResponseStatAppInfoModel;
import com.phicomm.smarthome.ssp.server.model.response.ResponseStatAppListModel;
import com.phicomm.smarthome.ssp.server.service.StatAnalyzeAppUserService;
import com.phicomm.smarthome.ssp.server.service.StatAnalyzeService;
import com.phicomm.smarthome.ssp.server.util.AccountDateUtil;
import com.phicomm.smarthome.util.StringUtil;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * 统计分析 App用户
 * 
 * @author fujiang.mao
 *
 */
@RestController
@ComponentScan
@EnableSwagger2
@PropertySource("classpath:application.properties")
public class StatAnalyzeAppUserController extends BaseController {

    private static final Logger LOGGER = LogManager.getLogger(StatAnalyzeAppUserController.class);

    @Autowired
    private StatAnalyzeService statAnalyzeService;

    @Autowired
    private StatAnalyzeAppUserService statAnalyzeAppUserService;

    /**
     * APP用户 统计指标信息
     * 
     * @param request
     * @return
     */
    @RequestMapping(value = "/query_stat_app_user_info", method = RequestMethod.POST, produces = { "application/json" })
    public SmartHomeResponse<Object> queryStatAppUserInfo(HttpServletRequest request) {
        LOGGER.debug("queryStatAppUserInfo begin");
        ResponseQueryStatAppInfoModel rspObj = statAnalyzeAppUserService.queryStatAppUserInfo();
        LOGGER.debug("queryStatAppUserInfo end");
        return successResponse(rspObj);
    }

    /**
     * APP用户 折线图
     * 
     * @param request
     * @return
     */
    @RequestMapping(value = "/stat_app_user", method = RequestMethod.POST, produces = { "application/json" })
    public SmartHomeResponse<Object> statAppUser(HttpServletRequest request, @RequestBody RequestStatAppModel model) {
        LOGGER.debug("statAppUser begin");
        int errCode = Const.ResponseStatus.STATUS_OK;
        String errMsg = Const.ResponseStatus.STATUS_OK_STR;
        ResponseStatAppInfoModel rspObj = new ResponseStatAppInfoModel();
        if (model == null || model.getSltIndex() == 0 || model.getIndexChild() == 0 || model.getVeidoo() == 0
                || StringUtil.isNullOrEmpty(model.getSltStartDate())
                || StringUtil.isNullOrEmpty(model.getSltEndDate())) {
            errCode = Const.ResponseStatus.STATUS_COMMON_NULL;
            errMsg = Const.ResponseStatus.STATUS_COMMON_NULL_STR;
            rspObj.setRetCode(errCode);
            rspObj.setRetMsg(errMsg);
            return successResponse(rspObj);
        }
        // 将日期xxxx/xx/xx转为xxxx-xx-xx格式
        model.setSltStartDate(AccountDateUtil.reverseDateFormat(model.getSltStartDate()));
        model.setSltEndDate(AccountDateUtil.reverseDateFormat(model.getSltEndDate()));
        // 规范日期范围
        model = statAnalyzeService.standardDateScope(model);
        // new user
        int indexChild = model.getIndexChild();
        if (indexChild == StatAnalyzeDataVar.INDEX_CHILD_NEW_USER) {
            rspObj = statAnalyzeAppUserService.statAppNewUser(model);
        } else if (indexChild == StatAnalyzeDataVar.INDEX_CHILD_PV_USER) {
            rspObj = statAnalyzeAppUserService.statAppPvUser(model);
        } else {
            rspObj = statAnalyzeAppUserService.statAppUvUser(model);
        }
        rspObj.setIndexChildName(statAnalyzeService.getIndexChildNameById(model.getIndexChild()));
        LOGGER.debug("statAppUser begin");
        return successResponse(rspObj);
    }

    /**
     * APP用户 列表统计
     * 
     * @param request
     * @return
     */
    @RequestMapping(value = "/stat_app_user_list", method = RequestMethod.POST, produces = { "application/json" })
    public SmartHomeResponse<Object> statAppUserList(HttpServletRequest request,
            @RequestBody RequestStatAppModel model) {
        LOGGER.debug("statAppUserList start");
        int errCode = Const.ResponseStatus.STATUS_OK;
        String errMsg = Const.ResponseStatus.STATUS_OK_STR;
        ResponseStatAppListModel rspObj = new ResponseStatAppListModel();
        if (model == null || model.getSltIndex() == 0 || model.getIndexChild() == 0 || model.getVeidoo() == 0
                || StringUtil.isNullOrEmpty(model.getSltStartDate())
                || StringUtil.isNullOrEmpty(model.getSltEndDate())) {
            errCode = Const.ResponseStatus.STATUS_COMMON_NULL;
            errMsg = Const.ResponseStatus.STATUS_COMMON_NULL_STR;
            rspObj.setRetCode(errCode);
            rspObj.setRetMsg(errMsg);
            return successResponse(rspObj);
        }
        if (model != null && (model.getPageSize() < 1) || model.getCurPage() < 1) {
            errCode = Const.ResponseStatus.STATUS_COMMON_NULL;
            errMsg = Const.ResponseStatus.STATUS_COMMON_NULL_STR;
            rspObj.setRetCode(errCode);
            rspObj.setRetMsg(errMsg);
            return successResponse(rspObj);
        }
        // 将日期xxxx/xx/xx转为xxxx-xx-xx格式
        model.setSltStartDate(AccountDateUtil.reverseDateFormat(model.getSltStartDate()));
        model.setSltEndDate(AccountDateUtil.reverseDateFormat(model.getSltEndDate()));
        // 规范日期范围
        model = statAnalyzeService.standardDateScope(model);
        rspObj = statAnalyzeAppUserService.getAnalyzeStatAppNewUserList(model, true);
        rspObj = statAnalyzeAppUserService.getAnalyzeStatAppPvUvList(rspObj, model);
        LOGGER.debug("statAppUserList end");
        return successResponse(rspObj);
    }

    /**
     * APP用户 列表导出到Excel
     * 
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/stat_app_user_list_export_excel", method = RequestMethod.GET)
    public SmartHomeResponse<Object> statAppUserListExportExcel(HttpServletRequest request,
            HttpServletResponse response, @RequestParam("slt_index") Integer sltIndex,
            @RequestParam("index_child") Integer indexChild, @RequestParam("veidoo") Integer veidoo,
            @RequestParam("slt_start_date") String sltStartDate, @RequestParam("slt_end_date") String sltEndDate) {
        LOGGER.debug("statAppUserListExportExcel start");
        int errCode = Const.ResponseStatus.STATUS_OK;
        String errMsg = Const.ResponseStatus.STATUS_OK_STR;
        ResponseStatAppListModel rspObj = new ResponseStatAppListModel();
        if (sltIndex == null || sltIndex.intValue() == 0 || indexChild == null || indexChild.intValue() == 0
                || veidoo == null || veidoo.intValue() == 0 || StringUtil.isNullOrEmpty(sltStartDate)
                || StringUtil.isNullOrEmpty(sltEndDate)) {
            errCode = Const.ResponseStatus.STATUS_COMMON_NULL;
            errMsg = Const.ResponseStatus.STATUS_COMMON_NULL_STR;
            rspObj.setRetCode(errCode);
            rspObj.setRetMsg(errMsg);
            return successResponse(rspObj);
        }
        RequestStatAppModel model = new RequestStatAppModel();
        model.setSltIndex(sltIndex);
        model.setIndexChild(indexChild);
        model.setVeidoo(veidoo);
        model.setSltStartDate(sltStartDate);
        model.setSltEndDate(sltEndDate);
        // 将日期xxxx/xx/xx转为xxxx-xx-xx格式
        model.setSltStartDate(AccountDateUtil.reverseDateFormat(model.getSltStartDate()));
        model.setSltEndDate(AccountDateUtil.reverseDateFormat(model.getSltEndDate()));
        // 规范日期范围
        model = statAnalyzeService.standardDateScope(model);
        // 获取所有需要导出的数据
        rspObj = statAnalyzeAppUserService.getAnalyzeStatAppNewUserList(model, true);
        rspObj = statAnalyzeAppUserService.getAnalyzeStatAppPvUvList(rspObj, model);
        // 导出数据到Excel
        statAnalyzeAppUserService.statAppUserListExportExcel(response, rspObj);
        LOGGER.debug("statAppUserListExportExcel end");
        return successResponse(rspObj);
    }

}