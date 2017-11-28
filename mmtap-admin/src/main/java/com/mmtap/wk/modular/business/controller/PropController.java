package com.mmtap.wk.modular.business.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.mmtap.wk.common.exception.BizExceptionEnum;
import com.mmtap.wk.common.exception.BussinessException;
import com.mmtap.wk.core.base.controller.BaseController;
import com.mmtap.wk.core.util.ToolUtil;
import com.mmtap.wk.modular.business.dao.ManageDao;
import com.mmtap.wk.modular.business.dao.PropDao;
import com.mmtap.wk.modular.business.model.Business;
import com.mmtap.wk.modular.business.model.Prop;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 属性控制器
 *
 * @author mmtap.com
 * @Date 2017-11-28 14:24:18
 */
@Controller
@RequestMapping("/prop")
public class PropController extends BaseController {

    private String PREFIX = "/business/prop/";

    @Autowired
    private PropDao propDao;
    @Autowired
    private ManageDao manageDao;


    /**
     * 跳转到属性首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "prop.html";
    }


    /**
     * 跳转到属性首页
     */
    @RequestMapping("/prop_list")
    public String propList(@RequestParam Integer bid,Model model) {
        if (ToolUtil.isEmpty(bid)) {
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }
        Business business = this.manageDao.selectById(bid);
        super.setAttr("business",business);

//        List propList = propDao.selectList(new EntityWrapper().eq("bid",bid));
//        model.addAttribute("propList",propList);
        return PREFIX + "prop_list.html";
    }

    /**
     * 跳转到添加属性
     */
    @RequestMapping("/prop_add")
    public String propAdd() {
        return PREFIX + "prop_add.html";
    }

    /**
     * 跳转到修改属性
     */
    @RequestMapping("/prop_update/{propId}")
    public String propUpdate(@PathVariable Integer propId, Model model) {
        return PREFIX + "prop_edit.html";
    }

    /**
     * 获取属性列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(@RequestParam Integer bid) {
        List propList = propDao.selectList(new EntityWrapper().eq("bid",bid));
        return propList;
    }

    /**
     * 新增属性
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(Prop prop) {
        if(ToolUtil.isOneEmpty(prop,prop.getBid(),prop.getTitle())){
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }
        prop.insert();
        return super.SUCCESS_TIP;
    }

    /**
     * 删除属性
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete() {
        return SUCCESS_TIP;
    }


    /**
     * 修改属性
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update() {
        return super.SUCCESS_TIP;
    }

    /**
     * 属性详情
     */
    @RequestMapping(value = "/detail")
    @ResponseBody
    public Object detail() {
        return null;
    }
}
