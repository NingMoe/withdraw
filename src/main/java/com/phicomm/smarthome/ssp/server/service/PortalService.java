package com.phicomm.smarthome.ssp.server.service;

import com.phicomm.smarthome.ssp.server.model.SwPortalPictureModel;

public interface PortalService {

    int addImgInfo(SwPortalPictureModel pictureModel);

    int removeImgInfo(String imgUrl, long updateTime);

    SwPortalPictureModel queryPortal(String deviceName);

    void portalImgStatusCheck();
}
