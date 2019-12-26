package com.broada.uyconf.web.utils.email;

import com.broada.uyconf.web.common.email.MailSenderInfo;
import com.broada.uyconf.web.common.email.SimpleMailSender;
import com.broada.uyconf.web.config.ApplicationPropertyConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author wnb
 * 14-3-2
 */
@Service
public class EmailService {

    @Autowired
    private ApplicationPropertyConfig emailProperties;

    /**
     * 发送HTML邮箱
     *
     * @return
     */
    public boolean sendHtmlEmail(String toEmail, String subject, String content) {

        //
        // 这个类主要是设置邮件
        //
        MailSenderInfo mailInfo = new MailSenderInfo();
        mailInfo.setMailServerHost(emailProperties.getEmailHost());
        mailInfo.setMailServerPort(emailProperties.getEmailPort());
        mailInfo.setValidate(true);
        mailInfo.setUserName(emailProperties.getEmailUser());
        mailInfo.setPassword(emailProperties.getEmailPassword());// 您的邮箱密码

        mailInfo.setFromAddress(emailProperties.getFromEmail());
        mailInfo.setToAddress(toEmail);

        mailInfo.setSubject(subject);
        mailInfo.setContent(content);

        // 这个类主要来发送邮件
        return SimpleMailSender.sendHtmlMail(mailInfo);// 发送文体格式
    }
}
