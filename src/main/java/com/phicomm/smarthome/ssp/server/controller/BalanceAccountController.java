package com.phicomm.smarthome.ssp.server.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bouncycastle.pqc.jcajce.provider.rainbow.RainbowKeyFactorySpi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.phicomm.smarthome.model.SmartHomeResponse;
import com.phicomm.smarthome.ssp.server.consts.Const;
import com.phicomm.smarthome.ssp.server.model.RequestBillReportParam;
import com.phicomm.smarthome.ssp.server.model.RequestDownloadBillModel;
import com.phicomm.smarthome.ssp.server.model.RequestSendMailParam;
import com.phicomm.smarthome.ssp.server.model.SwBillReportModel;
import com.phicomm.smarthome.ssp.server.model.response.ResponseGetBalanceReport;
import com.phicomm.smarthome.ssp.server.protocol.downloadbill.DownloadBillReqData;
import com.phicomm.smarthome.ssp.server.service.impl.DownloadBillService;
import com.phicomm.smarthome.ssp.server.service.impl.WxBillModelServiceImpl;
import com.phicomm.smarthome.ssp.server.util.CommonUtils;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author wenhua.tang
 *
 */
@RestController
@ComponentScan
@EnableSwagger2
@PropertySources({ @PropertySource(value = "classpath:application.properties") })
public class BalanceAccountController extends BaseController {

    private static final Logger LOGGER = LogManager.getLogger(BalanceAccountController.class);

    private static final DownloadBillService DOWNLOAD_BILL_SERVICE = new DownloadBillService();

    @Value("${super.admin.name}")
    private String superAdminName;

    @Value("${super.admin.password}")
    private String superAdminPassword;

    @Value("${backend.order.prefix}")
    private String orderPrefix;

    @Value("${spring.mail.sendto}")
    private String mailSendTo;

    @Autowired
    private WxBillModelServiceImpl wxBillModelServiceImpl;

    @RequestMapping(value = "/downloadBill", method = RequestMethod.POST, produces = { "application/json" })
    public SmartHomeResponse<Object> downloadBill(@RequestBody RequestDownloadBillModel unifiedOrderRequest,
            HttpServletRequest request) {
        LOGGER.info("downloadBill unifiedOrderRequest.billDate=" + unifiedOrderRequest.getBillDate());
        DownloadBillReqData unifiedorderReqData = new DownloadBillReqData("", unifiedOrderRequest.getBillDate(),
                unifiedOrderRequest.getBillType());

        String billTextData = "";
        try {
            billTextData = DOWNLOAD_BILL_SERVICE.request(unifiedorderReqData);

        } catch (Exception e) {
            LOGGER.error(e);
        }

        wxBillModelServiceImpl.downloadWxBillByDate(billTextData, unifiedOrderRequest.getBillDate(), orderPrefix);

        return new SmartHomeResponse<Object>(Const.WxBillStatus.DOWNLOAD_BILL_SUCC,
                Const.WxBillStatus.DOWNLOAD_BILL_SUCC_STR, billTextData);
    }

    @RequestMapping(value = "/loadGuestOrder", method = RequestMethod.POST, produces = { "application/json" })
    public SmartHomeResponse<Object> loadGuestOrder(@RequestBody RequestDownloadBillModel guestOrderRequest,
            HttpServletRequest request) {
        LOGGER.info("loadGuestOrder guestOrderRequest.billDate=" + guestOrderRequest.getBillDate());
        wxBillModelServiceImpl.loadSwGuestOrderByDate(guestOrderRequest.getBillDate(), orderPrefix);
        return new SmartHomeResponse<Object>(Const.GuestOrderBillStatus.LOAD_BILL_SUCC,
                Const.GuestOrderBillStatus.LOAD_BILL_SUCC_STR, null);
    }

    @RequestMapping(value = "/analyse", method = RequestMethod.POST, produces = { "application/json" })
    public SmartHomeResponse<Object> analyse(@RequestBody RequestDownloadBillModel guestOrderRequest,
            HttpServletRequest request) {
        LOGGER.info("analyse guestOrderRequest.billDate=" + guestOrderRequest.getBillDate());
        wxBillModelServiceImpl.analyse(guestOrderRequest.getBillDate());
        return new SmartHomeResponse<Object>(Const.GuestOrderBillStatus.LOAD_BILL_SUCC,
                Const.GuestOrderBillStatus.LOAD_BILL_SUCC_STR, null);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @RequestMapping(value = "/getBalanceReport", method = RequestMethod.POST, produces = { "application/json" })
    public SmartHomeResponse<Page<SwBillReportModel>> getBalanceReport(@RequestBody RequestBillReportParam param,
            HttpServletRequest request) {
        ResponseGetBalanceReport rspObj = new ResponseGetBalanceReport();
        Page<SwBillReportModel> pagedRes = null;
        int errCode = Const.ResponseStatus.STATUS_OK;
        String errMsg = Const.ResponseStatus.STATUS_OK_STR;
        int page = 0;
        int count = 0;

        // 检查参数
        try {
            // 日期不为空，检验合法性
            if (!param.getBillDateBegin().isEmpty()) {
                CommonUtils.isValidData(param.getBillDateBegin());
            }
            if (!param.getBillDateEnd().isEmpty()) {
                CommonUtils.isValidData(param.getBillDateEnd());
            }

            page = Integer.parseInt(param.getCurPage());
            count = Integer.parseInt(param.getPageSize());
            if (page < 1 || count <= 0) {
                errCode = Const.ResponseStatus.STATUS_COMMON_NULL;
                errMsg = Const.ResponseStatus.STATUS_COMMON_NULL_STR;
                return new SmartHomeResponse(errCode, errMsg, pagedRes);
            }

            // 转换一下，前段第一页从1开始
            page = page - 1;
        } catch (Exception e) {
            errCode = Const.ResponseStatus.STATUS_COMMON_NULL;
            errMsg = Const.ResponseStatus.STATUS_COMMON_NULL_STR;
            return new SmartHomeResponse(errCode, errMsg, pagedRes);
        }

        // 排序
        Sort.Order order1 = new Sort.Order(Sort.Direction.DESC, "status");
        Sort.Order order2 = new Sort.Order(Sort.Direction.ASC, "billDate");
        List<Sort.Order> orders = new ArrayList<Sort.Order>();
        orders.add(order1);
        orders.add(order2);
        Sort sort = new Sort(orders);
        pagedRes = wxBillModelServiceImpl.getBillReport4Pages(param, page, count, sort);
        rspObj.setList(pagedRes.getContent());
        rspObj.setFirst(pagedRes.isFirst());
        rspObj.setLast(pagedRes.isLast());
        rspObj.setNumber(pagedRes.getNumber());
        rspObj.setNumberOfElements(pagedRes.getNumberOfElements());
        rspObj.setSize(pagedRes.getSize());
        rspObj.setSort(pagedRes.getSort());
        rspObj.setTotalElements(pagedRes.getTotalElements());
        rspObj.setTotalPages(pagedRes.getTotalPages());
        return new SmartHomeResponse(errCode, errMsg, rspObj);
    }

    @RequestMapping(value = "/sendTestMail", method = RequestMethod.POST, produces = { "application/json" })
    public void sendTestMail(@RequestBody RequestSendMailParam param) {
        LOGGER.info("sendTestMail param.content=" + param.getContent());

        wxBillModelServiceImpl.sendTestMail(param.getContent());
    }

    @RequestMapping(value = "/sendMail", method = RequestMethod.POST, produces = { "application/json" })
    public void sendMail(@RequestBody RequestSendMailParam param) {
        LOGGER.info("sendTestMail param.content=" + param.getContent() + ",billdate=" + param.getBillDate());

        wxBillModelServiceImpl.sendExceptionBillMail(param.getBillDate(), mailSendTo);
    }
}
