package com.phicomm.smarthome.ssp.server.repository;

import com.phicomm.smarthome.ssp.server.model.WithDrawsAlipayModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * PROJECT_NAME: smarthome-sharedwifi
 * PACKAGE_NAME: com.phicomm.smarthome.sharedwifi.repository
 * DESCRIPTION:
 * AUTHOR: xiangrong.ke
 * DATE: 2017/6/13
 */
public interface WithDrawBackendModelJpaGerepository extends JpaRepository<WithDrawsAlipayModel, Long>,JpaSpecificationExecutor<WithDrawsAlipayModel> {
    /**
     *  SELECT EXECUTOR
     * */

    @Modifying
    @Query("update WithDrawsAlipayModel m set m.orderId = ?1,m.withdrawTime=?2,m.status = 1 where m.id = ?3")
    public int bindOrderNumWithId(@Param("orderId") String orderId,@Param("optimeStamp") long optimeStamp,@Param("id") long id);
}
