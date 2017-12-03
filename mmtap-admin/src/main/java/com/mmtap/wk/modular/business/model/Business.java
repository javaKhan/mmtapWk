package com.mmtap.wk.modular.business.model;


import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;
import java.util.Date;

public class Business extends Model<Business> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id ：业务id
     */
    @TableId(value="bid", type= IdType.AUTO)
    private Integer bid;

    /**
     * 业务名称
     */
    private String businessname;

    /**
     * 业务默认价格
     */
    private double busprice;
    /**
     * 创建人
     */
    private Integer creater;
    /**
     * 创建时间
     */
    private Date createtime;

    public Integer getBid() {
        return bid;
    }

    public void setBid(Integer bid) {
        this.bid = bid;
    }

    public String getBusinessname() {
        return businessname;
    }

    public void setBusinessname(String businessname) {
        this.businessname = businessname;
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
        return this.bid;
    }

    public double getBusprice() {
        return busprice;
    }

    public void setBusprice(double busprice) {
        this.busprice = busprice;
    }

    @Override
    public String toString() {
        return "Business{" +
                "bid=" + bid +
                ", businessname=" + businessname +
                ", creater=" + creater +
                ", createtime=" + createtime +
                "}";
    }
}
