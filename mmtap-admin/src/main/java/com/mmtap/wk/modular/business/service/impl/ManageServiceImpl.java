package com.mmtap.wk.modular.business.service.impl;

import com.mmtap.wk.modular.business.dao.FlowDao;
import com.mmtap.wk.modular.business.dao.ManageDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mmtap.wk.modular.business.service.IManageService;
import org.springframework.transaction.annotation.Transactional;

/**
 * 业务中心Service
 *
 * @author imkzp.com
 * @Date 2017-11-13 22:58:10
 */
@Service
public class ManageServiceImpl implements IManageService {
    @Autowired
    private ManageDao manageDao;

}
