package com.mmtap.wk.modular.order.service.impl;

import com.mmtap.wk.modular.order.dao.CustomDao;
import com.mmtap.wk.modular.order.dao.IndentDao;
import com.mmtap.wk.modular.order.dao.WorkDao;
import com.mmtap.wk.modular.order.model.Custom;
import com.mmtap.wk.modular.order.model.Indent;
import com.mmtap.wk.modular.order.model.Work;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mmtap.wk.modular.order.service.IOrderService;
import org.springframework.transaction.annotation.Transactional;

/**
 * 订单Service
 *
 * @author mmtap.com
 * @Date 2017-11-18 17:48:15
 */
@Service
public class OrderServiceImpl implements IOrderService {
    @Autowired
    private IndentDao orderDao;
    @Autowired
    private CustomDao customDao;
    @Autowired
    private WorkDao workDao;

    @Transactional
    public void newOrder(Custom custom, Integer[] buss, Indent order) {
        custom.setCreater(order.getCreater());
        custom.setCreatetime(order.getCreatetime());
        custom.insert();
        order.insert();
        if(null != buss){
            for(Integer bid : buss){
                Work work = new Work();
                work.setOid(order.getOid());
                work.setBid(bid);
                work.setCid(custom.getCid());
                work.setCreater(order.getCreater());
                work.setCreatetime(order.getCreatetime());
                work.insert();
            }
        }
    }
}
