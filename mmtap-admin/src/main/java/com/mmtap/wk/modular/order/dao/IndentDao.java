package com.mmtap.wk.modular.order.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.mmtap.wk.modular.order.model.Indent;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 订单Dao
 *
 * @author imkzp.com
 * @Date 2017-11-18 17:48:15
 */
public interface IndentDao extends BaseMapper<Indent>{


    List<Map<String,Object>> list(String condition);

    Indent selectById(String oid);

    List<Map<String,Object>> listCustomOrders(String customId);

    Map bakOneOrderBase(String oid);

    List bakOneOrderWorks(String oid);

    //查出要备份的订单
    List bakBatch(@Param("bdate") Date bdate, @Param("edate") Date edate);
}
