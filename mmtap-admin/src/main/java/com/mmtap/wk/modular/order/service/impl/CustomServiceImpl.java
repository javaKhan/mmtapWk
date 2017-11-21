package com.mmtap.wk.modular.order.service.impl;

import com.mmtap.wk.modular.order.model.Indent;
import com.mmtap.wk.modular.order.model.Work;
import org.springframework.stereotype.Service;
import com.mmtap.wk.modular.order.service.ICustomService;
import org.springframework.transaction.annotation.Transactional;

/**
 * 客户Service
 *
 * @author fengshuonan
 * @Date 2017-11-18 17:48:58
 */
@Service
public class CustomServiceImpl implements ICustomService {

    @Transactional
    public void appendWord(String cid, Integer[] buss, Indent order) {
        order.insert();
        if(null != buss){
            for(Integer bid : buss){
                Work work = new Work();
                work.setOid(order.getOid());
                work.setBid(bid);
                work.setCid(cid);
                work.setCreater(order.getCreater());
                work.setCreatetime(order.getCreatetime());
                work.insert();
            }
        }
    }
}
