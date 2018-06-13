package com.phicomm.smarthome.ssp.server.enums;

/**
 * @author fujiang.mao
 *
 */
public enum StatAppVeidooEnum {

    DAY {
        public String getName() {
            return "日";
        }
    },
    WEEK {
        public String getName() {
            return "周";
        }
    },
    MONTH {
        public String getName() {
            return "月";
        }
    };

    public abstract String getName();

}
