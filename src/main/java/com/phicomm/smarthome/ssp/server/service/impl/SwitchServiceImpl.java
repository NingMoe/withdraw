package com.phicomm.smarthome.ssp.server.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.phicomm.smarthome.ssp.server.dao.SwitchMapper;
import com.phicomm.smarthome.ssp.server.model.SwRouterConfigModel;
import com.phicomm.smarthome.ssp.server.service.SwitchService;

/**
 * Created by xiangrong.ke on 2017/7/7.
 */
@Service
public class SwitchServiceImpl implements SwitchService {

    @Autowired
    SwitchMapper switchMapper;

    @Override
    public int updateSwitch(SwRouterConfigModel model) {
        int affectRows = switchMapper.updateSwitch(model);
        return affectRows;
    }

    public List<SwRouterConfigModel> querySwitch() {
        return switchMapper.querySwitch();
    }

}
