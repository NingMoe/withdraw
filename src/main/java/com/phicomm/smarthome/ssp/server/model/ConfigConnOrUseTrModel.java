package com.phicomm.smarthome.ssp.server.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author fujiang.mao
 * @date 2018-05-25
 * @version V1.0
 * @Description config_conn_tr和config_use_tr实体类
 */
public class ConfigConnOrUseTrModel {

	@JsonProperty("range_id")
	private int rangeId;

	@JsonProperty("range_start")
	private int rangeStart;// 时长范围开始, 单位s

	@JsonProperty("range_end")
	private int rangeEnd;// 时长范围结束, 单位s. 允许null

	@JsonProperty("range_text")
	private String rangeText;// 时长范围, 如1-11分

	@JsonProperty("create_date")
	private long createDate;

	public void setRangeId(int rangeId) {
		this.rangeId=rangeId;
	} 

	public int getRangeId() {
		return rangeId;
	} 

	public void setRangeStart(int rangeStart) {
		this.rangeStart=rangeStart;
	} 

	public int getRangeStart() {
		return rangeStart;
	} 

	public void setRangeEnd(int rangeEnd) {
		this.rangeEnd=rangeEnd;
	} 

	public int getRangeEnd() {
		return rangeEnd;
	} 

	public void setRangeText(String rangeText) {
		this.rangeText=rangeText;
	} 

	public String getRangeText() {
		return rangeText;
	} 

	public void setCreateDate(long createDate) {
		this.createDate=createDate;
	} 

	public long getCreateDate() {
		return createDate;
	} 

}

