package com.phicomm.smarthome.ssp.server.controller.statanls;

import com.phicomm.smarthome.model.SmartHomeResponse;
import com.phicomm.smarthome.ssp.server.consts.Const;
import com.phicomm.smarthome.ssp.server.controller.BaseController;
import com.phicomm.smarthome.ssp.server.model.request.RequestStatTimePeriodModel;
import com.phicomm.smarthome.ssp.server.model.response.ResponseStatAppListModel;
import com.phicomm.smarthome.ssp.server.model.response.ResponseStatTimePeriodListModel;
import com.phicomm.smarthome.ssp.server.model.response.ResponseStatTimePeriodModel;
import com.phicomm.smarthome.ssp.server.service.StatAnalyzeRewardService;
import com.phicomm.smarthome.ssp.server.service.StatAnalyzeService;
import com.phicomm.smarthome.ssp.server.service.StatAnalyzeTimePeriodRewardService;
import com.phicomm.smarthome.ssp.server.util.AccountDateUtil;
import com.phicomm.smarthome.util.StringUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * 统计分析 任意时段内用户收益排名
 *
 *
 */
@RestController
@ComponentScan
@EnableSwagger2
@PropertySource("classpath:application.properties")
public class StatAnalyzeTimePeriodRewardController extends BaseController {

    private static final Logger LOGGER = LogManager.getLogger(StatAnalyzeTimePeriodRewardController.class);

    @Autowired
    private StatAnalyzeService statAnalyzeService;

    @Autowired
    private StatAnalyzeTimePeriodRewardService statAnalyzeTimePeriodRewardService;


    /**
     *
     * 统计模块优化，可查任意时段
     * 输入格式：
     *           slt_start_date : yyyy/MM/dd
     *           slt_end_date   : yyyy/MM/dd
     *           page_size      : int
     *           cur_page       : int
     *
     */
    @RequestMapping(value = "/rank_by_time_period", method = RequestMethod.POST, produces = { "application/json" })
    public SmartHomeResponse<Object> rankByTimePeriod(HttpServletRequest request,
              @RequestBody RequestStatTimePeriodModel model) {
        LOGGER.debug("statTimePeriodUserIncomeList start");
        int errCode = Const.ResponseStatus.STATUS_OK;
        String errMsg = Const.ResponseStatus.STATUS_OK_STR;
        ResponseStatTimePeriodListModel rspObj = new ResponseStatTimePeriodListModel();
        if (model == null || StringUtil.isNullOrEmpty(model.getSltStartDate())
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

        model = statAnalyzeService.standardDateScope(model);

        rspObj = statAnalyzeTimePeriodRewardService.getAnalyzeStatTimePeriodRewardList(model,true);

        return successResponse(rspObj);
    }


//    @RequestMapping(value = "/rank_by_time_period", method = RequestMethod.GET, produces = { "application/json" })
//    public SmartHomeResponse<Object> rankByTimePeriod(HttpServletRequest request, @RequestParam("dayBegin") String dayBegin,
//                                                      @RequestParam("dayEnd") String dayEnd) {
//        SmartHomeResponse resObj = new SmartHomeResponse();
//
//        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//        // 将日期xxxx/xx/xx转为xxxx-xx-xx格式
//        dayBegin = AccountDateUtil.reverseDateFormat(dayBegin);
//        dayEnd = AccountDateUtil.reverseDateFormat(dayEnd);
//        long dayBeginTs = 0;
//        long dayEndTs = 0;
//        try {
//            dayBeginTs = format.parse(dayBegin).getTime()/1000;
//            dayEndTs = format.parse(dayEnd).getTime()/1000;
//        }catch (ParseException e){
//            LOGGER.error("Get the millisecond error " + e.getMessage());
//        }
//
//        //获得日期间隔天数
//        long days = AccountDateUtil.countDay(dayBegin,dayEnd);
//
//        //得到查询结果数量
//        int userLimit = days <= 90 ? 100 : 200;
//
//        long nowTs =  System.currentTimeMillis()/1000;
//        LOGGER.info("rank_by_time_period,[{}],[{}],[{}],[{}]",dayBeginTs,dayEndTs,nowTs,userLimit);
//
//        List<ResponseStatTimePeriodModel> models = statAnalyzeTimePeriodRewardService.
//                getAnalyzeStatTimePeriodRewardList(dayBeginTs,dayEndTs,
//                        nowTs,userLimit);
//        LOGGER.info("size[{}]",models.size());
//        for (ResponseStatTimePeriodModel model:models) {
//            model.setDayBegin(dayBegin);
//            model.setDayEnd(dayEnd);
//            model.setPortrailUrl("");
//        }
//
//        resObj.setResult(models);
//        LOGGER.info("res[{}]",resObj);
//
//        return resObj;
//    }


}