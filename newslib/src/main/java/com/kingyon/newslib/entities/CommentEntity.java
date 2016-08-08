package com.kingyon.newslib.entities;

import android.os.Parcel;
import android.os.Parcelable;

import com.kingyon.newslib.greendao.entities.AttachmentEntity;

import java.util.List;

/**
 * Created by arvin on 2016/5/31 19:22
 */
public class CommentEntity implements Parcelable {

    /**
     * contentId : 0
     * contentTitle :
     * commentContent :
     * parentComment : ContentCommentBean
     * childComments : ["ContentCommentBean"]
     * commentTime : 0
     * commentUser : {"mobile":"","nickname":"","head":{"url":"","objectId":0},"sign":"","sex":"","birthDate":0,"objectId":0}
     * levalPath :
     * objectId : 0
     */

    private int contentId;
    private String content;
    private String contentTitle;
    private String commentContent;
    private int categoryId;
    private CommentEntity parentComment;
    private AttachmentEntity contentMainImage;
    private String displayTemplate;
    private long commentTime;
//    private User commentUser;
//    private String levalPath;
    private int objectId;
    private List<CommentEntity> childComments;

    public String getDisplayTemplate() {
        return displayTemplate;
    }

    public void setDisplayTemplate(String displayTemplate) {
        this.displayTemplate = displayTemplate;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getContentId() {
        return contentId;
    }

    public void setContentId(int contentId) {
        this.contentId = contentId;
    }

    public String getContentTitle() {
        return contentTitle;
    }

    public void setContentTitle(String contentTitle) {
        this.contentTitle = contentTitle;
    }

    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }

    public CommentEntity getParentComment() {
        return parentComment;
    }

    public void setParentComment(CommentEntity parentComment) {
        this.parentComment = parentComment;
    }

    public long getCommentTime() {
        return commentTime;
    }

    public void setCommentTime(long commentTime) {
        this.commentTime = commentTime;
    }

    public AttachmentEntity getContentMainImage() {
        return contentMainImage;
    }

    public void setContentMainImage(AttachmentEntity contentMainImage) {
        this.contentMainImage = contentMainImage;
    }

//    public User getCommentUser() {
//        return commentUser;
//    }
//
//    public void setCommentUser(User commentUser) {
//        this.commentUser = commentUser;
//    }

//    public String getLevalPath() {
//        return levalPath;
//    }
//
//    public void setLevalPath(String levalPath) {
//        this.levalPath = levalPath;
//    }

    public int getObjectId() {
        return objectId;
    }

    public void setObjectId(int objectId) {
        this.objectId = objectId;
    }

    public List<CommentEntity> getChildComments() {
        return childComments;
    }

    public void setChildComments(List<CommentEntity> childComments) {
        this.childComments = childComments;
    }

    public CommentEntity() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.contentId);
        dest.writeString(this.content);
        dest.writeString(this.contentTitle);
        dest.writeString(this.commentContent);
        dest.writeInt(this.categoryId);
        dest.writeParcelable(this.parentComment, flags);
        dest.writeParcelable(this.contentMainImage, flags);
        dest.writeString(this.displayTemplate);
        dest.writeLong(this.commentTime);
        dest.writeInt(this.objectId);
        dest.writeTypedList(this.childComments);
    }

    protected CommentEntity(Parcel in) {
        this.contentId = in.readInt();
        this.content = in.readString();
        this.contentTitle = in.readString();
        this.commentContent = in.readString();
        this.categoryId = in.readInt();
        this.parentComment = in.readParcelable(CommentEntity.class.getClassLoader());
        this.contentMainImage = in.readParcelable(AttachmentEntity.class.getClassLoader());
        this.displayTemplate = in.readString();
        this.commentTime = in.readLong();
        this.objectId = in.readInt();
        this.childComments = in.createTypedArrayList(CommentEntity.CREATOR);
    }

    public static final Creator<CommentEntity> CREATOR = new Creator<CommentEntity>() {
        @Override
        public CommentEntity createFromParcel(Parcel source) {
            return new CommentEntity(source);
        }

        @Override
        public CommentEntity[] newArray(int size) {
            return new CommentEntity[size];
        }
    };
}
