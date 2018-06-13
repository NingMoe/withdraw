package com.phicomm.smarthome.ssp.server.service;

import com.phicomm.smarthome.ssp.server.model.RequestBillReportParam;
import com.phicomm.smarthome.ssp.server.model.RequestWithDrawBodyModel;
import com.phicomm.smarthome.ssp.server.model.SwBillReportModel;
import com.phicomm.smarthome.ssp.server.model.WithDrawsAlipayModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import java.util.List;

/**
 * Created by xiangrong.ke on 2017/7/6.
 */
public interface WxBillModelService {

    void downloadWxBillByDate(String billTextData,String billDate,String orderPrefix);

    void loadSwGuestOrderByDate(String billDate,String orderPrefix);

    void analyse(String billDate);

    Page<SwBillReportModel> getBillReport4Pages(final RequestBillReportParam requestParam, int page, int count, Sort sort);

    void sendTestMail(String content);

    void sendExceptionBillMail(String billDate,String sendTo);
}
