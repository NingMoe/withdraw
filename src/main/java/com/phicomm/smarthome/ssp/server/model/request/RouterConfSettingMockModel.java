package com.phicomm.smarthome.ssp.server.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.phicomm.smarthome.ssp.server.util.Signature;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by kexiangrong on 2017/8/15.
 */
public class RouterConfSettingMockModel {

    //硬件id
    private String hw_id;

    // 产品id
    private String product_id;

    // 型号
    private String model;
    // 固件版本
    private String rom_version;

    //sign签名，安全校验用
    private String sign;

    public RouterConfSettingMockModel(RequestRouterConfSettingModel model){
        setHw_id(model.getHwId());
        setProduct_id(model.getProductId());
        setModel(model.getModel());
        setRom_version(model.getRomVersion());

        String sign = Signature.getSign(toMap());
        setSign(sign);
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<String, Object>();
        Field[] fields = this.getClass().getDeclaredFields();
        for (Field field : fields) {
            Object obj;
            try {
                obj = field.get(this);
                if (obj != null) {
                    map.put(field.getName(), obj);
                }
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return map;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }


    public String getHw_id() {
        return hw_id;
    }

    public void setHw_id(String hw_id) {
        this.hw_id = hw_id;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getRom_version() {
        return rom_version;
    }

    public void setRom_version(String rom_version) {
        this.rom_version = rom_version;
    }
}
