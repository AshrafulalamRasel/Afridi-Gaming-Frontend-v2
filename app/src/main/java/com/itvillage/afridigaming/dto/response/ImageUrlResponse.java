package com.itvillage.afridigaming.dto.response;


public class ImageUrlResponse {

    private String fileName;
    private String fileId;
    private String webUrl;
    private String imageUrl;
    private byte[] imageSize;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getWebUrl() {
        return webUrl;
    }

    public void setWebUrl(String webUrl) {
        this.webUrl = webUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public byte[] getImageSize() {
        return imageSize;
    }

    public void setImageSize(byte[] imageSize) {
        this.imageSize = imageSize;
    }
}