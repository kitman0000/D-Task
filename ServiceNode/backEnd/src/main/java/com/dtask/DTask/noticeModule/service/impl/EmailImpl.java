package com.dtask.DTask.noticeModule.service.impl;

import com.dtask.DTask.noticeModule.bo.EmailBo;
import com.dtask.DTask.noticeModule.bo.EmailDetailBo;
import com.dtask.DTask.noticeModule.dao.EmailDao;
import com.dtask.DTask.noticeModule.entity.EmailSearchEntity;
import com.dtask.DTask.noticeModule.service.IEmail;
import com.dtask.DTask.noticeModule.type.EmailResult;
import com.dtask.common.UserCommon;
import com.dtask.common.util.FileUtil;
import com.dtask.common.util.PageDivideUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * Created by zhong on 2020-4-13.
 */
@Service
public class EmailImpl implements IEmail {
    @Autowired
    EmailDao emailDao;

    final int ROWS_ONE_PAGE = 10;

    @Override
    public String sendMail(int[] receiverID, String title, String content, boolean isImportant, MultipartFile[] file) {
        String filenameJson;

        if(file != null) {
            try {
                filenameJson = FileUtil.saveFile(file);
            } catch (IOException e) {
                e.printStackTrace();
                return EmailResult.ATTACHMENT_UPLOAD_FAILED.toString();
            }
        }else {
            filenameJson = null;
        }

        int userID = UserCommon.getUserBo().getUserID();
        emailDao.insertEmail(userID,receiverID,title,content,isImportant,filenameJson);
        return EmailResult.EMAIL_SEND_SUCCESS.toString();
    }

    @Override
    public int getMailNumber(EmailSearchEntity emailSearchEntity) {
        int userID = UserCommon.getUserBo().getUserID();

        int number = emailDao.getMailNumber(userID,emailSearchEntity);
        return PageDivideUtil.getCountOfPages(number, ROWS_ONE_PAGE);
    }

    @Override
    public List<EmailBo> getReceiveEmailList(EmailSearchEntity emailSearchEntity) {
        int userID = UserCommon.getUserBo().getUserID();
        int startRow = (emailSearchEntity.getPage() -1) * ROWS_ONE_PAGE;
        return emailDao.getMailList(userID,emailSearchEntity,startRow,ROWS_ONE_PAGE);
    }

    @Override
    public EmailDetailBo getEmailDetail(int emailID) {
        int userID = UserCommon.getUserBo().getUserID();
        emailDao.setMailHasRead(emailID);
        return emailDao.getMailDetail(emailID,userID);
    }

    @Override
    public int getSentMailPageNumber(EmailSearchEntity emailSearchEntity) {
        int userID = UserCommon.getUserBo().getUserID();
        int page = emailDao.getSentMailNumber(userID,emailSearchEntity);
        return PageDivideUtil.getCountOfPages(page,ROWS_ONE_PAGE);
    }

    @Override
    public List<EmailBo> getSentEmailList(EmailSearchEntity emailSearchEntity) {
        int userID = UserCommon.getUserBo().getUserID();
        int startRow = (emailSearchEntity.getPage() -1) * ROWS_ONE_PAGE;
        return emailDao.getSentMailList(userID,emailSearchEntity,startRow,ROWS_ONE_PAGE);
    }

    @Override
    public EmailDetailBo getSentEmailDetail(int emailID) {
        int userID = UserCommon.getUserBo().getUserID();
        return emailDao.getSentMailDetail(emailID,userID);
    }
}
