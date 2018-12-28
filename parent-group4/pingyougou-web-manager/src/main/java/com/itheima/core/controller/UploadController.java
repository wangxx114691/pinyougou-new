package com.itheima.core.controller;

import com.itheima.core.utils.FastDFSClient;
import entity.Result;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("upload")
public class UploadController {

    @Value("${FILE_SERVER_URL}")
    private String url;
    @RequestMapping("uploadFile")
    public Result uploadFile(MultipartFile file){

        try {
            // file.getOriginalFilename();
            FastDFSClient dfsClient = new FastDFSClient("classpath:fastDFS/fdfs_client.conf");
            String ext = FilenameUtils.getExtension(file.getOriginalFilename());
            String path = dfsClient.uploadFile(file.getBytes(), ext);
            return new Result(true,url+path);   // 注意成功则返回url, 而不是成功信息方便图片通过src回显
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"文件上传失败");
        }
    }
}
