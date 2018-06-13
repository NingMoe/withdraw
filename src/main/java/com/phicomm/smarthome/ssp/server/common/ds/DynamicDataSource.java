package com.phicomm.smarthome.ssp.server.common.ds;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * 动态数据源切换
 * 
 * @author fujiang.mao
 */
public class DynamicDataSource extends AbstractRoutingDataSource {

    @Override
    protected Object determineCurrentLookupKey() {
        return getDataSourceType();
    }

    private static final ThreadLocal<String> CONTEXTHOLDER = new ThreadLocal<String>();

    public static void setDataSourceType(String dataSourceType) {
        CONTEXTHOLDER.set(dataSourceType);
    }

    public static String getDataSourceType() {
        return CONTEXTHOLDER.get();
    }

    public static void clearDataSourceType() {
        CONTEXTHOLDER.remove();
    }

}