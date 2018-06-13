package com.phicomm.smarthome.ssp.server.service;

import com.phicomm.smarthome.ssp.server.model.dao.SwRouterConfigDaoModel;

import java.util.List;


/**
 * 开关服务
 */
public interface RouterConfigService {

    public SwRouterConfigDaoModel queryRouterConfig();

    public int updateRouterConfig(SwRouterConfigDaoModel model);
    
}
