package com.mmtap.wk.modular.order.controller;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.mmtap.wk.common.exception.BizExceptionEnum;
import com.mmtap.wk.common.exception.BussinessException;
import com.mmtap.wk.core.base.controller.BaseController;
import com.mmtap.wk.core.base.tips.ErrorTip;
import com.mmtap.wk.core.shiro.ShiroKit;
import com.mmtap.wk.core.util.ToolUtil;
import com.mmtap.wk.modular.business.dao.FlowDao;
import com.mmtap.wk.modular.business.dao.PropDao;
import com.mmtap.wk.modular.business.model.Flow;
import com.mmtap.wk.modular.business.model.Prop;
import com.mmtap.wk.modular.order.dao.InfoDao;
import com.mmtap.wk.modular.order.dao.WorkDao;
import com.mmtap.wk.modular.order.model.Info;
import com.mmtap.wk.modular.order.service.IWorkService;
import com.mmtap.wk.modular.order.wrapper.WorkWrapper;
import org.apache.commons.collections.MapUtils;
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
    @Autowired
    private PropDao propDao;
    @Autowired
    private InfoDao infoDao;
    @Autowired
    private FlowDao flowDao;

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
     * 我的工作列表
     */
    @RequestMapping("/mywork")
    public String workLock(Model model) {
        Integer uid = ShiroKit.getUser().getId();
        List myWorks = workDao.getMyWorks(uid);
        model.addAttribute("myworks",myWorks);
        return PREFIX+"work_my.html";
    }


    /**
     * 跳转到处理工作
     */
    @RequestMapping("/work_do/{wid}")
    public String workDo(@PathVariable String wid,Model model) {
        if(ToolUtil.isEmpty(wid)){
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }
        Map workInfo = workDao.getWorkInfo(wid);
        model.addAttribute("workInfo",workInfo);

        List<Map<String, Object>> props = propDao.selectMaps(new EntityWrapper<Prop>().eq("bid",workInfo.get("bid")).orderBy("proporder"));
        model.addAttribute("props",props);

        List flowList = flowDao.selectList(new EntityWrapper<Flow>().eq("bid",workInfo.get("bid")).orderBy("floworder"));
        model.addAttribute("flows",flowList);
        Info info = infoDao.selectById(wid);
        if(null!=info && !"".equals(info.getInfo())){
            Map infos = JSON.parseObject(info.getInfo());
            for (Map prop : props){
                String key = prop.get("title").toString();
                Object text = infos.get(key);
                if(null!=text){
                    prop.put("text",infos.get(key));
                }
            }
        }
        return PREFIX + "work_do.html";
    }

    /**
     * 保存业务的备注信息
     * @param wid
     * @param workcom
     * @return
     */
    @RequestMapping("/workcom")
    @ResponseBody
    public Object saveWorkComments(@RequestParam String wid,String workcom){
        if(ToolUtil.isEmpty(workcom)){
            throw new BussinessException(BizExceptionEnum.TEXT_NULL);
        }
        workDao.saveWorkCom(wid,workcom);
        return SUCCESS_TIP;
    }

    /**
     * 退办工作
     */
    @RequestMapping("/work_dis/{wid}")
    @ResponseBody
    public Object workDis(@PathVariable String wid) {
        if(ToolUtil.isEmpty(wid)){
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }
        workDao.disWork(wid);
        return SUCCESS_TIP;
    }

    /**
     * 工作流程完结，流程跳转
     * @return
     */
    @RequestMapping("/next/{wid}")
    public String workNext(@PathVariable String wid){
        if(ToolUtil.isEmpty(wid)){
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }
        boolean result = workService.nextStep(wid);
        return PREFIX + "work_edit.html";
    }

    @RequestMapping("/newprice")
    @ResponseBody
    public Object newprice(@RequestParam String wid,@RequestParam double price){
        workService.newprice(wid,price);
        return SUCCESS_TIP;
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
