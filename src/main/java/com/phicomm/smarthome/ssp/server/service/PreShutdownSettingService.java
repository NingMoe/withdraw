package com.phicomm.smarthome.ssp.server.service;

import com.phicomm.smarthome.model.cache.PreShutdownModel;

/**
 * Created by xiangrong.ke on 2017/8/17.
 * 开关服务
 */
public interface PreShutdownSettingService {

    /**
     * 设置提前停用功能
     * @param model
     * @return
     */
    public int updateSwitch(PreShutdownModel model);

    /**
     * 查询当前设置
     * @return
     */
    public PreShutdownModel querySwitch();
}
