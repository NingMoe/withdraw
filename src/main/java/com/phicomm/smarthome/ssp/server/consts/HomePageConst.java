package com.phicomm.smarthome.ssp.server.consts;

/**
 * Created by zhengwang.li on 2018/4/17.
 */
public interface HomePageConst {

    /**
     * 官网管理模块
     */
    interface HomePageMgr {

        String IMG_BASE_PATH = "/images/sharedwifi-backend";

        /** 共享wifi官网管理首页banner的PC客户端图片相对地址 */
        String HOME_PAGE_BANNER_PC_IMG_BASE_PATH = IMG_BASE_PATH + "/home_page_banner/pc";

        /** 共享wifi官网管理首页banner移动客户端图片相对地址 */
        String HOME_PAGE_BANNER_MOBILE_IMG_BASE_PATH = IMG_BASE_PATH + "/home_page_banner/mobile";

        /** 共享wifi官网管理首页卡片PC客户端图片相对地址 */
        String HOME_PAGE_CARD_PC_IMG_BASE_PATH = IMG_BASE_PATH + "/home_page_card/pc";

        /** 共享wifi官网管理首页卡片移动客户端图片相对地址 */
        String HOME_PAGE_CARD_MOBILE_IMG_BASE_PATH = IMG_BASE_PATH + "/home_page_card/mobile";

        /** 共享wifi官网管理首页介绍PC客户端图片相对地址 */
        String HOME_PAGE_INTRODUCE_PC_IMG_BASE_PATH = IMG_BASE_PATH + "/home_page_introduce/pc";

        /** 共享wifi官网管理首页介绍移动客户端图片相对地址 */
        String HOME_PAGE_INTRODUCE_MOBILE_IMG_BASE_PATH = IMG_BASE_PATH + "/home_page_introduce/mobile";

        /** 无跳转动作为1 */
        int SKIP_NO_ACTION = 1;

        /** 跳转到图片为2 */
        int SKIP_ACTION = 2;

        /**
         * 首页banner数据库表status状态 status为0时为添加，-1为发布删除
         */
        int ADD_STATUS_VALUE = 0;

        int DELTE_PUBLIC_STATUS_VALUE = -1;

        /** 无跳转动作为1 */
        int LEFT_CARD = 1;

        /** 跳转到图片为2 */
        int RIGHT_CARD = 2;
    }
}
