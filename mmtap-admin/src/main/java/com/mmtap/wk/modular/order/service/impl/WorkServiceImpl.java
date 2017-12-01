package com.mmtap.wk.modular.order.service.impl;

import com.mmtap.wk.core.shiro.ShiroKit;
import com.mmtap.wk.modular.business.dao.FlowDao;
import com.mmtap.wk.modular.business.model.Business;
import com.mmtap.wk.modular.business.model.Flow;
import com.mmtap.wk.modular.order.dao.WorkDao;
import com.mmtap.wk.modular.order.model.Work;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mmtap.wk.modular.order.service.IWorkService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * 工作Service
 *
 * @author fengshuonan
 * @Date 2017-11-18 17:50:20
 */
@Service
public class WorkServiceImpl implements IWorkService {

    @Autowired
    private WorkDao workDao;
    @Autowired
    private FlowDao flowDao;

    @Override
    public List getTodoWorks() {
        List roleList = ShiroKit.getUser().getRoleList();
        List<Map<String,Object>> workInfoList = workDao.getTodoWorks(roleList);
        return workInfoList;
    }

    @Override
    public synchronized int  lockWork(String wid, Integer uid) {
        return workDao.lockWork(wid,uid);
    }

    @Override
    @Transactional
    public boolean nextStep(String wid) {
        Work work = workDao.selectById(wid);
        Flow flow =flowDao.getNextFlow(work.getBid(),work.getFid());
        int res = workDao.nextStep(wid,flow.getFid());
        if(res>0){
            return true;
        }
        return false;
    }
}
