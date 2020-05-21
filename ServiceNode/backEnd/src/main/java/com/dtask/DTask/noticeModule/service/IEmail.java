package com.dtask.DTask.noticeModule.service;

import com.dtask.DTask.noticeModule.bo.EmailBo;
import com.dtask.DTask.noticeModule.bo.EmailDetailBo;
import com.dtask.DTask.noticeModule.entity.EmailSearchEntity;
import com.dtask.common.ResponseData;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Created by zhong on 2020-4-13.
 */
public interface IEmail {
    ResponseData sendMail(int[] receiverID, String title, String content, boolean isImportant, MultipartFile[] file);

    ResponseData getMailNumber(EmailSearchEntity emailSearchEntity);

    ResponseData getReceiveEmailList(EmailSearchEntity emailSearchEntity);

    ResponseData getEmailDetail(int emailID);

    ResponseData getSentMailPageNumber(EmailSearchEntity emailSearchEntity);

    ResponseData getSentEmailList(EmailSearchEntity emailSearchEntity);

    ResponseData getSentEmailDetail(int emailID);
}
