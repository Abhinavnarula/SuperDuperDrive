package com.udacity.jwdnd.course1.cloudstorage.model;

public class File {
    
    private int fileId;
    private String fileName;
    private String contentType;
    private String fileSize;
    private Integer userId;
    private byte[] fileData;


    public File(int fileId, String fileName, String contentType, String fileSize, Integer userId, byte[] fileData) {
        this.fileId = fileId;
        this.fileName = fileName;
        this.contentType = contentType;
        this.fileSize = fileSize;
        this.userId = userId;
        this.fileData = fileData;
    }

    public int getFileId() {
        return this.fileId;
    }

    public void setFileId(int fileId) {
        this.fileId = fileId;
    }

    public String getFileName() {
        return this.fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getContentType() {
        return this.contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getFileSize() {
        return this.fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    public Integer getUserId() {
        return this.userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public byte[] getFileData() {
        return this.fileData;
    }

    public void setFileData(byte[] fileData) {
        this.fileData = fileData;
    }


}
