package com.phicomm.smarthome.ssp.server.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.phicomm.smarthome.ssp.server.consts.Const;
import com.phicomm.smarthome.ssp.server.consts.Const.ManagementBillVar;
import com.phicomm.smarthome.ssp.server.dao.ManagerUserIncomeMapper;
import com.phicomm.smarthome.ssp.server.model.SwRouterModel;
import com.phicomm.smarthome.ssp.server.model.SwUserIncomeModel;
import com.phicomm.smarthome.ssp.server.model.request.RequestIncomeRecordModel;
import com.phicomm.smarthome.ssp.server.model.request.RequestSwUserModel;
import com.phicomm.smarthome.ssp.server.model.response.ResponseIncomeRedordModel;
import com.phicomm.smarthome.ssp.server.model.response.ResponseUserInfoListModel;
import com.phicomm.smarthome.ssp.server.model.response.ResponseUserInfoModel;
import com.phicomm.smarthome.ssp.server.service.ManagerUserIncomeService;
import com.phicomm.smarthome.ssp.server.util.AccountDateUtil;
import com.phicomm.smarthome.ssp.server.util.CommonUtils;
import com.phicomm.smarthome.ssp.server.util.MyListUtils;
import com.phicomm.smarthome.ssp.server.util.NumberUtils;
import com.phicomm.smarthome.util.OptDateUtil;
import com.phicomm.smarthome.util.StringUtil;

/**
 * @author fujiang.mao
 *
 */
@Service
public class ManagerUserIncomeServicImpl implements ManagerUserIncomeService {

    public static final Logger LOGGER = LogManager.getLogger(ManagerUserIncomeServicImpl.class);

    @Autowired
    private ManagerUserIncomeMapper managerUserIncomeMapper;

    @Override
    public ResponseUserInfoListModel getUserInfoList(RequestSwUserModel model) {
        ResponseUserInfoListModel rspModel = new ResponseUserInfoListModel();
        try {
            // 设置时间戳
            if (!StringUtil.isNullOrEmpty(model.getUseStartDate())
                    && !StringUtil.isNullOrEmpty(model.getUseEndDate())) {
                // 将日期xxxx/xx/xx转为xxxx-xx-xx格式
                model.setUseStartDate(AccountDateUtil.reverseDateFormat(model.getUseStartDate()));
                model.setUseEndDate(AccountDateUtil.reverseDateFormat(model.getUseEndDate()));
                // 筛选时间戳范围
                model.setStartTimeTs(OptDateUtil.getLTimeByStr(model.getUseStartDate() + " 00:00:00"));
                model.setEndTimeTs(OptDateUtil.getLTimeByStr(model.getUseEndDate() + " 23:59:59"));
            }

            // 分页查询用户信息
            List<ResponseUserInfoModel> list = new ArrayList<>();
            try {
                // 计算起始下标跟偏移量，进行分页查询
                model.setStartIndex((model.getCurPage() - 1) * model.getPageSize());
                model.setOffset(model.getPageSize());
                list = managerUserIncomeMapper.getUserInfoList(model);
                int count = managerUserIncomeMapper.countUserInfoList(model);
                // 计算总页码数
                rspModel.setTotalCount(count);
                if (MyListUtils.isEmpty(list)) {
                    rspModel.setUserList(list);
                    return rspModel;
                }
            } catch (Exception e) {
                LOGGER.error("ManagerUserIncomeServicImpl's getUserInfoList method error!");
                LOGGER.error(e);
                rspModel.setUserList(list);
                rspModel.setRetCode(Const.ResponseStatus.STATUS_COMMON_QUERY_FAILED);
                rspModel.setRetMsg(Const.ResponseStatus.STATUS_COMMON_QUERY_FAILED_STR);
            }

            // 设置开启时间跟拼接uid
            StringBuilder stringBuilder = new StringBuilder();
            for (ResponseUserInfoModel userInfo : list) {
                    String activeTime = userInfo.getUseStartTime();
                    if (Integer.valueOf(activeTime) == 0) {
                        userInfo.setUseStartTime(ManagementBillVar.USER_ACCOUNT_NOT_BIND_ANY_ROUTER_EXPIRE_TIME_DESC);
                    } else {
                        userInfo.setUseStartTime(CommonUtils.stampToDateTimeStr(Long.valueOf(userInfo.getUseStartTime())));
                    }
                stringBuilder.append(userInfo.getUid() + ",");
            }
            String uids = stringBuilder.toString();

            // 查询用户总收益跟总余额
            if (!StringUtil.isNullOrEmpty(uids)) {
                uids = uids.substring(0, uids.length() - 1);
                // 标记用户总收益
                List<Map<String, Object>> incomeList = new ArrayList<>();
                try {
                    incomeList = managerUserIncomeMapper.getUserTotalIncomeByUids(uids);
                } catch (Exception e) {
                    LOGGER.error("ManagerUserIncomeServicImpl's getUserInfoList method error!");
                    LOGGER.error(e);
                    rspModel.setUserList(list);
                    rspModel.setRetCode(Const.ResponseStatus.STATUS_COMMON_QUERY_FAILED);
                    rspModel.setRetMsg(Const.ResponseStatus.STATUS_COMMON_QUERY_FAILED_STR);
                    return rspModel;
                }
                Map<String, Object> incomeMap = new HashMap<String, Object>();
                if (!MyListUtils.isEmpty(incomeList)) {
                    for (Map<String, Object> map : incomeList) {
                        incomeMap.put((String) map.get("uid"), map.get("total_income"));
                    }
                }

                // 标记用户总余额
                List<Map<String, Object>> balanceList = new ArrayList<>();
                try {
                    balanceList = managerUserIncomeMapper.getUserTotalBalanceByUids(uids);
                } catch (Exception e) {
                    LOGGER.error("ManagerUserIncomeServicImpl's getUserInfoList method error!");
                    LOGGER.error(e);
                    rspModel.setUserList(list);
                    rspModel.setRetCode(Const.ResponseStatus.STATUS_COMMON_QUERY_FAILED);
                    rspModel.setRetMsg(Const.ResponseStatus.STATUS_COMMON_QUERY_FAILED_STR);
                    return rspModel;
                }
                Map<String, Object> balanceMap = new HashMap<String, Object>();
                if (!MyListUtils.isEmpty(balanceList)) {
                    for (Map<String, Object> map : balanceList) {
                        balanceMap.put((String) map.get("uid"), map.get("total_balance"));
                    }
                }

                // 设置用户总收益跟总余额的值
                for (ResponseUserInfoModel userInfo : list) {
                    userInfo.setTotalIncome(incomeMap.get(userInfo.getUid()) == null ? "0"
                            : (String) incomeMap.get(userInfo.getUid()));
                    userInfo.setCurrentBalance(balanceMap.get(userInfo.getUid()) == null ? "0"
                            : (String) balanceMap.get(userInfo.getUid()));
                }
                incomeMap = null;
                balanceMap = null;
            }
            rspModel.setUserList(list);
        } catch (Exception e) {
            LOGGER.error("ManagerUserIncomeServicImpl's getUserInfoList method error!");
            LOGGER.error(e);
        }
        return rspModel;
    }

    @Override
    public List<ResponseIncomeRedordModel> getIncomeRecordList(RequestIncomeRecordModel model) {
        try {
            List<ResponseIncomeRedordModel> incomeRedord = new ArrayList<ResponseIncomeRedordModel>();
            if (!StringUtil.isNullOrEmpty(model.getRewardStartDate())
                    && !StringUtil.isNullOrEmpty(model.getRewardEndDate())) {
                // 将日期xxxx/xx/xx转为xxxx-xx-xx格式
                model.setRewardStartDate(AccountDateUtil.reverseDateFormat(model.getRewardStartDate()));
                model.setRewardEndDate(AccountDateUtil.reverseDateFormat(model.getRewardEndDate()));
                // 筛选时间戳范围
                model.setStartTimeTs(OptDateUtil.getLTimeByStr(model.getRewardStartDate() + " 00:00:00"));
                model.setEndTimeTs(OptDateUtil.getLTimeByStr(model.getRewardEndDate() + " 23:59:59"));
            }
            // 获取用户收益信息
            List<SwUserIncomeModel> userIncomeList = managerUserIncomeMapper.getUserIncomeList(model);
            if (userIncomeList != null && userIncomeList.size() > 0) {
                Map<String, Object> uidMap = new HashMap<String, Object>();
                Map<String, Object> iphoneMap = new HashMap<String, Object>();
                Map<String, Object> incomeMap = new HashMap<String, Object>();
                Map<String, Object> routerMap = new HashMap<String, Object>();
                String orderIds = "";
                String routerMacs = "";
                for (SwUserIncomeModel income : userIncomeList) {
                    orderIds += "'" + income.getOrderId() + "',";
                    uidMap.put(income.getOrderId(), income.getUid());
                    iphoneMap.put(income.getOrderId(), income.getIphone());
                    incomeMap.put(income.getOrderId(), income.getTodayIncome());
                    routerMacs += "'" + income.getRouterMac() + "',";
                }
                if (!StringUtil.isNullOrEmpty(orderIds)) {
                    orderIds = orderIds.substring(0, orderIds.length() - 1);
                }
                if (!StringUtil.isNullOrEmpty(routerMacs)) {
                    routerMacs = routerMacs.substring(0, routerMacs.length() - 1);
                }
                if (!StringUtil.isNullOrEmpty(orderIds)) {
                    // 获取订单信息
                    incomeRedord = managerUserIncomeMapper.getIncomeRedordInfoByOrderIds(orderIds);
                }
                if (incomeRedord != null && incomeRedord.size() > 0) {
                    for (ResponseIncomeRedordModel record : incomeRedord) {
                        record.setRewardTime(OptDateUtil.stampToDate(record.getBuyTime()));
                        record.setUid(uidMap.get(record.getWxOrderId()) == null ? ""
                                : (String) uidMap.get(record.getWxOrderId()));
                        record.setIphone(iphoneMap.get(record.getWxOrderId()) == null ? ""
                                : (String) iphoneMap.get(record.getWxOrderId()));
                        record.setUserIncome(incomeMap.get(record.getWxOrderId()) == null ? ""
                                : (String) incomeMap.get(record.getWxOrderId()));
                        // 分转元
                        record.setRewardMoney(NumberUtils.changeF2YZero(record.getRewardMoney()));
                        record.setCompanyIncome(
                                NumberUtils.bigdecimalSubByStr(record.getRewardMoney(), record.getUserIncome()));
                        record.setIpAddress(record.getIpAddress() == null ? "" : record.getIpAddress());
                    }
                }
                if (!StringUtil.isNullOrEmpty(routerMacs)) {
                    List<SwRouterModel> routerInfos = managerUserIncomeMapper.getRouterInfoByMacs(routerMacs);
                    if (routerInfos != null && routerInfos.size() > 0) {
                        for (SwRouterModel swRouterModel : routerInfos) {
                            routerMap.put(swRouterModel.getRouterMac(), swRouterModel.getRouterIp());
                        }
                    }
                    if (incomeRedord != null && incomeRedord.size() > 0) {
                        for (ResponseIncomeRedordModel record : incomeRedord) {
                            record.setIpAddress(routerMap.get(record.getMacAddress()) == null ? ""
                                    : (String) routerMap.get(record.getMacAddress()));
                        }
                    }
                }
                uidMap = null;
                iphoneMap = null;
                incomeMap = null;
                routerMap = null;
            }
            return incomeRedord;
        } catch (Exception e) {
            LOGGER.error("getIncomeRecordList error " + e.getMessage());
        }
        return null;
    }

    @Override
    public int getIncomeRecordListCount(RequestIncomeRecordModel model) {
        try {
            return managerUserIncomeMapper.getUserIncomeListCount(model);
        } catch (Exception e) {
            LOGGER.error("getIncomeRecordListCount error " + e.getMessage());
        }
        return 0;
    }

}
