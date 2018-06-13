package com.phicomm.smarthome.ssp.server.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ProtalUpImgModel {
	
    @JsonProperty("img_type")
    private Integer imgType;

    @JsonProperty("effect_time")
    private String effectTime;

}
