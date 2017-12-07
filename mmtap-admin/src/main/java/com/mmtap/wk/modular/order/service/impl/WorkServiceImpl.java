package com.mmtap.wk.modular.order.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.mmtap.wk.common.constant.factory.ConstantFactory;
import com.mmtap.wk.core.shiro.ShiroKit;
import com.mmtap.wk.modular.business.dao.FlowDao;
import com.mmtap.wk.modular.business.model.Business;
import com.mmtap.wk.modular.business.model.Flow;
import com.mmtap.wk.modular.business.model.Trace;
import com.mmtap.wk.modular.order.dao.WorkDao;
import com.mmtap.wk.modular.order.model.Work;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mmtap.wk.modular.order.service.IWorkService;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 工作Service
 *
 * @author imkzp.com
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
    @Transactional
    public synchronized int  lockWork(String[] wids, Integer uid) {
        int res = 0;
        for(String wid: wids){
            //TODO 业务日志
            /**
             *  业务日志部分
             *
             */
            Map info = workDao.getWorkInfo(wid);

            Trace trace = new Trace();
            trace.setOid(MapUtils.getString(info,"oid"));
            trace.setCreatetime(new Date());
            trace.setDoer(ShiroKit.getUser().getId());
            trace.setDoername(ShiroKit.getUser().getName());
            trace.setCid(MapUtils.getString(info,"cid"));
            trace.setCusname(MapUtils.getString(info,"customname"));

            trace.setWid(wid);
            trace.setWorkname(MapUtils.getString(info,"businessname"));

            int fid =  MapUtils.getIntValue(info,"fid");
            String state = MapUtils.getString(info,"flowname");
            trace.setBs(fid);  //新增业务上一业务状态为最小值
            trace.setCs(fid);  //新增业务当前务状态为最小值
            trace.setBsname(state);
            trace.setCsname(state);
            trace.setMsg("时间:"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(trace.getCreatetime())
                    +"    操作员:"+trace.getDoername()+ "申办业务["+trace.getWorkname()+"]    状态为:"+trace.getCsname());
            //日志写入
            trace.insert();
            workDao.lockWork(wid,uid);
            res++;
        }

        if(res==wids.length){
            return res;
        }


        return 0;
    }

    @Override
    @Transactional
    public boolean nextStep(String wid) {

        Map info = workDao.getWorkInfo(wid); //获取业务日志信息备用
        int currentFid =  MapUtils.getIntValue(info,"fid");
        //获取了所有的业务状态信息
        List<Flow> sortFlows = (List) flowDao.sortFlowList(MapUtils.getIntValue(info,"bid"));
        Flow nextFlow = null;
        boolean isLastState = false;
        for(int i=0;i<sortFlows.size()-1;i++){
            Flow tempFlow = sortFlows.get(i);
            if(tempFlow.getFid()==currentFid ){
                nextFlow = sortFlows.get(i+1);
                workDao.nextStep(wid,nextFlow.getFid()); //有问题-最后一步时
            }
            if((sortFlows.size()-1 ==i) || (sortFlows.size()==i+1)){
                isLastState = true;  //判断出是最后一步：应该完结具体业务
            }
        }

        if (isLastState){
            workDao.finishWork(wid);
        }


        /**
         *  业务日志部分
         */
        {
            Trace trace = new Trace();
            trace.setOid(MapUtils.getString(info,"oid"));
            trace.setCreatetime(new Date());
            trace.setDoer(ShiroKit.getUser().getId());
            trace.setDoername(ShiroKit.getUser().getName());
            trace.setCid(MapUtils.getString(info,"cid"));
            trace.setCusname(MapUtils.getString(info,"customname"));

            trace.setWid(wid);
            trace.setWorkname(MapUtils.getString(info,"businessname"));
            trace.setBs(MapUtils.getIntValue(info,"fid"));
            trace.setCs(nextFlow.getFid());  //新增业务当前务状态为最小值
            trace.setBsname(MapUtils.getString(info,"flowname"));
            trace.setCsname(nextFlow.getFlowname());
            trace.setMsg("时间:"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(trace.getCreatetime())
                    +"    操作员:"+trace.getDoername()+ "   处理完成业务["+trace.getWorkname()+"]    状态由["+trace.getBsname()+"]变更为:"+trace.getCsname());
            //日志写入
            trace.insert();
        }

        return true;
    }


    @Override
    public void newprice(String wid, double price) {

        Map info = workDao.getWorkInfo(wid); //获取业务日志信息备用
        //TODO 业务日志
        workDao.newprice(wid,price);
        /**
         *  业务日志部分
         */
        Trace trace = new Trace();
        trace.setOid(MapUtils.getString(info,"oid"));
        trace.setCreatetime(new Date());
        trace.setDoer(ShiroKit.getUser().getId());
        trace.setDoername(ShiroKit.getUser().getName());
        trace.setCid(MapUtils.getString(info,"cid"));
        trace.setCusname(MapUtils.getString(info,"customname"));

        trace.setWid(wid);
        trace.setWorkname(MapUtils.getString(info,"businessname"));
        trace.setBs(MapUtils.getIntValue(info,"fid"));
        trace.setCs(MapUtils.getIntValue(info,"fid"));  //新增业务当前务状态为最小值
        trace.setBsname(MapUtils.getString(info,"flowname"));
        trace.setCsname(MapUtils.getString(info,"flowname"));
        trace.setMsg("时间:"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(trace.getCreatetime())
                +"    操作员:"+trace.getDoername()+ "业务["+trace.getWorkname()+"]状态:["+trace.getBsname()+"] 价格由["+MapUtils.getIntValue(info,"price")+"]变更为["+price+"]");
        //日志写入
        trace.insert();
    }

    @Override
    public synchronized void disWork(String wid) {
        Map info = workDao.getWorkInfo(wid);

        Trace trace = new Trace();
        trace.setOid(MapUtils.getString(info,"oid"));
        trace.setCreatetime(new Date());
        trace.setDoer(ShiroKit.getUser().getId());
        trace.setDoername(ShiroKit.getUser().getName());
        trace.setCid(MapUtils.getString(info,"cid"));
        trace.setCusname(MapUtils.getString(info,"customname"));

        trace.setWid(wid);
        trace.setWorkname(MapUtils.getString(info,"businessname"));

        int fid =  MapUtils.getIntValue(info,"fid");
        String state = MapUtils.getString(info,"flowname");
        trace.setBs(fid);  //新增业务上一业务状态为最小值
        trace.setCs(fid);  //新增业务当前务状态为最小值
        trace.setBsname(state);
        trace.setCsname(state);
        trace.setMsg("时间:"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(trace.getCreatetime())
                +"    操作员:"+trace.getDoername()+ "退办业务["+trace.getWorkname()+"]    状态为:"+trace.getCsname());
        //日志写入
        trace.insert();
        workDao.disWork(wid);
    }
}
