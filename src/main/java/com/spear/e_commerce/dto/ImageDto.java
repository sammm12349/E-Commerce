package com.spear.e_commerce.dto;

public class ImageDto {
    private long imageId;
    private String imageName;
    private String downloadUrl;

    public long getImageId() { return imageId; }
    public void setImageId(long imageId) { this.imageId = imageId; }
    public String getImageName() { return imageName; }
    public void setImageName(String imageName) { this.imageName = imageName; }
    public String getDownloadUrl() { return downloadUrl; }
    public void setDownloadUrl(String downloadUrl) { this.downloadUrl = downloadUrl; }
}
