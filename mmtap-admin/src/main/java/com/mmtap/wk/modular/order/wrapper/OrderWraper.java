package com.mmtap.wk.modular.order.wrapper;

import com.mmtap.wk.common.constant.factory.ConstantFactory;
import com.mmtap.wk.core.base.warpper.BaseControllerWarpper;
import com.mmtap.wk.core.shiro.ShiroKit;
import com.mmtap.wk.modular.business.wrapper.BusinessWrapper;

import java.util.List;
import java.util.Map;

public class OrderWraper extends BaseControllerWarpper {

    public OrderWraper(Object list) {
        super(list);
    }

    @Override
    protected void warpTheMap(Map<String, Object> map) {
        Integer uid = (Integer) map.get("creater");
        map.put("createName", ConstantFactory.me().getUserNameById(uid));
        String cid = map.get("cid").toString();
        map.put("customName",ConstantFactory.me().getCustomName(cid));
    }
}
