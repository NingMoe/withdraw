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
import com.phicomm.smarthome.ssp.server.controller.BaseController;
import com.phicomm.smarthome.ssp.server.model.BaseResponseModel;
import com.phicomm.smarthome.ssp.server.model.request.RequestStatAppModel;
import com.phicomm.smarthome.ssp.server.model.response.ResponseQueryStatAppInfoModel;
import com.phicomm.smarthome.ssp.server.model.response.ResponseStatAppInfoModel;
import com.phicomm.smarthome.ssp.server.model.response.ResponseStatSurveyListModel;
import com.phicomm.smarthome.ssp.server.model.response.ResponseStatSurveyTotalInfoModel;
import com.phicomm.smarthome.ssp.server.service.StatAnalyzeService;
import com.phicomm.smarthome.ssp.server.service.StatAnalyzeSurveyService;
import com.phicomm.smarthome.ssp.server.util.AccountDateUtil;
import com.phicomm.smarthome.util.StringUtil;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * 统计分析 概况
 * 
 * @author fujiang.mao
 *
 */
@RestController
@ComponentScan
@EnableSwagger2
@PropertySource("classpath:application.properties")
public class StatAnalyzeSurveyController extends BaseController {

    private static final Logger LOGGER = LogManager.getLogger(StatAnalyzeSurveyController.class);

    @Autowired
    private StatAnalyzeService statAnalyzeService;

    @Autowired
    private StatAnalyzeSurveyService statAnalyzeSurveyService;

    /**
     * 概况 统计指标信息
     * 
     * @param request
     * @return
     */
    @RequestMapping(value = "/query_stat_survey_info", method = RequestMethod.POST, produces = { "application/json" })
    public SmartHomeResponse<Object> queryStatSurveyInfo(HttpServletRequest request) {
        LOGGER.debug("queryStatSurveyInfo begin");
        ResponseQueryStatAppInfoModel rspObj = statAnalyzeSurveyService.queryStatSurveyInfo();
        LOGGER.debug("queryStatSurveyInfo end");
        return successResponse(rspObj);
    }

    /**
     * 概况 累计数据
     * 
     * @param request
     * @return
     */
    @RequestMapping(value = "/stat_survey_total_info", method = RequestMethod.POST, produces = { "application/json" })
    public SmartHomeResponse<Object> statSurveyTotalInfo(HttpServletRequest request) {
        LOGGER.debug("statSurveyTotalInfo begin");
        ResponseStatSurveyTotalInfoModel rspObj = new ResponseStatSurveyTotalInfoModel();
        rspObj = statAnalyzeSurveyService.statSurveyTotalInfo(rspObj);
        LOGGER.debug("statSurveyTotalInfo end");
        return successResponse(rspObj);
    }

    /**
     * 概况 折线图
     * 
     * @param request
     * @return
     */
    @RequestMapping(value = "/stat_survey", method = RequestMethod.POST, produces = { "application/json" })
    public SmartHomeResponse<Object> statSurvey(HttpServletRequest request, @RequestBody RequestStatAppModel model) {
        int errCode = Const.ResponseStatus.STATUS_OK;
        String errMsg = Const.ResponseStatus.STATUS_OK_STR;
        ResponseStatAppInfoModel rspObj = new ResponseStatAppInfoModel();
        if (model == null || model.getIndexChild() == 0 || model.getVeidoo() == 0
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
        rspObj = statAnalyzeSurveyService.statSurvey(model);
        return successResponse(rspObj);
    }

    /**
     * 概况 列表统计
     * 
     * @param request
     * @return
     */
    @RequestMapping(value = "/stat_survey_list", method = RequestMethod.POST, produces = { "application/json" })
    public SmartHomeResponse<Object> statSurveyList(HttpServletRequest request,
            @RequestBody RequestStatAppModel model) {
        int errCode = Const.ResponseStatus.STATUS_OK;
        String errMsg = Const.ResponseStatus.STATUS_OK_STR;
        ResponseStatSurveyListModel rspObj = new ResponseStatSurveyListModel();
        if (model == null || model.getIndexChild() == 0 || model.getVeidoo() == 0
                || StringUtil.isNullOrEmpty(model.getSltStartDate())
                || StringUtil.isNullOrEmpty(model.getSltEndDate())) {
            errCode = Const.ResponseStatus.STATUS_COMMON_NULL;
            errMsg = Const.ResponseStatus.STATUS_COMMON_NULL_STR;
            rspObj.setRetCode(errCode);
            rspObj.setRetMsg(errMsg);
            return successResponse(rspObj);
        }
        if (model != null && (model.getPageSize() < 1) || model.getCurPage() < 1) {
            LOGGER.error("queryFirmwareVersionList error STATUS_PARA_ABNORBAL");
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
        rspObj = statAnalyzeSurveyService.statSurveyDataList(model, true);
        return successResponse(rspObj);
    }

    /**
     * 概况 折线图列表导出到Excel
     * 
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/stat_survey_list_export_excel", method = RequestMethod.GET)
    public SmartHomeResponse<Object> statSurveyListExportExcel(HttpServletRequest request, HttpServletResponse response,
            @RequestParam("index_child") Integer indexChild, @RequestParam("veidoo") Integer veidoo,
            @RequestParam("slt_start_date") String sltStartDate, @RequestParam("slt_end_date") String sltEndDate) {
        int errCode = Const.ResponseStatus.STATUS_OK;
        String errMsg = Const.ResponseStatus.STATUS_OK_STR;
        BaseResponseModel rspObj = new BaseResponseModel();
        ResponseStatSurveyListModel listModel = new ResponseStatSurveyListModel();
        if (indexChild == null || indexChild.intValue() == 0 || veidoo == null || veidoo.intValue() == 0
                || StringUtil.isNullOrEmpty(sltStartDate) || StringUtil.isNullOrEmpty(sltEndDate)) {
            errCode = Const.ResponseStatus.STATUS_COMMON_NULL;
            errMsg = Const.ResponseStatus.STATUS_COMMON_NULL_STR;
            rspObj.setRetCode(errCode);
            rspObj.setRetMsg(errMsg);
            return successResponse(rspObj);
        }
        RequestStatAppModel model = new RequestStatAppModel();
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
        listModel = statAnalyzeSurveyService.statSurveyDataList(model, false);
        // 导出数据到Excel
        statAnalyzeSurveyService.statSurveyListExportExcel(response, listModel);
        return successResponse(rspObj);
    }

}