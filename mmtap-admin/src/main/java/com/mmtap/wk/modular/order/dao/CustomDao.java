package com.mmtap.wk.modular.order.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.mmtap.wk.modular.order.model.Custom;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 客户Dao
 *
 * @author fengshuonan
 * @Date 2017-11-18 17:48:58
 */
public interface CustomDao extends BaseMapper<Custom> {

    Custom selectById(String cid);

    List<Map<String, Object>> list(@Param("condition") String condition);

    int updateCustom(Custom custom);

}
