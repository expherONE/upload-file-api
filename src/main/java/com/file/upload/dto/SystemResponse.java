package com.file.upload.dto;

public class SystemResponse {
    private String filename;

    private String contentType;

    private String url;

    public SystemResponse(String filename, String contentType, String url) {
        this.filename = filename;
        this.contentType = contentType;
        this.url = url;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
