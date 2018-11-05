package com.digitalhouse.a0818moacn01_02.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RadioDeezer {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("share")
    @Expose
    private String share;
    @SerializedName("picture")
    @Expose
    private String picture;
    @SerializedName("picture_small")
    @Expose
    private String pictureSmall;
    @SerializedName("picture_medium")
    @Expose
    private String pictureMedium;
    @SerializedName("picture_big")
    @Expose
    private String pictureBig;
    @SerializedName("picture_xl")
    @Expose
    private String pictureXl;
    @SerializedName("tracklist")
    @Expose
    private String tracklist;
    @SerializedName("type")
    @Expose
    private String type;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public RadioDeezer withId(String id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public RadioDeezer withTitle(String title) {
        this.title = title;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public RadioDeezer withDescription(String description) {
        this.description = description;
        return this;
    }

    public String getShare() {
        return share;
    }

    public void setShare(String share) {
        this.share = share;
    }

    public RadioDeezer withShare(String share) {
        this.share = share;
        return this;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public RadioDeezer withPicture(String picture) {
        this.picture = picture;
        return this;
    }

    public String getPictureSmall() {
        return pictureSmall;
    }

    public void setPictureSmall(String pictureSmall) {
        this.pictureSmall = pictureSmall;
    }

    public RadioDeezer withPictureSmall(String pictureSmall) {
        this.pictureSmall = pictureSmall;
        return this;
    }

    public String getPictureMedium() {
        return pictureMedium;
    }

    public void setPictureMedium(String pictureMedium) {
        this.pictureMedium = pictureMedium;
    }

    public RadioDeezer withPictureMedium(String pictureMedium) {
        this.pictureMedium = pictureMedium;
        return this;
    }

    public String getPictureBig() {
        return pictureBig;
    }

    public void setPictureBig(String pictureBig) {
        this.pictureBig = pictureBig;
    }

    public RadioDeezer withPictureBig(String pictureBig) {
        this.pictureBig = pictureBig;
        return this;
    }

    public String getPictureXl() {
        return pictureXl;
    }

    public void setPictureXl(String pictureXl) {
        this.pictureXl = pictureXl;
    }

    public RadioDeezer withPictureXl(String pictureXl) {
        this.pictureXl = pictureXl;
        return this;
    }

    public String getTracklist() {
        return tracklist;
    }

    public void setTracklist(String tracklist) {
        this.tracklist = tracklist;
    }

    public RadioDeezer withTracklist(String tracklist) {
        this.tracklist = tracklist;
        return this;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public RadioDeezer withType(String type) {
        this.type = type;
        return this;
    }

}
