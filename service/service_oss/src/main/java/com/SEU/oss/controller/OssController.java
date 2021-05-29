package com.SEU.oss.controller;

import com.SEU.commonUtils.R;
import com.SEU.oss.service.OssService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@CrossOrigin
@RequestMapping("/eduOss/fileOss")
public class OssController {

    @Autowired
    private OssService ossService;

    @PostMapping
    public R uploadOssFile(MultipartFile file) {

        String url = ossService.uploadFileAvatar(file);

        return R.ok().data("url",url);
    }
}
