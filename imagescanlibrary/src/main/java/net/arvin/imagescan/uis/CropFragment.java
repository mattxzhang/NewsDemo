package net.arvin.imagescan.uis;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;


import net.arvin.imagescan.R;
import net.arvin.imagescan.entitys.ConstantEntity;
import net.arvin.imagescan.entitys.ImageBean;
import net.arvin.imagescan.views.ClipImageLayout;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

@SuppressLint("HandlerLeak")
public class CropFragment extends BaseFragment {
    private ClipImageLayout mClipImageLayout = null;

    @Override
    protected int contentLayoutRes() {
        return R.layout.is_fragment_crop;
    }

    @Override
    protected void init() {
        initView();
        initData(getArguments());
    }

    private void initView() {
        mClipImageLayout = (ClipImageLayout) root.findViewById(R.id.clipImageLayout);
        chooseOk.setEnabled(true);
    }

    @Override
    protected void update(Bundle bundle) {
        initData(bundle);
    }

    private void initData(Bundle bundle) {
        String path = bundle.getString(ConstantEntity.CROP_IMAGE);
        if (path == null) {
            path = "";
        }
        int degree = readBitmapDegree(path);
        Bitmap bitmap = createBitmap(path);
        if (bitmap != null) {
            if (degree == 0) {
                mClipImageLayout.setImageBitmap(bitmap);
            } else {
                mClipImageLayout.setImageBitmap(rotateBitmap(degree, bitmap));
            }
            if (bitmap.isRecycled()) {
                bitmap.recycle();
            }
        } else {
            Toast.makeText(getActivity(), "没有找到该图片~", Toast.LENGTH_SHORT).show();
            switchFragment(0, null);
        }
    }

    /**
     * 创建图片
     *
     * @param path
     * @return
     */
    private Bitmap createBitmap(String path) {
        if (path == null) {
            return null;
        }
//        BitmapFactory.Options opts = new BitmapFactory.Options();
//        opts.inSampleSize = 1;
//        opts.inJustDecodeBounds = false;// 这里一定要将其设置回false，因为之前我们将其设置成了true
//        opts.inPurgeable = true;
//        opts.inInputShareable = true;
//        opts.inDither = false;
//        opts.inPurgeable = true;
//        FileInputStream is = null;
//        Bitmap bitmap = null;
//        try {
//            is = new FileInputStream(path);
//            bitmap = BitmapFactory.decodeFileDescriptor(is.getFD(), null, opts);
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                if (is != null) {
//                    is.close();
//                    is = null;
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
        return getimage(path, 480, 480);
    }

    public static Bitmap getimage(String srcPath, float maxOutWidth,
                                  float maxOutHeight) {
        if (srcPath.startsWith("file://")) {
            srcPath = srcPath.substring("file://".length());
        }
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        newOpts.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(srcPath, newOpts);
        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;

        int be = 1;
        if (w > h && w > maxOutWidth) {
            be = (int) (newOpts.outWidth / maxOutWidth);
        } else if (w < h && h > maxOutHeight) {
            be = (int) (newOpts.outHeight / maxOutHeight);
        }
        if (be <= 0)
            be = 1;
        newOpts.inSampleSize = be;
        return BitmapFactory.decodeFile(srcPath, newOpts);
    }

    // 读取图像的旋转度
    private int readBitmapDegree(String path) {
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }

    // 旋转图片
    private Bitmap rotateBitmap(int angle, Bitmap bitmap) {
        // 旋转图片 动作
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        // 创建新的图片
        Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0,
                bitmap.getWidth(), bitmap.getHeight(), matrix, false);
        return resizedBitmap;
    }

    @Override
    protected void onChooseOkBtnClicked() {
        showProgressDialog("裁剪中...");
        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    Bitmap bitmap = mClipImageLayout.clip();
                    String path = getPicPath();
                    String imagePath = path + "/" + Calendar.getInstance().getTimeInMillis() + ".jpg";
                    saveBitmap(bitmap, path, imagePath);
                    dismissProgressDialog();
                    selectedImages = new ArrayList<>();
                    selectedImages.add(new ImageBean(imagePath, true));
                    scanFile(imagePath);
                    setResultData();
                } catch (Exception e) {
                    e.printStackTrace();
                    dismissProgressDialog();
                }
            }
        }).start();
    }

    public static String getPicPath() {
        String fileDir = "";
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            fileDir = Environment.getExternalStorageDirectory()
                    .getAbsolutePath();
        } else {
            fileDir = Environment.getRootDirectory().getAbsolutePath();
        }
        return fileDir + "/" + ConstantEntity.SAVE_IMAGE_FILE_NAME;
    }

    private void saveBitmap(Bitmap bitmap, String path, String imagePath) {
        File dirFile = new File(path);
        if (!dirFile.exists()) {
            dirFile.mkdirs();
        }
        BufferedOutputStream bos = null;
        try {
            File myCaptureFile = new File(imagePath);
            bos = new BufferedOutputStream(
                    new FileOutputStream(myCaptureFile));
            if (bitmap.compress(Bitmap.CompressFormat.JPEG, 80, bos)) {
                bos.flush();
                bos.close();
                Log.i("TAG", "保存成功~");
            }
            if (bitmap.isRecycled()) {
                bitmap.recycle();
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        } finally {
            try {
                if (bos != null)
                    bos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void deleteClipImage() {
        try {
            File file = new File(getPicPath());
            deleteClipImage(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void deleteClipImage(File file) throws Exception {
        if (file.isFile()) {
            file.delete();
            return;
        }
        if (file.isDirectory()) {
            File[] childFile = file.listFiles();
            if (childFile == null || childFile.length == 0) {
                file.delete();
                return;
            }
            for (File f : childFile) {
                deleteClipImage(f);
            }
            file.delete();
        }
    }

    protected void onBackClicked() {
        switchFragment(0, null);
    }
}
