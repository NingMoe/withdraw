package com.phicomm.smarthome.ssp.server.repository;

import com.phicomm.smarthome.ssp.server.model.SwBillReportModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Created by kexiangrong on 2017/7/11.
 */
public interface SwBillReportJpaRepository extends JpaRepository<SwBillReportModel, Long>,JpaSpecificationExecutor<SwBillReportModel> {
}
