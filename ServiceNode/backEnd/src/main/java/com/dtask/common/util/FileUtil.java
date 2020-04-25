package com.dtask.common.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;

/**
 * Created by zhong on 2020-4-13.
 */
@Component
public class FileUtil {

    static private String fileSavePath;

    @Value("${fileSavePath}")
    public void setFileSavePath(String fileSavePath) {
        FileUtil.fileSavePath = fileSavePath;
    }

    /**
     * 保存前端上传过来的文件
     * @param multipartFiles 文件对象
     * @return 文件名
     * @throws IOException IO异常
     */
    public static String saveFile(MultipartFile multipartFiles[]) throws IOException{
        SimpleDateFormat df = new SimpleDateFormat("yyyy-mm-dd-hh-mm-ss-sss");
        String timeStamp = df.format(new Date());

        DTaskFile dTaskFile = new DTaskFile();

        for(MultipartFile multipartFile:multipartFiles){
            String filename = timeStamp + multipartFile.getOriginalFilename();
            File file = new File(fileSavePath + filename);
            multipartFile.transferTo(file);

            dTaskFile.setFilenameList(new LinkedList<>());
            dTaskFile.addFilename(filename);
        }

        return JsonUtil.objectToJson(dTaskFile);
    }
}
