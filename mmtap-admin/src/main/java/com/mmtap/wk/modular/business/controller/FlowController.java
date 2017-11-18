package com.mmtap.wk.modular.business.controller;

import com.mmtap.wk.common.constant.cache.Cache;
import com.mmtap.wk.common.constant.factory.ConstantFactory;
import com.mmtap.wk.common.exception.BizExceptionEnum;
import com.mmtap.wk.common.exception.BussinessException;
import com.mmtap.wk.core.base.controller.BaseController;
import com.mmtap.wk.core.cache.CacheKit;
import com.mmtap.wk.core.shiro.ShiroKit;
import com.mmtap.wk.core.util.ToolUtil;
import com.mmtap.wk.modular.business.dao.FlowDao;
import com.mmtap.wk.modular.business.dao.ManageDao;
import com.mmtap.wk.modular.business.model.Business;
import com.mmtap.wk.modular.business.model.Flow;
import com.mmtap.wk.modular.business.wrapper.FlowWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 业务状态控制器
 *
 * @author mmtap.com
 * @Date 2017-11-15 18:42:05
 */
@Controller
@RequestMapping("/flow")
public class FlowController extends BaseController {

    private String PREFIX = "/business/flow/";

    @Autowired
    private  ManageDao manageDao;
    @Autowired
    private FlowDao flowDao;


    /**
     * 跳转到业务的状态列表
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "flow.html";
    }

    /**
     * 跳转到添加业务状态
     */
    @RequestMapping("/flow_add")
    public String flowAdd() {
        return PREFIX + "flow_add.html";
    }

    /**
     * 跳转到修改业务状态
     */
    @RequestMapping("/flow_update/{flowId}")
    public String flowUpdate(@PathVariable Integer flowId, Model model) {
        if (ToolUtil.isEmpty(flowId)) {
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }
        Flow flow = flowDao.selectById(flowId);
        model.addAttribute("flow",flow);
        model.addAttribute("roleName", ConstantFactory.me().getSingleRoleName(flow.getFlowrole()));
        return PREFIX + "flow_edit.html";
    }

    /**
     * 获取业务状态列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(@RequestParam Integer bid) {
        List flowList = flowDao.listByBid(bid);
        return super.warpObject(new FlowWrapper(flowList)) ;//FlowWrapper(flowList);
    }

    /**
     * 新增业务状态
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(Flow flow) {
        if(ToolUtil.isOneEmpty(flow,flow.getBid(),flow.getFlowname())){
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }
        flow.setCreater(ShiroKit.getUser().getId());
        flow.setCreatetime(new Date());
        flow.insert();
        return super.SUCCESS_TIP;
    }

    /**
     * 删除业务状态
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete() {
        return SUCCESS_TIP;
    }


    /**
     * 修改业务状态
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(Flow flow) {
        flow.setCreater(ShiroKit.getUser().getId());
        flow.setCreatetime(new Date());
        flow.updateById();

        CacheKit.removeAll(Cache.CONSTANT);
        return super.SUCCESS_TIP;
    }

    /**
     * 业务状态详情
     */
    @RequestMapping(value = "/flowstate")
    public Object detail(@RequestParam Integer bid) {
        Business business = this.manageDao.selectById(bid);
        super.setAttr("business",business);
        List<Map<String,Object>> busFlows = this.flowDao.listByBid(bid);
        super.setAttr("flowList",busFlows);
        return PREFIX + "flowstate.html";
    }
}
