package com.mmtap.wk.modular.order.wrapper;

import com.mmtap.wk.common.constant.factory.ConstantFactory;
import com.mmtap.wk.core.base.warpper.BaseControllerWarpper;
import io.swagger.models.auth.In;

import java.util.List;
import java.util.Map;

public class CustomWrapper extends BaseControllerWarpper {
    public CustomWrapper(List cusList) {
        super(cusList);
    }

    @Override
    protected void warpTheMap(Map<String, Object> map) {
        Integer uid = (Integer)map.get("creater");
        String creater = ConstantFactory.me().getUserNameById(uid);
        map.put("createrName",creater);
    }
}
