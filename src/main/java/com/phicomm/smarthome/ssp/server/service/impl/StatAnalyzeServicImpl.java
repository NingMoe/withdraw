package com.phicomm.smarthome.ssp.server.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.phicomm.smarthome.ssp.server.model.request.RequestStatTimePeriodModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.phicomm.smarthome.ssp.server.consts.Const.StatAnalyzeDataVar;
import com.phicomm.smarthome.ssp.server.enums.StatIndexChildEnum;
import com.phicomm.smarthome.ssp.server.model.request.RequestStatAppModel;
import com.phicomm.smarthome.ssp.server.service.StatAnalyzeService;
import com.phicomm.smarthome.ssp.server.util.AccountDateUtil;

/**
 * @author fujiang.mao
 *
 */
@Service
public class StatAnalyzeServicImpl implements StatAnalyzeService {

    public static final Logger LOGGER = LogManager.getLogger(StatAnalyzeServicImpl.class);

    @Override
    public RequestStatAppModel standardDateScope(RequestStatAppModel model) {
        try {
            int veidoo = model.getVeidoo();
            // 起始日期不能大于当前日期
            if (AccountDateUtil.compareTwoDate(model.getSltStartDate(), AccountDateUtil.getNowDate())) {
                model.setSltStartDate(AccountDateUtil.getNowDate());
            }
            // 结束日期不能大于当前日期
            if (AccountDateUtil.compareTwoDate(model.getSltEndDate(), AccountDateUtil.getNowDate())) {
                model.setSltEndDate(AccountDateUtil.getNowDate());
            }
            String startDate = model.getSltStartDate() + " 00:00:00";
            String endDate = model.getSltEndDate() + " 23:59:59";
            String beforeByEndDate = model.getSltEndDate();
            switch (veidoo) {
            case StatAnalyzeDataVar.TIME_VEIDOO_DAY:// 维度 日
                int days = AccountDateUtil.getDifferentDays(startDate, endDate);
                if (days + 1 > StatAnalyzeDataVar.SEARCH_LENGTH_DAYS) {
                    model.setSltStartDate(AccountDateUtil.getDayBeforeDate(beforeByEndDate,
                            StatAnalyzeDataVar.SEARCH_LENGTH_DAYS - 1));
                }
                break;
            case StatAnalyzeDataVar.TIME_VEIDOO_WEEK:// 维度 周
                int weeks = AccountDateUtil.getDifferentWeeks(startDate, endDate);
                if (weeks + 1 > StatAnalyzeDataVar.SEARCH_LENGTH_WEEK) {
                    model.setSltStartDate(AccountDateUtil.getDayBeforeDate(beforeByEndDate,
                            StatAnalyzeDataVar.SEARCH_LENGTH_WEEK * 7));
                }
                break;
            case StatAnalyzeDataVar.TIME_VEIDOO_MONTH:// 维度 月
                int month = AccountDateUtil.getDifferentMonth(startDate, endDate);
                if (month + 1 > StatAnalyzeDataVar.SEARCH_LENGTH_MONTH) {
                    model.setSltStartDate(AccountDateUtil.getDayBeforeDate(beforeByEndDate,
                            (StatAnalyzeDataVar.SEARCH_LENGTH_MONTH - 1) * 30));
                }
                break;
            default:
                break;
            }
            return model;
        } catch (Exception e) {
            LOGGER.error("standardDateScope error " + e.getMessage());
        }
        return null;
    }

    @Override
    public RequestStatTimePeriodModel standardDateScope(RequestStatTimePeriodModel model) {
        try {
            // 起始日期不能大于当前日期
            if (AccountDateUtil.compareTwoDate(model.getSltStartDate(), AccountDateUtil.getNowDate())) {
                model.setSltStartDate(AccountDateUtil.getNowDate());
            }
            // 结束日期不能大于当前日期
            if (AccountDateUtil.compareTwoDate(model.getSltEndDate(), AccountDateUtil.getNowDate())) {
                model.setSltEndDate(AccountDateUtil.getNowDate());
            }
            String startDate = model.getSltStartDate() + " 00:00:00";
            String endDate = model.getSltEndDate() + " 23:59:59";
            String beforeByEndDate = model.getSltEndDate();
            return model;
        } catch (Exception e) {
            LOGGER.error("standardDateScope error " + e.getMessage());
        }
        return null;
    }

    @Override
    public String getIndexChildNameById(int indexChild) {
        try {
            Map<String, String> map = new HashMap<String, String>();
            for (StatIndexChildEnum childEnum : StatIndexChildEnum.values()) {
                map.put(childEnum.ordinal() + 1 + "", childEnum.getName());
            }
            return (String) map.get(indexChild + "");
        } catch (Exception e) {
            LOGGER.error("getIndexChildNameById error " + e.getMessage());
        }
        return null;
    }

    @Override
    public String getDatesByDate(String startDate, String endDate) {
        String result = "";
        List<String> collectLocalDates = AccountDateUtil.getEveryday(startDate, endDate);
        if (collectLocalDates != null & collectLocalDates.size() > 0) {
            for (String date : collectLocalDates) {
                result += date.replace("-", "") + ",";
            }
            result = result.substring(0, result.length() - 1);
        }
        return result;
    }

}
