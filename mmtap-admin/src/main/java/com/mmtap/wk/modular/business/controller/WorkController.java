package com.mmtap.wk.modular.business.controller;

import com.mmtap.wk.core.base.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * 业务中心控制器
 *
 * @author mmtap.com
 * @Date 2017-11-13 22:57:57
 */
@Controller
@RequestMapping("/work")
public class WorkController extends BaseController {

    private String PREFIX = "/business/work/";

    /**
     * 跳转到业务中心首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "work.html";
    }

    /**
     * 跳转到添加业务中心
     */
    @RequestMapping("/work_add")
    public String workAdd() {
        return PREFIX + "work_add.html";
    }

    /**
     * 跳转到修改业务中心
     */
    @RequestMapping("/work_update/{workId}")
    public String workUpdate(@PathVariable Integer workId, Model model) {
        return PREFIX + "work_edit.html";
    }

    /**
     * 获取业务中心列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        return null;
    }

    /**
     * 新增业务中心
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add() {
        return super.SUCCESS_TIP;
    }

    /**
     * 删除业务中心
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete() {
        return SUCCESS_TIP;
    }


    /**
     * 修改业务中心
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update() {
        return super.SUCCESS_TIP;
    }

    /**
     * 业务中心详情
     */
    @RequestMapping(value = "/detail")
    @ResponseBody
    public Object detail() {
        return null;
    }
}
