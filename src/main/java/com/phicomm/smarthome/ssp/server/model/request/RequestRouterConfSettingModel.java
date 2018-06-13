package com.phicomm.smarthome.ssp.server.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.phicomm.smarthome.model.cache.BaseReqPageModel;

/**
 * @author xiangrong.ke
 * @date 2018-01-17
 * @version V1.0
 * @Description sw_user实体类
 */
public class RequestRouterConfSettingModel {

    @JsonProperty("hw_id")
    private String hwId;//硬件id

    @JsonProperty("product_id")
    private String productId;// 产品id

    @JsonProperty("model")
    private String model;// 型号

    @JsonProperty("rom_version")
    private String romVersion;

    @JsonProperty("sign")
    private String sign;

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getRomVersion() {
        return romVersion;
    }

    public void setRomVersion(String romVersion) {
        this.romVersion = romVersion;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getHwId() {
        return hwId;
    }

    public void setHwId(String hwId) {
        this.hwId = hwId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }
}
