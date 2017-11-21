package com.mmtap.wk.modular.order.model;


import com.baomidou.mybatisplus.activerecord.Model;

import java.io.Serializable;
import java.util.Date;

/**
 * 客户信息
 */
public class Custom  extends Model<Custom>{
    private String cid;
    private String customname;
    private String mobile;
    private String netid;
    private String wwid;
    private String address;
    private String come;
    private String place;
    private String cuscom;
    private Integer creater;
    private Date createtime;

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getCustomname() {
        return customname;
    }

    public void setCustomname(String customname) {
        this.customname = customname;
    }

    public String getMobile() {
        return mobile;
    }

    public String getNetid() {
        return netid;
    }

    public void setNetid(String netid) {
        this.netid = netid;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getWwid() {
        return wwid;
    }

    public void setWwid(String wwid) {
        this.wwid = wwid;
    }

    public String getCome() {
        return come;
    }

    public void setCome(String come) {
        this.come = come;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCuscom() {
        return cuscom;
    }

    public void setCuscom(String cuscom) {
        this.cuscom = cuscom;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public Integer getCreater() {
        return creater;
    }

    public void setCreater(Integer creater) {
        this.creater = creater;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    @Override
    protected Serializable pkVal() {
        return this.cid;
    }
}
