package com.dtask.DTask.noticeModule.controller;

import com.dtask.DTask.noticeModule.bo.EmailBo;
import com.dtask.DTask.noticeModule.bo.EmailDetailBo;
import com.dtask.DTask.noticeModule.entity.EmailSearchEntity;
import com.dtask.DTask.noticeModule.service.IEmail;
import com.dtask.common.ResponseData;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Created by zhong on 2020-4-13.
 */

@RestController
public class EmailCl {
    @Autowired
    IEmail email;

    @RequiresPermissions("notice:mail:use")
    @RequestMapping(value = "/api/mail/mail",method = RequestMethod.POST)
    public ResponseData sendMail(int[] receiverID, String title, String content, boolean isImportant, String filename){
        return email.sendMail(receiverID, title, content, isImportant, filename);
    }

    @RequiresPermissions("notice:mail:use")
    @RequestMapping(value = "/api/mail/mailPage",method = RequestMethod.GET)
    public ResponseData getMailPageNumber(EmailSearchEntity emailSearchEntity){
        return email.getMailNumber(emailSearchEntity);
    }

    @RequiresPermissions("notice:mail:use")
    @RequestMapping(value = "/api/mail/mailList",method = RequestMethod.GET)
    public ResponseData getReceiveEmailList(EmailSearchEntity emailSearchEntity){
        return email.getReceiveEmailList(emailSearchEntity);
    }

    @Transactional
    @RequiresPermissions("notice:mail:use")
    @RequestMapping(value = "/api/mail/mailDetail",method = RequestMethod.GET)
    public ResponseData getEmailDetail(int mailID){
        return email.getEmailDetail(mailID);
    }

    @RequiresPermissions("notice:mail:use")
    @RequestMapping(value = "/api/mail/sentMailPage",method = RequestMethod.GET)
    public ResponseData getSentMailPageNumber(EmailSearchEntity emailSearchEntity){
        return email.getSentMailPageNumber(emailSearchEntity);
    }

    @RequiresPermissions("notice:mail:use")
    @RequestMapping(value = "/api/mail/mailSentList",method = RequestMethod.GET)
    public ResponseData getSentEmailList(EmailSearchEntity emailSearchEntity){
        return email.getSentEmailList(emailSearchEntity);
    }

    @RequiresPermissions("notice:mail:use")
    @RequestMapping(value = "/api/mail/mailSentDetail",method = RequestMethod.GET)
    public ResponseData getEmailSentDetail(int mailID){
        return email.getSentEmailDetail(mailID);
    }

    @RequiresPermissions("notice:mail:use")
    @RequestMapping(value = "/api/mail/attachment",method = RequestMethod.POST)
    public ResponseData uploadMailAttachment(MultipartFile file){
        return email.uploadMailAttachment(file);
    }
}
