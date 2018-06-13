package com.phicomm.smarthome.ssp.server.enums;

/**
 * @author fujiang.mao
 *
 */
public enum StatRewardConnDeviceEnum {

    ANDROID {
        public String getName() {
            return "安卓手机";
        }
    },
    IPHONE {
        public String getName() {
            return "苹果手机";
        }
    },
    MAC {
        public String getName() {
            return "苹果电脑";
        }
    },
    WINDOWS {
        public String getName() {
            return "Windows电脑";
        }
    },
    OTHER {
        public String getName() {
            return "其他";
        }
    };

    public abstract String getName();

}
