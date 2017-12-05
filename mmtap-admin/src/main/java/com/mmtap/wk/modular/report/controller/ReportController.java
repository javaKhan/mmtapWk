package com.mmtap.wk.modular.report.controller;

import com.mmtap.wk.core.base.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * 统计报表控制器
 *
 * @author mmtap.com
 * @Date 2017-11-13 23:06:01
 */
@Controller
@RequestMapping("/report")
public class ReportController extends BaseController {

    private String PREFIX = "/report/report/";

    /**
     * 业务统计
     */
    @RequestMapping("")
    public String index() {
        //个人单日处理业务
        //
        return PREFIX + "report.html";
    }

    /**
     * 数据报表
     */
    @RequestMapping("/daily")
    public String reportDaily() {
        return PREFIX + "report_daily.html";
    }






    /**
     * 跳转到修改统计报表
     */
    @RequestMapping("/report_update/{reportId}")
    public String reportUpdate(@PathVariable Integer reportId, Model model) {
        return PREFIX + "report_edit.html";
    }

    /**
     * 获取统计报表列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        return null;
    }

    /**
     * 新增统计报表
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add() {
        return super.SUCCESS_TIP;
    }

    /**
     * 删除统计报表
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete() {
        return SUCCESS_TIP;
    }


    /**
     * 修改统计报表
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update() {
        return super.SUCCESS_TIP;
    }

    /**
     * 统计报表详情
     */
    @RequestMapping(value = "/detail")
    @ResponseBody
    public Object detail() {
        return null;
    }
}
