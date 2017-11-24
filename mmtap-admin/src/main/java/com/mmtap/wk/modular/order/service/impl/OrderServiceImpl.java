package com.mmtap.wk.modular.order.service.impl;

import com.mmtap.wk.common.constant.factory.ConstantFactory;
import com.mmtap.wk.core.shiro.ShiroKit;
import com.mmtap.wk.modular.business.model.Flow;
import com.mmtap.wk.modular.business.model.Trace;
import com.mmtap.wk.modular.order.dao.CustomDao;
import com.mmtap.wk.modular.order.dao.IndentDao;
import com.mmtap.wk.modular.order.dao.WorkDao;
import com.mmtap.wk.modular.order.model.Custom;
import com.mmtap.wk.modular.order.model.Indent;
import com.mmtap.wk.modular.order.model.Work;
import com.mmtap.wk.modular.order.utils.OrderUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mmtap.wk.modular.order.service.IOrderService;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 订单Service
 *
 * @author mmtap.com
 * @Date 2017-11-18 17:48:15
 */
@Service
public class OrderServiceImpl implements IOrderService {
//    @Autowired
//    private IndentDao orderDao;
//    @Autowired
//    private CustomDao customDao;
//    @Autowired
//    private WorkDao workDao;

    @Transactional
    public void newOrder(Custom custom, Integer[] buss, Indent order) {
        custom.setCreater(order.getCreater());
        custom.setCreatetime(order.getCreatetime());
        custom.insert();
        order.insert();
        if(null != buss){
            for(Integer bid : buss){
                /**
                 * 业务数据部分
                 */
                Work work = new Work();
                work.setWid(OrderUtil.createWorkId()); //
                work.setOid(order.getOid());
                work.setBid(bid);

                Integer fid = ConstantFactory.me().getFirstFlowId(bid);
                if(null!=fid){
                    work.setFid(fid);
                }

                work.setCid(custom.getCid());
                work.setCreater(order.getCreater());
                work.setCreatetime(order.getCreatetime());
                work.insert();

                /**
                 *  业务日志部分
                 *  */
                Trace trace = new Trace();
                trace.setOid(order.getOid());
                trace.setCreatetime(new Date());
                trace.setDoer(ShiroKit.getUser().getId());
                trace.setDoername(ShiroKit.getUser().getName());
                trace.setCid(work.getCid());
                trace.setCusname(custom.getCustomname());

                trace.setWid(work.getWid());
                trace.setWorkname(ConstantFactory.me().getBusinessInfo(work.getBid()).getBusinessname());

                //新增业务时－业务日志纪录
//                Integer flowId =ConstantFactory.me().getFlowInfo(work.getBid()).getFid();
                if(null==work.getFid()){ //有业务了，还没有设置业务状态时
                    //TODO 没有业务状态的业务如何处理
                    trace.setMsg("时间:"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(trace.getCreatetime())
                            +"    操作员:"+trace.getDoername()+ "生成新订单:（该业务未配置流程） 业务["+trace.getWorkname()+"]    状态为:"+trace.getCsname());
                }else {
                    Flow flow = ConstantFactory.me().getFlowInfo(work.getFid());
                    trace.setBs(flow.getFid());  //新增业务上一业务状态为最小值
                    trace.setCs(flow.getFid());  //新增业务当前务状态为最小值
                    trace.setBsname(flow.getFlowname());
                    trace.setCsname(flow.getFlowname());
                    trace.setMsg("时间:"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(trace.getCreatetime())
                            +"    操作员:"+trace.getDoername()+ "生成新订单:业务["+trace.getWorkname()+"]    状态为:"+trace.getCsname());
                }

                trace.insert();


            }
        }
    }
}
