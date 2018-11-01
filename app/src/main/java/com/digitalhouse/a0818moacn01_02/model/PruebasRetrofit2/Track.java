package com.digitalhouse.a0818moacn01_02.model.PruebasRetrofit2;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Track {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("title_short")
    @Expose
    private String titleShort;
    @SerializedName("title_version")
    @Expose
    private String titleVersion;
    @SerializedName("link")
    @Expose
    private String link;
    @SerializedName("duration")
    @Expose
    private Integer duration;
    @SerializedName("rank")
    @Expose
    private Integer rank;
    @SerializedName("explicit_lyrics")
    @Expose
    private Boolean explicitLyrics;
    @SerializedName("preview")
    @Expose
    private String preview;
    @SerializedName("position")
    @Expose
    private Integer position;
    @SerializedName("artist")
    @Expose
    private ArtistDeezer artist;
    @SerializedName("album")
    @Expose
    private AlbumDeezer album;
    @SerializedName("type")
    @Expose
    private String type;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitleShort() {
        return titleShort;
    }

    public void setTitleShort(String titleShort) {
        this.titleShort = titleShort;
    }

    public String getTitleVersion() {
        return titleVersion;
    }

    public void setTitleVersion(String titleVersion) {
        this.titleVersion = titleVersion;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public Boolean getExplicitLyrics() {
        return explicitLyrics;
    }

    public void setExplicitLyrics(Boolean explicitLyrics) {
        this.explicitLyrics = explicitLyrics;
    }

    public String getPreview() {
        return preview;
    }

    public void setPreview(String preview) {
        this.preview = preview;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public ArtistDeezer getArtist() {
        return artist;
    }

    public void setArtist(ArtistDeezer artist) {
        this.artist = artist;
    }

    public AlbumDeezer getAlbum() {
        return album;
    }

    public void setAlbum(AlbumDeezer album) {
        this.album = album;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
