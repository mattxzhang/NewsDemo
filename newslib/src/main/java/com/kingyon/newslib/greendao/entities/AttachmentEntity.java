package com.kingyon.newslib.greendao.entities;

import android.os.Parcel;
import android.os.Parcelable;

// THIS CODE IS GENERATED BY greenDAO, EDIT ONLY INSIDE THE "KEEP"-SECTIONS

// KEEP INCLUDES - put your custom includes here
// KEEP INCLUDES END
/**
 * Entity mapped to table "ATTACHMENT_ENTITY".
 */
public class AttachmentEntity implements Parcelable {

    private Long objectId;
    private Long newsId;
    private String url;
    private String content;
     //1表示主图,2表示图集的图片
    private String type;

    // KEEP FIELDS - put your custom fields here
    public static final String TYPE_MAIN = "1";
    public static final String TYPE_LIST = "2";
    // KEEP FIELDS END

    public AttachmentEntity() {
    }

    public AttachmentEntity(Long objectId) {
        this.objectId = objectId;
    }

    public AttachmentEntity(Long objectId, Long newsId, String url, String content, String type) {
        this.objectId = objectId;
        this.newsId = newsId;
        this.url = url;
        this.content = content;
        this.type = type;
    }

    public Long getObjectId() {
        return objectId;
    }

    public void setObjectId(Long objectId) {
        this.objectId = objectId;
    }

    public Long getNewsId() {
        return newsId;
    }

    public void setNewsId(Long newsId) {
        this.newsId = newsId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    // KEEP METHODS - put your custom methods here
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.objectId);
        dest.writeString(this.url);
        dest.writeString(this.content);
    }

    protected AttachmentEntity(Parcel in) {
        this.objectId = (Long) in.readValue(Long.class.getClassLoader());
        this.url = in.readString();
        this.content = in.readString();
    }

    public static final Parcelable.Creator<AttachmentEntity> CREATOR = new Parcelable.Creator<AttachmentEntity>() {
        @Override
        public AttachmentEntity createFromParcel(Parcel source) {
            return new AttachmentEntity(source);
        }

        @Override
        public AttachmentEntity[] newArray(int size) {
            return new AttachmentEntity[size];
        }
    };
    // KEEP METHODS END

}