package com.mmtap.wk.modular.order.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.mmtap.wk.modular.order.model.Info;

/**
 * 业务内容Dao
 *
 * @author mmtap.com
 * @Date 2017-11-28 14:11:11
 */
public interface InfoDao extends BaseMapper<Info>{


    int isExitWorkInfo(String wid);

    void updateWorkInfo(Info info);

    void insertWorkInfo(Info info);

    void deleteInfoByOrderId(String orderId);
}
