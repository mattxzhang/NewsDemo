package net.arvin.imagescan.utils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import net.arvin.imagescan.entitys.ConstantEntity;
import net.arvin.imagescan.entitys.ImageBean;
import net.arvin.imagescan.uis.SelectMultImagesActivity;

import java.util.ArrayList;

/**
 * Created by arvin on 2016/2/18 11:22
 * .
 */
public class ChooseAvatarUtil {
    /**
     * @param num          一次可选照片的数量
     * @param isCrop       只有当num=1时，isCrop才有效，表示是否裁剪
     * @param isShowCamera 在选图界面是否要显示拍照按钮
     */
    public static void startMultImageActivity(Activity activity, int num, boolean isCrop, boolean isShowCamera, boolean isShowMyPhotos, ArrayList<ImageBean> selectedImages) {
        Intent intent = new Intent(activity, SelectMultImagesActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt(ConstantEntity.MAX_NUM, num);
        bundle.putBoolean(ConstantEntity.IS_CROP, isCrop);
        bundle.putBoolean(ConstantEntity.SHOW_CAMERA, isShowCamera);
        if (selectedImages != null && selectedImages.size() > 0) {
            bundle.putParcelableArrayList(ConstantEntity.SELECTED_IMAGES, selectedImages);
        }
        if (isShowMyPhotos) {
            bundle.putString(ConstantEntity.SHOW_FILE_NAME, ConstantEntity.TAKE_PHOTO_FILE_NAME);
        }
        intent.putExtras(bundle);
        activity.startActivityForResult(intent, ConstantEntity.REQUEST_CODE);
    }
}
