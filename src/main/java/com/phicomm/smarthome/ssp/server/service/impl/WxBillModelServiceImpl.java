package com.phicomm.smarthome.ssp.server.service.impl;

import com.phicomm.smarthome.ssp.server.common.ds.TargetDataSource;
import com.phicomm.smarthome.ssp.server.consts.Const;
import com.phicomm.smarthome.ssp.server.dao.BalanceAccountMapper;
import com.phicomm.smarthome.ssp.server.model.*;
import com.phicomm.smarthome.ssp.server.repository.SwBillReportJpaRepository;
import com.phicomm.smarthome.ssp.server.service.WxBillModelService;
import com.phicomm.smarthome.ssp.server.util.CommonUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiangrong.ke on 2017/7/6.
 */
@Service
public class WxBillModelServiceImpl implements WxBillModelService {
    private static final Logger logger = LogManager.getLogger(WxBillModelServiceImpl.class);

    @Autowired
    private BalanceAccountMapper balanceAccountMapper;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private SwBillReportJpaRepository swBillReportJpaRepository;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void downloadWxBillByDate(String billTextData,String billDate,String orderPrefix) {
        logger.info("downloadWxBillByDate billTextData"+billTextData+",billDate="+billDate);
        String text = billTextData;

        try {
            long billDateTs = CommonUtils.dateToStamp(billDate,"yyyyMMdd");
            //先删掉对应billDate数据
            int delRows = balanceAccountMapper.delWxBill(billDateTs);
            logger.info("del bill rows = "+delRows);

            String rowArr[] = text.split("\r\n");
            int rowLen = rowArr.length;//行数
            logger.info("billDateTs="+billDateTs+",rows="+rowLen);
            SwWxBillTiModel wxBillModel = new SwWxBillTiModel();
            for (int i = 1; i < rowLen-2; i++) {

                String colArr[] = rowArr[i].split(",`");
                //第一列 时间
                String dealTime = colArr[0].replace("`", "");
                wxBillModel.setDealTime(CommonUtils.dateToStamp(dealTime,"yyyy-MM-dd HH:mm:ss"));
                wxBillModel.setDealTimeFormat(dealTime);
                //微信订单号
                wxBillModel.setWxOrderId(colArr[5]);
                String orderId =  colArr[6];
                //根据环境不同，如果商户订单号不是以SWT或者SWP开头的，则跳过
                if(!orderId.isEmpty() && orderId.indexOf(orderPrefix) < 0){
                    continue;
                }
                //商户订单号
                wxBillModel.setOrderId(orderId);
                //总金额
                wxBillModel.setCost(colArr[12]);
                //商品名称
                String payType = colArr[20].split("\\|")[2];
                if(payType.indexOf("H5") != -1) {
                    wxBillModel.setPayType("H5");
                }
                else{
                    wxBillModel.setPayType("saoma");
                }
                //账单日期
                wxBillModel.setBillDate(billDateTs);
                balanceAccountMapper.insertWxBill(wxBillModel);
            }
        }
        catch (Exception e){
            logger.error(e.getMessage());
        }
        logger.info("saveWxBills end");

    }


    @Override
    public void loadSwGuestOrderByDate(String billDate,String orderPrefix){
        logger.info("loadSwGuestOrderByDate billDate="+billDate+",orderPrefix="+orderPrefix);
        int billDateBegin = (int)(CommonUtils.dateToStamp(billDate,"yyyyMMdd"));
        int billDateEnd = billDateBegin + 86400;

        List<SwGuestOrderModel> orders = balanceAccountMapper.getGuestOrderByDate(billDateBegin,billDateEnd,orderPrefix);

        saveGuestOrder(billDate,orders);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void analyse(String billDate) {
        long billDateTs = CommonUtils.dateToStamp(billDate,"yyyyMMdd");
        logger.info("analyse billDate="+billDate+",billDateTs="+billDateTs);
        //先删掉billdate数据
        int affectRows = balanceAccountMapper.delBillReportByDate(billDateTs);
        logger.info("del rows = "+affectRows);
        //再分析入库
        balanceAccountMapper.analyseTi(billDateTs);
    }

    @Override
    @TargetDataSource("ds3")
    public Page<SwBillReportModel> getBillReport4Pages(RequestBillReportParam requestParam, int page, int count, Sort sort) {
        Page<SwBillReportModel> resultList = null;
        Specification<SwBillReportModel> specification = new Specification<SwBillReportModel>() {
            @Override
            public Predicate toPredicate(Root<SwBillReportModel> root,
                                         CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<Predicate>();
                //参数非空判断。不为空则加此条件
                if (!requestParam.getPayType().isEmpty()) {
                    logger.info("payType:"+ requestParam.getPayType());
                    Predicate payType = criteriaBuilder.equal(root.get("wxPayType"), requestParam.getPayType());
                    predicates.add(payType);
                }
                //参数非空判断。不为空则加此条件
                if (!requestParam.getStatus().isEmpty()) {
                    logger.info("status:"+ requestParam.getStatus());
                    Predicate status = criteriaBuilder.equal(root.get("status"), requestParam.getStatus());
                    predicates.add(status);
                }
                //参数非空判断。不为空则加此条件
                if (!requestParam.getBillDateBegin().isEmpty()) {
                    logger.info("billDateBegin:"+ requestParam.getBillDateBegin());
                    long billDateBeginTs = (CommonUtils.dateToStamp(requestParam.getBillDateBegin()));
                    logger.info("billDateBeginTs:"+ billDateBeginTs);
                    Predicate billDateBegin = criteriaBuilder.ge(root.get("billDate"), billDateBeginTs);
                    predicates.add(billDateBegin);
                }
                //参数非空判断。不为空则加此条件
                if (!requestParam.getBillDateEnd().isEmpty()) {
                    logger.info("billDateEnd:"+ requestParam.getBillDateEnd());
                    long billDateEndTs = (CommonUtils.dateToStamp(requestParam.getBillDateEnd()));
                    logger.info("billDateEndTs:"+ billDateEndTs);
                    Predicate billDateEnd = criteriaBuilder.le(root.get("billDate"), billDateEndTs);
                    predicates.add(billDateEnd);
                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[]{}));
            }
        };
        Pageable pageable = new PageRequest(page, count, sort);
        resultList =  swBillReportJpaRepository.findAll(specification,pageable);
        return resultList;
    }

    @Override
    public void sendExceptionBillMail(String billDate,String sendTo) {
        long billDateTs = CommonUtils.dateToStamp(billDate,"yyyy-MM-dd");
        logger.info("sendMail billDate="+billDate+",sendTo="+sendTo+",billDateTs="+billDateTs);
        List<SwBillReportModel> res = balanceAccountMapper.getBalanceReportByDate(billDateTs);
        logger.info("res size:"+res.size());

        String sender="sohoapp@sina.com";
        String subject = "";
        String content = "";

        if(res==null || res.isEmpty()){
            subject = "【打赏网络】"+billDate+"对账单无异常";
        }
        else{
            subject = "【打赏网络】"+billDate+"对账单异常，请关注！";
            int rowNum = 1;
            StringBuffer sbContent = new StringBuffer();
            for (SwBillReportModel item:res) {
                sbContent.append("----第").append(rowNum).append("行记录----").append("\r\n");
                sbContent.append("【后台交易时间】：").append(item.getBackendDealTimeFormat()).append("\r\n");
                sbContent.append("【后台内部订单号】：").append(item.getBackendOrderId()).append("\r\n");
                sbContent.append("【后台交易金额】：").append(item.getBackendCost().isEmpty()?"":item.getBackendCost()+"分").append("\r\n");
                sbContent.append("【微信交易时间】：").append(item.getWxDealTimeFormat()).append("\r\n");
                sbContent.append("【微信商户内部订单号】：").append(item.getWxBackendOrderId()).append("\r\n");
                sbContent.append("【微信订单号】：").append(item.getWxOrderId()).append("\r\n");
                sbContent.append("【微信交易金额】：").append(item.getWxCost().isEmpty()?"":item.getWxCost()+"元").append("\r\n");
                sbContent.append("【微信支付方式】：").append(item.getWxPayType().isEmpty()?"":(item.getWxPayType().equalsIgnoreCase("saoma")?"扫码支付":"H5支付")).append("\r\n");
                sbContent.append("【交易状态】：").append(item.getStatus()==1?"异常":"正常").append("\r\n");

                rowNum++;
            }
            content = sbContent.toString();
            logger.info(content);
        }
        sendMail(sender,sendTo,subject,content);
    }

    @Override
    public void sendTestMail(String content) {
        logger.info("test sendMail");
        String sender="sohoapp@sina.com";
        String sendTo = "xiangrong.ke@phicomm.com;zhihao.yu@phicomm.com";
        String subject = "This is a test mail from phicomm!";
        sendMail(sender,sendTo,subject,content);
    }

    private void sendMail(String sender,String sendTo,String subject,String content) {
        logger.info("sendMail sender="+sender+",content="+content+",sendTo="+sendTo+"subject="+subject);
        if(sender == null || sender.isEmpty() ||
           sendTo == null || sendTo.isEmpty()){
            logger.info("nothing to send mail.");
            return;
        }
        SimpleMailMessage message = new SimpleMailMessage();
        //"sohoapp@sina.com"
        message.setFrom(sender);
        //"xiangrong.ke@phicomm.com;wenhua.tang@phicomm.com;zhihao.yu@phicomm.com"
        String sendToV2[] = sendTo.split(";");
        message.setTo(sendToV2);
        message.setSubject(subject);
        message.setText(content);
        mailSender.send(message);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    private void saveGuestOrder(String billDate,List<SwGuestOrderModel> orders) {
        logger.info("saveGuestOrder billDate="+billDate+",orders="+orders.size());
        //检查参数
        if(orders == null || orders.isEmpty()){
            logger.info("nothing to insert");
            return;
        }
        long billDateTs = CommonUtils.dateToStamp(billDate,"yyyyMMdd");
        //先删掉对应billDate数据
        int delRows = balanceAccountMapper.delGuestOrderBillByDate(billDateTs);
        logger.info("billDateTs"+billDateTs+",del guest order rows="+delRows);
        //再插入对应billDate数据

        for (SwGuestOrderModel item:orders) {
            SwGuestOrderBillTiModel guestOrderBillModel = new SwGuestOrderBillTiModel();
            guestOrderBillModel.setOrderId(item.getOrderId());
            guestOrderBillModel.setDealTime(item.getBuyTime());
            guestOrderBillModel.setCost(item.getOnlineTimeTotalCost());
            guestOrderBillModel.setDealTimeFormat(CommonUtils.stampToDateTimeStr(item.getBuyTime(),"yyyy-MM-dd HH:mm:ss"));
            guestOrderBillModel.setBillDate(billDateTs);

            balanceAccountMapper.insertGuestOrderBill(guestOrderBillModel);
        }
    }
}
