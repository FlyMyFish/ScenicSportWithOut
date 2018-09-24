package com.shichen.scenicsport.data;

import java.util.List;

public class SportResponse {
    String error;
    String msg;
    String frm;
    String pagesize;
    String keyword;
    List<Sport> values;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getFrm() {
        return frm;
    }

    public void setFrm(String frm) {
        this.frm = frm;
    }

    public String getPagesize() {
        return pagesize;
    }

    public void setPagesize(String pagesize) {
        this.pagesize = pagesize;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public List<Sport> getValues() {
        return values;
    }

    public void setValues(List<Sport> values) {
        this.values = values;
    }
}
