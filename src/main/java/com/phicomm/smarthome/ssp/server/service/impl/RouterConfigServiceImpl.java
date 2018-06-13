package com.phicomm.smarthome.ssp.server.service.impl;

import com.phicomm.smarthome.ssp.server.dao.RouterConfigMapper;
import com.phicomm.smarthome.ssp.server.dao.SwitchMapper;
import com.phicomm.smarthome.ssp.server.model.SwRouterConfigModel;
import com.phicomm.smarthome.ssp.server.model.dao.SwRouterConfigDaoModel;
import com.phicomm.smarthome.ssp.server.service.RouterConfigService;
import com.phicomm.smarthome.ssp.server.service.SwitchService;
import com.phicomm.smarthome.ssp.server.util.MyListUtils;
import com.phicomm.smarthome.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by xiangrong.ke on 2017/7/7.
 */
@Service
@PropertySources({ @PropertySource(value = "classpath:application.properties") })
public class RouterConfigServiceImpl implements RouterConfigService {

    @Autowired
    RouterConfigMapper routerConfigMapper;

    @Value("${SHAREDWIFI_ROUTER_CONFIG_PORTAL_URL}")
    private String swRouterConfPortalUrl;

    @Value("${SHAREDWIFI_ROUTER_CONFIG_CONF_VER}")
    private String swRouterConfConfigVersion;

    @Override
    public SwRouterConfigDaoModel queryRouterConfig(){
        List<SwRouterConfigDaoModel> lists = routerConfigMapper.queryRouterConfig();
        SwRouterConfigDaoModel model = new SwRouterConfigDaoModel();
        if(MyListUtils.isEmpty(lists)){
            //当数据库没有记录时，设置上初始参数
            model.setConfigVersion(swRouterConfConfigVersion);
            model.setPortalUrl(swRouterConfPortalUrl);
            model.setIsForbidden(0);
            model.setRouterId(0);
            model.setStatus(0);
        }
        else{
            //随便取一条的数据返回，主要是要config version数据要跟库里其他记录一致,status,portal url初始化一下
            model = lists.get(0);
            //status,portal url初始化一下
            model.setPortalUrl(swRouterConfPortalUrl);
            model.setStatus(0);
        }

        return model;
    }

    @Override
    public int updateRouterConfig(SwRouterConfigDaoModel model){
        int res = 0;
        if(StringUtil.isNullOrEmpty(model.getPortalUrl())) {
            model.setPortalUrl(swRouterConfPortalUrl);
        }
        res = routerConfigMapper.updateRouterConfig(model);
        return res;
    }
}
