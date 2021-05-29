package com.SEU.oss.service.impl;

import com.SEU.oss.service.OssService;
import com.SEU.oss.utils.ConstantPropertiesUtils;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.UUID;

@Service
public class OssServiceImpl implements OssService {

    @Override
    public String uploadFileAvatar(MultipartFile file) {

        String endPoint = ConstantPropertiesUtils.END_POINT;
        String accessKeyId = ConstantPropertiesUtils.ACCESS_KEY_ID;
        String accessKeySecret = ConstantPropertiesUtils.ACCESS_KEY_SECRET;
        String bucketName = ConstantPropertiesUtils.BUCKET_NAME;

        try {

            OSS ossClient = new OSSClientBuilder().build(endPoint, accessKeyId, accessKeySecret);

            InputStream inputStream = file.getInputStream();
            String fileName = file.getOriginalFilename();

            String uuid = UUID.randomUUID().toString().replaceAll("-","");
            fileName = uuid+fileName;
            String datePath = new DateTime().toString("yyyy/MM/dd");
            fileName = datePath+"/"+fileName;

            ossClient.putObject(bucketName, fileName, inputStream);

            ossClient.shutdown();

            String url = "https://"+bucketName+"."+endPoint+"/"+fileName;

            return url;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
