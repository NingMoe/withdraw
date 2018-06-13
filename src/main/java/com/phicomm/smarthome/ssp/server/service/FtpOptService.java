package com.phicomm.smarthome.ssp.server.service;

import org.springframework.web.multipart.MultipartFile;

import com.phicomm.smarthome.ssp.server.model.BaseResponseModel;

public interface FtpOptService {

    BaseResponseModel uploadFileNoDomainName(MultipartFile uploadFile, String imgPath);

    BaseResponseModel uploadFile(MultipartFile uploadFile, String imgPath);

    BaseResponseModel removeFile(String pathname);
}
