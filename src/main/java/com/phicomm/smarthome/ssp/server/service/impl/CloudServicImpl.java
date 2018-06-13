package com.phicomm.smarthome.ssp.server.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.phicomm.smarthome.cache.Cache;
import com.phicomm.smarthome.ssp.server.consts.Const.ManagementBillVar;
import com.phicomm.smarthome.ssp.server.service.CloudService;
import com.phicomm.smarthome.ssp.server.util.HttpsRequest;
import com.phicomm.smarthome.util.StringUtil;

/**
 * @author fujiang.mao
 *
 */
@Service
public class CloudServicImpl implements CloudService {

    public static final Logger LOGGER = LogManager.getLogger(CloudServicImpl.class);

    @Value("${CLOUD_DOMAIN_NAME}")
    private String cloudDomainName;

    @Value("${CLOUD_ROUTER_URL}")
    private String cloudRouterUrl;

    @Value("${CLOUD_ROUTER_DEVICE_TYPE}")
    private String cloudRouterDeviceType;

    @Autowired
    private Cache redisCache;

    @Override
    public String getUserMacByUid(long uid) {
        try {
            String key = ManagementBillVar.USER_DEVICE_INFO_DEVICE_MAC;
            String userMac = (String) redisCache.getHashValue(key, uid + "");
            if (StringUtil.isNullOrEmpty(userMac)) {
                Map<String, Object> data = queryCloudUserDeviceInfoByUid(uid + "");
                Integer retCode = (Integer) data.get("ret_code");
                if (retCode == 200) {
                    userMac = (String) data.get("devMac");
                    redisCache.putHashValue(key, uid + "", userMac, ManagementBillVar.USER_DEVICE_INFO_EXPIRE_TIME);
                } else if (retCode == 113) {
                    // account not bind any router
                    redisCache.putHashValue(key, uid + "",
                            ManagementBillVar.USER_ACCOUNT_NOT_BIND_ANY_ROUTER_STR,
                            ManagementBillVar.USER_DEVICE_ACCOUNT_NOT_BIND_ANY_ROUTER_EXPIRE_TIME);
                }
            }
            return userMac;
        } catch (Exception e) {
            LOGGER.error("getUserMacsByUid error " + e.getMessage());
        }
        return null;
    }

    public Map<String, Object> queryCloudUserDeviceInfoByUid(String uid) {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("ret_code", 200);
        data.put("ret_msg", "成功");
        String httpEntity = "";
        try {
            String getUrl = cloudDomainName + cloudRouterUrl + "?devType=" + cloudRouterDeviceType + "&uid=" + uid;
            LOGGER.debug("queryCloudUserDeviceInfoByUid getUrl---------->" + getUrl);
            HttpsRequest httpsRequest = new HttpsRequest();
            httpEntity = httpsRequest.sendGet(getUrl);
            LOGGER.debug("queryCloudUserDeviceInfoByUid httpEntity---------->" + httpEntity);
            // 如果返回的Entity中不为空则将其转化为Json对象并取出响应的变量
            if (!StringUtil.isNullOrEmpty(httpEntity)) {
                JSONObject jsonObject = new JSONObject(httpEntity);
                String error = jsonObject.getString("error");
                Integer retCode = NumberUtils.toInt(error);
                // 只有error等于0时正常返回结果
                if (retCode == 0) {
                    org.json.JSONArray jsonArray = jsonObject.getJSONArray("data");
                    List<Object> list = jsonArray.toList();
                    if (list != null && list.size() > 0) {
                        Object object = list.get(0);
                        net.sf.json.JSONObject deviceJson = net.sf.json.JSONObject.fromObject(object);
                        data.put("devMac", deviceJson.get("devMac").toString());
                    } else {
                        data.put("ret_code", 3);
                        data.put("ret_msg", "data为空");
                        data.put("devMac", "");
                    }
                } else {
                    data.put("ret_code", retCode);
                    String retMsg = "";
                    // 云账号接口返回信息
                    switch (retCode) {
                    case 12:
                        retMsg = "参数错误";
                        break;
                    case 50:
                        retMsg = "服务器异常";
                        break;
                    case 113:
                        retMsg = "账户未绑定任何设备";
                        break;
                    default:
                        break;
                    }
                    data.put("ret_msg", retMsg);
                }
            } else {
                data.put("ret_code", 4);
                data.put("ret_msg", "获取信息失败");
                LOGGER.debug("queryCloudUserDeviceInfoByUid httpEntity " + httpEntity);
            }
        } catch (JSONException je) {
            LOGGER.error("JSONException error " + je.getMessage());
            LOGGER.error("queryCloudUserDeviceInfoByUid 获取信息失败");
            data.put("ret_code", 2);
            data.put("ret_msg", "查询失败：" + httpEntity);
        } catch (Exception e) {
            LOGGER.error("error " + e.getMessage());
            data.put("ret_code", 1);
            data.put("ret_msg", "查询异常");
        }
        return data;
    }

    public static void main(String[] args) {
        org.json.JSONArray jsonArray = new org.json.JSONArray(
                "[{'devMac':'FF:FF:FF:FF:FF:FF','devModel':'K2','online':'1'},{'devMac':'FF:FF:FF:FF:FF:GG','devModel':'K3','online':'0'}]");
        List<Object> list = jsonArray.toList();
        if (list != null && list.size() > 0) {
            for (Object model : list) {
                Object object = list.get(0);
                net.sf.json.JSONObject jsonObject = net.sf.json.JSONObject.fromObject(model);
                net.sf.json.JSONObject objectJs = net.sf.json.JSONObject.fromObject(object);
                System.out.println(jsonObject.toString());
                System.out.println(objectJs.toString());
                System.out.println("devMac--->" + jsonObject.get("devMac").toString());
            }
        }
    }

}
