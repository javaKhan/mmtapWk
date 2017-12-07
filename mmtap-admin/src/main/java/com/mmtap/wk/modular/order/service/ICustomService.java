package com.mmtap.wk.modular.order.service;

import com.mmtap.wk.modular.order.model.Indent;

/**
 * 客户Service
 *
 * @author imkzp.com
 * @Date 2017-11-18 17:48:58
 */
public interface ICustomService {
    public void appendWord(String cid,Integer[] buss,Indent order);
}
