package com.SEU.vod.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface VodService {
    String uploadVideoAly(MultipartFile file) throws IOException;
}
