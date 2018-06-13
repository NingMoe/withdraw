package com.phicomm.smarthome.ssp.server.service.impl;


import com.phicomm.smarthome.ssp.server.protocol.downloadbill.DownloadBillReqData;
import com.phicomm.smarthome.ssp.server.util.Configure;
import com.phicomm.smarthome.ssp.server.util.wxutil.BaseService;

/**
 * User: wenhua.tang Date: 2017/06/18 Time: 16:03
 */

public class DownloadBillService extends BaseService {

	public DownloadBillService() {
		super(Configure.DOWNLOAD_BILL_API);
	}

	/**
	 * 请求支付服务
	 * 
	 * @param scanPayReqData
	 *            这个数据对象里面包含了API要求提交的各种数据字段
	 * @return API返回的数据
	 * @throws Exception
	 */
	public String request(DownloadBillReqData unifiedorderReqData) throws Exception {

		// --------------------------------------------------------------------
		// 发送HTTPS的Post请求到API地址
		// --------------------------------------------------------------------
		String responseString = sendPost(unifiedorderReqData);

		return responseString;
	}
}
