package com.mmtap.wk.modular.order.controller;


import com.mmtap.wk.common.exception.BizExceptionEnum;
import com.mmtap.wk.common.exception.BussinessException;
import com.mmtap.wk.core.base.controller.BaseController;
import com.mmtap.wk.core.base.tips.ErrorTip;
import com.mmtap.wk.core.shiro.ShiroKit;
import com.mmtap.wk.core.util.ToolUtil;
import com.mmtap.wk.modular.order.dao.WorkDao;
import com.mmtap.wk.modular.order.model.Work;
import com.mmtap.wk.modular.order.service.IWorkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

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

    @Autowired
    private IWorkService workService;
    @Autowired
    private WorkDao workDao;


    /**
     * 跳转到工作首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "work.html";
    }

    /**
     * 受理工作
     */
    @RequestMapping("/work_lock")
    @ResponseBody
    public Object workLock(@RequestParam String wid) {
        Integer uid = ShiroKit.getUser().getId();
        int res = workService.lockWork(wid,uid);
        if(res>0){
            return SUCCESS_TIP;
        }
        return new ErrorTip(100,"工作已经被受理或发生异常！");
    }

    /**
     * 受理工作
     */
    @RequestMapping("/mywork")
    public String workLock() {
        Integer uid = ShiroKit.getUser().getId();
        List myWorks = workDao.getMyWorks(uid);
        return PREFIX+"work_my.html";
    }


    /**
     * 跳转到处理工作
     */
    @RequestMapping("/work_do")
    public String workDo(@RequestParam Map map) {
        if (ToolUtil.isEmpty(map) &&
                null!=map.get("wid") &&
                null!=map.get("cid") &&
                null!=map.get("bid") &&
                null!=map.get("fid")) {
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }
        String wid = map.get("wid").toString();
        Work work = workDao.selectById(wid);


        return PREFIX + "work_do.html";
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
        List todoWorks = workService.getTodoWorks();
        return todoWorks;
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