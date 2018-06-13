package com.phicomm.smarthome.ssp.server.service.impl;

import java.util.ArrayList;
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
import com.phicomm.smarthome.ssp.server.dao.StatAnalyzeMapper;
import com.phicomm.smarthome.ssp.server.dao.StatAnalyzeSurveyMapper;
import com.phicomm.smarthome.ssp.server.enums.StatAppVeidooEnum;
import com.phicomm.smarthome.ssp.server.model.StatAppModel;
import com.phicomm.smarthome.ssp.server.model.TblGeneralDetailModel;
import com.phicomm.smarthome.ssp.server.model.TblStatSurveyModel;
import com.phicomm.smarthome.ssp.server.model.WeekModel;
import com.phicomm.smarthome.ssp.server.model.request.RequestStatAppModel;
import com.phicomm.smarthome.ssp.server.model.response.ResponseBaseModel;
import com.phicomm.smarthome.ssp.server.model.response.ResponseQueryStatAppInfoModel;
import com.phicomm.smarthome.ssp.server.model.response.ResponseStatAppInfoModel;
import com.phicomm.smarthome.ssp.server.model.response.ResponseStatAppModel;
import com.phicomm.smarthome.ssp.server.model.response.ResponseStatSurveyListModel;
import com.phicomm.smarthome.ssp.server.model.response.ResponseStatSurveyTotalInfoModel;
import com.phicomm.smarthome.ssp.server.service.StatAnalyzeSurveyService;
import com.phicomm.smarthome.ssp.server.util.AccountDateUtil;
import com.phicomm.smarthome.ssp.server.util.ExcelUtil;
import com.phicomm.smarthome.util.OptDateUtil;

/**
 * @author fujiang.mao
 *
 */
@Service
public class StatAnalyzeSurveyServicImpl implements StatAnalyzeSurveyService {

    public static final Logger LOGGER = LogManager.getLogger(StatAnalyzeSurveyServicImpl.class);

    @Autowired
    private StatAnalyzeMapper statAnalyzeMapper;

    @Autowired
    private StatAnalyzeSurveyMapper statAnalyzeSurveyMapper;

    @Override
    @TargetDataSource("ds1")
    public TblGeneralDetailModel getSurveyIndexChildInfoById(int indexChild) {
        try {
            return statAnalyzeSurveyMapper.getSurveyIndexChildInfoById(indexChild);
        } catch (Exception e) {
            LOGGER.error("getSurveyIndexChildInfoById error " + e.getMessage());
        }
        return null;
    }

    @Override
    @TargetDataSource("ds1")
    public ResponseQueryStatAppInfoModel queryStatSurveyInfo() {
        try {
            ResponseQueryStatAppInfoModel model = new ResponseQueryStatAppInfoModel();
            // 指标统计项
            model.setSltIndexChilds(statAnalyzeSurveyMapper.getSurveySltIndexChilds());
            // 维度 1.日 2.周 3.月
            List<ResponseBaseModel> veidoos = new ArrayList<ResponseBaseModel>();
            for (StatAppVeidooEnum veidooEnum : StatAppVeidooEnum.values()) {
                veidoos.add(new ResponseBaseModel(veidooEnum.ordinal() + 1 + "", veidooEnum.getName()));
            }
            model.setVeidoos(veidoos);
            return model;
        } catch (Exception e) {
            LOGGER.error("queryStatSurveyInfo error " + e.getMessage());
        }
        return null;
    }

    @Override
    @TargetDataSource("ds1")
    public ResponseStatAppInfoModel statSurvey(RequestStatAppModel model) {
        try {
            ResponseStatAppInfoModel responseModel = new ResponseStatAppInfoModel();
            TblGeneralDetailModel surveyInfo = statAnalyzeSurveyMapper
                    .getSurveyIndexChildInfoById(model.getIndexChild());
            if (surveyInfo != null) {
                responseModel.setIndexChildName(surveyInfo.getDetailIndexName());
                model.setIndexChildColumn(surveyInfo.getDetailColumnName());
            }
            List<String> lineChartXs = new ArrayList<String>();
            List<Number> lineChartYs = new ArrayList<Number>();
            String startDate = model.getSltStartDate();
            String endDate = model.getSltEndDate();
            int veidoo = model.getVeidoo();
            switch (veidoo) {
                case 1:// 维度 日
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
                    List<StatAppModel> appUserModels = statAnalyzeSurveyMapper.statSurveyByDay(model);
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
                case 2:// 维度 周
                    List<WeekModel> weekSplit = AccountDateUtil.getWeekSplit(startDate, endDate);
                    if (weekSplit != null && weekSplit.size() > 0) {
                        for (WeekModel weekModel : weekSplit) {
                            String weekBegin = weekModel.getWeekBegin();
                            String weekEnd = weekModel.getWeekEnd();
                            String lineChartX = AccountDateUtil.formatDateOnlyMD(weekBegin) + "~"
                                    + AccountDateUtil.formatDateOnlyMD(weekEnd);
                            lineChartXs.add(lineChartX);
                            // 获取周开始和结束时间戳
                            // 如果是累计数据，只查询该周最后一天数据
                            if (model.getIndexChild() > 7) {
                                model.setSltStartDateTs(
                                        NumberUtils.toInt(OptDateUtil.getLTimeByStr(weekEnd + " 00:00:00") + ""));
                            } else {
                                model.setSltStartDateTs(
                                        NumberUtils.toInt(OptDateUtil.getLTimeByStr(weekBegin + " 00:00:00") + ""));
                            }
                            model.setSltEndDateTs(NumberUtils.toInt(OptDateUtil.getLTimeByStr(weekEnd + " 23:59:59") + ""));
                            long lineChartY = statAnalyzeSurveyMapper.statSurveyByTimestamp(model);
                            lineChartYs.add(lineChartY);
                        }
                    }
                    break;
                case 3:// 维度 月
                    List<String> months = AccountDateUtil.getMonthBetween(startDate, endDate);
                    if (months != null && months.size() > 0) {
                        for (String month : months) {
                            lineChartXs.add(month);
                            // 如果是累计数据，只查询该月最后一天数据
                            int daysOfMonth = AccountDateUtil.getDaysOfMonth(month + "-01");
                            if (model.getIndexChild() > 7) {
                                model.setSltStartDateTs(NumberUtils
                                        .toInt(OptDateUtil.getLTimeByStr(month + "-" + daysOfMonth + " 00:00:00") + ""));
                            } else {
                                model.setSltStartDateTs(
                                        NumberUtils.toInt(OptDateUtil.getLTimeByStr(month + "-01 00:00:00") + ""));
                            }
                            model.setSltEndDateTs(NumberUtils
                                    .toInt(OptDateUtil.getLTimeByStr(month + "-" + daysOfMonth + " 23:59:59") + ""));
                            long lineChartY = statAnalyzeSurveyMapper.statSurveyByTimestamp(model);
                            lineChartYs.add(lineChartY);
                        }
                    }
                    break;
                default:
                    break;
            }
            responseModel.setLineChartXs(lineChartXs);
            int indexChild = model.getIndexChild();
            // 查询结果为金额，进行分转元操作
            if (indexChild == 5 || indexChild == 6 || indexChild == 7 || indexChild == 12 || indexChild == 13
                    || indexChild == 14) {
                List<Number> lineChartYsF2Y = new ArrayList<Number>();
                for (Number lineChartY : lineChartYs) {
                    lineChartY = NumberUtils
                            .toFloat(com.phicomm.smarthome.ssp.server.util.NumberUtils.changeF2Y(lineChartY + ""));
                    lineChartYsF2Y.add(lineChartY);
                }
                lineChartYs = lineChartYsF2Y;
            }
            responseModel.setLineChartYs(lineChartYs);
            return responseModel;
        } catch (Exception e) {
            LOGGER.error("statSurvey error " + e.getMessage());
        }
        return null;
    }

    @Override
    @TargetDataSource("ds1")
    public ResponseStatSurveyListModel statSurveyDataList(RequestStatAppModel model, boolean isPage) {
        try {
            ResponseStatSurveyListModel rspModel = new ResponseStatSurveyListModel();
            List<TblStatSurveyModel> rspList = new ArrayList<TblStatSurveyModel>();
            List<String> dates = new ArrayList<String>();
            String startDate = model.getSltStartDate();
            String endDate = model.getSltEndDate();
            String sltIndexName = statAnalyzeMapper.getSltIndexNameById(model.getSltIndex());
            int veidoo = model.getVeidoo();
            switch (veidoo) {
                case 1:// 维度 日
                       // 获得起始结束时间戳
                    model.setSltStartDateTs(NumberUtils.toInt(OptDateUtil.getLTimeByStr(startDate + " 00:00:00") + ""));
                    model.setSltEndDateTs(NumberUtils.toInt(OptDateUtil.getLTimeByStr(endDate + " 23:59:59") + ""));
                    List<TblStatSurveyModel> statSurveyDataList = statAnalyzeSurveyMapper.statSurveyDataList(model);
                    if (statSurveyDataList != null && statSurveyDataList.size() > 0) {
                        for (TblStatSurveyModel surveyModel : statSurveyDataList) {
                            surveyModel.setLoggerDateStr(OptDateUtil
                                    .stampToDate(NumberUtils.toLong(surveyModel.getLoggerDateTs() + "")).substring(0, 10));
                        }
                    }
                    rspList = statSurveyDataList;
                    break;
                case 2:// 维度 周
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
                            TblStatSurveyModel statSurveyData = statAnalyzeSurveyMapper.statSurveyDataByDateTS(model);
                            if (statSurveyData != null) {
                                statSurveyData.setLoggerDateStr(date);
                            }
                            // 按周查询时，累计数据展示周最后一天数据
                            model.setLoggerDateTs(NumberUtils.toInt(OptDateUtil.getLTimeByStr(weekEnd + " 00:00:00") + ""));
                            TblStatSurveyModel statSurvey = statAnalyzeSurveyMapper.statSurveyDataByDate(model);
                            if (statSurvey != null) {
                                statSurveyData.setTotalUser(statSurvey.getTotalUser());
                                statSurveyData.setTotalRouter(statSurvey.getTotalRouter());
                                statSurveyData.setTotalSwUser(statSurvey.getTotalSwUser());
                                statSurveyData.setTotalSwCount(statSurvey.getTotalSwCount());
                                statSurveyData.setTotalSwIncome(statSurvey.getTotalSwIncome());
                                statSurveyData.setTotalUserIncome(statSurvey.getTotalUserIncome());
                                statSurveyData.setTotalCompanyIncome(statSurvey.getTotalCompanyIncome());
                            }
                            rspList.add(statSurveyData);
                        }
                    }
                    break;
                case 3:// 维度 月
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
    
                            TblStatSurveyModel statSurveyData = statAnalyzeSurveyMapper.statSurveyDataByDateTS(model);
                            if (statSurveyData != null) {
                                statSurveyData.setLoggerDateStr(month);
                            }
                            // 按月查询时，累计数据展示月最后一天数据
                            model.setLoggerDateTs(NumberUtils
                                    .toInt(OptDateUtil.getLTimeByStr(month + "-" + daysOfMonth + " 00:00:00") + ""));
                            TblStatSurveyModel statSurvey = statAnalyzeSurveyMapper.statSurveyDataByDate(model);
                            if (statSurvey != null) {
                                statSurveyData.setTotalUser(statSurvey.getTotalUser());
                                statSurveyData.setTotalRouter(statSurvey.getTotalRouter());
                                statSurveyData.setTotalSwUser(statSurvey.getTotalSwUser());
                                statSurveyData.setTotalSwCount(statSurvey.getTotalSwCount());
                                statSurveyData.setTotalSwIncome(statSurvey.getTotalSwIncome());
                                statSurveyData.setTotalUserIncome(statSurvey.getTotalUserIncome());
                                statSurveyData.setTotalCompanyIncome(statSurvey.getTotalCompanyIncome());
                            }
                            rspList.add(statSurveyData);
                        }
                    }
                    break;
                default:
                    break;
            }
            // 查询结果为金额，进行分转元操作
            if (rspList != null && rspList.size() > 0) {
                for (TblStatSurveyModel rsp : rspList) {
                    rsp.setShowSwMoney(
                            com.phicomm.smarthome.ssp.server.util.NumberUtils.changeF2Y(rsp.getSwMoney() + ""));
                    rsp.setShowUserIncome(
                            com.phicomm.smarthome.ssp.server.util.NumberUtils.changeF2Y(rsp.getUserIncome() + ""));
                    rsp.setShowCompanyIncome(
                            com.phicomm.smarthome.ssp.server.util.NumberUtils.changeF2Y(rsp.getCompanyIncome() + ""));
                    rsp.setShowTotalSwIncome(
                            com.phicomm.smarthome.ssp.server.util.NumberUtils.changeF2Y(rsp.getTotalSwIncome() + ""));
                    rsp.setShowTotalUserIncome(
                            com.phicomm.smarthome.ssp.server.util.NumberUtils.changeF2Y(rsp.getTotalUserIncome() + ""));
                    rsp.setShowTotalCompanyIncome(com.phicomm.smarthome.ssp.server.util.NumberUtils
                            .changeF2Y(rsp.getTotalCompanyIncome() + ""));
                }
            }
            // 分页查询
            if (isPage) {
                int pageSize = model.getPageSize();
                int curPage = model.getCurPage();
                int start = (curPage - 1) * pageSize;
                int end = curPage * pageSize;
                int totalCount = rspList == null ? 0 : rspList.size();
                if (rspList != null && rspList.size() > 0) {
                    rspList = rspList.subList(start > rspList.size() ? 0 : start,
                            end > rspList.size() ? totalCount : end);
                }
                rspModel.setTotalCount(totalCount);
            }
            rspModel.setSurveyList(rspList);
            return rspModel;
        } catch (Exception e) {
            LOGGER.error("statSurveyDataList error " + e.getMessage());
        }
        return null;
    }

    @Override
    public void statSurveyListExportExcel(HttpServletResponse response, ResponseStatSurveyListModel listModel) {
        try {
            String[] titles = { "日期", "新增用户", "新增路由器", "打赏人数", "打赏次数", "打赏金额(¥)", "用户收益(¥)", "公司收益(¥)", "累计用户",
                "累计路由器数", "累计打赏人数", "累计打赏次数", "累计打赏金额(¥)", "累计用户收益(¥)", "累计公司收益(¥)" };
            String fileName = "SurveyData_" + OptDateUtil.nowDate();
            List<List<String>> content = new ArrayList<List<String>>();
            if (listModel != null && listModel.getSurveyList() != null && listModel.getSurveyList().size() > 0) {
                for (TblStatSurveyModel model : listModel.getSurveyList()) {
                    List<String> strList = new ArrayList<String>();
                    strList.add(model.getLoggerDateStr() + "");
                    strList.add(model.getNewUser() + "");
                    strList.add(model.getNewRouter() + "");
                    strList.add(model.getSwUserCount() + "");
                    strList.add(model.getSwCount() + "");
                    strList.add(model.getShowSwMoney() + "");
                    strList.add(model.getShowUserIncome() + "");
                    strList.add(model.getShowCompanyIncome() + "");
                    strList.add(model.getTotalUser() + "");
                    strList.add(model.getTotalRouter() + "");
                    strList.add(model.getTotalSwUser() + "");
                    strList.add(model.getTotalSwCount() + "");
                    strList.add(model.getShowTotalSwIncome() + "");
                    strList.add(model.getShowTotalUserIncome() + "");
                    strList.add(model.getShowTotalCompanyIncome() + "");
                    content.add(strList);
                }
            }
            ExcelUtil.exportToExcel(response, titles, fileName, content);
        } catch (Exception e) {
            LOGGER.error("statSurveyListExportExcel error " + e.getMessage());
        }
    }

    @Override
    @TargetDataSource("ds1")
    public ResponseStatSurveyTotalInfoModel statSurveyTotalInfo(ResponseStatSurveyTotalInfoModel model) {
        ResponseStatSurveyTotalInfoModel rspObj = new ResponseStatSurveyTotalInfoModel();
        try {
            String date = AccountDateUtil.getDayBeforeDate(AccountDateUtil.getNowDate(), 1);
            long dateTs = OptDateUtil.getLTimeByStr(date + " 00:00:00");
            model = statAnalyzeSurveyMapper.statSurveyTotalByDate(NumberUtils.toInt(dateTs + ""));
            if (model != null) {
                rspObj = model;
            }
            return rspObj;
        } catch (Exception e) {
            LOGGER.error("statSurveyTotalInfo error " + e.getMessage());
        }
        return null;
    }

}
