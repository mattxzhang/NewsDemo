package com.kingyon;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Property;
import de.greenrobot.daogenerator.Schema;
import javafx.scene.Scene;

public class Generator {
    private static final String rootDir = "../NewsDemo/";
    private static final String postfixDir = "/src/main/java";

    private static final String packageRootName = "com.kingyon.";
    private static final String postfixPackageName = ".greendao.entities";

    private static final String NEWS_LIB = "newslib";
    private static final int NEWS_VERSION = 1;

    public static void main(String[] args) throws Exception {
        Schema newsSchema = new Schema(NEWS_VERSION, getPackageName(NEWS_LIB));
        newsSchema.enableKeepSectionsByDefault();
        addColumnEntity(newsSchema);
        addNewsEntity(newsSchema);
        addAttachmentEntity(newsSchema);
        addSocialInfoEntity(newsSchema);
        new DaoGenerator().generateAll(newsSchema, getModuleName(NEWS_LIB));

    }

    private static String getModuleName(String moduleName) {
        return rootDir + moduleName + postfixDir;
    }

    private static String getPackageName(String moduleName) {
        return packageRootName + moduleName + postfixPackageName;
    }

    private static void addColumnEntity(Schema schema) {
        Entity column = schema.addEntity("ColumnEntity");
        column.addIdProperty();
        column.addStringProperty("name").notNull();
        column.addStringProperty("type");
        column.addStringProperty("specificInformation");
        column.addStringProperty("specificLink");
        column.addStringProperty("displayTemplate");
        column.addIntProperty("imageCount");
        column.addIntProperty("categoryImageSize");
        column.addLongProperty("objectId");
        column.addBooleanProperty("isPay");
        column.addIntProperty("itemWeight").codeBeforeField("//权重,表示位置");
        column.addBooleanProperty("isOrder").codeBeforeField("//是否订阅");


        column.addImport("android.os.Parcel");
        column.addImport("android.os.Parcelable");
        column.implementsInterface("Parcelable");
    }

    private static void addNewsEntity(Schema newsSchema) {
        Entity news = newsSchema.addEntity("NewsEntity");
        news.addStringProperty("type");
        news.addStringProperty("displayTemplate");
        news.addStringProperty("title");
        news.addStringProperty("content");
        news.addStringProperty("summary");
        news.addStringProperty("source");
        news.addStringProperty("contentShareUrl");
        news.addStringProperty("webUrl");
        news.addStringProperty("videoUrl");

        news.addLongProperty("categoryId");
        news.addLongProperty("objectId").columnName("newsId").primaryKey();
        news.addLongProperty("publishTime");

        news.addBooleanProperty("isPay");

        news.addImport("android.os.Parcel");
        news.addImport("android.os.Parcelable");
        news.implementsInterface("Parcelable");
        news.addImport("java.util.ArrayList");
        news.addImport("java.util.List");
    }

    private static void addAttachmentEntity(Schema newsSchema) {
        Entity attachment = newsSchema.addEntity("AttachmentEntity");
        attachment.addLongProperty("objectId").columnName("attachmentId").primaryKey();
        attachment.addLongProperty("newsId");
        attachment.addStringProperty("url");
        attachment.addStringProperty("content");
        attachment.addStringProperty("type").codeBeforeField("//1表示主图,2表示图集的图片");

        attachment.addImport("android.os.Parcel");
        attachment.addImport("android.os.Parcelable");
        attachment.implementsInterface("Parcelable");
    }

    private static void addSocialInfoEntity(Schema newsSchema) {
        Entity social = newsSchema.addEntity("SocialEntity");
        social.addLongProperty("objectId").columnName("socialId").primaryKey();
        social.addLongProperty("newsId");
        social.addIntProperty("praisedCount");
        social.addIntProperty("stepedCount");
        social.addIntProperty("favouratedCount");
        social.addIntProperty("commentedCount");

        social.addBooleanProperty("allowComment");
        social.addBooleanProperty("allowPraise");
        social.addBooleanProperty("allowStep");
        social.addBooleanProperty("isFavorite");
        social.addBooleanProperty("isPraise");
        social.addBooleanProperty("isStep");
        social.addBooleanProperty("isCommented");


        social.addImport("android.os.Parcel");
        social.addImport("android.os.Parcelable");
        social.implementsInterface("Parcelable");
    }

}
