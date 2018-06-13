package com.phicomm.smarthome.ssp.server.service.impl;

import com.phicomm.smarthome.cache.Cache;
import com.phicomm.smarthome.consts.SharedwifiConst;
import com.phicomm.smarthome.model.cache.PreShutdownModel;
import com.phicomm.smarthome.ssp.server.dao.PreShutdownMapper;
import com.phicomm.smarthome.ssp.server.model.dao.ParamSettingDaoModel;
import com.phicomm.smarthome.ssp.server.service.PreShutdownSettingService;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import org.apache.logging.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by xiangrong.ke on 2017/8/17.
 */
@Service
public class PreShutdownSettingServiceImpl implements PreShutdownSettingService {

    private static final Logger LOGGER = LogManager.getLogger(PreShutdownSettingServiceImpl.class);

    @Autowired
    private PreShutdownMapper mapper;

    @Autowired
    private Cache cache;

    @Override
    @Transactional
    public int updateSwitch(PreShutdownModel model) {
        LOGGER.info("updateSwitch,model[{}][{}][{}]", model.getPreShutdownTime(), model.getPreNoticeTime(),
                model.getStatus());
        try {
            ParamSettingDaoModel paramModel = new ParamSettingDaoModel();

            int affectRows = 0;
            int ret = 0;
            paramModel.setBusid(SharedwifiConst.ParamSetting.BUS_ID_SHAREDWIFI);
            paramModel.setModule(SharedwifiConst.ParamSetting.MODULE_PRE_SHUTDOWN_SETTING);
            paramModel.setFieldParam(SharedwifiConst.ParamSetting.FIELD_PARAM_PRE_SHUTDOWN_TIME);
            paramModel.setValue(model.getPreShutdownTime() + "");
            ret = mapper.updateSwitch(paramModel);

            affectRows++;

            paramModel.setBusid(SharedwifiConst.ParamSetting.BUS_ID_SHAREDWIFI);
            paramModel.setModule(SharedwifiConst.ParamSetting.MODULE_PRE_SHUTDOWN_SETTING);
            paramModel.setFieldParam(SharedwifiConst.ParamSetting.FIELD_PARAM_PRE_NOTICE_TIME);
            paramModel.setValue(model.getPreNoticeTime() + "");
            ret = mapper.updateSwitch(paramModel);

            affectRows++;

            paramModel.setBusid(SharedwifiConst.ParamSetting.BUS_ID_SHAREDWIFI);
            paramModel.setModule(SharedwifiConst.ParamSetting.MODULE_PRE_SHUTDOWN_SETTING);
            paramModel.setFieldParam(SharedwifiConst.ParamSetting.FIELD_PARAM_STATUS);
            paramModel.setValue(model.getStatus() + "");
            ret = mapper.updateSwitch(paramModel);

            affectRows++;

            // 成功更新了3个配置项
            if (affectRows == 3) {
                // 更新缓存cache,1000_pre_shutdown_setting作为key
                String key = SharedwifiConst.ParamSetting.BUS_ID_SHAREDWIFI + "_"
                        + SharedwifiConst.ParamSetting.MODULE_PRE_SHUTDOWN_SETTING;
                // 设0表示永不过期
                cache.put(key, model, 0);
            }
            return affectRows;
        } catch (Exception e) {
            LOGGER.info("error occur" + e.getMessage());
        }
        return 0;
    }

    public PreShutdownModel querySwitch() {
        LOGGER.info("querySwitch");
        // 先查cache
        String key = SharedwifiConst.ParamSetting.PRE_SHUTDOWN_SETTING_CACHE_KEY;
        PreShutdownModel model = (PreShutdownModel) cache.get(key);
        if (model == null) {
            model = new PreShutdownModel();
            List<ParamSettingDaoModel> params = mapper.querySwitch();
            for (ParamSettingDaoModel param : params) {
                if (param.getFieldParam() == SharedwifiConst.ParamSetting.FIELD_PARAM_PRE_SHUTDOWN_TIME) {
                    model.setPreShutdownTime(Integer.parseInt(param.getValue()));
                }
                if (param.getFieldParam() == SharedwifiConst.ParamSetting.FIELD_PARAM_PRE_NOTICE_TIME) {
                    model.setPreNoticeTime(Integer.parseInt(param.getValue()));
                }
                if (param.getFieldParam() == SharedwifiConst.ParamSetting.FIELD_PARAM_PRE_NOTICE_TIME) {
                    model.setStatus(Integer.parseInt(param.getValue()));
                }
            }
            // 设0表示永不过期
            cache.put(key, model, 0);
        }
        return model;
    }
}
