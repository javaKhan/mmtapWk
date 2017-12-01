package com.mmtap.wk.modular.order.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;
import java.util.Date;

/**
 * 订单业务项
 */
public class Work extends Model<Work>{
    @TableId(value="wid", type= IdType.UUID)
    private String wid;  //工单号
    private String oid;   //订单号
    private String cid;   //客户号

    private Integer bid;  //业务类型
    private Integer fid;  //业务状态
    private Integer creater; //创建者
    private Date createtime; //创建时间
    private double price;    //价格
    private String workcom; //备注

    public String getWid() {
        return wid;
    }

    public void setWid(String wid) {
        this.wid = wid;
    }

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

    public Integer getBid() {
        return bid;
    }

    public void setBid(Integer bid) {
        this.bid = bid;
    }

    public Integer getFid() {
        return fid;
    }

    public void setFid(Integer fid) {
        this.fid = fid;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getWorkcom() {
        return workcom;
    }

    public void setWorkcom(String workcom) {
        this.workcom = workcom;
    }

    @Override
    protected Serializable pkVal() {
        return this.wid;
    }
}
