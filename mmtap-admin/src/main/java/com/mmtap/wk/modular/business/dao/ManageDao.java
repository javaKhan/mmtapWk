package com.mmtap.wk.modular.business.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.mmtap.wk.modular.business.model.Business;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 业务中心Dao
 *
 * @author fengshuonan
 * @Date 2017-11-13 22:58:10
 */
public interface ManageDao extends BaseMapper<Business> {
    List<Map<String, Object>> list(@Param("condition") String condition);
}
