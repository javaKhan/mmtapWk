package com.mmtap.wk.modular.business.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;
import java.util.Date;

public class Flow extends Model<Flow> {

    @TableId(value="fid", type= IdType.AUTO)
    private Integer fid;
    /**
     * 业务id
     */
    private Integer bid;
    /**
     * 流程状态名称
     */
    private String flowname;
    /**
     * 流程排序
     */
    private int floworder;

    /**
     * 操作此流程的角色
     */
    private Integer flowrole;
    /**
     * 创建者
     */
    private Integer creater;
    /**
     * 创建时间
     */
    private Date createtime;


    @Override
    protected Serializable pkVal() {
        return this.fid;
    }

    public Integer getFid() {
        return fid;
    }

    public void setFid(Integer fid) {
        this.fid = fid;
    }

    public int getBid() {
        return bid;
    }

    public void setBid(int bid) {
        this.bid = bid;
    }

    public String getFlowname() {
        return flowname;
    }

    public void setFlowname(String flowname) {
        this.flowname = flowname;
    }

    public int getFloworder() {
        return floworder;
    }

    public void setFloworder(int floworder) {
        this.floworder = floworder;
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

    public Integer getFlowrole() {
        return flowrole;
    }

    public void setFlowrole(Integer flowrole) {
        this.flowrole = flowrole;
    }
}
