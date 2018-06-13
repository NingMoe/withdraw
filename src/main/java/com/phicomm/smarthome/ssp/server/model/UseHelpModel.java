package com.phicomm.smarthome.ssp.server.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

public class UseHelpModel implements Serializable {

    private static final long serialVersionUID = -6563801279471617815L;
    
    @JsonProperty("id")
    private int id; // 自增长id
    
    @JsonProperty("title")
    private String title; // 标题
    
    @JsonProperty("html")
    private String html; // 前端页面html，用于预览
    
    @JsonProperty("content_url_list")
    private List<HelpContent> helpContentList; // 文本Url列表

    @JsonProperty("client")
    private String client; // 客户端，portal跟app

    @JsonProperty("sequence")
    private int sequence; // 排序号
    
    @JsonProperty("lastOptUid")
    private long lastOptUid; // 用户uid
    
    @JsonProperty("real_name")
    private String lastOptUserName; // 用户名称

    @JsonIgnore
    private byte status; // 0:未删除  -1.删除

    @JsonIgnore
    private long createTime;

    @JsonIgnore
    private long updateTime;
    
    @JsonProperty("create_time")
    private String createTimeStr; 

    @JsonProperty("update_time")
    private String updateTimeStr; 
    
    public static class HelpContent{

        @JsonIgnore
        private int id;

        @JsonIgnore
        private int useHelpId; // 对应帮助主表的id
        
        @JsonProperty("content")
        private String content; // 内容或者是url
        
        @JsonProperty("type")
        private String type; // 文本内容还是图片url，"text"为文本，"pic"为图片url

        @JsonIgnore
        private Integer sequence; // 内容排序

        @JsonIgnore
        private byte status;

        public byte getStatus() {
            return status;
        }

        public void setStatus(byte status) {
            this.status = status;
        }

        public int getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public int getUseHelpId() {
            return useHelpId;
        }

        public void setUseHelpId(Integer useHelpId) {
            this.useHelpId = useHelpId;
        }

        public int getSequence() {
            return sequence;
        }

        public void setSequence(Integer sequence) {
            this.sequence = sequence;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        @Override
        public String toString() {
            return "HelpContent{" +
                    "id=" + id +
                    ", useHelpId=" + useHelpId +
                    ", content='" + content + '\'' +
                    ", type='" + type + '\'' +
                    ", sequence=" + sequence +
                    ", status=" + status +
                    '}';
        }
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getCreateTimeStr() {
        return createTimeStr;
    }

    public void setCreateTimeStr(String createTimeStr) {
        this.createTimeStr = createTimeStr;
    }

    public String getUpdateTimeStr() {
        return updateTimeStr;
    }

    public void setUpdateTimeStr(String updateTimeStr) {
        this.updateTimeStr = updateTimeStr;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }
    
    public List<HelpContent> getHelpContentList() {
        return helpContentList;
    }

    public void setHelpContentList(List<HelpContent> helpContentList) {
        this.helpContentList = helpContentList;
    }

    public int getSequence() {
        return sequence;
    }

    public void setSequence(int sequence) {
        this.sequence = sequence;
    }

    public long getLastOptUid() {
        return lastOptUid;
    }

    public void setLastOptUid(long lastOptUid) {
        this.lastOptUid = lastOptUid;
    }

    public String getLastOptUserName() {
        return lastOptUserName;
    }

    public void setLastOptUserName(String lastOptUserName) {
        this.lastOptUserName = lastOptUserName;
    }

    public byte getStatus() {
        return status;
    }

    public void setStatus(byte status) {
        this.status = status;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }
}
