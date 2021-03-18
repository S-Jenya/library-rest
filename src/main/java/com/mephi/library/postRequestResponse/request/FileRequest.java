package com.mephi.library.postRequestResponse.request;

import org.springframework.web.multipart.MultipartFile;

public class FileRequest {
    public MultipartFile fileContent;
    public MultipartFile fileImage;

    public MultipartFile getFileImage() {
        return fileImage;
    }

    public MultipartFile getFileContent() {
        return fileContent;
    }
}
