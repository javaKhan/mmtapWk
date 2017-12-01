package com.mmtap.wk.modular.order.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;

/**
 * 业务资料信息
 */
public class Info extends Model<Info> {
    @TableId(value="cid", type= IdType.NONE)
    private String wid;
    private String info;

    public String getWid() {
        return wid;
    }

    public void setWid(String wid) {
        this.wid = wid;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    @Override
    protected Serializable pkVal() {
        return this.wid;
    }
}
