package com.phicomm.smarthome.ssp.server.service;

import com.phicomm.smarthome.ssp.server.model.RequestHandleWithDrawBodyModel;
import com.phicomm.smarthome.ssp.server.model.RequestWithDrawBodyModel;
import com.phicomm.smarthome.ssp.server.model.WithDrawsAlipayModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

/**
 * PROJECT_NAME: SspBusServer
 * PACKAGE_NAME: com.phicomm.ssp.server.service.sharedwifi.service.impl
 * DESCRIPTION:
 * AUTHOR: liang04.zhang
 * DATE: 2017/6/19
 */

public interface WithDrawBackendModelService {

    public Page<WithDrawsAlipayModel> getWithDrawList4Pages(final RequestWithDrawBodyModel requestWithDrawBodyModel, int page, int count, Sort sort);

    public int bindOrderNumWithId(final RequestHandleWithDrawBodyModel requestHandleWithDrawBodyModel,int optimeStamp);
}
