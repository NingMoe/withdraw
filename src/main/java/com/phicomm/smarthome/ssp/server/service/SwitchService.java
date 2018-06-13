package com.phicomm.smarthome.ssp.server.service;

import com.phicomm.smarthome.ssp.server.model.SwRouterConfigModel;

import java.util.List;


/**
 * 开关服务
 */
public interface SwitchService {

    /**
     * 是否停用打赏功能 0 开启 1禁止
     * @param model
     * @return
     */
    public int updateSwitch(SwRouterConfigModel model);

    /**
     * 查询开关列表
     * @return
     */
    public List<SwRouterConfigModel> querySwitch();
    
}
