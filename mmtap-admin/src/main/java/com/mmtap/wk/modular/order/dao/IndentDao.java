package com.mmtap.wk.modular.order.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.mmtap.wk.modular.order.model.Indent;

import java.util.List;
import java.util.Map;

/**
 * 订单Dao
 *
 * @author fengshuonan
 * @Date 2017-11-18 17:48:15
 */
public interface IndentDao extends BaseMapper<Indent>{


    List<Map<String,Object>> list(String condition);

    Indent selectById(String oid);

    List<Map<String,Object>> listCustomOrders(String customId);
}
