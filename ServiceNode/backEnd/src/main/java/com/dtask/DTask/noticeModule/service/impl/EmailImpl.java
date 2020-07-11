package com.dtask.DTask.noticeModule.service.impl;

import com.dtask.DTask.noticeModule.bo.EmailBo;
import com.dtask.DTask.noticeModule.bo.EmailDetailBo;
import com.dtask.DTask.noticeModule.dao.EmailDao;
import com.dtask.DTask.noticeModule.entity.EmailSearchEntity;
import com.dtask.DTask.noticeModule.service.IEmail;
import com.dtask.common.ResponseData;
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
    public ResponseData sendMail(int[] receiverID, String title, String content, boolean isImportant, String filenames) {
        int userID = UserCommon.getUserBo().getUserID();
        emailDao.insertEmail(userID,receiverID,title,content,isImportant,filenames);
        return new ResponseData(1,"发送成功",null);
    }

    @Override
    public ResponseData getMailNumber(EmailSearchEntity emailSearchEntity) {
        int userID = UserCommon.getUserBo().getUserID();

        int number = emailDao.getMailNumber(userID,emailSearchEntity);
        int page = PageDivideUtil.getCountOfPages(number,ROWS_ONE_PAGE);
        return new ResponseData(1,"查询成功",page);
    }

    @Override
    public ResponseData getReceiveEmailList(EmailSearchEntity emailSearchEntity) {
        int userID = UserCommon.getUserBo().getUserID();
        int startRow = (emailSearchEntity.getPage() -1) * ROWS_ONE_PAGE;
        List<EmailBo> emailBoList = emailDao.getMailList(userID,emailSearchEntity,startRow,ROWS_ONE_PAGE);
        return new ResponseData(1,"查询成功",emailBoList);
    }

    @Override
    public ResponseData getEmailDetail(int emailID) {
        int userID = UserCommon.getUserBo().getUserID();
        emailDao.setMailHasRead(emailID);
        EmailDetailBo emailDetailBo = emailDao.getMailDetail(emailID,userID);
        return new ResponseData(1,"查询成功",emailDetailBo);
    }

    @Override
    public ResponseData getSentMailPageNumber(EmailSearchEntity emailSearchEntity) {
        int userID = UserCommon.getUserBo().getUserID();
        int page = emailDao.getSentMailNumber(userID,emailSearchEntity);
        page = PageDivideUtil.getCountOfPages(page,ROWS_ONE_PAGE);
        return new ResponseData(1,"查询成功",page);
    }

    @Override
    public ResponseData getSentEmailList(EmailSearchEntity emailSearchEntity) {
        int userID = UserCommon.getUserBo().getUserID();
        int startRow = (emailSearchEntity.getPage() -1) * ROWS_ONE_PAGE;

        List<EmailBo> emailBoList = emailDao.getSentMailList(userID,emailSearchEntity,startRow,ROWS_ONE_PAGE);
        return new ResponseData(1,"查询成功",emailBoList);
    }

    @Override
    public ResponseData getSentEmailDetail(int emailID) {
        int userID = UserCommon.getUserBo().getUserID();
        EmailDetailBo emailDetailBo = emailDao.getSentMailDetail(emailID,userID);

        return new ResponseData(1,"查询成功",emailDetailBo);
    }

    @Override
    public ResponseData uploadMailAttachment(MultipartFile file) {

        String filename;

        if(file != null) {
            try {
                filename = FileUtil.saveFile(file);
            } catch (IOException e) {
                e.printStackTrace();
                return new ResponseData(2,"文件错误",null);
            }
        }else {
            filename = null;
        }
        return new ResponseData(1,"上传成功",filename);
    }
}
