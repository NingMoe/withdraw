package com.phicomm.smarthome.ssp.server.consts;

import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

/**
 * 常量定义
 * 
 * @author wenhua.tang
 *
 */
@PropertySources(@PropertySource(value = "classpath:server.properties", ignoreResourceNotFound = true))
public interface Const {

    String DEFAULT_CHARSET = "utf-8";

    /**
     * header头部
     */
    String AUTHORIZATION = "Authorization";

    String ORDER_PRE = "sw_";
    String STRING_EMPTY = "";
    String STRING_SHARED_WIFI_CODE = "SW";

    public interface ThirdParty {
        String PHICOMM_CLOUD_URL = "https://account.phicomm.com/";
        String PHICOMM_CLOUD_URL_ACCOUNT_DETAIL = PHICOMM_CLOUD_URL + "v1/accountDetail";
    }

    public interface ResponseStatus {

        /** 服务正常返回数据 */
        int STATUS_OK = 200;
        String STATUS_OK_STR = "服务正常返回数据";
        String STATUS_OK_ENABLE = "服务正常，但功能未启用";
        /** 错误码定义， 10000以上定义为错误码 */
        int STATUS_COMMON_FAILED = 10001;
        String STATUS_COMMON_FAILED_STR = "发生未知错误";

        /** 服务无异常，但用户名密码不对 */
        int STATUS_COMMON_NOT_LOGIN = 10002;
        String STATUS_COMMON_NOT_LOGIN_STR = "未登录用户，请重新登录";

        /** 服务无异常,但请求参数异常 */
        int STATUS_COMMON_NULL = 10003;
        String STATUS_COMMON_NULL_STR = "服务正常,但请求参数异常";

        /** 服务无异常，但数据插入失败 */
        int STATUS_COMMON_INSERT_FAILED = 10004;
        String STATUS_COMMON_INSERT_FAILED_STR = "服务正常,但数据插入失败";

        /** 服务无异常，但数据更新失败 */
        int STATUS_COMMON_UPDATE_FAILED = 10005;
        String STATUS_COMMON_UPDATE_FAILED_STR = "服务正常,但数据更新失败";

        /** 服务无异常，但用户名密码不对 */
        int STATUS_COMMON_INVALID_USER = 10006;
        String STATUS_COMMON_INVALID_USER_STR = "服务正常,但用户名不对";

        /** 服务无异常，但用户名密码不对 */
        int STATUS_COMMON_INVALID_PASSWORD = 10007;
        String STATUS_COMMON_INVALID_PASSWORD_STR = "服务正常,但密码不对";

        int STATUS_COMMON_DATE_SCOPE_ERROR = 10008;
        String STATUS_COMMON_DATE_SCOPE_ERROR_STR = "日期范围不合法";

        int STATUS_SIGN_ERR = 10014;//签名错误

        int STATUS_FW_VER_EXIST = 10015; //对应型号版本已存在
        
        /** 服务无异常，但数据查询失败 */
        int STATUS_COMMON_QUERY_FAILED = 10016;
        String STATUS_COMMON_QUERY_FAILED_STR = "服务正常,但数据查询失败";
        
        /** banner最多添加5张轮播图 */
        int STATUS_COUNT_BANNER_EXCEED_ERROR = 10017;
        String STATUS_COUNT_BANNER_EXCEED_ERROR_STR = "banner最多添加5张轮播图";

        /** banner最多添加5张轮播图 */
        int STATUS_COUNT_HOME_PAGE_INTRODUCE_EXCEED_ERROR = 10018;
        String STATUS_COUNT_HOME_PAGE_INTRODUCE_EXCEED_ERROR_STR = "首页介绍已经存在";
        
        /** 首页卡片最多添加2条 */
        int STATUS_COUNT_HOME_PAGE_CAR_EXCEED_ERROR = 10019;
        String STATUS_COUNT_HOME_PAGE_CAR_EXCEED_ERROR_STR = "首页卡片已经存在左右卡片，不能再添加";
    }

    public interface FtpFileStatus {

        int STATUS_FILE_OK = 0;
        String STATUS_FILE_OK_STR = "文件操作成功";

        int STATUS_FILE_UPLOAD_ERROR = 10001;
        String STATUS_FILE_UPLOAD_ERROR_STR = "文件上传失败";

    }

    /**
     * SERVICE BUSINESS INTERFACES DEFINITION
     ************************************************/

    /** 微信账单下载状态 */
    public interface WxBillStatus {

        /** 账单下载成功 */
        int DOWNLOAD_BILL_SUCC = 0;
        String DOWNLOAD_BILL_SUCC_STR = "账单下载成功";

        int DOWNLOAD_BILL_FAIL = 10001;
        String DOWNLOAD_BILL_FAIL_STR = "账单下载失败";

        int SIGN_VALIDE_FAIL = 10002;
        String SIGN_VALIDE_FAIL_STR = "【下载账单失败】支付请求API返回的数据签名验证失败，有可能数据被篡改了";

    }

    /** 自己系统账单状态 */
    public interface GuestOrderBillStatus {

        /** 账单加载成功 */
        int LOAD_BILL_SUCC = 0;
        String LOAD_BILL_SUCC_STR = "账单加载成功";

        int LOAD_BILL_FAIL = 10001;
        String LOAD_BILL_FAIL_STR = "账单加载失败";

    }

    /** 订单整体状态 */
    public interface OrderStatusInterfaces {

        /** 订单状态_未支付_Int_1 */
        int ORDER_STATUS_UNPAID = 1;

        /** 订单状态_消费中_Int_2 */
        int ORDER_STATUS_PAIDINCOMMON = 2;

        /** 订单状态_已过期_Int_3 */
        int ORDER_STATUS_EXPIRED = 3;
    }

    // 功能开关
    public interface FunctionSwitch {
        // 是否停用打赏功能 0 开启 1禁止
        int SWITCH_OPEN_SHAREWIFI = 0;
        int SWITCH_CLOSE_SHAREWIFI = 1;
    }

    // 对账
    public interface BalanceAccount {
        int STATUS_NORMAL = 0;
        int STATUS_ABNORMAL = 1;
    }

    // 统计需求自定义变量
    public interface StatAnalyzeDataVar {

        String ALL_SELECT = "-1";
        String ALL_SELECT_STR = "全部";

        String LINK_DURATION = "1";
        String LINK_DURATION_STR = "连接时长";
//        String LINK_DURATION_TABLE = "tbl_stat_duration_link";
        String LINK_DURATION_TABLE = "stat_conn_tr";
        String LINK_DURATION_TABLE_INFO = "config_conn_tr";
        String LINK_DURATION_COLUMN = "link_time";

        String USE_DURATION = "2";
        String USE_DURATION_STR = "使用时长";
//        String USE_DURATION_TABLE = "tbl_stat_duration_use";
        String USE_DURATION_TABLE = "stat_use_tr";
        String USE_DURATION_TABLE_INFO = "config_use_tr";
        String USE_DURATION_COLUMN = "user_time";
        
        String SLT_INDEX_SETWIFI = "2";
        String SLT_INDEX_SETWIFI_STR = "设置共享wifi";
        
        String SLT_INDEX_NOTECASE = "4";
        String SLT_INDEX_NOTECASE_STR = "钱包";
        String SLT_INDEX_NOTECASE_COLUMN_PV = "pv_income";
        String SLT_INDEX_NOTECASE_COLUMN_UV = "uv_income";
        
        String SLT_INDEX_CHANGE_DETAIL = "5";
        String SLT_INDEX_CHANGE_DETAIL_STR = "零钱明细";
        String SLT_INDEX_CHANGE_DETAIL_COLUMN_PV = "pv_income_dtl";
        String SLT_INDEX_CHANGE_DETAIL_COLUMN_UV = "uv_income_dtl";
        
        String SLT_INDEX_WITHDRAW = "6";
        String SLT_INDEX_WITHDRAW_STR = "提现";
        String SLT_INDEX_WITHDRAW_COLUMN_PV = "pv_withdraw";
        String SLT_INDEX_WITHDRAW_COLUMN_UV = "uv_withdraw";
        
        int INDEX_CHILD_NEW_USER = 1;
        int INDEX_CHILD_PV_USER = 2;
        String REWARD_COLUMN_PV = "pv_total";
        int INDEX_CHILD_UV_USER = 3;
        String REWARD_COLUMN_UV = "uv_total";
        
        int TIME_VEIDOO_DAY = 1;
        int TIME_VEIDOO_WEEK = 2;
        int TIME_VEIDOO_MONTH = 3;
        
        int SEARCH_LENGTH_DAYS = 90;
        int SEARCH_LENGTH_WEEK = 12;
        int SEARCH_LENGTH_MONTH = 12;

    }
    
    public interface ManagementBillVar {
        
        String USER_DEVICE_INFO_DEVICE_MAC = "USER_DEVICE_INFO_DEVICE_MAC";
        
        String USER_ACCOUNT_NOT_BIND_ANY_ROUTER_STR = "-1";
        
        String USER_ACCOUNT_NOT_BIND_ANY_ROUTER_EXPIRE_TIME_DESC = "未启用";
        
        // 过期时间1天
        int USER_DEVICE_INFO_EXPIRE_TIME = 1 * 24 * 60 * 60;
        
        // 过期时间1分钟
        int USER_DEVICE_ACCOUNT_NOT_BIND_ANY_ROUTER_EXPIRE_TIME = 1 * 60;
        
    }

    /**
     * @author fujiang.mao
     *
     */
    public interface ExcelMgrVariate {

        int SHEET_MAX_ROW = 50000;

    }
}
