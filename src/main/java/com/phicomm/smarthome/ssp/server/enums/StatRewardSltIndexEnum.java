package com.phicomm.smarthome.ssp.server.enums;

/**
 * @author fujiang.mao
 *
 */
public enum StatRewardSltIndexEnum {

    REWARDPAGE {
        public String getName() {
            return "打赏页面";
        }
    },
    PAYPAGE {
        public String getName() {
            return "支付页面";
        }
    },
    CONNECTING {
        public String getName() {
            return "连接中";
        }
    },
    CONNSUCCPAGE {
        public String getName() {
            return "连接成功页面";
        }
    },
    PAYFAILD {
        public String getName() {
            return "支付失败";
        }
    };

    public abstract String getName();

}
