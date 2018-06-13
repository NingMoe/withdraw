package com.phicomm.smarthome.ssp.server.service.impl;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.phicomm.smarthome.ssp.server.dao.PortalMapper;
import com.phicomm.smarthome.ssp.server.model.SwPortalPictureModel;
import com.phicomm.smarthome.ssp.server.service.PortalService;
import com.phicomm.smarthome.util.OptDateUtil;

import net.sf.json.JSONObject;

/**
 * 图片上传服务
 * <p>
 * Title: PictureServicImpl
 * </p>
 * <p>
 * Description:
 * </p>
 * 
 * @version 1.0
 */
@Service
public class PortalServicImpl implements PortalService {

    private static final Logger LOGGER = LogManager.getLogger(PortalServicImpl.class);

    @Autowired
    private PortalMapper portalMapper;

    @Override
    public int addImgInfo(SwPortalPictureModel model) {
        int rst = 0;
        // 判断生效时间是否在当前时间之前（包含当前时间），是则图片可直接展示，否则延迟展示（未生效）
        long effectiveTime = model.getEffectiveTime();
        long nowTimeLong = OptDateUtil.getNowTimeLong();
        byte status = 0;
        if (effectiveTime <= nowTimeLong) {
            status = 1;
            model.setStatus(status);
            rst = portalMapper.addImgInfo(model);
            // 立刻生效的图片，删除已存在的以后生效图片
            if (rst > 0) {
                portalMapper.removeAfterEftImgInfo(model);
            }
        } else {
            // 未生效的图片
            model.setStatus(status);
            // 先判断是否已存在未生效图片，有替换，否则新增（始终保持只有一条未生效的图片）
            int ineffectiveImgCount = portalMapper.getIneffectiveImgCount(model);
            if (ineffectiveImgCount > 0) {
                rst = portalMapper.updateImgInfo(model);
            } else {
                rst = portalMapper.addImgInfo(model);
            }
        }
        return rst;
    }

    @Override
    public int removeImgInfo(String imgUrl, long updateTime) {
        // 数据库标识图片删除，服务器不进行图片资源删除
        // BaseResponseModel removeFile = ftpOptService.removeFile(imgUrl);
        return portalMapper.removeImgInfo(imgUrl, updateTime);
    }

    @Override
    public SwPortalPictureModel queryPortal(String deviceName) {
        SwPortalPictureModel queryPortal = portalMapper.queryPortal(deviceName);
        if (queryPortal != null && queryPortal.getEffectiveTime() != 0) {
            queryPortal.setEffectiveTimeStr(OptDateUtil.stampToDate(queryPortal.getEffectiveTime()));
        }
        return queryPortal;
    }

    @Override
    public void portalImgStatusCheck() {
        // 获取未到生效时间的图片
        List<SwPortalPictureModel> ineffectiveImgs = portalMapper.getIneffectiveImg();
        if (ineffectiveImgs != null && ineffectiveImgs.size() > 0) {
            for (SwPortalPictureModel model : ineffectiveImgs) {
                // 检查图片是否到了设置的生效时间
                long effectiveTime = model.getEffectiveTime();
                long nowTimeLong = OptDateUtil.getNowTimeLong();
                if (effectiveTime <= nowTimeLong) {
                    model.setUpdateTime(nowTimeLong);
                    portalMapper.updateIneffectiveImg(model);
                    JSONObject json = JSONObject.fromObject(model);
                    LOGGER.info("portalImgStatusCheck model--->" + json.toString());
                }
            }
        }
    }

}
