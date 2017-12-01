package com.mmtap.wk.modular.order.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.mmtap.wk.modular.order.model.Work;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 工作Dao
 *
 * @author fengshuonan
 * @Date 2017-11-18 17:50:20
 */
public interface WorkDao extends BaseMapper<Work> {
    List<Map<String,Object>> getTodoWorks(List roleList);

    int lockWork(@Param("wid") String wid,@Param("uid") Integer uid);

    List getMyWorks(Integer uid);

    Map getWorkInfo(String wid);

    void disWork(String wid);
}
