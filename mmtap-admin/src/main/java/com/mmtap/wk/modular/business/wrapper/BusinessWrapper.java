package com.mmtap.wk.modular.business.wrapper;

import com.mmtap.wk.common.constant.factory.ConstantFactory;
import com.mmtap.wk.core.base.warpper.BaseControllerWarpper;

import java.util.Map;

public class BusinessWrapper extends BaseControllerWarpper {

    public BusinessWrapper(Object list) {
        super(list);
    }
    @Override
    protected void warpTheMap(Map<String, Object> map) {
        Integer uid = (Integer) map.get("creater");
        map.put("createName", ConstantFactory.me().getUserNameById(uid));
    }
}
