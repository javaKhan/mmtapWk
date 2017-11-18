package com.mmtap.wk.modular.business.wrapper;

import com.mmtap.wk.common.constant.factory.ConstantFactory;
import com.mmtap.wk.core.base.warpper.BaseControllerWarpper;

import java.util.Map;

public class FlowWrapper extends BaseControllerWarpper {
    public FlowWrapper(Object list){
        super(list);
    }

    @Override
    protected void warpTheMap(Map<String, Object> map) {
        Integer uid = (Integer)map.get("creater");
        if(null!=uid ){
            map.put("createrName", ConstantFactory.me().getUserNameById(uid));
        }
        Integer rid = (Integer)map.get("flowrole");
        if(null!=rid){
            map.put("roleName",ConstantFactory.me().getSingleRoleName(rid));
        }
    }
}
