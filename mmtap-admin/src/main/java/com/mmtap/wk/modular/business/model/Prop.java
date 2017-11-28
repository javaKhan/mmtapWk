package com.mmtap.wk.modular.business.model;

import com.baomidou.mybatisplus.activerecord.Model;

import java.io.Serializable;

/**
 * 业务属性
 */
public class Prop extends Model<Prop>{
    private Integer pid;
    private Integer bid;
    private String title;
    private int proporder;
    private String code;

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public Integer getBid() {
        return bid;
    }

    public void setBid(Integer bid) {
        this.bid = bid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getProporder() {
        return proporder;
    }

    public void setProporder(int proporder) {
        this.proporder = proporder;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    protected Serializable pkVal() {
        return this.pid;
    }
}
