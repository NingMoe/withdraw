package com.phicomm.smarthome.ssp.server.model.response;

import java.util.List;

import org.springframework.data.domain.Sort;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.phicomm.smarthome.ssp.server.model.BaseResponseModel;
import com.phicomm.smarthome.ssp.server.model.SwBillReportModel;

public class ResponseGetBalanceReport extends BaseResponseModel {

    @JsonProperty("content")
    private List<SwBillReportModel> list;

    private boolean first;
    
    private boolean last;
    
    private int number;
    
    private int numberOfElements;
    
    private int size;
    
    private Sort sort;
    
    private long totalElements;
    
    private int totalPages;

    public List<SwBillReportModel> getList() {
        return list;
    }

    public void setList(List<SwBillReportModel> list) {
        this.list = list;
    }

    public boolean isFirst() {
        return first;
    }

    public void setFirst(boolean first) {
        this.first = first;
    }

    public boolean isLast() {
        return last;
    }

    public void setLast(boolean last) {
        this.last = last;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getNumberOfElements() {
        return numberOfElements;
    }

    public void setNumberOfElements(int numberOfElements) {
        this.numberOfElements = numberOfElements;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public Sort getSort() {
        return sort;
    }

    public void setSort(Sort sort) {
        this.sort = sort;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(long totalElements) {
        this.totalElements = totalElements;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }
}
