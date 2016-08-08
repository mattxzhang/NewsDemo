package net.arvin.imagescan.utils;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import net.arvin.imagescan.entitys.ConstantEntity;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;

public class TakePhotoUtils {
    private Activity mActivity;
    private String fileDir;
    private String imagePath;
    private File mCurrentFile;
    private SelectImageSuccessListener imageSuccess;
    public static final int IMAGE_REQUEST_TAKE_PHOTO = 1001;
    public static final int RESULT_OK = -1;

    public TakePhotoUtils(Activity mActivity,
                          SelectImageSuccessListener imageSuccess) {
        this.mActivity = mActivity;
        this.imageSuccess = imageSuccess;
        initFileDir();
    }

    public void initFileDir() {
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            try {
                fileDir = mActivity.getExternalCacheDir().getAbsolutePath();
//                        Environment.getExternalStorageDirectory().getAbsolutePath();
            } catch (NullPointerException e) {
                fileDir = mActivity.getCacheDir().getAbsolutePath();
            }
        } else {
            fileDir = mActivity.getCacheDir().getAbsolutePath();
//            Environment.getRootDirectory().getAbsolutePath();
        }
        fileDir += "/symiles/" + ConstantEntity.TAKE_PHOTO_FILE_NAME;
    }

    public void choosePhotoFromCamera() {
        File photoDir = new File(fileDir);
        if (!photoDir.exists()) {
            photoDir.mkdirs();
        }
        mCurrentFile = new File(fileDir, getPhotoName());
        Log.i("URI", mCurrentFile.toString());
        imagePath = mCurrentFile.toString();
        final Intent intent = getCameraIntent(mCurrentFile);
        mActivity.startActivityForResult(intent, IMAGE_REQUEST_TAKE_PHOTO);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data,
                                 Activity mActivity) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case IMAGE_REQUEST_TAKE_PHOTO:
                    imageSuccess.onSelectImageSuccess(imagePath);
                    scanFile(imagePath);
                    break;
            }
        }
    }

    private String getPhotoName() {
        return Calendar.getInstance().getTimeInMillis() + ".jpg";
    }

    private Intent getCameraIntent(File f) {
        final Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
        return intent;
    }

    public interface SelectImageSuccessListener {
        public void onSelectImageSuccess(String path);
    }

    protected void scanFile(String path) {
        MediaScannerConnection.scanFile(mActivity, new String[]{path},
                null, new MediaScannerConnection.OnScanCompletedListener() {
                    @Override
                    public void onScanCompleted(String path, Uri uri) {
                        Log.i("scanFile", "刷新成功");
                    }
                });
    }
}
