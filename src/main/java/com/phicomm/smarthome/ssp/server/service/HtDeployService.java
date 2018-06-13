package com.phicomm.smarthome.ssp.server.service;

import com.phicomm.smarthome.model.cache.PreFallRatioModel;

public interface HtDeployService {

    int uploadFallRatio(PreFallRatioModel model);

    PreFallRatioModel queryFallRatio();

    void fallRatioStatusCheck();

}
