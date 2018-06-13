package com.phicomm.smarthome.ssp.server.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.phicomm.smarthome.ssp.server.model.SwRouterModel;
import com.phicomm.smarthome.ssp.server.model.SwUserIncomeModel;
import com.phicomm.smarthome.ssp.server.model.request.RequestIncomeRecordModel;
import com.phicomm.smarthome.ssp.server.model.request.RequestSwUserModel;
import com.phicomm.smarthome.ssp.server.model.response.ResponseIncomeRedordModel;
import com.phicomm.smarthome.ssp.server.model.response.ResponseUserInfoModel;

public interface ManagerUserIncomeMapper {

    List<ResponseUserInfoModel> getUserInfoList(RequestSwUserModel model);

    int countUserInfoList(RequestSwUserModel model);
    
    int getUserInfoListCount(RequestSwUserModel model);

    List<SwUserIncomeModel> getUserIncomeList(RequestIncomeRecordModel model);

    int getUserIncomeListCount(RequestIncomeRecordModel model);

    @Select("SELECT MAX(total_income)AS total_income,uid " + "FROM `sw_user_income` WHERE STATUS=0 "
            + "AND uid IN(${uids}) " + "GROUP BY uid")
    List<Map<String, Object>> getUserTotalIncomeByUids(@Param("uids") String uids);

    @Select("SELECT MAX(total_balance)AS total_balance,uid " + "FROM `sw_user_income_balance` WHERE STATUS=0 "
            + "AND uid IN(${uids}) " + "GROUP BY uid")
    List<Map<String, Object>> getUserTotalBalanceByUids(@Param("uids") String uids);

    @Select("SELECT o.order_id,o.buy_time,o.online_time_total,o.online_time_total_cost,g.router_ip,g.router_mac "
            + "FROM `sw_guest_order` o " + "LEFT JOIN `sw_guest` g ON o.guest_id=g.id "
            + "WHERE o.status=0 AND o.order_id IN(${orderIds}) "
            + "order by o.buy_time desc")
    @Results({ @Result(property = "wxOrderId", column = "order_id"),
            @Result(property = "ipAddress", column = "router_ip"), @Result(property = "buyTime", column = "buy_time"),
            @Result(property = "useDuration", column = "online_time_total"),
            @Result(property = "rewardMoney", column = "online_time_total_cost"),
            @Result(property = "macAddress", column = "router_mac") })
    List<ResponseIncomeRedordModel> getIncomeRedordInfoByOrderIds(@Param("orderIds") String orderIds);

    // @Select("SELECT MIN(create_time) AS create_time,router_mac "
    // + "FROM `sw_router` WHERE STATUS=0 "
    // + "AND create_time>=#{startTimeTs} AND create_time<=#{endTimeTs} "
    // + "GROUP BY router_mac "
    // + "ORDER BY create_time ASC")
    List<Map<String, Object>> getAllRouterMacByTs(@Param("startTimeTs") long startTimeTs,
            @Param("endTimeTs") long endTimeTs);

    @Select("SELECT router_ip,router_mac FROM `sw_router` " + "WHERE router_mac IN(${routerMacs})")
    @Results({ @Result(property = "routerIp", column = "router_ip"),
            @Result(property = "routerMac", column = "router_mac") })
    List<SwRouterModel> getRouterInfoByMacs(@Param("routerMacs") String routerMacs);

}
