package com.phicomm.smarthome.ssp.server.dao;

import com.phicomm.smarthome.ssp.server.model.SwBillReportModel;
import com.phicomm.smarthome.ssp.server.model.SwGuestOrderBillTiModel;
import com.phicomm.smarthome.ssp.server.model.SwGuestOrderModel;
import com.phicomm.smarthome.ssp.server.model.SwWxBillTiModel;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface BalanceAccountMapper {

    @Insert("INSERT into sw_wx_bill_ti(deal_time,deal_time_format,wx_order_id,order_id,cost,pay_type,bill_date)values(#{wxBillModel.dealTime},"
            + "#{wxBillModel.dealTimeFormat},#{wxBillModel.wxOrderId},#{wxBillModel.orderId},#{wxBillModel.cost},#{wxBillModel.payType},#{wxBillModel.billDate})")
    int insertWxBill(@Param("wxBillModel") SwWxBillTiModel wxBillModel);

    @Delete("delete from sw_wx_bill_ti where bill_date = #{billDate}")
    int delWxBill(@Param("billDate") long billDate);

    @Insert("INSERT into sw_guest_order_bill_ti(deal_time,deal_time_format,order_id,cost,bill_date)values(#{billModel.dealTime},"
            + "#{billModel.dealTimeFormat},#{billModel.orderId},#{billModel.cost},#{billModel.billDate})")
    int insertGuestOrderBill(@Param("billModel") SwGuestOrderBillTiModel billModel);

    @Delete("delete from sw_guest_order_bill_ti where bill_date = #{billDate}")
    int delGuestOrderBillByDate(@Param("billDate") long billDate);

    @Select("select order_id,buy_time, online_time_total_cost "
            + "from sw_guest_order "
            + "where buy_time >= #{billDateBegin} and buy_time < #{billDateEnd} and order_id like "
            + "CONCAT(#{orderPrefix,jdbcType=VARCHAR},'%')")
    @Results({ @Result(property = "orderId", column = "order_id"), @Result(property = "buyTime", column = "buy_time"),
            @Result(property = "onlineTimeTotalCost", column = "online_time_total_cost") })
    List<SwGuestOrderModel> getGuestOrderByDate(@Param("billDateBegin") int billDateBegin,
            @Param("billDateEnd") int billDateEnd, @Param("orderPrefix") String orderPrefix);

    @Select("select deal_time, deal_time_format,wx_order_id,order_id,cost,pay_type,bill_date from sw_wx_bill_ti where bill_date = #{billDate}")
    @Results({ @Result(property = "dealTime", column = "deal_time"),
            @Result(property = "dealTimeFormat", column = "deal_time_format"),
            @Result(property = "wxOrderId", column = "wx_order_id"), @Result(property = "orderId", column = "order_id"),
            @Result(property = "cost", column = "cost"), @Result(property = "payType", column = "pay_type"),
            @Result(property = "billDate", column = "bill_date") })
    List<SwWxBillTiModel> getWxBillByDateFromTi(@Param("billDate") long billDate);

    @Insert("    INSERT INTO sw_bill_report ( \n" +
            "              backend_deal_time, \n" +
            "              backend_deal_time_format, \n" +
            "              backend_order_id, \n" +
            "              backend_cost, \n" +
            "              wx_deal_time, \n" +
            "              wx_deal_time_format, \n" +
            "              wx_backend_order_id, \n" +
            "              wx_order_id, \n" +
            "              wx_cost, \n" +
            "              wx_pay_type, \n" +
            "              status, \n" +
            "              bill_date \n" +
            "             ) \n" +
            "             SELECT * FROM \n" +
            "              (SELECT IF(a.deal_time IS NULL,0,a.deal_time) backend_deal_time,\n" +
            "                      IF(a.deal_time_format IS NULL,'',a.deal_time_format) backend_deal_time_format, \n" +
            "                      IF(a.order_id IS NULL,'',a.order_id) backend_order_id, \n" +
            "                      IF(a.cost IS NULL,'',a.cost) backend_cost, \n" +
            "                      IF(b.deal_time IS NULL,0,b.deal_time) wx_deal_time, \n" +
            "                      IF(b.deal_time_format IS NULL,'',b.deal_time_format) wx_deal_time_format, \n" +
            "                      IF(b.order_id IS NULL,'',b.order_id) wx_backend_order_id, \n" +
            "                      IF(b.wx_order_id IS NULL,'',b.wx_order_id) wx_order_id, \n" +
            "                      IF(b.cost IS NULL,'',b.cost) wx_cost, \n" +
            "                      IF(b.pay_type IS NULL,'',b.pay_type) wx_pay_type, \n" +
            "                      (CASE WHEN b.order_id IS NULL OR \n" +
            "                                 a.cost != CONVERT(b.cost*100,DECIMAL) THEN 1 \n" +
            "                       ELSE 0 END ) status, \n" +
            "                      IF(a.bill_date IS NULL,0,a.bill_date) bill_date \n" +
            "                 FROM sw_guest_order_bill_ti a LEFT JOIN \n" +
            "                   sw_wx_bill_ti b ON a.order_id = b.order_id \n" +
            "                 WHERE a.bill_date = #{billDate}) c \n" +
            "               WHERE c.status = 1 \n" +
            "              UNION \n" +
            "              SELECT * FROM \n" +
            "                (SELECT IF(a.deal_time IS NULL,0,a.deal_time) backend_deal_time, \n" +
            "                        IF(a.deal_time_format IS NULL,'',a.deal_time_format) backend_deal_time_format, \n" +
            "                        IF(a.order_id IS NULL,'',a.order_id) backend_order_id, \n" +
            "                        IF(a.cost IS NULL,'',a.cost) backend_cost, \n" +
            "                        IF(b.deal_time IS NULL,0,b.deal_time) wx_deal_time, \n" +
            "                        IF(b.deal_time_format IS NULL,'',b.deal_time_format) wx_deal_time_format, \n" +
            "                        IF(b.order_id IS NULL,'',b.order_id) wx_backend_order_id, \n" +
            "                        IF(b.wx_order_id IS NULL,'',b.wx_order_id) wx_order_id, \n" +
            "                        IF(b.cost IS NULL,'',b.cost) wx_cost, \n" +
            "                        IF(b.pay_type IS NULL,'',b.pay_type) wx_pay_type, \n" +
            "                        (CASE WHEN a.order_id IS NULL OR \n" +
            "                        a.cost != CONVERT(b.cost*100,DECIMAL) THEN 1 \n" +
            "                        ELSE 0 END ) status, \n" +
            "                        IF(b.bill_date IS NULL,0,b.bill_date) bill_date \n" +
            "                      FROM sw_guest_order_bill_ti a RIGHT JOIN \n" +
            "                      sw_wx_bill_ti b ON a.order_id = b.order_id \n" +
            "                  WHERE b.bill_date = #{billDate}) c \n" +
            "                WHERE c.status = 1 \n" +
            "              UNION \n" +
            "              SELECT * FROM \n" +
            "                (SELECT IF(a.deal_time IS NULL,0,a.deal_time) backend_deal_time, \n" +
            "                        IF(a.deal_time_format IS NULL,'',a.deal_time_format) backend_deal_time_format, \n" +
            "                        IF(a.order_id IS NULL,'',a.order_id) backend_order_id,\n" +
            "                        IF(a.cost IS NULL,'',a.cost) backend_cost, \n" +
            "                        IF(b.deal_time IS NULL,0,b.deal_time) wx_deal_time, \n" +
            "                        IF(b.deal_time_format IS NULL,'',b.deal_time_format) wx_deal_time_format, \n" +
            "                        IF(b.order_id IS NULL,'',b.order_id) wx_backend_order_id, \n" +
            "                        IF(b.wx_order_id IS NULL,'',b.wx_order_id) wx_order_id, \n" +
            "                        IF(b.cost IS NULL,'',b.cost) wx_cost, \n" +
            "                        IF(b.pay_type IS NULL,'',b.pay_type) wx_pay_type, \n" +
            "                        (CASE WHEN  \n" +
            "                        a.cost != CONVERT(b.cost*100,DECIMAL) THEN 1 \n" +
            "                        ELSE 0 END ) status, \n" +
            "                        IF(a.bill_date IS NULL,0,a.bill_date) bill_date \n" +
            "                    FROM sw_guest_order_bill_ti a INNER JOIN \n" +
            "                          sw_wx_bill_ti b ON a.order_id = b.order_id \n" +
            "                    WHERE b.bill_date = #{billDate}) c \n" +
            "            ")
    void analyseTi(@Param("billDate") long billDate);

    @Select("select backend_deal_time," + "backend_deal_time_format," + "backend_order_id ," + "backend_cost, "
            + "wx_deal_time," + "wx_deal_time_format," + "wx_backend_order_id, " + "wx_order_id, " + "wx_cost,"
            + "wx_pay_type, " + "status, " + "bill_date "
            + "from sw_bill_report where bill_date = #{billDate} and status = 1")
    @Results({ @Result(property = "backendDealTime", column = "backend_deal_time"),
            @Result(property = "backendDealTimeFormat", column = "backend_deal_time_format"),
            @Result(property = "backendOrderId", column = "backend_order_id"),
            @Result(property = "backendCost", column = "backend_cost"),
            @Result(property = "wxDealTime", column = "wx_deal_time"),
            @Result(property = "wxDealTimeFormat", column = "wx_deal_time_format"),
            @Result(property = "wxBackendOrderId", column = "wx_backend_order_id"),
            @Result(property = "wxOrderId", column = "wx_order_id"), @Result(property = "wxCost", column = "wx_cost"),
            @Result(property = "wxPayType", column = "wx_pay_type"), @Result(property = "status", column = "status"),
            @Result(property = "billDate", column = "bill_date") })
    List<SwBillReportModel> getBalanceReportByDate(@Param("billDate") long billDate);

    @Delete("delete from sw_bill_report where bill_date = #{billDate}")
    int delBillReportByDate(@Param("billDate") long billDate);
}
