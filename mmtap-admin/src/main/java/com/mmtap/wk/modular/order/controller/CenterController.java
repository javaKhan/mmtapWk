package com.mmtap.wk.modular.order.controller;

import com.mmtap.wk.core.base.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * 订单中心控制器
 *
 * @author mmtap.com
 * @Date 2017-11-13 23:05:27
 */
@Controller
@RequestMapping("/center")
public class CenterController extends BaseController {

    private String PREFIX = "/order/center/";

    /**
     * 跳转到订单中心首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "center.html";
    }

    /**
     * 跳转到添加订单中心
     */
    @RequestMapping("/center_add")
    public String centerAdd() {
        return PREFIX + "center_add.html";
    }

    /**
     * 跳转到修改订单中心
     */
    @RequestMapping("/center_update/{centerId}")
    public String centerUpdate(@PathVariable Integer centerId, Model model) {
        return PREFIX + "center_edit.html";
    }

    /**
     * 获取订单中心列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        return null;
    }

    /**
     * 新增订单中心
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add() {
        return super.SUCCESS_TIP;
    }

    /**
     * 删除订单中心
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete() {
        return SUCCESS_TIP;
    }


    /**
     * 修改订单中心
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update() {
        return super.SUCCESS_TIP;
    }

    /**
     * 订单中心详情
     */
    @RequestMapping(value = "/detail")
    @ResponseBody
    public Object detail() {
        return null;
    }
}
