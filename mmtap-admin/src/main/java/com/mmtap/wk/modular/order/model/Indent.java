package com.mmtap.wk.modular.order.model;

import com.baomidou.mybatisplus.activerecord.Model;

import java.io.Serializable;
import java.util.Date;

/**
 * 订单对象
 */
public class Indent extends Model {
    private String oid;
    private String cid;         //客户
    private Integer creater;    //创建者
    private Date createtime;    //生成时间
    private String comments;    //订单备注

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
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

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    @Override
    protected Serializable pkVal() {
        return this.oid;
    }
}
