package com.phicomm.smarthome.ssp.server.service.impl;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.phicomm.smarthome.cache.Cache;
import com.phicomm.smarthome.consts.SharedwifiConst;
import com.phicomm.smarthome.model.cache.PreFallRatioModel;
import com.phicomm.smarthome.ssp.server.dao.HtDeployMapper;
import com.phicomm.smarthome.ssp.server.service.HtDeployService;
import com.phicomm.smarthome.util.OptDateUtil;
import com.phicomm.smarthome.util.StringUtil;

import net.sf.json.JSONObject;

/**
 * @author fujiang.mao
 *
 */
@Service
public class HtDeployServicImpl implements HtDeployService {

    public static final Logger LOGGER = LogManager.getLogger(HtDeployServicImpl.class);

    @Autowired
    private HtDeployMapper htDeployMapper;

    @Autowired
    private Cache cache;

    @Override
    public int uploadFallRatio(PreFallRatioModel model) {
        Long effectiveTime = OptDateUtil.getLTimeByStr(model.getFallRatioEftTime());
        long nowTimeLong = OptDateUtil.getNowTimeLong();
        String key = SharedwifiConst.ParamSetting.APP_DRAW_SETTING_CACHE_KEY;
        int busId = SharedwifiConst.ParamSetting.BUS_ID_SHAREDWIFI;
        String module = SharedwifiConst.ParamSetting.APP_DRAW_SETTING;
        String fallRatio = SharedwifiConst.ParamSetting.FALL_RATIO;
        String fallRatioExecuted = SharedwifiConst.ParamSetting.FALL_RATIO_EXECUTED;
        model.setFallRatioEftTime(effectiveTime + "");
        String fallRatioEftTimeParam = htDeployMapper.getServiceParam(busId, module, fallRatioExecuted);
        model.setFallRatioExecuted(NumberUtils.toDouble(fallRatioEftTimeParam));
        if (effectiveTime <= nowTimeLong) {
            htDeployMapper.uploadServiceParam(busId, module, fallRatioExecuted, model.getFallRatio() + "");
            htDeployMapper.uploadServiceParam(busId, module, fallRatio, "-1");
            model.setFallRatioExecuted(model.getFallRatio());
            model.setFallRatio(-1d);
        } else {
            // 设置将来要生效的分成比例
            String fallRatioEftTime = SharedwifiConst.ParamSetting.FALL_RATIO_EFT_TIME;
            htDeployMapper.uploadServiceParam(busId, module, fallRatio, model.getFallRatio() + "");
            htDeployMapper.uploadServiceParam(busId, module, fallRatioEftTime, effectiveTime + "");
        }
        JSONObject json = JSONObject.fromObject(model);
        System.out.println("uploadFallRatio model--->" + json.toString());
        // 更新缓存
        cache.put(key, model, 0);
        return 0;
    }

    @Override
    public PreFallRatioModel queryFallRatio() {
        // 先查缓存
        String key = SharedwifiConst.ParamSetting.APP_DRAW_SETTING_CACHE_KEY;
        String fallRatio = SharedwifiConst.ParamSetting.FALL_RATIO;
        String fallRatioExecuted = SharedwifiConst.ParamSetting.FALL_RATIO_EXECUTED;
        String fallRatioEftTime = SharedwifiConst.ParamSetting.FALL_RATIO_EFT_TIME;
        PreFallRatioModel preFallRatioModel = (PreFallRatioModel) cache.get(key);
        if (preFallRatioModel == null) {
            // 缓存没有则从数据库查询
            int busId = SharedwifiConst.ParamSetting.BUS_ID_SHAREDWIFI;
            String module = SharedwifiConst.ParamSetting.APP_DRAW_SETTING;
            String fallRatioParam = htDeployMapper.getServiceParam(busId, module, fallRatio);
            String fallRatioExcutedParam = htDeployMapper.getServiceParam(busId, module, fallRatioExecuted);
            String fallRatioEftTimeParam = htDeployMapper.getServiceParam(busId, module, fallRatioEftTime);
            if (!StringUtil.isNullOrEmpty(fallRatioParam) && !StringUtil.isNullOrEmpty(fallRatioExcutedParam)
                    && !StringUtil.isNullOrEmpty(fallRatioEftTimeParam)) {
                // 写入缓存
                try {
                    preFallRatioModel = new PreFallRatioModel();
                    preFallRatioModel.setFallRatio(NumberUtils.toDouble(fallRatioParam));
                    preFallRatioModel.setFallRatioExecuted(NumberUtils.toDouble(fallRatioExcutedParam));
                    preFallRatioModel.setFallRatioEftTime(fallRatioEftTimeParam);
                    cache.put(key, preFallRatioModel, 0);
                } catch (Exception e) {
                    LOGGER.info("queryFallRatio update cache error !");
                    LOGGER.info(e);
                }
            } else {
                LOGGER.info("queryFallRatio DB is null !");
            }
        }
        if (preFallRatioModel.getFallRatio() == -1) {
            preFallRatioModel.setFallRatio(preFallRatioModel.getFallRatioExecuted());
        }
        preFallRatioModel.setFallRatioEftTimeStr(
                OptDateUtil.stampToDate(NumberUtils.toLong(preFallRatioModel.getFallRatioEftTime())));
        JSONObject json = JSONObject.fromObject(preFallRatioModel);
        LOGGER.info("preFallRatioModel--->" + json.toString());
        return preFallRatioModel;
    }

    @Override
    public void fallRatioStatusCheck() {
        String key = SharedwifiConst.ParamSetting.APP_DRAW_SETTING_CACHE_KEY;
        String fallRatio = SharedwifiConst.ParamSetting.FALL_RATIO;
        String fallRatioEftTime = SharedwifiConst.ParamSetting.FALL_RATIO_EFT_TIME;
        int busId = SharedwifiConst.ParamSetting.BUS_ID_SHAREDWIFI;
        String module = SharedwifiConst.ParamSetting.APP_DRAW_SETTING;
        String fallRatioParam = htDeployMapper.getServiceParam(busId, module, fallRatio);
        String fallRatioEftTimeParam = htDeployMapper.getServiceParam(busId, module, fallRatioEftTime);
        // 判断当前生效时间是否已到
        long nowTimeLong = OptDateUtil.getNowTimeLong();
        if (!"-1".equals(fallRatioParam) && NumberUtils.toLong(fallRatioEftTimeParam) <= nowTimeLong) {
            PreFallRatioModel model = new PreFallRatioModel();
            String fallRatioExecuted = SharedwifiConst.ParamSetting.FALL_RATIO_EXECUTED;
            htDeployMapper.uploadServiceParam(busId, module, fallRatioExecuted, fallRatioParam);
            htDeployMapper.uploadServiceParam(busId, module, fallRatio, "-1");
            model.setFallRatio(-1d);
            model.setFallRatioExecuted(NumberUtils.toDouble(fallRatioParam));
            model.setFallRatioEftTime(fallRatioEftTimeParam);
            // 更新缓存
            cache.put(key, model, 0);
            JSONObject json = JSONObject.fromObject(model);
            LOGGER.info("fallRatioStatusCheck model--->" + json.toString());
        }
    }

}
