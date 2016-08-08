package net.arvin.imagescan.entitys;

public class ConstantEntity {
    /**
     * 保存图片文件夹的名字
     */
    public static final String SAVE_IMAGE_FILE_NAME = "imagescan";
    public static final String TAKE_PHOTO_FILE_NAME = "ec_image";
    public static final String SELECTED_IMAGES = "selected_images";
    public static final String CURRENT_IMAGES = "current_images";
    public static final String CLICKED_POSITION = "clicked_position";
    /**
     * 返回数据的关键字
     */
    public static final String RESPONSE_KEY = "response_key";
    /**
     * 可选图片的最大张数的关键字
     */
    public static final String MAX_NUM = "max_num";
    /**
     * 是否需要裁剪的关键字,只有当MAX_NUM为1时，设置才有效
     */
    public static final String IS_CROP = "is_crop";
    public static final String SHOW_CAMERA = "show_camera";
    public static final String CROP_IMAGE = "crop_image";
    public static final String SHOW_FILE_NAME = "show_file_name";
    public static final int REQUEST_CODE = 2001;
    public static final int IMAGE_REQUEST_TAKE_PHOTO = 1001;
    public static final int TYPE_CAMERA = 0;
    public static final int TYPE_NORMAL = 1;
    public static final int SCAN_OK = 0;
    public static final int RESULT_OK = -1;

    /**
     * return a number,which is the default most selected images' number
     */
    public static int getDefaultMaxSelectNum() {
        return 1;
    }
}
