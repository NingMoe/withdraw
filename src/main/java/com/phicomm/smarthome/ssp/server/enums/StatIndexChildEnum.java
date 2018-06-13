package com.phicomm.smarthome.ssp.server.enums;

/**
 * @author fujiang.mao
 *
 */
public enum StatIndexChildEnum {

    NEWUSER {
        public String getName() {
            return "新增用户";
        }
    },
    PV {
        public String getName() {
            return "PV";
        }
    },
    UV {
        public String getName() {
            return "UV";
        }
    };

    public abstract String getName();

}
