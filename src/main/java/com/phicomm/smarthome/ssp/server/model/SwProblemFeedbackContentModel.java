package com.phicomm.smarthome.ssp.server.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author zhengwang.li
 * @date 2018-05-09
 * @version V1.0
 * @Description sw_problem_feedback_content实体类
 */
public class SwProblemFeedbackContentModel {

	@JsonProperty("id")
	private int id;// 自增长Id

	@JsonProperty("problem_feedback_id")
	private int problemFeedbackId;// 对应表sw_problem_feedback的id

	@JsonProperty("feedback_content")
	private String feedbackContent;// 反馈内容

	@JsonProperty("content_type")
	private String contentType;// 文本和图片url的顺序,text为文本，pic为url

	@JsonProperty("content_sequence")
	private int contentSequence;// 内容排序

	@JsonProperty("status")
	private byte status;// 0:可用， -1.删除

	public void setId(int id) {
		this.id=id;
	} 

	public int getId() {
		return id;
	} 

	public void setProblemFeedbackId(int problemFeedbackId) {
		this.problemFeedbackId=problemFeedbackId;
	} 

	public int getProblemFeedbackId() {
		return problemFeedbackId;
	} 

	public void setFeedbackContent(String feedbackContent) {
		this.feedbackContent=feedbackContent;
	} 

	public String getFeedbackContent() {
		return feedbackContent;
	} 

	public void setContentType(String contentType) {
		this.contentType=contentType;
	} 

	public String getContentType() {
		return contentType;
	} 

	public void setContentSequence(int contentSequence) {
		this.contentSequence=contentSequence;
	} 

	public int getContentSequence() {
		return contentSequence;
	} 

	public void setStatus(byte status) {
		this.status=status;
	} 

	public byte getStatus() {
		return status;
	}
}

