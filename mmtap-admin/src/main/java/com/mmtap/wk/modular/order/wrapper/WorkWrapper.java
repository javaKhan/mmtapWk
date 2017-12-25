package com.mmtap.wk.modular.order.wrapper;

import com.mmtap.wk.common.constant.factory.ConstantFactory;
import com.mmtap.wk.core.base.warpper.BaseControllerWarpper;
import com.mmtap.wk.modular.order.utils.CommentUtil;
import org.apache.commons.collections.MapUtils;

import java.util.List;
import java.util.Map;

public class WorkWrapper extends BaseControllerWarpper {
    public WorkWrapper(List<Map<String,Object>> workList) {
        super(workList);
    }

    @Override
    protected void warpTheMap(Map<String, Object> map) {

        Integer bid = (Integer)map.get("bid");
        Integer fid = (Integer)map.get("fid");

        Integer uid = (Integer) map.get("creater");
        map.put("createName", ConstantFactory.me().getUserNameById(uid));
        map.put("business",ConstantFactory.me().getBusinessInfo(bid));
        map.put("flow",ConstantFactory.me().getFlowInfo(fid));
        map.put("wcoms", CommentUtil.toComObj(MapUtils.getString(map,"workcom")));
    }
}
