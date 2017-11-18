package com.mmtap.wk.modular.business.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.mmtap.wk.modular.business.model.Flow;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 业务状态Dao
 *
 * @author fengshuonan
 * @Date 2017-11-15 18:42:05
 */
public interface FlowDao extends BaseMapper<Flow> {

    public List<Map<String, Object>> listByBid(@Param("bid") Integer bid);
}
