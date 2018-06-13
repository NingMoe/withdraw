package com.phicomm.smarthome.ssp.server.dao;

import com.phicomm.smarthome.ssp.server.model.response.ResponseStatTimePeriodModel;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface StatAnalyzeTimePeriodRewardMapper {



    @Select(
            " SELECT " +
                    " rr.uid uid" +
                    " ,phi_nickname nickname" +
                    " ,rr.income income " +
                    " ,dd.income totalIncome" +
                    " ,rank " +
                    " FROM ( " +
                    " SELECT " +
                    " uid " +
                    " ,income " +
                    " ,@cur_rank:=if(@prev_income=ff.income,@cur_rank,@inc_rank) as rank " +
                    " ,@prev_income:=ff.income " +
                    " ,@inc_rank:=@inc_rank + 1 " +
                    " FROM ( " +
                    " SELECT uid " +
                    " ,sum(CAST(today_income as decimal(9,2))) as income " +
                    " FROM sw_user_income " +
                    " where create_time > #{dayBeginTs} and create_time < #{dayEndTs}" +
                    " group by uid " +
                    " ORDER BY income DESC " +
                    " ) ff " +
                    " , ( " +
                    " SELECT " +
                    " @cur_rank := 0 " +
                    " ,@inc_rank := 1 " +
                    " ,@prev_income := NULL " +
                    " ) tt " +
                    " LIMIT #{userLimit} " +
                    " ) rr " +
                    " , ( " +
                    " SELECT " +
                    " uid " +
                    " ,sum(CAST(today_income as decimal(9,2))) as income " +
                    " FROM sw_user_income " +
                    " where create_time < #{nowTs} " +
                    " group by uid " +
                    " ) dd " +
                    " , ( " +
                    " SELECT " +
                    " uid " +
                    " ,phi_nickname "+
                    " FROM sw_user_router " +
                    " ) nn " +
                    " WHERE nn.uid=rr.uid AND rr.uid=dd.uid " +
                    " ORDER BY income DESC "

            /*SELECT  rr.uid uid,phi_nickname nickname, rr.income income,dd.income total_income ,rank FROM ( SELECT uid ,income ,
            @cur_rank:=if(@prev_income=ff.income,@cur_rank,@inc_rank) as rank ,@prev_income:=ff.income ,@inc_rank:=@inc_rank + 1
            FROM ( SELECT uid ,sum(CAST(today_income as decimal(9,2))) as income FROM sw_user_income
            where create_time > 1525104000 and create_time < 1527696000 group by uid ORDER BY income DESC ) ff ,
            ( SELECT @cur_rank := 0 ,@inc_rank := 1 ,@prev_income := NULL ) tt LIMIT 200 ) rr ,( SELECT uid ,
            sum(CAST(today_income as decimal(9,2))) as income FROM sw_user_income where create_time < 1527696000 group by uid ) dd,
            (SELECT uid, phi_nickname FROM sw_user_router ) nn WHERE rr.uid=nn.uid AND rr.uid=dd.uid ORDER BY income DESC
            */
    )

    /**
     *获取任意时段内用户收益排名
     * @param dayBeginTs
     * @param dayEndTs
     * @param nowTs
     * @param userLimit
     * @return
     */
    List<ResponseStatTimePeriodModel> rankByTimePeriod(@Param("dayBeginTs") long dayBeginTs, @Param("dayEndTs") long dayEndTs, @Param("nowTs") long nowTs,
                                                       @Param("userLimit") int userLimit);

}
