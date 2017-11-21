package com.mmtap.wk.modular.order.service;

import com.mmtap.wk.modular.order.model.Custom;
import com.mmtap.wk.modular.order.model.Indent;

/**
 * 订单Service
 *
 * @author mmtap.com
 * @Date 2017-11-18 17:48:15
 */
public interface IOrderService {

    void newOrder(Custom custom, Integer[] buss, Indent order);
}
