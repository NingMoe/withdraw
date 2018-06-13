package com.phicomm.smarthome.ssp.server.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.phicomm.smarthome.ssp.server.consts.Const;
import com.phicomm.smarthome.ssp.server.model.RequestHandleWithDrawBodyModel;
import com.phicomm.smarthome.ssp.server.model.RequestWithDrawBodyModel;
import com.phicomm.smarthome.ssp.server.model.SmartHomeResponse;
import com.phicomm.smarthome.ssp.server.model.WithDrawsAlipayModel;
import com.phicomm.smarthome.ssp.server.model.response.ResponseWithdrawQueryList;
import com.phicomm.smarthome.ssp.server.service.WithDrawBackendModelService;
import com.phicomm.smarthome.ssp.server.util.CommonUtils;

import io.swagger.annotations.ApiOperation;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * PROJECT_NAME: SspBusServer PACKAGE_NAME:
 * com.phicomm.ssp.server.service.sharedwifi.controller DESCRIPTION: AUTHOR:
 * liang04.zhang DATE: 2017/6/19
 */

@RestController
@ComponentScan
@EnableSwagger2
@PropertySource("classpath:application.properties")
public class WithDrawBackendController extends BaseController {

    @Value("${super.admin.name}")
    private String superAdminName;

    @Value("${super.admin.password}")
    private String superAdminPassword;

    @Autowired
    private WithDrawBackendModelService withDrawBackendModelService;

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @ApiOperation(value = "分页查询提现记录", httpMethod = "POST", notes = "查询提现记录")
    @RequestMapping(value = "/withdraw/queryList", method = RequestMethod.POST, produces = { "application/json" })
    public SmartHomeResponse<Page<WithDrawsAlipayModel>> getWithDrawList4Pages(HttpServletRequest request,
            @RequestBody RequestWithDrawBodyModel requestWithDrawBodyModel) {
        ResponseWithdrawQueryList rspObj = new ResponseWithdrawQueryList();
        Page<WithDrawsAlipayModel> pagedRes = null;
        int errCode = Const.ResponseStatus.STATUS_OK;
        String errMsg = Const.ResponseStatus.STATUS_OK_STR;
        int page = 0;
        int count = 0;

        // 检查参数
        try {
            // 日期不为空，检验合法性
            if (!requestWithDrawBodyModel.getApplytimeBegin().isEmpty()) {
                CommonUtils.isValidData(requestWithDrawBodyModel.getApplytimeBegin());
            }
            if (!requestWithDrawBodyModel.getApplytimeEnd().isEmpty()) {
                CommonUtils.isValidData(requestWithDrawBodyModel.getApplytimeEnd());
            }
            if (!requestWithDrawBodyModel.getOptimeBegin().isEmpty()) {
                CommonUtils.isValidData(requestWithDrawBodyModel.getOptimeBegin());
            }
            if (!requestWithDrawBodyModel.getOptimeEnd().isEmpty()) {
                CommonUtils.isValidData(requestWithDrawBodyModel.getOptimeEnd());
            }

            // 手机号不为空，一定要为数字才合法
            if (!requestWithDrawBodyModel.getMobileNum().isEmpty()) {
                Long.parseLong(requestWithDrawBodyModel.getMobileNum());
            }

            page = Integer.parseInt(requestWithDrawBodyModel.getCurPage());
            count = Integer.parseInt(requestWithDrawBodyModel.getPageSize());
            if (page < 1 || count <= 0) {
                errCode = Const.ResponseStatus.STATUS_COMMON_NULL;
                errMsg = Const.ResponseStatus.STATUS_COMMON_NULL_STR;
                return new SmartHomeResponse(errCode, errMsg, pagedRes);
            }

            // 转换一下，前段第一页从1开始
            page = page - 1;
        } catch (Exception e) {
            errCode = Const.ResponseStatus.STATUS_COMMON_NULL;
            errMsg = Const.ResponseStatus.STATUS_COMMON_NULL_STR;
            return new SmartHomeResponse(errCode, errMsg, pagedRes);
        }

        // 排序
        Sort.Order order1 = new Sort.Order(Sort.Direction.ASC, "status");
        Sort.Order order2 = new Sort.Order(Sort.Direction.ASC, "createTime");
        List<Sort.Order> orders = new ArrayList<Sort.Order>();
        orders.add(order1);
        orders.add(order2);
        Sort sort = new Sort(orders);

        pagedRes = withDrawBackendModelService.getWithDrawList4Pages(requestWithDrawBodyModel, page, count, sort);
        rspObj.setList(pagedRes.getContent());
        rspObj.setFirst(pagedRes.isFirst());
        rspObj.setLast(pagedRes.isLast());
        rspObj.setNumber(pagedRes.getNumber());
        rspObj.setNumberOfElements(pagedRes.getNumberOfElements());
        rspObj.setSize(pagedRes.getSize());
        rspObj.setSort(pagedRes.getSort());
        rspObj.setTotalElements(pagedRes.getTotalElements());
        rspObj.setTotalPages(pagedRes.getTotalPages());
        return new SmartHomeResponse(errCode, errMsg, rspObj);
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @ApiOperation(value = "处理提现", httpMethod = "POST", notes = "处理提现")
    @RequestMapping(value = "/withdraw/handleWithdraw", method = RequestMethod.POST, produces = { "application/json" })
    public SmartHomeResponse<WithDrawsAlipayModel> withDraw(HttpServletRequest request,
            @RequestBody RequestHandleWithDrawBodyModel requestHandleWithDrawBodyModel) {
        int errCode = Const.ResponseStatus.STATUS_OK;
        String errMsg = Const.ResponseStatus.STATUS_OK_STR;

        WithDrawsAlipayModel resp = new WithDrawsAlipayModel();
        int nowTimeStamp = CommonUtils.getNowTimeToStamp();
        int res = withDrawBackendModelService.bindOrderNumWithId(requestHandleWithDrawBodyModel, nowTimeStamp);
        // 操作不成功
        if (res != 1) {
            errCode = Const.ResponseStatus.STATUS_COMMON_UPDATE_FAILED;
            errMsg = Const.ResponseStatus.STATUS_COMMON_UPDATE_FAILED_STR;
            return new SmartHomeResponse(errCode, errMsg, resp);
        }
        resp.setId(requestHandleWithDrawBodyModel.getId());
        resp.setWithdrawTime(nowTimeStamp);
        resp.setOrderId(requestHandleWithDrawBodyModel.getOrderNum());
        // 设置状态已提现
        resp.setStatus(1);
        return new SmartHomeResponse(errCode, errMsg, resp);
    }
}
