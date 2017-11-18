package com.mmtap.wk.modular.business.controller;

import com.mmtap.wk.core.base.controller.BaseController;
import com.mmtap.wk.core.shiro.ShiroKit;
import com.mmtap.wk.modular.business.dao.ManageDao;
import com.mmtap.wk.modular.business.model.Business;
import com.mmtap.wk.modular.business.wrapper.BusinessWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 业务中心控制器
 *
 * @author mmtap.com
 * @Date 2017-11-13 22:58:10
 */
@Controller
@RequestMapping("/manage")
public class ManageController extends BaseController {

    private String PREFIX = "/business/manage/";

    @Autowired
    private ManageDao manageDao;

    /**
     * 跳转到业务中心首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "manage.html";
    }

    /**
     * 跳转到添加业务
     */
    @RequestMapping("/manage_add")
    public String manageAdd() {
        return PREFIX + "manage_add.html";
    }

    /**
     * 跳转到修改业务中心
     */
    @RequestMapping("/manage_update/{manageId}")
    public String manageUpdate(@PathVariable Integer manageId, Model model) {
        return PREFIX + "manage_edit.html";
    }

    /**
     * 获取业务类型列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        List<Map<String, Object>> list = this.manageDao.list(condition);
        return super.warpObject(new BusinessWrapper(list));
    }

    /**
     * 新增业务中心
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(@RequestParam String businessname) {
        Business bo = new Business();
        bo.setCreater(ShiroKit.getUser().getId());
        bo.setCreatetime(new Date());
        bo.setBusinessname(businessname);
        bo.insert();
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

    /**
     * 业务状态管理页
     */
    @RequestMapping("/flow")
    public String flow() {
        return PREFIX + "flow.html";
    }


}
