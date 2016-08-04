package com.kingyon;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

public class Generator {
    private static final String rootDir = "../NewsDemo/";
    private static final String postfixDir = "/src/main/java";

    private static final String packageRootName = "com.kingyon.";
    private static final String postfixPackageName = ".greendao.entities";

    private static final String NEWS_LIB = "newslib";
    private static final int NEWS_VERSION = 1;

    public static void main(String[] args) throws Exception {
        Schema newsSchema = new Schema(NEWS_VERSION, getPackageName(NEWS_LIB));
        addChannelItem(newsSchema);
        new DaoGenerator().generateAll(newsSchema, getModuleName(NEWS_LIB));

    }

    private static String getModuleName(String moduleName) {
        return rootDir + moduleName + postfixDir;
    }

    private static String getPackageName(String moduleName) {
        return packageRootName + moduleName + postfixPackageName;
    }

    private static void addChannelItem(Schema schema) {
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
        column.addIntProperty("itemWeight");//权重,表示位置
        column.addBooleanProperty("isOrder");//订阅
    }
}
