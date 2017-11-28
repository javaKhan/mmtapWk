package com.mmtap.wk.modular.order.controller;

import com.alibaba.fastjson.JSON;
import com.mmtap.wk.common.exception.BizExceptionEnum;
import com.mmtap.wk.common.exception.BussinessException;
import com.mmtap.wk.core.base.controller.BaseController;
import com.mmtap.wk.core.util.ToolUtil;
import com.mmtap.wk.modular.business.model.Prop;
import com.mmtap.wk.modular.order.model.Info;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * 业务内容控制器
 *
 * @author mmtap.com
 * @Date 2017-11-28 14:11:11
 */
@Controller
@RequestMapping("/info")
public class InfoController extends BaseController {

    private String PREFIX = "/order/info/";

    /**
     * 跳转到业务内容首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "info.html";
    }

//    /**
//     * 跳转到添加业务内容
//     */
//    @RequestMapping("/info_add")
//    public String infoAdd() {
//        return PREFIX + "info_add.html";
//    }

//    /**
//     * 跳转到修改业务内容
//     */
//    @RequestMapping("/info_update/{infoId}")
//    public String infoUpdate(@PathVariable Integer infoId, Model model) {
//        return PREFIX + "info_edit.html";
//    }

    /**
     * 获取业务内容列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        return null;
    }

    /**
     * 新增业务内容
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(@RequestParam Map map) {
        if (ToolUtil.isEmpty(map)|| ToolUtil.isEmpty(map.get("wid"))){
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }
        String wid = map.get("wid").toString();
        String propStr = JSON.toJSONString(map);
        Info info = new Info();
        info.setWid(wid);
        info.setInfo(propStr);
        info.insert();
        return super.SUCCESS_TIP;
    }

    /**
     * 删除业务内容
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete() {
        return SUCCESS_TIP;
    }


    /**
     * 修改业务内容
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update() {
        return super.SUCCESS_TIP;
    }

    /**
     * 业务内容详情
     */
    @RequestMapping(value = "/detail")
    @ResponseBody
    public Object detail() {
        return null;
    }
}
