package com.mmtap.wk.modular.order.service;

import java.util.List;

/**
 * 工作Service
 *
 * @author fengshuonan
 * @Date 2017-11-18 17:50:20
 */
public interface IWorkService {

    List getTodoWorks();

    int lockWork(String wid, Integer uid);

    boolean nextStep(String wid);

    void newprice(String wid, double price);

    void disWork(String wid);
}
