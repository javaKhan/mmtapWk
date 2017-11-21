package com.mmtap.wk.modular.business.model;

import java.util.Date;

/**
 * 业务跟踪纪录
 */
public class Trace {
    private int tid;
    private int doer;
    private String oid;
    private int bs;
    private int cs;
    private int wid;
    private String msg;
    private Date createtime;

    public int getTid() {
        return tid;
    }

    public void setTid(int tid) {
        this.tid = tid;
    }

    public int getDoer() {
        return doer;
    }

    public void setDoer(int doer) {
        this.doer = doer;
    }

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public int getBs() {
        return bs;
    }

    public void setBs(int bs) {
        this.bs = bs;
    }

    public int getCs() {
        return cs;
    }

    public void setCs(int cs) {
        this.cs = cs;
    }

    public int getWid() {
        return wid;
    }

    public void setWid(int wid) {
        this.wid = wid;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }
}
