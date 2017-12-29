package com.mmtap.wk.modular.order.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.mmtap.wk.common.constant.factory.PageFactory;
import com.mmtap.wk.common.exception.BizExceptionEnum;
import com.mmtap.wk.common.exception.BussinessException;
import com.mmtap.wk.core.base.controller.BaseController;
import com.mmtap.wk.core.shiro.ShiroKit;
import com.mmtap.wk.core.util.ToolUtil;
import com.mmtap.wk.modular.business.dao.ManageDao;
import com.mmtap.wk.modular.business.wrapper.BusinessWrapper;
import com.mmtap.wk.modular.order.dao.CustomDao;
import com.mmtap.wk.modular.order.dao.IndentDao;
import com.mmtap.wk.modular.order.model.Custom;
import com.mmtap.wk.modular.order.model.Indent;
import com.mmtap.wk.modular.order.service.ICustomService;
import com.mmtap.wk.modular.order.utils.OrderUtil;
import com.mmtap.wk.modular.order.wrapper.CustomWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;

/**
 * 客户控制器
 *
 * @author mmtap.com
 * @Date 2017-11-18 17:48:58
 */
@Controller
@RequestMapping("/custom")
public class CustomController extends BaseController {

    private String PREFIX = "/order/custom/";

    @Autowired
    private CustomDao customDao;
    @Autowired
    private ICustomService customService;

    @Autowired
    private IndentDao indentDao;

    @Autowired
    private ManageDao busDao;

    /**
     * 跳转到客户首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "custom.html";
    }

    /**
     * 跳转到添加客户
     */
    @RequestMapping("/custom_add")
    public String customAdd() {
        return PREFIX + "custom_add.html";
    }

    /**
     * 跳转到修改客户
     */
    @RequestMapping("/custom_update/{customId}")
    public String customUpdate(@PathVariable String customId, Model model) {
        Custom custom = customDao.selectById(customId);
        model.addAttribute("custom",custom);
        return PREFIX + "custom_edit.html";
    }

    /**
     * 获取客户列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        Page page = new PageFactory().defaultPage();
//        customDao.listPage(page,condition);
        List cusList = customDao.list(condition);
        return super.warpObject(new CustomWrapper(cusList));
    }

    /**
     * 新增客户
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(Custom custom) {
        if (ToolUtil.isEmpty(custom.getCustomname())||ToolUtil.isEmpty(custom.getMobile())) {
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }
        custom.setCid(OrderUtil.createCustomID());
        custom.setCreater(ShiroKit.getUser().getId());
        custom.setCreatetime(new Date());
        custom.insert();
        return super.SUCCESS_TIP;
    }

    /**
     * 删除客户
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam String customId) {
        //如果该客户有订单的情况下不能删除
        int ords = indentDao.selectCount(new EntityWrapper<Indent>().eq("cid",customId));
        if(ords>0){
            throw  new BussinessException(BizExceptionEnum.ORDER_NOT_NULL);
        }
        customDao.deleteById(customId);
        return SUCCESS_TIP;
    }


    /**
     * 修改客户
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(Custom custom) {
        if (    ToolUtil.isEmpty(custom.getCid()) ||
                ToolUtil.isEmpty(custom.getCustomname())||
                ToolUtil.isEmpty(custom.getMobile())) {
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }
        custom.setCreater(ShiroKit.getUser().getId());
        custom.setCreatetime(new Date());
        customDao.updateCustom(custom);
        return super.SUCCESS_TIP;
    }

    /**
     * 客户详情
     */
    @RequestMapping(value = "/detail")
    @ResponseBody
    public Object detail() {
        return null;
    }


    @RequestMapping("/append/{customId}")
    public String appendWork(@PathVariable String customId,Model model){
        Custom custom = customDao.selectById(customId);
        List busList = busDao.list(null);
        Object busWrapList = super.warpObject(new BusinessWrapper(busList));
        model.addAttribute("busList",busWrapList);
        model.addAttribute("custom",custom);
        return PREFIX+"custom_append.html";
    }

    @RequestMapping(value = "/append/save")
    public Object appendWorkSave(@RequestParam String customId,Integer[] buss,String ordcom){
        Indent order = new Indent();
        order.setCid(customId);
        order.setOid(OrderUtil.createOrderID());
        order.setComments(ordcom);
        customService.appendWord(customId,buss,order);
        return super.SUCCESS_TIP;
    }

    @RequestMapping("/works/{customId}")
    public Object customWorks(@PathVariable String customId,Model model){
        List orderList = indentDao.listCustomOrders(customId);

        model.addAttribute("orders",orderList);
        return PREFIX+"custom_works.html";
    }
}
