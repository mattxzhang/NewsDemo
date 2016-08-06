package com.kingyon.newslib.greendao.entities;

// THIS CODE IS GENERATED BY greenDAO, EDIT ONLY INSIDE THE "KEEP"-SECTIONS

// KEEP INCLUDES - put your custom includes here
// KEEP INCLUDES END

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Entity mapped to table "COLUMN_ENTITY".
 */
public class ColumnEntity implements Parcelable {

    private Long id;
    /** Not-null value. */
    private String name;
    private String type;
    private String specificInformation;
    private String specificLink;
    private String displayTemplate;
    private Integer imageCount;
    private Integer categoryImageSize;
    private Long objectId;
    private Boolean isPay;
    private Integer itemWeight;
    private Boolean isOrder;

    // KEEP FIELDS - put your custom fields here
    // KEEP FIELDS END

    public ColumnEntity() {
    }

    public ColumnEntity(Long id) {
        this.id = id;
    }

    public ColumnEntity(Long id, String name, String type, String specificInformation, String specificLink, String displayTemplate, Integer imageCount, Integer categoryImageSize, Long objectId, Boolean isPay, Integer itemWeight, Boolean isOrder) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.specificInformation = specificInformation;
        this.specificLink = specificLink;
        this.displayTemplate = displayTemplate;
        this.imageCount = imageCount;
        this.categoryImageSize = categoryImageSize;
        this.objectId = objectId;
        this.isPay = isPay;
        this.itemWeight = itemWeight;
        this.isOrder = isOrder;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /** Not-null value. */
    public String getName() {
        return name;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSpecificInformation() {
        return specificInformation;
    }

    public void setSpecificInformation(String specificInformation) {
        this.specificInformation = specificInformation;
    }

    public String getSpecificLink() {
        return specificLink;
    }

    public void setSpecificLink(String specificLink) {
        this.specificLink = specificLink;
    }

    public String getDisplayTemplate() {
        return displayTemplate;
    }

    public void setDisplayTemplate(String displayTemplate) {
        this.displayTemplate = displayTemplate;
    }

    public Integer getImageCount() {
        return imageCount;
    }

    public void setImageCount(Integer imageCount) {
        this.imageCount = imageCount;
    }

    public Integer getCategoryImageSize() {
        return categoryImageSize;
    }

    public void setCategoryImageSize(Integer categoryImageSize) {
        this.categoryImageSize = categoryImageSize;
    }

    public Long getObjectId() {
        return objectId;
    }

    public void setObjectId(Long objectId) {
        this.objectId = objectId;
    }

    public Boolean getIsPay() {
        return isPay;
    }

    public void setIsPay(Boolean isPay) {
        this.isPay = isPay;
    }

    public Integer getItemWeight() {
        return itemWeight;
    }

    public void setItemWeight(Integer itemWeight) {
        this.itemWeight = itemWeight;
    }

    public Boolean getIsOrder() {
        return isOrder;
    }

    public void setIsOrder(Boolean isOrder) {
        this.isOrder = isOrder;
    }

    // KEEP METHODS - put your custom methods here
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeString(this.name);
        dest.writeString(this.type);
        dest.writeString(this.specificInformation);
        dest.writeString(this.specificLink);
        dest.writeString(this.displayTemplate);
        dest.writeValue(this.imageCount);
        dest.writeValue(this.categoryImageSize);
        dest.writeValue(this.objectId);
        dest.writeValue(this.isPay);
        dest.writeValue(this.itemWeight);
        dest.writeValue(this.isOrder);
    }

    protected ColumnEntity(Parcel in) {
        this.id = (Long) in.readValue(Long.class.getClassLoader());
        this.name = in.readString();
        this.type = in.readString();
        this.specificInformation = in.readString();
        this.specificLink = in.readString();
        this.displayTemplate = in.readString();
        this.imageCount = (Integer) in.readValue(Integer.class.getClassLoader());
        this.categoryImageSize = (Integer) in.readValue(Integer.class.getClassLoader());
        this.objectId = (Long) in.readValue(Long.class.getClassLoader());
        this.isPay = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.itemWeight = (Integer) in.readValue(Integer.class.getClassLoader());
        this.isOrder = (Boolean) in.readValue(Boolean.class.getClassLoader());
    }

    public static final Parcelable.Creator<ColumnEntity> CREATOR = new Parcelable.Creator<ColumnEntity>() {
        @Override
        public ColumnEntity createFromParcel(Parcel source) {
            return new ColumnEntity(source);
        }

        @Override
        public ColumnEntity[] newArray(int size) {
            return new ColumnEntity[size];
        }
    };
    // KEEP METHODS END
}
