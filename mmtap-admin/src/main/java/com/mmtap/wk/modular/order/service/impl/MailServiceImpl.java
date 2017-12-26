package com.mmtap.wk.modular.order.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.mmtap.wk.common.persistence.dao.UserMapper;
import com.mmtap.wk.common.persistence.model.User;
import com.mmtap.wk.modular.order.dao.WorkDao;
import com.mmtap.wk.modular.order.service.MailService;
import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.List;
import java.util.Map;

@Component
public class MailServiceImpl implements MailService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private WorkDao workDao ;
    @Autowired
    private UserMapper userMapper;

    @Value("${spring.mail.username}")
    private String from;

    @Override
    public void sendHtmlMail(String wid){
        //任务信息
        Map map = workDao.getWorkInfo(wid);
        Integer roleId = MapUtils.getIntValue(map,"flowrole");
        if(null!=roleId){
            List<User> users = userMapper.selectList(new EntityWrapper<User>().eq("status",1));
            for (User u :users){
                String[] role =u.getRoleid().split("[,]");
                for(int i=0;i<role.length;i++){
                    if(roleId==Integer.parseInt(role[i]) && null!=u.getEmail() && !"".equals(u.getEmail())){
                        //有角色对用户发邮件
                        StringBuilder sb = new StringBuilder();
                        sb.append("尊敬的"+u.getName()+":<br>");

                        sb.append("<table border='1'>");
                        sb.append("<tr><td>订单号</td><td>"+map.get("oid")+"</td></tr>");
                        sb.append("<tr><td>客户姓名</td><td>"+map.get("customname")+"</td></tr>");
                        sb.append("<tr><td>当前状态</td><td>"+map.get("flowname")+"</td></tr>");
                        sb.append("<tr><td>旺旺号</td><td>"+map.get("wwid")+"</td></tr>");
                        sb.append("<tr><td>业务办理类型</td><td>"+map.get("businessname")+"</td></tr>");
                        sb.append("</table>");
                        sb.append("<br>此邮件为自动发送邮件，请勿直接回复，如有问题联系管理员邮箱kanglong1205@dingtalk.com");
                        send(u.getEmail(),"您有一个待办事项需要处理，请立即处理，详情见OA待办事项",sb.toString());
                    }
                }


            }
        }
    }

    public void send(String to,String subject,String content){
        MimeMessage message = mailSender.createMimeMessage();
        try {
            //true表示需要创建一个multipart message
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true);
            mailSender.send(message);
            logger.info("html邮件发送成功");
        } catch (MessagingException e) {
            logger.error("发送html邮件时发生异常！", e);
        }
    }
}
