package com.phicomm.smarthome.ssp.server.service.impl;

import com.phicomm.smarthome.ssp.server.common.ds.TargetDataSource;
import com.phicomm.smarthome.ssp.server.controller.statanls.StatAnalyzeTimePeriodRewardController;
import com.phicomm.smarthome.ssp.server.dao.StatAnalyzeTimePeriodRewardMapper;
import com.phicomm.smarthome.ssp.server.model.request.RequestStatTimePeriodModel;
import com.phicomm.smarthome.ssp.server.model.response.ResponseStatTimePeriodListModel;
import com.phicomm.smarthome.ssp.server.model.response.ResponseStatTimePeriodModel;
import com.phicomm.smarthome.ssp.server.service.StatAnalyzeTimePeriodRewardService;
import com.phicomm.smarthome.ssp.server.util.AccountDateUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


@Service
public class StatAnalyzeTimePeriodRewardServiceImpl implements StatAnalyzeTimePeriodRewardService {

    private static final Logger LOGGER = LogManager.getLogger(StatAnalyzeTimePeriodRewardController.class);

    @Autowired
    private StatAnalyzeTimePeriodRewardMapper statAnalyzeTimePeriodRewardMapper;

    @Override
    @TargetDataSource("ds3")
    public ResponseStatTimePeriodListModel getAnalyzeStatTimePeriodRewardList(RequestStatTimePeriodModel model, boolean isPage) {
        ResponseStatTimePeriodListModel listModel = new ResponseStatTimePeriodListModel();
        List<ResponseStatTimePeriodModel> models = new ArrayList<ResponseStatTimePeriodModel>();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String dayBegin = model.getSltStartDate();
        String dayEnd = model.getSltEndDate();
        long dayBeginTs = 0;
        long dayEndTs = 0;
        try {
            dayBeginTs = format.parse(dayBegin).getTime()/1000;
            dayEndTs = format.parse(dayEnd).getTime()/1000;
        }catch (ParseException e){
            LOGGER.error("Get the millisecond error " + e.getMessage());
        }

        //获得日期间隔天数
        long days = AccountDateUtil.countDay(dayBegin,dayEnd);

        //得到查询结果数量
        int userLimit = days <= 90 ? 100 : 200;

        long nowTs =  System.currentTimeMillis()/1000;
        LOGGER.info("rank_by_time_period,[{}],[{}],[{}],[{}]",dayBeginTs,dayEndTs,nowTs,userLimit);

        models = statAnalyzeTimePeriodRewardMapper.rankByTimePeriod(dayBeginTs, dayEndTs, nowTs, userLimit);
        
        LOGGER.info("size[{}]",models.size());
        for (ResponseStatTimePeriodModel responseStatTimePeriodModel:models) {
            responseStatTimePeriodModel.setDayBegin(dayBegin);
            responseStatTimePeriodModel.setDayEnd(dayEnd);
            responseStatTimePeriodModel.setPortrailUrl("");
        }
        listModel.setTimePeriodModelList(models);
        // 分页查询
        if (isPage) {
            int pageSize = model.getPageSize();
            int curPage = model.getCurPage();
            int start = (curPage - 1) * pageSize;
            int end = curPage * pageSize;
            int totalCount = models == null ? 0 : models.size();
            if (models != null && models.size() > 0) {
                int fromIndex = start > models.size() ? 0 : start;
                int toIndex = end > models.size() ? totalCount : end;
                listModel.setTimePeriodModelList(models.subList(fromIndex, toIndex));
            }
            listModel.setTotalCount(totalCount);
        } else {
            listModel.setTimePeriodModelList(models);
        }
        return listModel;
    }
}
