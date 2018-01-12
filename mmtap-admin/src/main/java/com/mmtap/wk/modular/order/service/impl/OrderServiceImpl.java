package com.mmtap.wk.modular.order.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.mmtap.wk.common.constant.factory.ConstantFactory;
import com.mmtap.wk.common.persistence.model.User;
import com.mmtap.wk.core.shiro.ShiroKit;
import com.mmtap.wk.modular.business.dao.TraceDao;
import com.mmtap.wk.modular.business.model.Business;
import com.mmtap.wk.modular.business.model.Flow;
import com.mmtap.wk.modular.business.model.Trace;
import com.mmtap.wk.modular.order.dao.CustomDao;
import com.mmtap.wk.modular.order.dao.IndentDao;
import com.mmtap.wk.modular.order.dao.InfoDao;
import com.mmtap.wk.modular.order.dao.WorkDao;
import com.mmtap.wk.modular.order.model.Custom;
import com.mmtap.wk.modular.order.model.Indent;
import com.mmtap.wk.modular.order.model.Work;
import com.mmtap.wk.modular.order.service.MailService;
import com.mmtap.wk.modular.order.utils.MailThread;
import com.mmtap.wk.modular.order.utils.OrderUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mmtap.wk.modular.order.service.IOrderService;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 订单Service
 *
 * @author mmtap.com
 * @Date 2017-11-18 17:48:15
 */
@Service
public class OrderServiceImpl implements IOrderService {
    @Autowired
    private IndentDao indentDao;
    @Autowired
    private CustomDao customDao;
    @Autowired
    private WorkDao workDao;
    @Autowired
    private InfoDao infoDao;
    @Autowired
    private TraceDao traceDao;
    @Autowired
    private MailService mailService;


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
                Business business = ConstantFactory.me().getBusinessInfo(bid);
                work.setPrice(business.getBusprice());
                work.setCid(custom.getCid());
                work.setCreater(order.getCreater());
                work.setCreatetime(order.getCreatetime());
                work.insert();

                //邮件提醒
                MailThread mailThread = new MailThread(mailService,work.getWid());
//                mailService.sendHtmlMail(work.getWid());

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


    @Override
    public Map bakSig(String oid) {
        //有订单没客户会有问题－就是产生了脏数据
        Map result = indentDao.bakOneOrderBase(oid);
        List workList = indentDao.bakOneOrderWorks(oid);
        result.put("works",workList);
        return result;
    }

    @Override
    public List bakBatch(Date bdate,Date edate) {
        List result = new ArrayList();
        List<String> orders = indentDao.bakBatch(bdate,edate);
        for (String oid : orders){
            result.add(bakSig(oid));
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    @Override
    public List<Map> listInfo(String condition) {
        return indentDao.listInfo(condition);
    }

    @Override
    @Transactional
    public void deleteOrder(String orderId) {
        infoDao.deleteInfoByOrderId(orderId);
        workDao.delete(new EntityWrapper<Work>().eq("oid",orderId));
        traceDao.delete(new EntityWrapper<Trace>().eq("oid",orderId));
        indentDao.deleteById(orderId);
    }
}
