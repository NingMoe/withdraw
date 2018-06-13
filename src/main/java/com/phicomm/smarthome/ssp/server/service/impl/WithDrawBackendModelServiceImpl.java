package com.phicomm.smarthome.ssp.server.service.impl;

import com.phicomm.smarthome.ssp.server.model.RequestHandleWithDrawBodyModel;
import com.phicomm.smarthome.ssp.server.model.RequestWithDrawBodyModel;
import com.phicomm.smarthome.ssp.server.model.WithDrawsAlipayModel;
import com.phicomm.smarthome.ssp.server.repository.WithDrawBackendModelJpaGerepository;
import com.phicomm.smarthome.ssp.server.service.WithDrawBackendModelService;
import com.phicomm.smarthome.ssp.server.util.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 * PROJECT_NAME: SspBusServer
 * PACKAGE_NAME: com.phicomm.ssp.server.service.sharedwifi.service.impl
 * DESCRIPTION:
 * AUTHOR: xiangrong.ke
 * DATE: 2017/6/21
 */

@Transactional
@Service
public class WithDrawBackendModelServiceImpl implements WithDrawBackendModelService {

    @Autowired
    private WithDrawBackendModelJpaGerepository withDrawBackendModelJpaGerepository;

    @Override
    public Page<WithDrawsAlipayModel> getWithDrawList4Pages(final RequestWithDrawBodyModel requestWithDrawBodyModel, int page, int count, Sort sort){
        Page<WithDrawsAlipayModel> resultList = null;
        Specification<WithDrawsAlipayModel> specification = new Specification<WithDrawsAlipayModel>() {
            @Override
            public Predicate toPredicate(Root<WithDrawsAlipayModel> root,
                                         CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<Predicate>();
                //参数非空判断。不为空则加此条件
                if (!requestWithDrawBodyModel.getMobileNum().isEmpty()) {
                    System.out.println("phone:"+ requestWithDrawBodyModel.getMobileNum());
                    Predicate phone = criteriaBuilder.equal(root.get("phone"), requestWithDrawBodyModel.getMobileNum());
                    predicates.add(phone);
                }
                //参数非空判断。不为空则加此条件
                if (!requestWithDrawBodyModel.getApplytimeBegin().isEmpty()) {
                    System.out.println("applyTimeBegin:"+ requestWithDrawBodyModel.getApplytimeBegin());
                    long applayTimeBeginTs = (CommonUtils.dateToStamp(requestWithDrawBodyModel.getApplytimeBegin()));
                    System.out.println("applayTimeBeginTs:"+ applayTimeBeginTs);
                    Predicate applyTimeBegin = criteriaBuilder.ge(root.get("createTime"), applayTimeBeginTs);
                    predicates.add(applyTimeBegin);
                }
                //参数非空判断。不为空则加此条件
                if (!requestWithDrawBodyModel.getApplytimeEnd().isEmpty()) {
                    System.out.println("applyTimeEnd:"+ requestWithDrawBodyModel.getApplytimeEnd());
                    long applayTimeEndTs = (CommonUtils.dateToStamp(requestWithDrawBodyModel.getApplytimeEnd()));
                    System.out.println("applayTimeEndTs:"+ applayTimeEndTs);
                    Predicate applyTimeEnd = criteriaBuilder.le(root.get("createTime"), applayTimeEndTs);
                    predicates.add(applyTimeEnd);
                }

                //参数非空判断。不为空则加此条件
                if (!requestWithDrawBodyModel.getOptimeBegin().isEmpty()) {
                    System.out.println("withDrawTimeBegin:"+ requestWithDrawBodyModel.getOptimeBegin());
                    long withDrawTimeBeginTs = (CommonUtils.dateToStamp(requestWithDrawBodyModel.getOptimeBegin()));
                    System.out.println("withDrawTimeBeginTs:"+ withDrawTimeBeginTs);
                    Predicate withDrawTimeBegin = criteriaBuilder.ge(root.get("withdrawTime"), withDrawTimeBeginTs);
                    predicates.add(withDrawTimeBegin);
                }
                //参数非空判断。不为空则加此条件
                if (!requestWithDrawBodyModel.getOptimeEnd().isEmpty()) {
                    System.out.println("withDrawTimeEnd:"+ requestWithDrawBodyModel.getOptimeEnd());
                    long optimeEndTs = (CommonUtils.dateToStamp(requestWithDrawBodyModel.getOptimeEnd()));
                    System.out.println("withDrawTimeEnd:"+ optimeEndTs);
                    Predicate withDrawTimeEnd = criteriaBuilder.le(root.get("withdrawTime"), optimeEndTs);
                    predicates.add(withDrawTimeEnd);
                }

                return criteriaBuilder.and(predicates.toArray(new Predicate[]{}));
            }
        };
        Pageable pageable = new PageRequest(page, count, sort);
        resultList =  withDrawBackendModelJpaGerepository.findAll(specification,pageable);
        return resultList;
    }

    @Override
    public int bindOrderNumWithId(RequestHandleWithDrawBodyModel requestHandleWithDrawBodyModel,int optimeStamp) {
        return withDrawBackendModelJpaGerepository.bindOrderNumWithId(requestHandleWithDrawBodyModel.getOrderNum(),optimeStamp,requestHandleWithDrawBodyModel.getId());
    }


//    @Override
//    public Page<RouterItemModel> findAllRouterItem4Pages(Integer pageStartIndex, Integer pageSize) {
//
//        Sort sort = new Sort(Sort.Direction.DESC, "create_time");
//        Pageable pageable = new PageRequest(pageStartIndex, pageSize, sort);
//        return withDrawBackendModelJpaGerepository.findAll(pageable);
//    }
}
