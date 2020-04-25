package com.dtask.DTask.noticeModule.service;

import com.dtask.DTask.noticeModule.bo.EmailBo;
import com.dtask.DTask.noticeModule.bo.EmailDetailBo;
import com.dtask.DTask.noticeModule.entity.EmailSearchEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Created by zhong on 2020-4-13.
 */
public interface IEmail {
    String sendMail(int[] receiverID, String title, String content, boolean isImportant, MultipartFile[] file);

    int getMailNumber(EmailSearchEntity emailSearchEntity);

    List<EmailBo> getReceiveEmailList(EmailSearchEntity emailSearchEntity);

    EmailDetailBo getEmailDetail(int emailID);

    int getSentMailPageNumber(EmailSearchEntity emailSearchEntity);

    List<EmailBo> getSentEmailList(EmailSearchEntity emailSearchEntity);

    EmailDetailBo getSentEmailDetail(int emailID);
}
