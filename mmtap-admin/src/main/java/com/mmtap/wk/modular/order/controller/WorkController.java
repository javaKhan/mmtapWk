package com.mmtap.wk.modular.order.controller;


import com.mmtap.wk.core.base.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * 工作控制器
 *
 * @author mmtap.com
 * @Date 2017-11-18 17:50:20
 */
@Controller
@RequestMapping("/work")
public class WorkController extends BaseController {

    private String PREFIX = "/order/work/";

    /**
     * 跳转到工作首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "work.html";
    }

    /**
     * 跳转到添加工作
     */
    @RequestMapping("/work_add")
    public String workAdd() {
        return PREFIX + "work_add.html";
    }

    /**
     * 跳转到修改工作
     */
    @RequestMapping("/work_update/{workId}")
    public String workUpdate(@PathVariable Integer workId, Model model) {
        return PREFIX + "work_edit.html";
    }

    /**
     * 获取工作列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        return null;
    }

    /**
     * 新增工作
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add() {
        return super.SUCCESS_TIP;
    }

    /**
     * 删除工作
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete() {
        return SUCCESS_TIP;
    }


    /**
     * 修改工作
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update() {
        return super.SUCCESS_TIP;
    }

    /**
     * 工作详情
     */
    @RequestMapping(value = "/detail")
    @ResponseBody
    public Object detail() {
        return null;
    }
}
