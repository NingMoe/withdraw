package com.phicomm.smarthome.ssp.server.controller.statanls;

import java.util.List;

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
import com.phicomm.smarthome.ssp.server.model.TblStatDurationUserDetailModel;
import com.phicomm.smarthome.ssp.server.model.request.RequestStatAppModel;
import com.phicomm.smarthome.ssp.server.model.request.RequestStatRewardDurationModel;
import com.phicomm.smarthome.ssp.server.model.response.ResponseQueryStatRewardDurationInfoModel;
import com.phicomm.smarthome.ssp.server.model.response.ResponseQueryStatRewardUserInfoModel;
import com.phicomm.smarthome.ssp.server.model.response.ResponseStatAppInfoModel;
import com.phicomm.smarthome.ssp.server.model.response.ResponseStatAppListModel;
import com.phicomm.smarthome.ssp.server.model.response.ResponseStatDurationListModel;
import com.phicomm.smarthome.ssp.server.model.response.ResponseStatSwUserDetailListModel;
import com.phicomm.smarthome.ssp.server.service.StatAnalyzeRewardService;
import com.phicomm.smarthome.ssp.server.service.StatAnalyzeService;
import com.phicomm.smarthome.ssp.server.util.AccountDateUtil;
import com.phicomm.smarthome.util.StringUtil;

import net.sf.json.JSONObject;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * 统计分析 打赏用户
 * 
 * @author fujiang.mao
 *
 */
@RestController
@ComponentScan
@EnableSwagger2
@PropertySource("classpath:application.properties")
public class StatAnalyzeRewardController extends BaseController {

    private static final Logger LOGGER = LogManager.getLogger(StatAnalyzeRewardController.class);

    @Autowired
    private StatAnalyzeService statAnalyzeService;

    @Autowired
    private StatAnalyzeRewardService statAnalyzeRewardService;

    /**
     * 打赏用户 统计指标信息
     * 
     * @param request
     * @return
     */
    @RequestMapping(value = "/query_stat_reward_user_info", method = RequestMethod.POST, produces = {
            "application/json" })
    public SmartHomeResponse<Object> queryStatRewardUserInfo(HttpServletRequest request) {
        LOGGER.debug("queryStatRewardUserInfo begin");
        ResponseQueryStatRewardUserInfoModel rspObj = statAnalyzeRewardService.queryStatRewardUserInfo();
        LOGGER.debug("queryStatRewardUserInfo end");
        return successResponse(rspObj);
    }

    /**
     * 打赏用户 折线图
     * 
     * @param request
     * @return
     */
    @RequestMapping(value = "/stat_reward_user", method = RequestMethod.POST, produces = { "application/json" })
    public SmartHomeResponse<Object> statRewardUser(HttpServletRequest request,
            @RequestBody RequestStatAppModel model) {
        LOGGER.debug("statRewardUser begin");
        int errCode = Const.ResponseStatus.STATUS_OK;
        String errMsg = Const.ResponseStatus.STATUS_OK_STR;
        ResponseStatAppInfoModel rspObj = new ResponseStatAppInfoModel();
        if (model == null || model.getSltIndex() == 0 || model.getIndexChild() == 0 || model.getIndexChild() == 1
                || model.getVeidoo() == 0 || model.getSwDevice() == 0
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
        rspObj = statAnalyzeRewardService.statRewardUser(model);
        rspObj.setIndexChildName(statAnalyzeService.getIndexChildNameById(model.getIndexChild()));
        LOGGER.debug("statRewardUser end");
        return successResponse(rspObj);
    }

    /**
     * 打赏用户 折线图列表统计
     * 
     * @param request
     * @return
     */
    @RequestMapping(value = "/stat_reward_user_list", method = RequestMethod.POST, produces = { "application/json" })
    public SmartHomeResponse<Object> statRewardUserList(HttpServletRequest request,
            @RequestBody RequestStatAppModel model) {
        LOGGER.debug("statRewardUserList begin");
        JSONObject jsonObject = JSONObject.fromObject(model);
        System.out.println(jsonObject.toString());
        int errCode = Const.ResponseStatus.STATUS_OK;
        String errMsg = Const.ResponseStatus.STATUS_OK_STR;
        ResponseStatAppListModel rspObj = new ResponseStatAppListModel();
        if (model == null || model.getSltIndex() == 0 || model.getIndexChild() == 0 || model.getVeidoo() == 0
                || model.getSwDevice() == 0 || StringUtil.isNullOrEmpty(model.getSltStartDate())
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
        rspObj = statAnalyzeRewardService.getAnalyzeStatRewardList(model, true);
        LOGGER.debug("statRewardUserList end");
        return successResponse(rspObj);
    }

    /**
     * 打赏用户 折线图列表导出到Excel
     * 
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/stat_reward_user_list_export_excel", method = RequestMethod.GET)
    public SmartHomeResponse<Object> statRewardUserListExportExcel(HttpServletRequest request,
            HttpServletResponse response, @RequestParam("slt_index") Integer sltIndex,
            @RequestParam("index_child") Integer indexChild, @RequestParam("sw_device") Integer swDevice,
            @RequestParam("veidoo") Integer veidoo, @RequestParam("slt_start_date") String sltStartDate,
            @RequestParam("slt_end_date") String sltEndDate) {
        LOGGER.debug("statRewardUserListExportExcel begin");
        int errCode = Const.ResponseStatus.STATUS_OK;
        String errMsg = Const.ResponseStatus.STATUS_OK_STR;
        BaseResponseModel rspObj = new BaseResponseModel();
        ResponseStatAppListModel listModel = new ResponseStatAppListModel();
        if (sltIndex == null || sltIndex.intValue() == 0 || indexChild == null || indexChild.intValue() == 0
                || veidoo == null || veidoo.intValue() == 0 || swDevice == null || swDevice.intValue() == 0
                || StringUtil.isNullOrEmpty(sltStartDate) || StringUtil.isNullOrEmpty(sltEndDate)) {
            errCode = Const.ResponseStatus.STATUS_COMMON_NULL;
            errMsg = Const.ResponseStatus.STATUS_COMMON_NULL_STR;
            rspObj.setRetCode(errCode);
            rspObj.setRetMsg(errMsg);
            return successResponse(rspObj);
        }
        RequestStatAppModel model = new RequestStatAppModel();
        model.setSltIndex(sltIndex);
        model.setIndexChild(indexChild);
        model.setSwDevice(swDevice);
        model.setVeidoo(veidoo);
        model.setSltStartDate(sltStartDate);
        model.setSltEndDate(sltEndDate);
        // 将日期xxxx/xx/xx转为xxxx-xx-xx格式
        model.setSltStartDate(AccountDateUtil.reverseDateFormat(model.getSltStartDate()));
        model.setSltEndDate(AccountDateUtil.reverseDateFormat(model.getSltEndDate()));
        // 规范日期范围
        model = statAnalyzeService.standardDateScope(model);
        // 获取所有需要导出的数据
        listModel = statAnalyzeRewardService.getAnalyzeStatRewardList(model, false);
        // 导出数据到Excel
        statAnalyzeRewardService.statRewardUserListExportExcel(response, listModel);
        LOGGER.debug("statRewardUserListExportExcel end");
        return successResponse(rspObj);
    }

    /**
     * 打赏用户 时长分布指标信息
     * 
     * @param request
     * @return
     */
    @RequestMapping(value = "/query_stat_reward_duration_info", method = RequestMethod.POST, produces = {
            "application/json" })
    public SmartHomeResponse<Object> queryStatRewardDurationInfo(HttpServletRequest request) {
        LOGGER.debug("queryStatRewardDurationInfo begin");
        ResponseQueryStatRewardDurationInfoModel rspObj = statAnalyzeRewardService.queryStatRewardDurationInfo();
        LOGGER.debug("queryStatRewardDurationInfo end");
        return successResponse(rspObj);
    }

    /**
     * 打赏用户 时长分布柱状图
     * 
     * @param request
     * @return
     */
    @RequestMapping(value = "/stat_reward_duration", method = RequestMethod.POST, produces = { "application/json" })
    public SmartHomeResponse<Object> statRewardDuration(HttpServletRequest request,
            @RequestBody RequestStatRewardDurationModel model) {
        LOGGER.debug("statRewardDuration begin");
        int errCode = Const.ResponseStatus.STATUS_OK;
        String errMsg = Const.ResponseStatus.STATUS_OK_STR;
        ResponseStatAppInfoModel rspObj = new ResponseStatAppInfoModel();
        if (model == null || model.getSwDuration() == 0 || StringUtil.isNullOrEmpty(model.getDurationDate())) {
            errCode = Const.ResponseStatus.STATUS_COMMON_NULL;
            errMsg = Const.ResponseStatus.STATUS_COMMON_NULL_STR;
            rspObj.setRetCode(errCode);
            rspObj.setRetMsg(errMsg);
            return successResponse(rspObj);
        }
        // 将日期xxxx/xx/xx转为xxxx-xx-xx格式
        model.setDurationDate(AccountDateUtil.reverseDateFormat(model.getDurationDate()));
        rspObj = statAnalyzeRewardService.statRewardDuration(model);
        LOGGER.debug("statRewardDuration end");
        return successResponse(rspObj);
    }

    /**
     * 打赏用户 时长分布明细列表
     * 
     * @param request
     * @return
     */
    @RequestMapping(value = "/stat_reward_duration_list", method = RequestMethod.POST, produces = {
            "application/json" })
    public SmartHomeResponse<Object> statRewardDurationList(HttpServletRequest request,
            @RequestBody RequestStatRewardDurationModel model) {
        LOGGER.debug("statRewardDurationList begin");
        int errCode = Const.ResponseStatus.STATUS_OK;
        String errMsg = Const.ResponseStatus.STATUS_OK_STR;
        ResponseStatDurationListModel rspObj = new ResponseStatDurationListModel();
        if (model == null || StringUtil.isNullOrEmpty(model.getDurationDate())) {
            errCode = Const.ResponseStatus.STATUS_COMMON_NULL;
            errMsg = Const.ResponseStatus.STATUS_COMMON_NULL_STR;
            rspObj.setRetCode(errCode);
            rspObj.setRetMsg(errMsg);
            return successResponse(rspObj);
        }
        // 将日期xxxx/xx/xx转为xxxx-xx-xx格式
        model.setDurationDate(AccountDateUtil.reverseDateFormat(model.getDurationDate()));
        rspObj = statAnalyzeRewardService.statRewardDurationList(model);
        LOGGER.debug("statRewardDurationList end");
        return successResponse(rspObj);
    }

    /**
     * 打赏用户 时长分布明细列表导出到Excel
     * 
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/stat_reward_duration_list_export_excel", method = RequestMethod.GET)
    public SmartHomeResponse<Object> statRewardDurationListExportExcel(HttpServletRequest request,
            HttpServletResponse response, @RequestParam("duration_date") String durationDate) {
        LOGGER.debug("statRewardUserListExportExcel begin");
        int errCode = Const.ResponseStatus.STATUS_OK;
        String errMsg = Const.ResponseStatus.STATUS_OK_STR;
        ResponseStatDurationListModel rspObj = new ResponseStatDurationListModel();
        if (StringUtil.isNullOrEmpty(durationDate)) {
            errCode = Const.ResponseStatus.STATUS_COMMON_NULL;
            errMsg = Const.ResponseStatus.STATUS_COMMON_NULL_STR;
            rspObj.setRetCode(errCode);
            rspObj.setRetMsg(errMsg);
            return successResponse(rspObj);
        }
        RequestStatRewardDurationModel model = new RequestStatRewardDurationModel();
        // 将日期xxxx/xx/xx转为xxxx-xx-xx格式
        model.setDurationDate(AccountDateUtil.reverseDateFormat(durationDate));
        rspObj = statAnalyzeRewardService.statRewardDurationList(model);
        // 导出数据到Excel
        statAnalyzeRewardService.statRewardDurationListExportExcel(response, rspObj.getDurationList());
        LOGGER.debug("statRewardUserListExportExcel end");
        return successResponse(rspObj);
    }

    /**
     * 打赏用户 数据明细列表
     * 
     * @param request
     * @return
     */
    @RequestMapping(value = "/stat_reward_sw_user_detail_list", method = RequestMethod.POST, produces = {
            "application/json" })
    public SmartHomeResponse<Object> statRewardSwUserDetailList(HttpServletRequest request,
            @RequestBody RequestStatAppModel model) {
        LOGGER.debug("statRewardSwUserDetailList begin");
        int errCode = Const.ResponseStatus.STATUS_OK;
        String errMsg = Const.ResponseStatus.STATUS_OK_STR;
        ResponseStatSwUserDetailListModel rspObj = new ResponseStatSwUserDetailListModel();
        if (model == null || StringUtil.isNullOrEmpty(model.getSltStartDate())
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
        List<TblStatDurationUserDetailModel> detailList = statAnalyzeRewardService
                .getAnalyzeStatRewardSwUserDetailList(model);
        rspObj.setDetailList(detailList);
        rspObj.setTotalCount(statAnalyzeRewardService.getAnalyzeStatRewardSwUserDetailListCount(model));
        LOGGER.debug("statRewardSwUserDetailList end");
        return successResponse(rspObj);
    }

    /**
     * 打赏用户 数据明细列表导出到Excel
     * 
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/stat_reward_sw_user_detail_list_export_excel", method = RequestMethod.GET)
    public SmartHomeResponse<Object> statRewardSwUserDetailListExportExcel(HttpServletRequest request,
            HttpServletResponse response, @RequestParam("slt_start_date") String sltStartDate,
            @RequestParam("slt_end_date") String sltEndDate) {
        LOGGER.debug("statRewardSwUserDetailListExportExcel begin");
        int errCode = Const.ResponseStatus.STATUS_OK;
        String errMsg = Const.ResponseStatus.STATUS_OK_STR;
        ResponseStatSwUserDetailListModel rspObj = new ResponseStatSwUserDetailListModel();
        if (StringUtil.isNullOrEmpty(sltStartDate) || StringUtil.isNullOrEmpty(sltEndDate)) {
            errCode = Const.ResponseStatus.STATUS_COMMON_NULL;
            errMsg = Const.ResponseStatus.STATUS_COMMON_NULL_STR;
            rspObj.setRetCode(errCode);
            rspObj.setRetMsg(errMsg);
            return successResponse(rspObj);
        }
        RequestStatAppModel model = new RequestStatAppModel();
        // 将日期xxxx/xx/xx转为xxxx-xx-xx格式
        model.setSltStartDate(AccountDateUtil.reverseDateFormat(sltStartDate));
        model.setSltEndDate(AccountDateUtil.reverseDateFormat(sltEndDate));
        List<TblStatDurationUserDetailModel> list = statAnalyzeRewardService.geAllTblStatDurationUserDetails(model);
        // 导出数据到Excel
        statAnalyzeRewardService.statRewardSwUserDetailListExportExcel(response, list);
        LOGGER.debug("statRewardSwUserDetailListExportExcel end");
        return successResponse(rspObj);
    }

}