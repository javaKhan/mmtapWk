package com.mmtap.wk.modular.order.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.mmtap.wk.common.constant.factory.ConstantFactory;
import com.mmtap.wk.common.exception.BizExceptionEnum;
import com.mmtap.wk.common.exception.BussinessException;
import com.mmtap.wk.core.base.controller.BaseController;
import com.mmtap.wk.core.shiro.ShiroKit;
import com.mmtap.wk.core.util.ToolUtil;
import com.mmtap.wk.modular.business.dao.ManageDao;
import com.mmtap.wk.modular.business.dao.TraceDao;
import com.mmtap.wk.modular.business.model.Trace;
import com.mmtap.wk.modular.business.wrapper.BusinessWrapper;
import com.mmtap.wk.modular.order.dao.CustomDao;
import com.mmtap.wk.modular.order.dao.IndentDao;
import com.mmtap.wk.modular.order.dao.WorkDao;
import com.mmtap.wk.modular.order.model.Custom;
import com.mmtap.wk.modular.order.model.Indent;
import com.mmtap.wk.modular.order.model.Work;
import com.mmtap.wk.modular.order.service.IOrderService;
import com.mmtap.wk.modular.order.utils.AllOrderExcel;
import com.mmtap.wk.modular.order.utils.ExcelView;
import com.mmtap.wk.modular.order.utils.OrderUtil;
import com.mmtap.wk.modular.order.utils.SigOrderExcel;
import com.mmtap.wk.modular.order.wrapper.OrderWraper;
import com.mmtap.wk.modular.order.wrapper.WorkWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 订单控制器
 *
 * @author mmtap.com
 * @Date 2017-11-18 17:48:15
 */
@Controller
@RequestMapping("/order")
public class OrderController extends BaseController {

    private String PREFIX = "/order/order/";

    @Autowired
    private IOrderService orderService;

    @Autowired
    private CustomDao customDao;
    @Autowired
    private WorkDao workDao;

    @Autowired
    private ManageDao busDao;
    @Autowired
    private IndentDao indentDao;
    @Autowired
    private TraceDao traceDao;



    /**
     * 跳转到我的订单
     */
    @RequestMapping("/myorder")
    public String myorder() {
        return PREFIX + "myorder.html";
    }


    /**
     * 跳转到订单首页
     */
    @RequestMapping("")
    public String index(Model model) {
        Indent indent = new Indent();
        List orderList = indent.selectAll();
        model.addAttribute("orderList",orderList);
        return PREFIX + "order.html";
    }


    /**
     * 跳转到添加订单
     */
    @RequestMapping("/order_add")
    public String orderAdd(Model model) {
        List busList = busDao.list(null);
        Object busWrapList = super.warpObject(new BusinessWrapper(busList));
        model.addAttribute("busList",busWrapList);
        return PREFIX + "order_add.html";
    }

    /**
     * 跳转到订单状态
     */
    @RequestMapping("/order_state/{orderId}")
    public String orderState(@PathVariable String orderId, Model model) {
        if (ToolUtil.isEmpty(orderId)) {
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }
        Indent indent = indentDao.selectById(orderId);

        if(null!=indent && null!=indent.getCid() && !"".equals(indent.getCid())){
            Custom custom = customDao.selectById(indent.getCid());
            model.addAttribute("custom",custom);
            model.addAttribute("order",indent);
            model.addAttribute("createName", ConstantFactory.me().getUserNameById(indent.getCreater()));

            List workList = workDao.selectMaps(new EntityWrapper<Work>().eq("oid",orderId));
            Object workWrapList = super.warpObject(new WorkWrapper(workList));
            model.addAttribute("works",workWrapList);
        }
        return PREFIX + "order_state.html";
    }

    /**
     * 订单跟踪
     */
    @RequestMapping("/order_trace/{orderId}")
    public String orderTrace(@PathVariable String orderId, Model model) {
        List<Trace> list = traceDao.selectList(new EntityWrapper<Trace>().eq("oid",orderId).orderBy("createtime"));
        model.addAttribute("traces",list);
        return PREFIX + "order_trace.html";
    }



    /**
     * 获取订单列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        List<Map<String,Object>> list = this.indentDao.list(condition);
        return super.warpObject(new OrderWraper(list));
    }

    /**
     * 新增订单
     */
    @RequestMapping(value = "/add")
    public Object add(Custom custom, Integer[] buss,String ordcom) {
        //生成客户ID
        String cid = OrderUtil.createCustomID();
        custom.setCid(cid);

        //生成订单对象
        Indent order = new Indent();
        String oid = OrderUtil.createOrderID();
        order.setOid(oid);
        order.setCid(custom.getCid());
        Integer creater = ShiroKit.getUser().getId();
        order.setCreater(creater);
        order.setCreatetime(new Date());
        order.setComments(ordcom);
        //判断业务
        orderService.newOrder(custom,buss,order);
        return PREFIX+"order_result.html";
    }

    @RequestMapping(value = "/detail/{oid}")
    public Object orderDetail(@PathVariable String oid,Model model){
        Map map = new HashMap();

        return PREFIX+"order_detail.html";
    }

    /**
     * 删除订单
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete() {
        return SUCCESS_TIP;
    }


    /**
     * 修改订单
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update() {
        return super.SUCCESS_TIP;
    }



    /**
     * 备份操作页
     */
    @RequestMapping(value = "/backup")
    public Object orderBackup() {
        return PREFIX+"order_backup.html";
    }

    /**
     * 单条备份处理
     */
    @RequestMapping(value = "/baksig/{oid}")
    public ModelAndView orderBackupSig(@PathVariable String oid) {
        Map<String, Object> map = orderService.bakSig(oid);
        map.put("filename", new SimpleDateFormat("yyyyMMddHHmm").format(new Date())+"-"+oid);
        ExcelView excelView = new SigOrderExcel();
        return new ModelAndView(excelView, map);
    }

    /**
     * 单条备份处理
     */
    @RequestMapping(value = "/bakall")
    public Object orderBackupAll() {
        Map map = new HashMap();
        map.put("name","all-order");
        ExcelView excelView = new AllOrderExcel();
        return new ModelAndView(excelView,map);
    }
}
