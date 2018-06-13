package com.phicomm.smarthome.ssp.server.service.impl;

import java.text.DecimalFormat;
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
import com.phicomm.smarthome.ssp.server.dao.StatAnalyzeMapper;
import com.phicomm.smarthome.ssp.server.dao.StatAnalyzeRewardMapper;
import com.phicomm.smarthome.ssp.server.enums.StatAppVeidooEnum;
import com.phicomm.smarthome.ssp.server.enums.StatIndexChildEnum;
import com.phicomm.smarthome.ssp.server.enums.StatRewardConnDeviceEnum;
import com.phicomm.smarthome.ssp.server.enums.StatRewardSltIndexEnum;
import com.phicomm.smarthome.ssp.server.model.ConfigConnOrUseTrModel;
import com.phicomm.smarthome.ssp.server.model.TblStatDurationUserDetailModel;
import com.phicomm.smarthome.ssp.server.model.WeekModel;
import com.phicomm.smarthome.ssp.server.model.request.RequestStatAppModel;
import com.phicomm.smarthome.ssp.server.model.request.RequestStatRewardDurationModel;
import com.phicomm.smarthome.ssp.server.model.response.ResponseBaseModel;
import com.phicomm.smarthome.ssp.server.model.response.ResponseQueryStatRewardDurationInfoModel;
import com.phicomm.smarthome.ssp.server.model.response.ResponseQueryStatRewardUserInfoModel;
import com.phicomm.smarthome.ssp.server.model.response.ResponseStatAppInfoModel;
import com.phicomm.smarthome.ssp.server.model.response.ResponseStatAppListModel;
import com.phicomm.smarthome.ssp.server.model.response.ResponseStatAppModel;
import com.phicomm.smarthome.ssp.server.model.response.ResponseStatDurationListModel;
import com.phicomm.smarthome.ssp.server.model.response.ResponseStatDurationModel;
import com.phicomm.smarthome.ssp.server.service.StatAnalyzeRewardService;
import com.phicomm.smarthome.ssp.server.service.StatAnalyzeService;
import com.phicomm.smarthome.ssp.server.util.AccountDateUtil;
import com.phicomm.smarthome.ssp.server.util.ExcelUtil;
import com.phicomm.smarthome.util.OptDateUtil;
import com.phicomm.smarthome.util.StringUtil;

/**
 * @author fujiang.mao
 *
 */
@Service
public class StatAnalyzeRewardServicImpl implements StatAnalyzeRewardService {

    public static final Logger LOGGER = LogManager.getLogger(StatAnalyzeRewardServicImpl.class);

    @Autowired
    private StatAnalyzeService statAnalyzeService;

    @Autowired
    private StatAnalyzeMapper statAnalyzeMapper;

    @Autowired
    private StatAnalyzeRewardMapper statAnalyzeRewardMapper;

    @Override
    @TargetDataSource("ds1")
    public ResponseQueryStatRewardUserInfoModel queryStatRewardUserInfo() {
        try {
            ResponseQueryStatRewardUserInfoModel model = new ResponseQueryStatRewardUserInfoModel();
            // 选择指标
            List<ResponseBaseModel> sltIndexs = new ArrayList<ResponseBaseModel>();
            for (StatRewardSltIndexEnum rewardEnum : StatRewardSltIndexEnum.values()) {
                sltIndexs.add(new ResponseBaseModel(rewardEnum.ordinal() + 1 + "", rewardEnum.getName()));
            }
            model.setSltIndexs(sltIndexs);
            // 指标统计项 PV UV
            List<ResponseBaseModel> childs = new ArrayList<ResponseBaseModel>();
            for (StatIndexChildEnum statEnum : StatIndexChildEnum.values()) {
                childs.add(new ResponseBaseModel(statEnum.ordinal() + 1 + "", statEnum.getName()));
            }
            // remove new user index
            childs.remove(0);
            model.setSltIndexChilds(childs);
            // 维度 1.日 2.周 3.月
            List<ResponseBaseModel> veidoos = new ArrayList<ResponseBaseModel>();
            for (StatAppVeidooEnum statEnum : StatAppVeidooEnum.values()) {
                veidoos.add(new ResponseBaseModel(statEnum.ordinal() + 1 + "", statEnum.getName()));
            }
            model.setVeidoos(veidoos);
            // 连接设备
            List<ResponseBaseModel> devices = new ArrayList<ResponseBaseModel>();
            for (StatRewardConnDeviceEnum statEnum : StatRewardConnDeviceEnum.values()) {
                devices.add(new ResponseBaseModel(statEnum.ordinal() + 1 + "", statEnum.getName()));
            }
            ResponseBaseModel device = new ResponseBaseModel();
            device.setStrId(StatAnalyzeDataVar.ALL_SELECT);
            device.setStrValue(StatAnalyzeDataVar.ALL_SELECT_STR);
            devices.add(0, device);
            model.setSwDevices(devices);
            return model;
        } catch (Exception e) {
            LOGGER.error("queryStatRewardUserInfo error " + e.getMessage());
        }
        return null;
    }

    @Override
    @TargetDataSource("ds1")
    public String getIndexChildNameById(int indexChild) {
        try {
            return statAnalyzeMapper.getIndexChildNameById(indexChild);
        } catch (Exception e) {
            LOGGER.error("getIndexChildNameById error " + e.getMessage());
        }
        return null;
    }

    @Override
    @TargetDataSource("ds2")
    public ResponseStatAppInfoModel statRewardUser(RequestStatAppModel model) {
        try {
            ResponseStatAppInfoModel responseModel = new ResponseStatAppInfoModel();
            List<String> lineChartXs = new ArrayList<String>();
            List<Number> lineChartYs = new ArrayList<Number>();
            String startDate = model.getSltStartDate();
            String endDate = model.getSltEndDate();
            int veidoo = model.getVeidoo();
            String datesplit = "";
            String statColumn = "";
            int indexChild = model.getIndexChild();
            if (indexChild == StatAnalyzeDataVar.INDEX_CHILD_PV_USER) {
                statColumn = StatAnalyzeDataVar.REWARD_COLUMN_PV;
            } else if (indexChild == StatAnalyzeDataVar.INDEX_CHILD_UV_USER) {
                statColumn = StatAnalyzeDataVar.REWARD_COLUMN_UV;
            }
            if (StringUtil.isNullOrEmpty(statColumn)) {
                responseModel.setLineChartXs(lineChartXs);
                responseModel.setLineChartYs(lineChartYs);
                return responseModel;
            }
            // 判断连接设备是否为"全部"
            boolean isAllDevice = model.getSwDevice() == NumberUtils.toInt(StatAnalyzeDataVar.ALL_SELECT) ? true
                    : false;
            switch (veidoo) {
            case StatAnalyzeDataVar.TIME_VEIDOO_DAY:// 维度 日
                // 先遍历出日期范围内的每一天，初始化数据为0
                List<String> collectLocalDates = AccountDateUtil.getEveryday(startDate, endDate);
                Map<String, Integer> pvOrUvMap = new LinkedHashMap<String, Integer>();
                if (collectLocalDates != null && collectLocalDates.size() > 0) {
                    for (String date : collectLocalDates) {
                        datesplit += date.replace("-", "") + ",";
                        pvOrUvMap.put(date.replace("-", ""), 0);
                        lineChartXs.add(AccountDateUtil.formatDateOnlyMD(date));
                    }
                    datesplit = datesplit.substring(0, datesplit.length() - 1);
                }
                // 获得日期起始结束时间戳
                model.setSltStartDateTs(NumberUtils.toInt(OptDateUtil.getLTimeByStr(startDate + " 00:00:00") + ""));
                model.setSltEndDateTs(NumberUtils.toInt(OptDateUtil.getLTimeByStr(endDate + " 23:59:59") + ""));
                List<Map<String, Object>> rewardUsers = new ArrayList<Map<String, Object>>();
                // 判断连接设备是否为"全部"
                if (isAllDevice) {
                    rewardUsers = statAnalyzeRewardMapper.statRewardUserAllDeviceByDates(datesplit, statColumn, model);
                } else {
                    rewardUsers = statAnalyzeRewardMapper.statRewardUserByDates(datesplit, statColumn, model);
                }
                if (rewardUsers != null && rewardUsers.size() > 0) {
                    for (Map<String, Object> rewardUser : rewardUsers) {
                        String statDate = (String) rewardUser.get("stat_date");
                        Object statVal = rewardUser.get("stat_val");
                        pvOrUvMap.put(statDate, statVal == null ? 0 : NumberUtils.toInt(statVal.toString()));
                    }
                }
                // 数据值赋到具体每一天
                for (String key : pvOrUvMap.keySet()) {
                    lineChartYs.add(NumberUtils.toInt(pvOrUvMap.get(key) + ""));
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
                        int lineChartY = 0;
                        datesplit = statAnalyzeService.getDatesByDate(weekBegin, weekEnd);
                        // 判断连接设备是否为"全部"
                        if (isAllDevice) {
                            lineChartY = statAnalyzeRewardMapper.statRewardUserAllDevices(datesplit, statColumn, model);
                        } else {
                            lineChartY = statAnalyzeRewardMapper.statRewardUsers(datesplit, statColumn, model);
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
                        int daysOfMonth = AccountDateUtil.getDaysOfMonth(month + "-01");
                        String monthBegin = month + "-01";
                        String monthEnd = month + "-" + daysOfMonth;
                        int lineChartY = 0;
                        datesplit = statAnalyzeService.getDatesByDate(monthBegin, monthEnd);
                        // 判断连接设备是否为"全部"
                        if (isAllDevice) {
                            lineChartY = statAnalyzeRewardMapper.statRewardUserAllDevices(datesplit, statColumn, model);
                        } else {
                            lineChartY = statAnalyzeRewardMapper.statRewardUsers(datesplit, statColumn, model);
                        }
                        lineChartYs.add(lineChartY);
                    }
                }
                break;
            default:
                break;
            }
            responseModel.setLineChartXs(lineChartXs);
            responseModel.setLineChartYs(lineChartYs);
            return responseModel;
        } catch (Exception e) {
            LOGGER.error("statRewardUser error " + e.getMessage());
        }
        return null;
    }

    @Override
    @TargetDataSource("ds2")
    public ResponseStatAppListModel getAnalyzeStatRewardList(RequestStatAppModel model, boolean isPage) {
        try {
            ResponseStatAppListModel rspModel = new ResponseStatAppListModel();
            List<ResponseStatAppModel> appModels = new ArrayList<ResponseStatAppModel>();
            List<String> dates = new ArrayList<String>();
            String startDate = model.getSltStartDate();
            String endDate = model.getSltEndDate();
            // String sltIndexName =
            // statAnalyzeRewardMapper.getSwUserIndexNameById(model.getSltIndex());
            String sltIndexName = getSwUserIndexNameById(model.getSltIndex());
            String swDeviceName = "";
            if (model.getSwDevice() == -1) {
                swDeviceName = "全部";
            } else {
                // swDeviceName =
                // statAnalyzeRewardMapper.getSwDeviceNameById(model.getSwDevice());
                swDeviceName = getSwDeviceNameById(model.getSwDevice());
            }
            int veidoo = model.getVeidoo();
            String datesplit = "";
            String statColumn = "";
            // 判断连接设备是否为"全部"
            boolean isAllDevice = model.getSwDevice() == NumberUtils.toInt(StatAnalyzeDataVar.ALL_SELECT) ? true
                    : false;
            switch (veidoo) {
            case StatAnalyzeDataVar.TIME_VEIDOO_DAY:// 维度 日
                // 先遍历出日期范围内的每一天，初始化数据为0
                List<String> collectLocalDates = AccountDateUtil.getEveryday(startDate, endDate);
                // Map<String, Integer> map = new LinkedHashMap<String,
                // Integer>();
                Map<String, Integer> pvMap = new LinkedHashMap<String, Integer>();
                Map<String, Integer> uvMap = new LinkedHashMap<String, Integer>();
                if (collectLocalDates != null && collectLocalDates.size() > 0) {
                    for (String date : collectLocalDates) {
                        datesplit += date.replace("-", "") + ",";
                        pvMap.put(date.replace("-", ""), 0);
                        uvMap.put(date.replace("-", ""), 0);
                        dates.add(AccountDateUtil.formatDateOnlyMD(date));
                    }
                    datesplit = datesplit.substring(0, datesplit.length() - 1);
                    List<Map<String, Object>> rewardUsers = new ArrayList<Map<String, Object>>();
                    // pv
                    statColumn = StatAnalyzeDataVar.REWARD_COLUMN_PV;
                    // 判断连接设备是否为"全部"
                    if (isAllDevice) {
                        rewardUsers = statAnalyzeRewardMapper.statRewardUserAllDeviceByDates(datesplit, statColumn,
                                model);
                    } else {
                        rewardUsers = statAnalyzeRewardMapper.statRewardUserByDates(datesplit, statColumn, model);
                    }
                    if (rewardUsers != null && rewardUsers.size() > 0) {
                        for (Map<String, Object> rewardUser : rewardUsers) {
                            String statDate = (String) rewardUser.get("stat_date");
                            Object statVal = rewardUser.get("stat_val");
                            pvMap.put(statDate, statVal == null ? 0 : NumberUtils.toInt(statVal.toString()));
                        }
                    }
                    // uv
                    statColumn = StatAnalyzeDataVar.REWARD_COLUMN_UV;
                    // 判断连接设备是否为"全部"
                    if (isAllDevice) {
                        rewardUsers = statAnalyzeRewardMapper.statRewardUserAllDeviceByDates(datesplit, statColumn,
                                model);
                    } else {
                        rewardUsers = statAnalyzeRewardMapper.statRewardUserByDates(datesplit, statColumn, model);
                    }
                    if (rewardUsers != null && rewardUsers.size() > 0) {
                        for (Map<String, Object> rewardUser : rewardUsers) {
                            String statDate = (String) rewardUser.get("stat_date");
                            Object statVal = rewardUser.get("stat_val");
                            uvMap.put(statDate, statVal == null ? 0 : NumberUtils.toInt(statVal.toString()));
                        }
                    }
                    for (String date : collectLocalDates) {
                        ResponseStatAppModel appModel = new ResponseStatAppModel();
                        appModel.setDate(date);
                        appModel.setSltIndexName(sltIndexName);
                        appModel.setSwDeviceName(swDeviceName);
                        appModel.setPvVal(pvMap.get(date.replace("-", "")));
                        appModel.setUvVal(uvMap.get(date.replace("-", "")));
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
                        appModel.setSwDeviceName(swDeviceName);
                        datesplit = statAnalyzeService.getDatesByDate(weekBegin, weekEnd);
                        int pvVal = 0;
                        statColumn = StatAnalyzeDataVar.REWARD_COLUMN_PV;
                        // 判断连接设备是否为"全部"
                        if (isAllDevice) {
                            pvVal = statAnalyzeRewardMapper.statRewardUserAllDevices(datesplit, statColumn, model);
                        } else {
                            pvVal = statAnalyzeRewardMapper.statRewardUsers(datesplit, statColumn, model);
                        }
                        appModel.setPvVal(pvVal);
                        int uvVal = 0;
                        statColumn = StatAnalyzeDataVar.REWARD_COLUMN_UV;
                        // 判断连接设备是否为"全部"
                        if (isAllDevice) {
                            uvVal = statAnalyzeRewardMapper.statRewardUserAllDevices(datesplit, statColumn, model);
                        } else {
                            uvVal = statAnalyzeRewardMapper.statRewardUsers(datesplit, statColumn, model);
                        }
                        appModel.setUvVal(uvVal);
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

                        String monthBegin = month + "-01";
                        String monthEnd = month + "-" + daysOfMonth;
                        datesplit = statAnalyzeService.getDatesByDate(monthBegin, monthEnd);
                        ResponseStatAppModel appModel = new ResponseStatAppModel();
                        appModel.setDate(month);
                        appModel.setSltIndexName(sltIndexName);
                        appModel.setSwDeviceName(swDeviceName);
                        // pv
                        statColumn = StatAnalyzeDataVar.REWARD_COLUMN_PV;
                        int pvVal = 0;
                        // 判断连接设备是否为"全部"
                        if (isAllDevice) {
                            pvVal = statAnalyzeRewardMapper.statRewardUserAllDevices(datesplit, statColumn, model);
                        } else {
                            pvVal = statAnalyzeRewardMapper.statRewardUsers(datesplit, statColumn, model);
                        }
                        appModel.setPvVal(pvVal);
                        // uv
                        statColumn = StatAnalyzeDataVar.REWARD_COLUMN_UV;
                        int uvVal = 0;
                        // 判断连接设备是否为"全部"
                        if (isAllDevice) {
                            uvVal = statAnalyzeRewardMapper.statRewardUserAllDevices(datesplit, statColumn, model);
                        } else {
                            uvVal = statAnalyzeRewardMapper.statRewardUsers(datesplit, statColumn, model);
                        }
                        appModel.setUvVal(uvVal);
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
            return rspModel;
        } catch (Exception e) {
            LOGGER.error("getAnalyzeStatRewardList error " + e.getMessage());
        }
        return null;
    }

    private String getSwUserIndexNameById(int indexId) {
        String result = "";
        Map<Integer, String> map = new HashMap<Integer, String>();
        for (StatRewardSltIndexEnum statEnum : StatRewardSltIndexEnum.values()) {
            map.put(statEnum.ordinal() + 1, statEnum.getName());
        }
        result = map.get(indexId) == null ? "" : map.get(indexId);
        return result;
    }

    private String getSwDeviceNameById(int indexId) {
        String result = "";
        Map<Integer, String> map = new HashMap<Integer, String>();
        for (StatRewardConnDeviceEnum statEnum : StatRewardConnDeviceEnum.values()) {
            map.put(statEnum.ordinal() + 1, statEnum.getName());
        }
        result = map.get(indexId) == null ? "" : map.get(indexId);
        return result;
    }

    @Override
    public void statRewardUserListExportExcel(HttpServletResponse response, ResponseStatAppListModel listModel) {
        try {
            String[] titles = { "日期", "指标", "连接设备", "PV", "UV" };
            String fileName = "RewardUserData_" + OptDateUtil.nowDate();
            List<List<String>> content = new ArrayList<List<String>>();
            if (listModel != null && listModel.getAppList() != null && listModel.getAppList().size() > 0) {
                for (ResponseStatAppModel model : listModel.getAppList()) {
                    List<String> strList = new ArrayList<String>();
                    strList.add(model.getDate() + "");
                    strList.add(model.getSltIndexName() + "");
                    strList.add(model.getSwDeviceName() + "");
                    strList.add(model.getPvVal() + "");
                    strList.add(model.getUvVal() + "");
                    content.add(strList);
                }
            }
            ExcelUtil.exportToExcel(response, titles, fileName, content);
        } catch (Exception e) {
            LOGGER.error("statRewardUserListExportExcel error " + e.getMessage());
        }
    }

    @Override
    public void statRewardDurationListExportExcel(HttpServletResponse response, List<ResponseStatDurationModel> list) {
        try {
            String[] titles = { "日期", "连接时长", "打赏次数", "占比", "使用时长", "打赏次数", "占比" };
            String fileName = "RewardDuration_" + OptDateUtil.nowDate();
            List<List<String>> content = new ArrayList<List<String>>();
            if (list != null && list != null && list.size() > 0) {
                for (ResponseStatDurationModel model : list) {
                    List<String> strList = new ArrayList<String>();
                    strList.add(model.getDate() + "");
                    strList.add(model.getLinkTimeDesc() + "");
                    strList.add(model.getLinkSwCount() + "");
                    strList.add(model.getLinkPercent() + "");
                    strList.add(model.getUseTimeDesc() + "");
                    strList.add(model.getUseSwCount() + "");
                    strList.add(model.getUsePercent() + "");
                    content.add(strList);
                }
            }
            ExcelUtil.exportToExcel(response, titles, fileName, content);
        } catch (Exception e) {
            LOGGER.error("statRewardDurationListExportExcel error " + e.getMessage());
        }
    }

    @Override
    @TargetDataSource("ds1")
    public void statRewardSwUserDetailListExportExcel(HttpServletResponse response,
            List<TblStatDurationUserDetailModel> list) {
        try {
            String[] titles = { "日期", "打赏金额(¥)", "连接时长", "使用时长", "连接设备", "IP地址", "MAC地址" };
            String fileName = "RewardSwUserDataDetail_" + OptDateUtil.nowDate();
            List<List<String>> content = new ArrayList<List<String>>();
            if (list != null && list != null && list.size() > 0) {
                for (TblStatDurationUserDetailModel model : list) {
                    List<String> strList = new ArrayList<String>();
                    strList.add(model.getDate() + "");
                    strList.add(model.getShowSwMoney() + "");
                    strList.add(model.getLinkTime() + "");
                    strList.add(model.getUserTime() + "");
                    strList.add(
                            // statAnalyzeRewardMapper.getSwDeviceNameById(NumberUtils.toInt(model.getDeviceType()
                            // + ""))
                            getSwDeviceNameById(NumberUtils.toInt(model.getDeviceType() + "")) + "");
                    strList.add(model.getIpAddress() + "");
                    strList.add(model.getDeviceMac() + "");
                    content.add(strList);
                }
            }
            ExcelUtil.exportToExcel(response, titles, fileName, content);
        } catch (Exception e) {
            LOGGER.error("statRewardSwUserDetailListExportExcel error " + e.getMessage());
        }
    }

    @Override
    @TargetDataSource("ds1")
    public ResponseQueryStatRewardDurationInfoModel queryStatRewardDurationInfo() {
        try {
            ResponseQueryStatRewardDurationInfoModel model = new ResponseQueryStatRewardDurationInfoModel();
            List<ResponseBaseModel> durations = new ArrayList<ResponseBaseModel>();
            // 分布指标
            ResponseBaseModel linkDuration = new ResponseBaseModel();
            linkDuration.setStrId(StatAnalyzeDataVar.LINK_DURATION);
            linkDuration.setStrValue(StatAnalyzeDataVar.LINK_DURATION_STR);
            durations.add(linkDuration);
            ResponseBaseModel useDuration = new ResponseBaseModel();
            useDuration.setStrId(StatAnalyzeDataVar.USE_DURATION);
            useDuration.setStrValue(StatAnalyzeDataVar.USE_DURATION_STR);
            durations.add(useDuration);
            model.setDurations(durations);
            return model;
        } catch (Exception e) {
            LOGGER.error("queryStatRewardDurationInfo error " + e.getMessage());
        }
        return null;
    }

    @Override
    @TargetDataSource("ds2")
    public ResponseStatAppInfoModel statRewardDuration(RequestStatRewardDurationModel model) {
        try {
            model.setDurationDate(model.getDurationDate().replace("-", ""));
            ResponseStatAppInfoModel rspObj = new ResponseStatAppInfoModel();
            List<String> lineChartXs = new ArrayList<String>();
            List<Number> lineChartYs = new ArrayList<Number>();
            String tableName = "";
            String infoTableName = "";
            String timeColumn = "";
            // 根据时长指标获取对应表名、列字段名
            if (model.getSwDuration() == NumberUtils.toInt(StatAnalyzeDataVar.LINK_DURATION)) {
                tableName = StatAnalyzeDataVar.LINK_DURATION_TABLE;
                infoTableName = StatAnalyzeDataVar.LINK_DURATION_TABLE_INFO;
                timeColumn = StatAnalyzeDataVar.LINK_DURATION_COLUMN;
            } else if (model.getSwDuration() == NumberUtils.toInt(StatAnalyzeDataVar.USE_DURATION)) {
                tableName = StatAnalyzeDataVar.USE_DURATION_TABLE;
                infoTableName = StatAnalyzeDataVar.USE_DURATION_TABLE_INFO;
                timeColumn = StatAnalyzeDataVar.USE_DURATION_COLUMN;
            }
            if (StringUtil.isNullOrEmpty(tableName) || StringUtil.isNullOrEmpty(timeColumn)) {
                LOGGER.error("statRewardDuration error swDuration param illegal ");
                return rspObj;
            }
            // 先获取连接或使用的总数
            int linkOrUseTimeCountTotal = statAnalyzeRewardMapper.getLinkOrUseTimeCountByTs(tableName, model);
            boolean totalCountIsZero = linkOrUseTimeCountTotal < 1 ? true : false;
            List<ConfigConnOrUseTrModel> durationInfos = statAnalyzeRewardMapper
                    .getStatDurationLinkOrUseInfo(infoTableName);
            if (durationInfos != null && durationInfos.size() > 0) {
                for (ConfigConnOrUseTrModel info : durationInfos) {
                    lineChartXs.add(info.getRangeText());
                    if (totalCountIsZero) {
                        lineChartYs.add(0);
                    } else {
                        model.setRangeId(info.getRangeId());
                        model.setTableName(tableName);
                        int linkOrUserTimeCount = statAnalyzeRewardMapper.getLinkOrUseTimeCountByTsAndScope(tableName,
                                model);
                        lineChartYs.add(formatPercent(linkOrUserTimeCount, linkOrUseTimeCountTotal));
                    }
                }
            }
            rspObj.setLineChartXs(lineChartXs);
            rspObj.setLineChartYs(lineChartYs);
            rspObj.setIndexChildName(AccountDateUtil.formatDateOnlyMD(model.getDurationDate()));
            return rspObj;
        } catch (Exception e) {
            LOGGER.error("statRewardDuration error " + e.getMessage());
        }
        return null;
    }

    @Override
    @TargetDataSource("ds2")
    public ResponseStatDurationListModel statRewardDurationList(RequestStatRewardDurationModel model) {
        try {
            model.setDurationDate(model.getDurationDate().replace("-", ""));
            ResponseStatDurationListModel rspObj = new ResponseStatDurationListModel();
            List<ResponseStatDurationModel> list = new ArrayList<ResponseStatDurationModel>();
            int linkTimeCountTotal = statAnalyzeRewardMapper
                    .getLinkOrUseTimeCountByTs(StatAnalyzeDataVar.LINK_DURATION_TABLE, model);
            boolean linkTotalCountIsZero = linkTimeCountTotal < 1 ? true : false;
            int useTimeCountTotal = statAnalyzeRewardMapper
                    .getLinkOrUseTimeCountByTs(StatAnalyzeDataVar.USE_DURATION_TABLE, model);
            boolean useTotalCountIsZero = useTimeCountTotal < 1 ? true : false;
            List<ConfigConnOrUseTrModel> durationInfos = statAnalyzeRewardMapper
                    .getStatDurationLinkOrUseInfo(StatAnalyzeDataVar.LINK_DURATION_TABLE_INFO);
            List<ConfigConnOrUseTrModel> useDurationInfos = statAnalyzeRewardMapper
                    .getStatDurationLinkOrUseInfo(StatAnalyzeDataVar.USE_DURATION_TABLE_INFO);
            Map<Integer, String> useInfoMap = new HashMap<Integer, String>();
            if (useDurationInfos != null && useDurationInfos.size() > 0) {
                for (ConfigConnOrUseTrModel useInfo : useDurationInfos) {
                    useInfoMap.put(useInfo.getRangeId(), useInfo.getRangeText());
                }
            }
            if (durationInfos != null && durationInfos.size() > 0) {
                for (ConfigConnOrUseTrModel linkInfo : durationInfos) {
                    model.setRangeId(linkInfo.getRangeId());
                    ResponseStatDurationModel durationModel = new ResponseStatDurationModel();
                    durationModel.setDate(model.getDurationDate());
                    durationModel.setLinkTimeDesc(linkInfo.getRangeText());
                    durationModel.setUseTimeDesc(
                            useInfoMap.get(linkInfo.getRangeId()) == null ? "" : useInfoMap.get(linkInfo.getRangeId()));
                    // 连接时长信息
                    model.setTableName(StatAnalyzeDataVar.LINK_DURATION_TABLE);
                    if (linkTotalCountIsZero) {
                        durationModel.setLinkSwCount(0);
                        durationModel.setLinkPercent(0 + "%");
                    } else {
                        int linkTimeCount = statAnalyzeRewardMapper
                                .getLinkOrUseTimeCountByTsAndScope(StatAnalyzeDataVar.LINK_DURATION_TABLE, model);
                        durationModel.setLinkSwCount(linkTimeCount);
                        durationModel.setLinkPercent(formatPercent(linkTimeCount, linkTimeCountTotal) + "%");
                    }
                    // 使用时长信息
                    model.setTableName(StatAnalyzeDataVar.USE_DURATION_TABLE);
                    if (useTotalCountIsZero) {
                        durationModel.setUseSwCount(0);
                        durationModel.setUsePercent(0 + "%");
                    } else {
                        int useTimeCount = statAnalyzeRewardMapper
                                .getLinkOrUseTimeCountByTsAndScope(StatAnalyzeDataVar.USE_DURATION_TABLE, model);
                        durationModel.setUseSwCount(useTimeCount);
                        durationModel.setUsePercent(formatPercent(useTimeCount, useTimeCountTotal) + "%");
                    }
                    list.add(durationModel);
                }
            }
            rspObj.setDurationList(list);
            return rspObj;
        } catch (Exception e) {
            LOGGER.error("statRewardDurationList error " + e.getMessage());
        }
        return null;
    }

    /**
     * 格式化百分数
     * 
     * @param child
     * @param parent
     * @return
     */
    private int formatPercent(int child, int parent) {
        try {
            if (parent == 0) {
                return 0;
            }
            float num = (float) child / parent;
            DecimalFormat df = new DecimalFormat("0.00");// 格式化小数
            String numStr = df.format(num);
            float result = NumberUtils.toFloat(numStr) * 100;
            return (int) result;
        } catch (Exception e) {
            LOGGER.error("formatPercent error " + e.getMessage());
        }
        return 0;
    }

    @Override
    @TargetDataSource("ds2")
    public List<TblStatDurationUserDetailModel> getAnalyzeStatRewardSwUserDetailList(RequestStatAppModel model) {
        try {
            String dateSplite = statAnalyzeService.getDatesByDate(model.getSltStartDate(), model.getSltEndDate());
            List<TblStatDurationUserDetailModel> list = statAnalyzeRewardMapper.geTblStatDurationUserDetails(dateSplite,
                    model);
            if (list != null && list.size() > 0) {
                for (TblStatDurationUserDetailModel detail : list) {
                    detail.setDate(AccountDateUtil.getDateByDtStr(detail.getDate()));
                    detail.setShowSwMoney(
                            com.phicomm.smarthome.ssp.server.util.NumberUtils.changeF2Y(detail.getSwMoney() + ""));
                    detail.setLinkDevice(getSwDeviceNameById(NumberUtils.toInt(detail.getDeviceType() + "")));
                }
            }
            return list;
        } catch (Exception e) {
            LOGGER.error("getAnalyzeStatRewardSwUserDetailList error " + e.getMessage());
        }
        return null;
    }

    @Override
    @TargetDataSource("ds1")
    public List<TblStatDurationUserDetailModel> geAllTblStatDurationUserDetails(RequestStatAppModel model) {
        try {
            String dateSplite = statAnalyzeService.getDatesByDate(model.getSltStartDate(), model.getSltEndDate());
            List<TblStatDurationUserDetailModel> list = statAnalyzeRewardMapper
                    .geAllTblStatDurationUserDetails(dateSplite, model);
            if (list != null && list.size() > 0) {
                for (TblStatDurationUserDetailModel detail : list) {
                    detail.setDate(AccountDateUtil.getDateByDtStr(detail.getDate()));
                    detail.setShowSwMoney(
                            com.phicomm.smarthome.ssp.server.util.NumberUtils.changeF2Y(detail.getSwMoney() + ""));
                    detail.setLinkDevice(getSwDeviceNameById(NumberUtils.toInt(detail.getDeviceType() + "")));
                }
            }
            return list;
        } catch (Exception e) {
            LOGGER.error("geAllTblStatDurationUserDetails error " + e.getMessage());
        }
        return null;
    }

    @Override
    @TargetDataSource("ds2")
    public int getAnalyzeStatRewardSwUserDetailListCount(RequestStatAppModel model) {
        try {
            String dateSplite = statAnalyzeService.getDatesByDate(model.getSltStartDate(), model.getSltEndDate());
            return statAnalyzeRewardMapper.geTblStatDurationUserDetailsCount(dateSplite, model);
        } catch (Exception e) {
            LOGGER.error("getAnalyzeStatRewardSwUserDetailListCount error " + e.getMessage());
        }
        return 0;
    }

}
