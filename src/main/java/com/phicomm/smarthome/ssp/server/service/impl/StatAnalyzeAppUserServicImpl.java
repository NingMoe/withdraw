package com.phicomm.smarthome.ssp.server.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.phicomm.smarthome.ssp.server.common.ds.TargetDataSource;
import com.phicomm.smarthome.ssp.server.consts.Const.StatAnalyzeDataVar;
import com.phicomm.smarthome.ssp.server.dao.StatAnalyzeAppUserMapper;
import com.phicomm.smarthome.ssp.server.dao.StatAnalyzeMapper;
import com.phicomm.smarthome.ssp.server.enums.StatAppVeidooEnum;
import com.phicomm.smarthome.ssp.server.enums.StatIndexChildEnum;
import com.phicomm.smarthome.ssp.server.model.StatAppModel;
import com.phicomm.smarthome.ssp.server.model.WeekModel;
import com.phicomm.smarthome.ssp.server.model.request.RequestStatAppModel;
import com.phicomm.smarthome.ssp.server.model.response.ResponseBaseModel;
import com.phicomm.smarthome.ssp.server.model.response.ResponseQueryStatAppInfoModel;
import com.phicomm.smarthome.ssp.server.model.response.ResponseStatAppInfoModel;
import com.phicomm.smarthome.ssp.server.model.response.ResponseStatAppListModel;
import com.phicomm.smarthome.ssp.server.model.response.ResponseStatAppModel;
import com.phicomm.smarthome.ssp.server.service.StatAnalyzeAppUserService;
import com.phicomm.smarthome.ssp.server.service.StatAnalyzeService;
import com.phicomm.smarthome.ssp.server.util.AccountDateUtil;
import com.phicomm.smarthome.ssp.server.util.ExcelUtil;
import com.phicomm.smarthome.util.OptDateUtil;

/**
 * @author fujiang.mao
 *
 */
@Service
public class StatAnalyzeAppUserServicImpl implements StatAnalyzeAppUserService {

    public static final Logger LOGGER = LogManager.getLogger(StatAnalyzeAppUserServicImpl.class);

    @Autowired
    private StatAnalyzeService statAnalyzeService;

    @Autowired
    private StatAnalyzeMapper statAnalyzeMapper;

    @Autowired
    private StatAnalyzeAppUserMapper statAnalyzeAppUserMapper;

    @Override
    @TargetDataSource("ds1")
    public ResponseStatAppListModel getAnalyzeStatAppList(RequestStatAppModel model, boolean isPage) {
        ResponseStatAppListModel rspModel = new ResponseStatAppListModel();
        List<ResponseStatAppModel> appModels = new ArrayList<ResponseStatAppModel>();
        try {
            List<String> dates = new ArrayList<String>();
            String startDate = model.getSltStartDate();
            String endDate = model.getSltEndDate();
            String sltIndexName = statAnalyzeMapper.getSltIndexNameById(model.getSltIndex());
            int veidoo = model.getVeidoo();
            switch (veidoo) {
            case StatAnalyzeDataVar.TIME_VEIDOO_DAY:// 维度 日
                // 先遍历出日期范围内的每一天，初始化数据为0
                List<String> collectLocalDates = AccountDateUtil.getEveryday(startDate, endDate);
                Map<String, Integer> map = new LinkedHashMap<String, Integer>();
                if (collectLocalDates != null && collectLocalDates.size() > 0) {
                    for (String date : collectLocalDates) {
                        map.put(date, 0);
                        dates.add(AccountDateUtil.formatDateOnlyMD(date));

                        ResponseStatAppModel appModel = new ResponseStatAppModel();
                        appModel.setDate(date);
                        appModel.setSltIndexName(sltIndexName);
                        // 获得起始结束时间戳
                        model.setSltStartDateTs(NumberUtils.toInt(OptDateUtil.getLTimeByStr(date + " 00:00:00") + ""));
                        model.setSltEndDateTs(NumberUtils.toInt(OptDateUtil.getLTimeByStr(date + " 23:59:59") + ""));
                        // 新增用户
                        model.setIndexChild(1);
                        appModel.setNewUser(statAnalyzeAppUserMapper.getPvOrUvValBySltIndexTS(model));
                        // PV
                        model.setIndexChild(2);
                        appModel.setPvVal(statAnalyzeAppUserMapper.getPvOrUvValBySltIndexTS(model));
                        // UV
                        model.setIndexChild(3);
                        appModel.setUvVal(statAnalyzeAppUserMapper.getPvOrUvValBySltIndexTS(model));
                        appModels.add(appModel);
                    }
                }
                break;
            case StatAnalyzeDataVar.TIME_VEIDOO_WEEK:// 维度 周
                List<WeekModel> weekSplit = AccountDateUtil.getWeekSplit(startDate, endDate);
                if (weekSplit != null && weekSplit.size() > 0) {
                    for (WeekModel weekModel : weekSplit) {
                        String weekBegin = weekModel.getWeekBegin();
                        String weekEnd = weekModel.getWeekEnd();
                        String date = AccountDateUtil.formatDateOnlyMD(weekBegin) + "~"
                                + AccountDateUtil.formatDateOnlyMD(weekEnd);

                        ResponseStatAppModel appModel = new ResponseStatAppModel();
                        appModel.setDate(date);
                        appModel.setSltIndexName(sltIndexName);
                        // 获得起始结束时间戳
                        model.setSltStartDateTs(
                                NumberUtils.toInt(OptDateUtil.getLTimeByStr(weekBegin + " 00:00:00") + ""));
                        model.setSltEndDateTs(NumberUtils.toInt(OptDateUtil.getLTimeByStr(weekEnd + " 23:59:59") + ""));
                        // 新增用户
                        model.setIndexChild(1);
                        appModel.setNewUser(statAnalyzeAppUserMapper.getPvOrUvValBySltIndexTS(model));
                        // PV
                        model.setIndexChild(2);
                        appModel.setPvVal(statAnalyzeAppUserMapper.getPvOrUvValBySltIndexTS(model));
                        // UV
                        model.setIndexChild(3);
                        appModel.setUvVal(statAnalyzeAppUserMapper.getPvOrUvValBySltIndexTS(model));
                        appModels.add(appModel);
                    }
                }
                break;
            case StatAnalyzeDataVar.TIME_VEIDOO_MONTH:// 维度 月
                List<String> months = AccountDateUtil.getMonthBetween(startDate, endDate);
                if (months != null && months.size() > 0) {
                    for (String month : months) {
                        dates.add(month);
                        // 获得起始结束时间戳
                        model.setSltStartDateTs(
                                NumberUtils.toInt(OptDateUtil.getLTimeByStr(month + "-01 00:00:00") + ""));
                        int daysOfMonth = AccountDateUtil.getDaysOfMonth(month + "-01");
                        model.setSltEndDateTs(NumberUtils
                                .toInt(OptDateUtil.getLTimeByStr(month + "-" + daysOfMonth + " 23:59:59") + ""));

                        ResponseStatAppModel appModel = new ResponseStatAppModel();
                        appModel.setDate(month);
                        appModel.setSltIndexName(sltIndexName);
                        // 新增用户
                        model.setIndexChild(1);
                        appModel.setNewUser(statAnalyzeAppUserMapper.getPvOrUvValBySltIndexTS(model));
                        // PV
                        model.setIndexChild(2);
                        appModel.setPvVal(statAnalyzeAppUserMapper.getPvOrUvValBySltIndexTS(model));
                        // UV
                        model.setIndexChild(3);
                        appModel.setUvVal(statAnalyzeAppUserMapper.getPvOrUvValBySltIndexTS(model));
                        appModels.add(appModel);
                    }
                }
                break;
            default:
                break;
            }
            // 分页查询
            if (isPage) {
                int pageSize = model.getPageSize();
                int curPage = model.getCurPage();
                int start = (curPage - 1) * pageSize;
                int end = curPage * pageSize;
                int totalCount = appModels == null ? 0 : appModels.size();
                if (appModels != null && appModels.size() > 0) {
                    rspModel.setAppList(appModels.subList(start > appModels.size() ? 0 : start,
                            end > appModels.size() ? totalCount : end));
                }
                rspModel.setTotalCount(totalCount);
            } else {
                rspModel.setAppList(appModels);
            }
        } catch (Exception e) {
            LOGGER.error("getAnalyzeStatAppList error " + e.getMessage());
        }
        return rspModel;
    }

    @Override
    @TargetDataSource("ds2")
    public ResponseStatAppListModel getAnalyzeStatAppPvUvList(ResponseStatAppListModel rpsModel,
            RequestStatAppModel model) {
        try {
            List<ResponseStatAppModel> list = rpsModel.getAppList();
            if (list == null || list.size() < 1) {
                return rpsModel;
            }
            boolean isSetWifi = false;
            int sltIndex = model.getSltIndex();
            if (StatAnalyzeDataVar.SLT_INDEX_SETWIFI.equals(sltIndex + "")) {
                isSetWifi = true;
            }
            String pvColumn = getAppSltIndexColumnPvByIndex(model.getSltIndex());
            String uvColumn = getAppSltIndexColumnUvByIndex(model.getSltIndex());
            String datesplit = "";
            int veidoo = model.getVeidoo();
            switch (veidoo) {
            case StatAnalyzeDataVar.TIME_VEIDOO_DAY:// 维度 日
                // 先遍历出日期范围内的每一天，初始化数据为0
                Map<String, Integer> pvMap = new HashMap<String, Integer>();
                Map<String, Integer> uvMap = new HashMap<String, Integer>();
                for (ResponseStatAppModel appModel : list) {
                    String date = appModel.getDate();
                    datesplit += date.replace("-", "") + ",";
                    pvMap.put(date.replace("-", ""), 0);
                    uvMap.put(date.replace("-", ""), 0);
                }
                datesplit = datesplit.substring(0, datesplit.length() - 1);
                // 选择指标不为设置共享WiFi，才统计PV指标
                if (!isSetWifi) {
                    // PV数据值赋到具体每一天
                    List<Map<String, Object>> pvIncomes = statAnalyzeAppUserMapper
                            .getStatIncomeEveryDateByDates(datesplit, pvColumn);
                    if (pvIncomes != null && pvIncomes.size() > 0) {
                        for (Map<String, Object> income : pvIncomes) {
                            String statDate = (String) income.get("stat_date");
                            Long statVal = (Long) income.get("stat_val");
                            pvMap.put(statDate, statVal.intValue());
                        }
                    }
                    // UV数据值赋到具体每一天
                    List<Map<String, Object>> uvIncomes = statAnalyzeAppUserMapper
                            .getStatIncomeEveryDateByDates(datesplit, uvColumn);
                    if (uvIncomes != null && uvIncomes.size() > 0) {
                        for (Map<String, Object> income : uvIncomes) {
                            String statDate = (String) income.get("stat_date");
                            Long statVal = (Long) income.get("stat_val");
                            uvMap.put(statDate, statVal.intValue());
                        }
                    }
                } else {
                    // 选择指标为设置共享WiFi，只统计UV指标
                    List<Map<String, Object>> routers = statAnalyzeAppUserMapper
                            .getStatRouterEveryDateByDates(datesplit);
                    if (routers != null && routers.size() > 0) {
                        for (Map<String, Object> income : routers) {
                            String statDate = (String) income.get("stat_date");
                            Long statVal = (Long) income.get("stat_val");
                            uvMap.put(statDate, statVal.intValue());
                        }
                    }
                }
                // PV、UV set value
                for (ResponseStatAppModel appModel : list) {
                    String date = appModel.getDate();
                    appModel.setPvVal(pvMap.get(date.replace("-", "")));
                    appModel.setUvVal(uvMap.get(date.replace("-", "")));
                }
                break;
            case StatAnalyzeDataVar.TIME_VEIDOO_WEEK:// 维度 周
                List<WeekModel> weekSplit = rpsModel.getWeekSplit();
                Map<String, Integer> pvWeekMap = new HashMap<String, Integer>();
                Map<String, Integer> uvWeekMap = new HashMap<String, Integer>();
                if (weekSplit != null && weekSplit.size() > 0) {
                    for (WeekModel weekModel : weekSplit) {
                        String weekBegin = weekModel.getWeekBegin();
                        String weekEnd = weekModel.getWeekEnd();
                        String date = AccountDateUtil.formatDateOnlyMD(weekBegin) + "~"
                                + AccountDateUtil.formatDateOnlyMD(weekEnd);
                        int pvWeek = 0;
                        int uvWeek = 0;
                        datesplit = statAnalyzeService.getDatesByDate(weekBegin, weekEnd);
                        // 按周取PV数据
                        if (!isSetWifi) {
                            pvWeek = statAnalyzeAppUserMapper.getStatIncomeValByDates(datesplit, pvColumn);
                            uvWeek = statAnalyzeAppUserMapper.getStatIncomeValByDates(datesplit, uvColumn);
                        } else {
                            pvWeek = statAnalyzeAppUserMapper.getStatRouterValByDates(datesplit);
                        }
                        pvWeekMap.put(date, pvWeek);
                        uvWeekMap.put(date, uvWeek);
                    }
                    // PV、UV set value
                    for (ResponseStatAppModel appModel : list) {
                        String weekdate = appModel.getDate();
                        appModel.setPvVal(pvWeekMap.get(weekdate) == null ? 0 : pvWeekMap.get(weekdate));
                        appModel.setUvVal(uvWeekMap.get(weekdate) == null ? 0 : uvWeekMap.get(weekdate));
                    }
                }
                break;
            case StatAnalyzeDataVar.TIME_VEIDOO_MONTH:// 维度 月
                List<String> months = rpsModel.getMonths();
                Map<String, Integer> pvMonthMap = new HashMap<String, Integer>();
                Map<String, Integer> uvMonthMap = new HashMap<String, Integer>();
                if (months != null && months.size() > 0) {
                    for (String month : months) {
                        // 获得起始结束时间戳
                        int daysOfMonth = AccountDateUtil.getDaysOfMonth(month + "-01");
                        String monthBegin = month + "-01";
                        String monthEnd = month + "-" + daysOfMonth;
                        datesplit = statAnalyzeService.getDatesByDate(monthBegin, monthEnd);
                        // 按月取数据
                        int pvMonth = 0;
                        int uvMonth = 0;
                        if (!isSetWifi) {
                            pvMonth = statAnalyzeAppUserMapper.getStatIncomeValByDates(datesplit, pvColumn);
                            uvMonth = statAnalyzeAppUserMapper.getStatIncomeValByDates(datesplit, uvColumn);
                        } else {
                            uvMonth = statAnalyzeAppUserMapper.getStatRouterValByDates(datesplit);
                        }
                        pvMonthMap.put(month, pvMonth);
                        uvMonthMap.put(month, uvMonth);
                    }
                    // PV、UV set value
                    for (ResponseStatAppModel appModel : list) {
                        String month = appModel.getDate();
                        appModel.setPvVal(pvMonthMap.get(month) == null ? 0 : pvMonthMap.get(month));
                        appModel.setUvVal(uvMonthMap.get(month) == null ? 0 : uvMonthMap.get(month));
                    }
                }
                break;
            default:
                break;
            }
        } catch (Exception e) {
            LOGGER.error("getAnalyzeStatAppList error " + e.getMessage());
        }
        return rpsModel;
    }

    private String getSltIndexNameById(int sltIndex) {
        String result = "";
        switch (sltIndex + "") {
        case StatAnalyzeDataVar.SLT_INDEX_SETWIFI:
            result = StatAnalyzeDataVar.SLT_INDEX_SETWIFI_STR;
            break;
        case StatAnalyzeDataVar.SLT_INDEX_NOTECASE:
            result = StatAnalyzeDataVar.SLT_INDEX_NOTECASE_STR;
            break;
        case StatAnalyzeDataVar.SLT_INDEX_CHANGE_DETAIL:
            result = StatAnalyzeDataVar.SLT_INDEX_CHANGE_DETAIL_STR;
            break;
        case StatAnalyzeDataVar.SLT_INDEX_WITHDRAW:
            result = StatAnalyzeDataVar.SLT_INDEX_WITHDRAW_STR;
            break;

        default:
            break;
        }
        return result;
    }

    @Override
    @TargetDataSource("ds1")
    public ResponseStatAppListModel getAnalyzeStatAppNewUserList(RequestStatAppModel model, boolean isPage) {
        ResponseStatAppListModel rpsModel = new ResponseStatAppListModel();
        List<ResponseStatAppModel> appModels = new ArrayList<ResponseStatAppModel>();
        try {
            List<String> dates = new ArrayList<String>();
            String startDate = model.getSltStartDate();
            String endDate = model.getSltEndDate();
            String sltIndexName = getSltIndexNameById(model.getSltIndex());
            int veidoo = model.getVeidoo();
            switch (veidoo) {
            case StatAnalyzeDataVar.TIME_VEIDOO_DAY:// 维度 日
                // 先遍历出日期范围内的每一天，初始化数据为0
                List<String> collectLocalDates = AccountDateUtil.getEveryday(startDate, endDate);
                Map<String, Integer> map = new LinkedHashMap<String, Integer>();
                if (collectLocalDates != null && collectLocalDates.size() > 0) {
                    for (String date : collectLocalDates) {
                        map.put(date, 0);
                        dates.add(AccountDateUtil.formatDateOnlyMD(date));

                        ResponseStatAppModel appModel = new ResponseStatAppModel();
                        appModel.setDate(date);
                        appModel.setSltIndexName(sltIndexName);
                        // 获得起始结束时间戳
                        model.setSltStartDateTs(NumberUtils.toInt(OptDateUtil.getLTimeByStr(date + " 00:00:00") + ""));
                        model.setSltEndDateTs(NumberUtils.toInt(OptDateUtil.getLTimeByStr(date + " 23:59:59") + ""));
                        // 新增用户
                        model.setIndexChild(StatAnalyzeDataVar.INDEX_CHILD_NEW_USER);
                        appModel.setNewUser(statAnalyzeAppUserMapper.getPvOrUvValBySltIndexTS(model));
                        appModels.add(appModel);
                    }
                }
                break;
            case StatAnalyzeDataVar.TIME_VEIDOO_WEEK:// 维度 周
                List<WeekModel> weekSplit = AccountDateUtil.getWeekSplit(startDate, endDate);
                rpsModel.setWeekSplit(weekSplit);
                if (weekSplit != null && weekSplit.size() > 0) {
                    for (WeekModel weekModel : weekSplit) {
                        String weekBegin = weekModel.getWeekBegin();
                        String weekEnd = weekModel.getWeekEnd();
                        String date = AccountDateUtil.formatDateOnlyMD(weekBegin) + "~"
                                + AccountDateUtil.formatDateOnlyMD(weekEnd);

                        ResponseStatAppModel appModel = new ResponseStatAppModel();
                        appModel.setDate(date);
                        appModel.setSltIndexName(sltIndexName);
                        // 获得起始结束时间戳
                        model.setSltStartDateTs(
                                NumberUtils.toInt(OptDateUtil.getLTimeByStr(weekBegin + " 00:00:00") + ""));
                        model.setSltEndDateTs(NumberUtils.toInt(OptDateUtil.getLTimeByStr(weekEnd + " 23:59:59") + ""));
                        // 新增用户
                        model.setIndexChild(StatAnalyzeDataVar.INDEX_CHILD_NEW_USER);
                        appModel.setNewUser(statAnalyzeAppUserMapper.getPvOrUvValBySltIndexTS(model));
                        appModels.add(appModel);
                    }
                }
                break;
            case StatAnalyzeDataVar.TIME_VEIDOO_MONTH:// 维度 月
                List<String> months = AccountDateUtil.getMonthBetween(startDate, endDate);
                if (months != null && months.size() > 0) {
                    for (String month : months) {
                        dates.add(month);
                        // 获得起始结束时间戳
                        model.setSltStartDateTs(
                                NumberUtils.toInt(OptDateUtil.getLTimeByStr(month + "-01 00:00:00") + ""));
                        int daysOfMonth = AccountDateUtil.getDaysOfMonth(month + "-01");
                        model.setSltEndDateTs(NumberUtils
                                .toInt(OptDateUtil.getLTimeByStr(month + "-" + daysOfMonth + " 23:59:59") + ""));

                        ResponseStatAppModel appModel = new ResponseStatAppModel();
                        appModel.setDate(month);
                        appModel.setSltIndexName(sltIndexName);
                        // 新增用户
                        model.setIndexChild(StatAnalyzeDataVar.INDEX_CHILD_NEW_USER);
                        appModel.setNewUser(statAnalyzeAppUserMapper.getPvOrUvValBySltIndexTS(model));
                        appModels.add(appModel);
                    }
                }
                break;
            default:
                break;
            }
            // 分页查询
            if (isPage) {
                int pageSize = model.getPageSize();
                int curPage = model.getCurPage();
                int start = (curPage - 1) * pageSize;
                int end = curPage * pageSize;
                int totalCount = appModels == null ? 0 : appModels.size();
                if (appModels != null && appModels.size() > 0) {
                    int fromIndex = start > appModels.size() ? 0 : start;
                    int toIndex = end > appModels.size() ? totalCount : end;
                    rpsModel.setAppList(appModels.subList(fromIndex, toIndex));
                    if (veidoo == StatAnalyzeDataVar.TIME_VEIDOO_WEEK) {
                        rpsModel.setWeekSplit(rpsModel.getWeekSplit().subList(fromIndex, toIndex));
                    }
                    if (veidoo == StatAnalyzeDataVar.TIME_VEIDOO_MONTH) {
                        rpsModel.setMonths(rpsModel.getMonths().subList(fromIndex, toIndex));
                    }
                }
                rpsModel.setTotalCount(totalCount);
            } else {
                rpsModel.setAppList(appModels);
            }
        } catch (Exception e) {
            LOGGER.error("getAnalyzeStatAppNewUserList error " + e.getMessage());
        }
        return rpsModel;
    }

    @Override
    public ResponseQueryStatAppInfoModel queryStatAppUserInfo() {
        ResponseQueryStatAppInfoModel model = new ResponseQueryStatAppInfoModel();
        try {
            // 选择指标 2.设置共享wifi 4.钱包 5.零钱明细 6.提现
            List<ResponseBaseModel> sltIndexs = new ArrayList<ResponseBaseModel>();
            sltIndexs.add(new ResponseBaseModel(StatAnalyzeDataVar.SLT_INDEX_SETWIFI,
                    StatAnalyzeDataVar.SLT_INDEX_SETWIFI_STR));
            sltIndexs.add(new ResponseBaseModel(StatAnalyzeDataVar.SLT_INDEX_NOTECASE,
                    StatAnalyzeDataVar.SLT_INDEX_NOTECASE_STR));
            sltIndexs.add(new ResponseBaseModel(StatAnalyzeDataVar.SLT_INDEX_CHANGE_DETAIL,
                    StatAnalyzeDataVar.SLT_INDEX_CHANGE_DETAIL_STR));
            sltIndexs.add(new ResponseBaseModel(StatAnalyzeDataVar.SLT_INDEX_WITHDRAW,
                    StatAnalyzeDataVar.SLT_INDEX_WITHDRAW_STR));
            model.setSltIndexs(sltIndexs);
            // 指标统计项 1.新增用户 2.PV 3.UV
            List<ResponseBaseModel> indexChilds = new ArrayList<ResponseBaseModel>();
            for (StatIndexChildEnum childEnum : StatIndexChildEnum.values()) {
                indexChilds.add(new ResponseBaseModel(childEnum.ordinal() + 1 + "", childEnum.getName()));
            }
            model.setSltIndexChilds(indexChilds);
            // 维度 1.日 2.周 3.月
            List<ResponseBaseModel> veidoos = new ArrayList<ResponseBaseModel>();
            for (StatAppVeidooEnum veidooEnum : StatAppVeidooEnum.values()) {
                veidoos.add(new ResponseBaseModel(veidooEnum.ordinal() + 1 + "", veidooEnum.getName()));
            }
            model.setVeidoos(veidoos);
        } catch (Exception e) {
            LOGGER.error("queryStatAppUserInfo error " + e.getMessage());
        }
        return model;
    }

    @Override
    @TargetDataSource("ds1")
    public ResponseStatAppInfoModel statAppUser(RequestStatAppModel model) {
        ResponseStatAppInfoModel responseModel = new ResponseStatAppInfoModel();
        try {
            List<String> lineChartXs = new ArrayList<String>();
            List<Number> lineChartYs = new ArrayList<Number>();
            String startDate = model.getSltStartDate();
            String endDate = model.getSltEndDate();
            int veidoo = model.getVeidoo();
            switch (veidoo) {
            case StatAnalyzeDataVar.TIME_VEIDOO_DAY:// 维度 日
                // 先遍历出日期范围内的每一天，初始化数据为0
                List<String> collectLocalDates = AccountDateUtil.getEveryday(startDate, endDate);
                Map<String, Integer> map = new LinkedHashMap<String, Integer>();
                if (collectLocalDates != null && collectLocalDates.size() > 0) {
                    for (String date : collectLocalDates) {
                        map.put(date, 0);
                        lineChartXs.add(AccountDateUtil.formatDateOnlyMD(date));
                    }
                }
                // 获得日期起始结束时间戳
                model.setSltStartDateTs(NumberUtils.toInt(OptDateUtil.getLTimeByStr(startDate + " 00:00:00") + ""));
                model.setSltEndDateTs(NumberUtils.toInt(OptDateUtil.getLTimeByStr(endDate + " 23:59:59") + ""));
                List<StatAppModel> appUserModels = new ArrayList<StatAppModel>();
                int indexChild = model.getIndexChild();
                // 新增指标
                if (indexChild == StatAnalyzeDataVar.INDEX_CHILD_NEW_USER) {
                    appUserModels = statAnalyzeAppUserMapper.statAppUserByDay(model);
                    // 数据值赋到具体每一天
                    if (appUserModels != null && appUserModels.size() > 0) {
                        for (StatAppModel appUserModel : appUserModels) {
                            String date = OptDateUtil
                                    .stampToDate(NumberUtils.toLong(appUserModel.getConnectDateTs() + ""));
                            if (map != null && map.get(date.substring(0, 10)) != null) {
                                map.put(date.substring(0, 10), appUserModel.getStatCategoryValue());
                            }
                        }
                    }
                    for (String key : map.keySet()) {
                        lineChartYs.add(NumberUtils.toInt(map.get(key) + ""));
                    }
                }
                // PV

                // UV

                break;
            case StatAnalyzeDataVar.TIME_VEIDOO_WEEK:// 维度 周
                List<WeekModel> weekSplit = AccountDateUtil.getWeekSplit(startDate, endDate);
                if (weekSplit != null && weekSplit.size() > 0) {
                    for (WeekModel weekModel : weekSplit) {
                        String weekBegin = weekModel.getWeekBegin();
                        String weekEnd = weekModel.getWeekEnd();
                        String lineChartX = AccountDateUtil.formatDateOnlyMD(weekBegin) + "~"
                                + AccountDateUtil.formatDateOnlyMD(weekEnd);
                        lineChartXs.add(lineChartX);
                        // 获取周开始和结束时间戳
                        model.setSltStartDateTs(
                                NumberUtils.toInt(OptDateUtil.getLTimeByStr(weekBegin + " 00:00:00") + ""));
                        model.setSltEndDateTs(NumberUtils.toInt(OptDateUtil.getLTimeByStr(weekEnd + " 23:59:59") + ""));
                        int lineChartY = statAnalyzeAppUserMapper.statAppUserByTimestamp(model);
                        lineChartYs.add(lineChartY);
                    }
                }
                break;
            case StatAnalyzeDataVar.TIME_VEIDOO_MONTH:// 维度 月
                List<String> months = AccountDateUtil.getMonthBetween(startDate, endDate);
                if (months != null && months.size() > 0) {
                    for (String month : months) {
                        lineChartXs.add(month);
                        model.setSltStartDateTs(
                                NumberUtils.toInt(OptDateUtil.getLTimeByStr(month + "-01 00:00:00") + ""));
                        int daysOfMonth = AccountDateUtil.getDaysOfMonth(month + "-01");
                        model.setSltEndDateTs(NumberUtils
                                .toInt(OptDateUtil.getLTimeByStr(month + "-" + daysOfMonth + " 23:59:59") + ""));
                        int lineChartY = statAnalyzeAppUserMapper.statAppUserByTimestamp(model);
                        lineChartYs.add(lineChartY);
                    }
                }
                break;
            default:
                break;
            }
            responseModel.setLineChartXs(lineChartXs);
            responseModel.setLineChartYs(lineChartYs);
        } catch (Exception e) {
            LOGGER.error("statAppUser error " + e.getMessage());
        }
        return responseModel;
    }

    @Override
    @TargetDataSource("ds2")
    public ResponseStatAppInfoModel statAppPvUser(RequestStatAppModel model) {
        ResponseStatAppInfoModel rpsModel = new ResponseStatAppInfoModel();
        try {
            List<String> lineChartXs = new ArrayList<String>();
            List<Number> lineChartYs = new ArrayList<Number>();
            // 设置共享wifi项PV返回空
            int sltIndex = model.getSltIndex();
            if (StatAnalyzeDataVar.SLT_INDEX_SETWIFI.equals(sltIndex + "")) {
                rpsModel.setLineChartXs(lineChartXs);
                rpsModel.setLineChartYs(lineChartYs);
                return rpsModel;
            }
            String startDate = model.getSltStartDate();
            String endDate = model.getSltEndDate();
            String pvColumn = getAppSltIndexColumnPvByIndex(sltIndex);
            String dates = "";
            int veidoo = model.getVeidoo();
            switch (veidoo) {
            case StatAnalyzeDataVar.TIME_VEIDOO_DAY:// 维度 日
                // 先遍历出日期范围内的每一天，初始化数据为0
                List<String> collectLocalDates = AccountDateUtil.getEveryday(startDate, endDate);
                Map<String, Integer> map = new LinkedHashMap<String, Integer>();
                if (collectLocalDates != null && collectLocalDates.size() > 0) {
                    for (String date : collectLocalDates) {
                        map.put(date.replace("-", ""), 0);
                        lineChartXs.add(AccountDateUtil.formatDateOnlyMD(date));
                        dates += date.replace("-", "") + ",";
                    }
                    dates = dates.substring(0, dates.length() - 1);
                }
                // 数据值赋到具体每一天
                List<Map<String, Object>> incomes = statAnalyzeAppUserMapper.getStatIncomeEveryDateByDates(dates,
                        pvColumn);
                if (incomes != null && incomes.size() > 0) {
                    for (Map<String, Object> income : incomes) {
                        String statDate = (String) income.get("stat_date");
                        Long statVal = (Long) income.get("stat_val");
                        map.put(statDate, statVal.intValue());
                    }
                }
                for (String key : map.keySet()) {
                    lineChartYs.add(NumberUtils.toInt(map.get(key) + ""));
                }
                break;
            case StatAnalyzeDataVar.TIME_VEIDOO_WEEK:// 维度 周
                List<WeekModel> weekSplit = AccountDateUtil.getWeekSplit(startDate, endDate);
                if (weekSplit != null && weekSplit.size() > 0) {
                    for (WeekModel weekModel : weekSplit) {
                        String weekBegin = weekModel.getWeekBegin();
                        String weekEnd = weekModel.getWeekEnd();
                        String lineChartX = AccountDateUtil.formatDateOnlyMD(weekBegin) + "~"
                                + AccountDateUtil.formatDateOnlyMD(weekEnd);
                        lineChartXs.add(lineChartX);
                        // 按周取数据
                        dates = statAnalyzeService.getDatesByDate(weekBegin, weekEnd);
                        int lineChartY = statAnalyzeAppUserMapper.getStatIncomeValByDates(dates, pvColumn);
                        lineChartYs.add(lineChartY);
                    }
                }
                break;
            case StatAnalyzeDataVar.TIME_VEIDOO_MONTH:// 维度 月
                List<String> months = AccountDateUtil.getMonthBetween(startDate, endDate);
                if (months != null && months.size() > 0) {
                    for (String month : months) {
                        lineChartXs.add(month);
                        String monthBegin = month + "-01";
                        int daysOfMonth = AccountDateUtil.getDaysOfMonth(month + "-01");
                        String monthEnd = month + "-" + daysOfMonth;
                        // 按月取数据
                        dates = statAnalyzeService.getDatesByDate(monthBegin, monthEnd);
                        int lineChartY = statAnalyzeAppUserMapper.getStatIncomeValByDates(dates, pvColumn);
                        lineChartYs.add(lineChartY);
                    }
                }
                break;
            default:
                break;
            }
            rpsModel.setLineChartXs(lineChartXs);
            rpsModel.setLineChartYs(lineChartYs);
        } catch (Exception e) {
            LOGGER.error("statAppPvUser error " + e.getMessage());
        }
        return rpsModel;
    }

    @Override
    @TargetDataSource("ds2")
    public ResponseStatAppInfoModel statAppUvUser(RequestStatAppModel model) {
        ResponseStatAppInfoModel rpsModel = new ResponseStatAppInfoModel();
        try {
            List<String> lineChartXs = new ArrayList<String>();
            List<Number> lineChartYs = new ArrayList<Number>();
            // 设置共享wifi项UV单独处理
            boolean isSetWifi = false;
            int sltIndex = model.getSltIndex();
            if (StatAnalyzeDataVar.SLT_INDEX_SETWIFI.equals(sltIndex + "")) {
                isSetWifi = true;
            }
            String startDate = model.getSltStartDate();
            String endDate = model.getSltEndDate();
            String uvColumn = getAppSltIndexColumnUvByIndex(sltIndex);
            String dates = "";
            int veidoo = model.getVeidoo();
            switch (veidoo) {
            case StatAnalyzeDataVar.TIME_VEIDOO_DAY:// 维度 日
                // 先遍历出日期范围内的每一天，初始化数据为0
                List<String> collectLocalDates = AccountDateUtil.getEveryday(startDate, endDate);
                Map<String, Integer> map = new LinkedHashMap<String, Integer>();
                if (collectLocalDates != null && collectLocalDates.size() > 0) {
                    for (String date : collectLocalDates) {
                        map.put(date.replace("-", ""), 0);
                        lineChartXs.add(AccountDateUtil.formatDateOnlyMD(date));
                        dates += date.replace("-", "") + ",";
                    }
                    dates = dates.substring(0, dates.length() - 1);
                }
                // 数据值赋到具体每一天
                List<Map<String, Object>> incomes = new ArrayList<Map<String, Object>>();
                if (isSetWifi) {
                    incomes = statAnalyzeAppUserMapper.getStatRouterEveryDateByDates(dates);
                } else {
                    incomes = statAnalyzeAppUserMapper.getStatIncomeEveryDateByDates(dates, uvColumn);
                }
                if (incomes != null && incomes.size() > 0) {
                    for (Map<String, Object> income : incomes) {
                        String statDate = (String) income.get("stat_date");
                        Long statVal = (Long) income.get("stat_val");
                        map.put(statDate, statVal.intValue());
                    }
                }
                for (String key : map.keySet()) {
                    lineChartYs.add(NumberUtils.toInt(map.get(key) + ""));
                }
                break;
            case StatAnalyzeDataVar.TIME_VEIDOO_WEEK:// 维度 周
                List<WeekModel> weekSplit = AccountDateUtil.getWeekSplit(startDate, endDate);
                if (weekSplit != null && weekSplit.size() > 0) {
                    for (WeekModel weekModel : weekSplit) {
                        String weekBegin = weekModel.getWeekBegin();
                        String weekEnd = weekModel.getWeekEnd();
                        String lineChartX = AccountDateUtil.formatDateOnlyMD(weekBegin) + "~"
                                + AccountDateUtil.formatDateOnlyMD(weekEnd);
                        lineChartXs.add(lineChartX);
                        // 按周取数据
                        dates = statAnalyzeService.getDatesByDate(weekBegin, weekEnd);
                        int lineChartY = 0;
                        if (isSetWifi) {
                            lineChartY = statAnalyzeAppUserMapper.getStatRouterValByDates(dates);
                        } else {
                            lineChartY = statAnalyzeAppUserMapper.getStatIncomeValByDates(dates, uvColumn);
                        }
                        lineChartYs.add(lineChartY);
                    }
                }
                break;
            case StatAnalyzeDataVar.TIME_VEIDOO_MONTH:// 维度 月
                List<String> months = AccountDateUtil.getMonthBetween(startDate, endDate);
                if (months != null && months.size() > 0) {
                    for (String month : months) {
                        lineChartXs.add(month);
                        String monthBegin = month + "-01";
                        int daysOfMonth = AccountDateUtil.getDaysOfMonth(month + "-01");
                        String monthEnd = month + "-" + daysOfMonth;
                        // 按月取数据
                        dates = statAnalyzeService.getDatesByDate(monthBegin, monthEnd);
                        int lineChartY = 0;
                        if (isSetWifi) {
                            lineChartY = statAnalyzeAppUserMapper.getStatRouterValByDates(dates);
                        } else {
                            lineChartY = statAnalyzeAppUserMapper.getStatIncomeValByDates(dates, uvColumn);
                        }
                        lineChartYs.add(lineChartY);
                    }
                }
                break;
            default:
                break;
            }
            rpsModel.setLineChartXs(lineChartXs);
            rpsModel.setLineChartYs(lineChartYs);
        } catch (Exception e) {
            LOGGER.error("statAppUvUser error " + e.getMessage());
        }
        return rpsModel;
    }

    private String getAppSltIndexColumnPvByIndex(int sltIndex) {
        String result = "";
        switch (sltIndex + "") {
        case StatAnalyzeDataVar.SLT_INDEX_NOTECASE:
            result = StatAnalyzeDataVar.SLT_INDEX_NOTECASE_COLUMN_PV;
            break;
        case StatAnalyzeDataVar.SLT_INDEX_CHANGE_DETAIL:
            result = StatAnalyzeDataVar.SLT_INDEX_CHANGE_DETAIL_COLUMN_PV;
            break;
        case StatAnalyzeDataVar.SLT_INDEX_WITHDRAW:
            result = StatAnalyzeDataVar.SLT_INDEX_WITHDRAW_COLUMN_PV;
            break;
        default:
            break;
        }
        return result;
    }

    private String getAppSltIndexColumnUvByIndex(int sltIndex) {
        String result = "";
        switch (sltIndex + "") {
        case StatAnalyzeDataVar.SLT_INDEX_NOTECASE:
            result = StatAnalyzeDataVar.SLT_INDEX_NOTECASE_COLUMN_UV;
            break;
        case StatAnalyzeDataVar.SLT_INDEX_CHANGE_DETAIL:
            result = StatAnalyzeDataVar.SLT_INDEX_CHANGE_DETAIL_COLUMN_UV;
            break;
        case StatAnalyzeDataVar.SLT_INDEX_WITHDRAW:
            result = StatAnalyzeDataVar.SLT_INDEX_WITHDRAW_COLUMN_UV;
            break;
        default:
            break;
        }
        return result;
    }

    @Override
    @TargetDataSource("ds1")
    public ResponseStatAppInfoModel statAppNewUser(RequestStatAppModel model) {
        ResponseStatAppInfoModel responseModel = new ResponseStatAppInfoModel();
        try {
            List<String> lineChartXs = new ArrayList<String>();
            List<Number> lineChartYs = new ArrayList<Number>();
            String startDate = model.getSltStartDate();
            String endDate = model.getSltEndDate();
            int veidoo = model.getVeidoo();
            switch (veidoo) {
            case StatAnalyzeDataVar.TIME_VEIDOO_DAY:// 维度 日
                // 先遍历出日期范围内的每一天，初始化数据为0
                List<String> collectLocalDates = AccountDateUtil.getEveryday(startDate, endDate);
                Map<String, Integer> map = new LinkedHashMap<String, Integer>();
                if (collectLocalDates != null && collectLocalDates.size() > 0) {
                    for (String date : collectLocalDates) {
                        map.put(date, 0);
                        lineChartXs.add(AccountDateUtil.formatDateOnlyMD(date));
                    }
                }
                // 获得日期起始结束时间戳
                model.setSltStartDateTs(NumberUtils.toInt(OptDateUtil.getLTimeByStr(startDate + " 00:00:00") + ""));
                model.setSltEndDateTs(NumberUtils.toInt(OptDateUtil.getLTimeByStr(endDate + " 23:59:59") + ""));
                List<StatAppModel> appUserModels = new ArrayList<StatAppModel>();
                // 新增指标
                appUserModels = statAnalyzeAppUserMapper.statAppUserByDay(model);
                // 数据值赋到具体每一天
                if (appUserModels != null && appUserModels.size() > 0) {
                    for (StatAppModel appUserModel : appUserModels) {
                        String date = OptDateUtil.stampToDate(NumberUtils.toLong(appUserModel.getConnectDateTs() + ""));
                        if (map != null && map.get(date.substring(0, 10)) != null) {
                            map.put(date.substring(0, 10), appUserModel.getStatCategoryValue());
                        }
                    }
                }
                for (String key : map.keySet()) {
                    lineChartYs.add(NumberUtils.toInt(map.get(key) + ""));
                }
                break;
            case StatAnalyzeDataVar.TIME_VEIDOO_WEEK:// 维度 周
                List<WeekModel> weekSplit = AccountDateUtil.getWeekSplit(startDate, endDate);
                if (weekSplit != null && weekSplit.size() > 0) {
                    for (WeekModel weekModel : weekSplit) {
                        String weekBegin = weekModel.getWeekBegin();
                        String weekEnd = weekModel.getWeekEnd();
                        String lineChartX = AccountDateUtil.formatDateOnlyMD(weekBegin) + "~"
                                + AccountDateUtil.formatDateOnlyMD(weekEnd);
                        lineChartXs.add(lineChartX);
                        // 获取周开始和结束时间戳
                        model.setSltStartDateTs(
                                NumberUtils.toInt(OptDateUtil.getLTimeByStr(weekBegin + " 00:00:00") + ""));
                        model.setSltEndDateTs(NumberUtils.toInt(OptDateUtil.getLTimeByStr(weekEnd + " 23:59:59") + ""));
                        int lineChartY = statAnalyzeAppUserMapper.statAppUserByTimestamp(model);
                        lineChartYs.add(lineChartY);
                    }
                }
                break;
            case StatAnalyzeDataVar.TIME_VEIDOO_MONTH:// 维度 月
                List<String> months = AccountDateUtil.getMonthBetween(startDate, endDate);
                if (months != null && months.size() > 0) {
                    for (String month : months) {
                        lineChartXs.add(month);
                        model.setSltStartDateTs(
                                NumberUtils.toInt(OptDateUtil.getLTimeByStr(month + "-01 00:00:00") + ""));
                        int daysOfMonth = AccountDateUtil.getDaysOfMonth(month + "-01");
                        model.setSltEndDateTs(NumberUtils
                                .toInt(OptDateUtil.getLTimeByStr(month + "-" + daysOfMonth + " 23:59:59") + ""));
                        int lineChartY = statAnalyzeAppUserMapper.statAppUserByTimestamp(model);
                        lineChartYs.add(lineChartY);
                    }
                }
                break;
            default:
                break;
            }
            responseModel.setLineChartXs(lineChartXs);
            responseModel.setLineChartYs(lineChartYs);
        } catch (Exception e) {
            LOGGER.error("statAppNewUser error " + e.getMessage());
        }
        return responseModel;
    }

    @Override
    public void statAppUserListExportExcel(HttpServletResponse response, ResponseStatAppListModel listModel) {
        try {
            String[] titles = { "日期", "指标", "新增用户", "PV", "UV" };
            String fileName = "AppUserData_" + OptDateUtil.nowDate();
            List<List<String>> content = new ArrayList<List<String>>();
            if (listModel != null && listModel.getAppList() != null && listModel.getAppList().size() > 0) {
                for (ResponseStatAppModel model : listModel.getAppList()) {
                    List<String> strList = new ArrayList<String>();
                    strList.add(model.getDate() + "");
                    strList.add(model.getSltIndexName() + "");
                    strList.add(model.getNewUser() + "");
                    strList.add(model.getPvVal() + "");
                    strList.add(model.getUvVal() + "");
                    content.add(strList);
                }
            }
            ExcelUtil.exportToExcel(response, titles, fileName, content);
        } catch (Exception e) {
            LOGGER.error("statAppUserListExportExcel error " + e.getMessage());
        }
    }

}
