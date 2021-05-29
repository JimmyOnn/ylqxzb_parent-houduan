package com.SEU.vod.controller;

import com.SEU.commonUtils.R;
import com.SEU.vod.service.VodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/eduVod/video")
@CrossOrigin
public class VodController {

    @Autowired
    private VodService vodService;

    @PostMapping("/uploadAlyVideo")
    public R uploadAlyVideo(MultipartFile file) throws IOException {

        String videoId = vodService.uploadVideoAly(file);

        return R.ok().data("videoId",videoId);
    }
}
