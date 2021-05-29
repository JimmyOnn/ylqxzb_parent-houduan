package com.SEU.vodTest;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadVideoRequest;
import com.aliyun.vod.upload.resp.UploadVideoResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoRequest;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoResponse;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;

import java.util.List;

public class testVod {
    public static void main(String[] args) throws ClientException {
        String accessKeyId = "LTAI5tJ6TtLNAr9V5yQb1SsZ";
        String accessKeySecret = "CuyMbu2kuAYRU9oAChJjyxfuRAwNV5";
        String title = "test upload by sdk";
        String fileName = "D:/test.mp4";
        UploadVideoRequest request = new UploadVideoRequest(accessKeyId,accessKeySecret,title,fileName);
        request.setPartSize(2 * 1024 * 1024L);
        request.setTaskNum(1);
        UploadVideoImpl uploader = new UploadVideoImpl();
        UploadVideoResponse response = uploader.uploadVideo(request);
        System.out.println("RequestId=" + response.getRequestId() + "\n");
        if (response.isSuccess()) {
            System.out.println("VideoId=" + response.getVideoId() + "\n");
        } else {
            System.out.println("VideoId=" + response.getVideoId() + "\n");
            System.out.println("ErrorCode=" + response.getCode() + "\n");
            System.out.println("ErrorMessage=" + response.getMessage() + "\n");
        }
    }

    public static void getPlayAuth() throws Exception {
        DefaultAcsClient client = InitObject.initVodClient("LTAI5tJ6TtLNAr9V5yQb1SsZ", "CuyMbu2kuAYRU9oAChJjyxfuRAwNV5");
        GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
        GetVideoPlayAuthResponse response = new GetVideoPlayAuthResponse();
        request.setVideoId("264cc3863e814165bc01d1c14d8a6c72");
        response = client.getAcsResponse(request);
        System.out.println("playAuth:" + response.getPlayAuth());
    }

    public static void getPlayUrl() throws Exception {
        DefaultAcsClient client = InitObject.initVodClient("LTAI5tJ6TtLNAr9V5yQb1SsZ", "CuyMbu2kuAYRU9oAChJjyxfuRAwNV5");
        GetPlayInfoRequest request = new GetPlayInfoRequest();
        GetPlayInfoResponse response = new GetPlayInfoResponse();
        request.setVideoId("4ef5c58856154a3c9514e245af7f3c9e");
        response = client.getAcsResponse(request);
        List<GetPlayInfoResponse.PlayInfo> playInfoList = response.getPlayInfoList();
        //播放地址
        for (GetPlayInfoResponse.PlayInfo playInfo : playInfoList) {
            System.out.print("PlayInfo.PlayURL = " + playInfo.getPlayURL() + "\n");
        }
        //Base信息
        System.out.print("VideoBase.Title = " + response.getVideoBase().getTitle() + "\n");
    }
}
