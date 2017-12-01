package com.mmtap.wk.modular.business.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;
import java.util.Date;

/**
 * 业务跟踪纪录
 */
public class Trace extends Model<Trace>{
    @TableId(value = "tid",type = IdType.AUTO)
    private int tid;
    private String oid;

    private int doer;
    private String doername; //不好获取


    private String cid;
    private String cusname;

    private String wid;
    private String workname;

    private int bs;
    private String bsname;  //

    private int cs;
    private String csname;


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

    public String getWid() {
        return wid;
    }

    public void setWid(String wid) {
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

    public String getDoername() {
        return doername;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getCusname() {
        return cusname;
    }

    public void setCusname(String cusname) {
        this.cusname = cusname;
    }

    public void setDoername(String doername) {
        this.doername = doername;
    }

    public String getBsname() {
        return bsname;
    }

    public void setBsname(String bsname) {
        this.bsname = bsname;
    }

    public String getCsname() {
        return csname;
    }

    public void setCsname(String csname) {
        this.csname = csname;
    }

    public String getWorkname() {
        return workname;
    }

    public void setWorkname(String workname) {
        this.workname = workname;
    }

    @Override
    protected Serializable pkVal() {
        return this.wid;
    }
}
