package com.phicomm.smarthome.ssp.server.service.impl;

import com.phicomm.smarthome.cache.Cache;
import com.phicomm.smarthome.consts.SharedwifiConst;
import com.phicomm.smarthome.model.cache.PreShutdownModel;
import com.phicomm.smarthome.ssp.server.dao.SwitchMapper;
import com.phicomm.smarthome.ssp.server.model.SwRouterConfigModel;
import com.phicomm.smarthome.ssp.server.service.PreShutdownSettingService;
import com.phicomm.smarthome.ssp.server.util.CommonUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by kexiangrong on 2017/8/27.
 */
@Component
public class PreShutdownSettingScheduler {

    private static final Logger LOGGER = LogManager.getLogger(PreShutdownSettingScheduler.class);

    @Autowired
    private PreShutdownSettingService preShutdownSettingService;

    @Autowired
    private SwitchMapper mapper;

    @Autowired
    private Cache cache;

    @Scheduled(cron = "0 0/1 * * * ?") // 每分钟执行一次
    public void statusCheck() {
        LOGGER.info("每分钟执行一次。开始……");

        PreShutdownModel model = preShutdownSettingService.querySwitch();

        int preShutdownTime = model.getPreShutdownTime();
        int now = CommonUtils.getNowTimeToStamp();
        int nowMin = now / 60 * 60;
        int preShutdownTimeMin = preShutdownTime / 60 * 60;
        if (nowMin == preShutdownTimeMin) {
            LOGGER.info("now[{}],preShutdownTime[{}],min[{}]", now, preShutdownTime, nowMin);
            // 先找出最大的配置版本号，往上+0.1,例如sw0.2改为sw0.3
            String newConfigVersion = "0.0";
            List<SwRouterConfigModel> routerConfigModels = mapper.querySwitch();
            if (routerConfigModels == null || routerConfigModels.isEmpty()) {
                LOGGER.info("router config table is empty,nothing to update");
                return;
            } else {
                SwRouterConfigModel configModel = routerConfigModels.get(0);
                String curConfigModel = configModel.getConfigVersion();
                String[] models = curConfigModel.split("\\.");
                // 小数点前面的版本号
                String prefixVer = models[0];
                // 小数点后的版本号
                String dotVer = models[models.length - 1];
                //
                if (dotVer == null || dotVer.isEmpty()) {
                    LOGGER.info("dot ver is null or empty");
                    return;
                }
                int newIntDotVer = Integer.parseInt(dotVer) + 1;
                newConfigVersion = prefixVer + "." + newIntDotVer;
                LOGGER.info("newConfigVersion[{}]", newConfigVersion);
            }
            // 更改数据库数据
            mapper.updateSwitchAll(model.getStatus(), newConfigVersion);
            // 更新缓存
            cache.put(SharedwifiConst.ParamSetting.SHAREDWIFI_FUNCTION_STATUS_CACHE_KEY, model.getStatus(), 0);
            LOGGER.info("SHAREDWIFI_FUNCTION_STATUS_CACHE_KEY[{}]",
                    cache.get(SharedwifiConst.ParamSetting.SHAREDWIFI_FUNCTION_STATUS_CACHE_KEY));
        } else {
            // donothing
        }
        LOGGER.info("每分钟执行一次。结束。");
    }

}
