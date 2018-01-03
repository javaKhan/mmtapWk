package com.mmtap.wk.modular.order.utils;

import com.mmtap.wk.modular.order.service.MailService;

/**
 * 发送邮件的专用线程
 */
public class MailThread extends Thread {
    private String wid;
    private MailService mailService;

    public MailThread(MailService mailService,String wid){
        this.mailService = mailService;
        this.wid = wid;
        this.run();
    }

    @Override
    public void run() {
        mailService.sendHtmlMail(wid);
    }
}
