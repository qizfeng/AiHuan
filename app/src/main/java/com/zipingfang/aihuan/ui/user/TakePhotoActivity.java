package com.zipingfang.aihuan.ui.user;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Toast;

import com.photo.choosephotos.photo.Item;
import com.photo.choosephotos.photo.PhotoAlbumActivity;
import com.zipingfang.aihuan.R;
import com.zipingfang.aihuan.constants.CacheManager;
import com.zipingfang.aihuan.constants.Constants;
import com.zipingfang.aihuan.utils.PhotoUtil;
import com.zipingfang.aihuan.utils.ToastUtils;

import java.io.File;
import java.util.ArrayList;

/**
 * 通用的照片拍摄或选取照片 Author:heiyue Email:heiyue623@126.com 2015-5-16下午1:41:02
 */
public class TakePhotoActivity extends Activity {
    private Activity context;
    /**
     * 是否为多图选择
     */
    public static final String IS_CHOOSE_MANY = "is_choose_many";
    /**
     * 多图选择，当前选择的张数
     */
    public static final String CHOOSE_MANY_CURRENT_SIZE = "choose_many_current_count";
    /**
     * 是否需要裁剪，对拍照和单图有效
     */
    public static final String NEED_CROP = "need_crop";
    // 多图选择模式
    private boolean isChooseMany;
    // 是否需要裁剪
    private boolean needCrop;
    // 多图选择当前的张数
    private int chooseManyCurrentCout;
    /**
     * 返回结果，单图或拍照返回单张的路径
     */
    public static final String SINGLE_PHOTO_PATH = "single_photo_path";
    /**
     * 多图选择返回的多图路基String arrayList
     */
    public static final String MANY_PHOTO_PATH_ARR = "many_photo_path_arr";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zpf_activity_take_photo);

        isChooseMany = getIntent().getBooleanExtra(IS_CHOOSE_MANY, false);
        chooseManyCurrentCout = getIntent().getIntExtra(
                CHOOSE_MANY_CURRENT_SIZE, 0);
        needCrop = getIntent().getBooleanExtra(NEED_CROP, false);
        needCrop = false;
        findViewById(R.id.pop_layout).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });
        getWindow().setLayout(LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT);
        context = this;
        initCacheDir();
        layoutBtns = findViewById(R.id.layoutBtns);
        // 拍照
        findViewById(R.id.tvCamera).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (ContextCompat.checkSelfPermission(TakePhotoActivity.this,
                                Manifest.permission.CAMERA)
                                != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(TakePhotoActivity.this,
                                    new String[]{ Manifest.permission.CAMERA},
                                    1);

                        } else {
                            //执行获取权限后的操作
                            String currentPhotoName = PhotoUtil.createDefaultName();
                            currentPhotoPath = IMG_DIR + currentPhotoName;
                            PhotoUtil.takePhotoCustomerPath(context, IMG_DIR,
                                    currentPhotoName, REQUEST_PHOTO_CAMERA);
                            Log.e("qizfeng", "checkPermission:" + currentPhotoName + "," + currentPhotoPath);
                            layoutBtns.setVisibility(View.GONE);
                        }

//                        checkPermission(Manifest.permission.CAMERA);
                    }
                });
        // 相册选择
        findViewById(R.id.tvPhoto).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (ContextCompat.checkSelfPermission(TakePhotoActivity.this,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(TakePhotoActivity.this,
                                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                    2);

                        } else {
                            if (isChooseMany) {
                                // 多图选择
                                Intent intent = new Intent(context,
                                        PhotoAlbumActivity.class);
                                PhotoAlbumActivity.hasCount = chooseManyCurrentCout;
                                startActivityForResult(intent, REQUEST_PHOTO_GARRY);
                            } else {
                                // 相册选择
                                PhotoUtil.pickPhoto(context,
                                        REQUEST_PHOTO_GARRY_SIGLE);
                            }
                        }

                        layoutBtns.setVisibility(View.GONE);
                    }
                });
        // 取消
        findViewById(R.id.tvCancel).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });
    }


    private void checkPermission(String permission) {
        int permissionCheck = ContextCompat.checkSelfPermission(TakePhotoActivity.this, permission);
        if (permissionCheck
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_CONTACTS)) {
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_CONTACTS},
                        1);
            }
        } else {
            //执行获取权限后的操作
            String currentPhotoName = PhotoUtil.createDefaultName();
            currentPhotoPath = IMG_DIR + currentPhotoName;
            PhotoUtil.takePhotoCustomerPath(context, IMG_DIR,
                    currentPhotoName, REQUEST_PHOTO_CAMERA);
            Log.e("qizfeng", "checkPermission:" + currentPhotoName + "," + currentPhotoPath);
            layoutBtns.setVisibility(View.GONE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1:
                // If request is cancelled, the result arrays are empty.
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    String currentPhotoName = PhotoUtil.createDefaultName();
                    currentPhotoPath = IMG_DIR + currentPhotoName;
                    PhotoUtil.takePhotoCustomerPath(context, IMG_DIR,
                            currentPhotoName, REQUEST_PHOTO_CAMERA);
                    Log.e("qizfeng", "onRequestPermissionsResult:" + currentPhotoName + "," + currentPhotoPath);
                    layoutBtns.setVisibility(View.GONE);
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    ToastUtils.show(TakePhotoActivity.this, "您已拒绝了权限");
                }
//                return;
                break;

            case 2:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (isChooseMany) {
                        // 多图选择
                        Intent intent = new Intent(context,
                                PhotoAlbumActivity.class);
                        PhotoAlbumActivity.hasCount = chooseManyCurrentCout;
                        startActivityForResult(intent, REQUEST_PHOTO_GARRY);
                    } else {
                        // 相册选择
                        PhotoUtil.pickPhoto(context,
                                REQUEST_PHOTO_GARRY_SIGLE);
                    }
                } else {
                    // Permission Denied
                    ToastUtils.show(TakePhotoActivity.this, "您已拒绝了权限");
                    //Toast.makeText(MainActivity.this, "Permission Denied", Toast.LENGTH_SHORT).show();
                }

                break;

        }
    }


    // 拍照获取的
    private String currentPhotoPath;
    /**
     * 图片路径
     */
    private String IMG_DIR;

    /**
     * 初始化缓存空间
     */
    private void initCacheDir() {
        IMG_DIR = CacheManager.getImgDir(context);
    }

    public static final int REQUEST_PHOTO_CAMERA = 1; // 拍照
    public static final int REQUEST_PHOTO_GARRY = 2; // 相册选择
    public static final int REQUEST_PHOTO_GARRY_SIGLE = 3; // 选择单张照片
    public static final int REQUEST_CROP = 4; // 裁剪

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_PHOTO_GARRY:
                    // 图片多选
                    if (data != null) {
                        ArrayList<Item> items = data
                                .getParcelableArrayListExtra(PhotoAlbumActivity.RESULT_FILES);
                        if (items != null && items.size() > 0) {
                            int size = items.size();
                            pickImgsCount = size;
                            ZoomListener zoomListener = new ZoomListener(true);
                            for (int i = 0; i < size; i++) {
                                // 批量图片压缩,图片执行压缩更多的是因为选取的返回的图片是翻转的
                                Item item = items.get(i);
                                // imageAdapter.add(new File(item.getPhotoPath()));
                                PhotoUtil
                                        .zoomImage(context, Uri.fromFile(new File(
                                                        item.getPhotoPath())), IMG_DIR
                                                        + "uploadimg_" + i + "_"
                                                        + PhotoUtil.createDefaultName(),
                                                Constants.IMG_ZOOM_WIDTH_MAX,
                                                Constants.IMG_ZOOM_HEIGHT_MAX,
                                                Constants.IMG_ZOOM_QUALITY,
                                                zoomListener, true);
                            }
                        }
                    }
                    break;
                case REQUEST_PHOTO_GARRY_SIGLE:
                    // 单图选择
                    Uri photoPath = PhotoUtil.getPhotoPath(context, data);
                    if (photoPath != null) {
                        zoomImageSingle(photoPath.getPath());
                        // if (needCrop) {
                        // currentPhotoPath = IMG_DIR+PhotoUtil.createDefaultName();
                        // PhotoUtil.openCropImage(context, Uri.fromFile(new
                        // File(photoPath.getPath())),
                        // Uri.fromFile(new File(currentPhotoPath)), 400, 400,
                        // REQUEST_CROP);
                        // }else{
                        //
                        // }
                    }
                    break;
                case REQUEST_PHOTO_CAMERA:
                    Log.e("qizfeng", "onac:" + currentPhotoPath);
                    // 拍照
                    if (currentPhotoPath != null) {
                        zoomImageSingle(currentPhotoPath);
                        // if (needCrop) {
                        // // currentPhotoPath =
                        // IMG_DIR+PhotoUtil.createDefaultName();
                        // PhotoUtil.openCropImage(context, Uri.fromFile(new
                        // File(currentPhotoPath)),
                        // Uri.fromFile(new File(currentPhotoPath)), 400, 400,
                        // REQUEST_CROP);
                        // }else{
                        //
                        //
                        // }
                    }
                    break;
                case REQUEST_CROP:
                    // 裁剪成功
                    onSingChooseSuccess();
                    break;
                default:
                    break;
            }
        } else {
            finish();
        }
    }

    /**
     * 压缩单张照片
     */
    private void zoomImageSingle(String oldPath) {
        PhotoUtil.zoomImage(context, Uri.fromFile(new File(oldPath)), IMG_DIR
                        + "uploadimg_" + "_" + PhotoUtil.createDefaultName(),
                Constants.IMG_ZOOM_WIDTH_MAX, Constants.IMG_ZOOM_HEIGHT_MAX,
                Constants.IMG_ZOOM_QUALITY, new ZoomListener(false), true);
    }

    // 图片选择的总张数
    public int pickImgsCount;
    // 成功压缩的张数
    public int successZoomSize;
    ArrayList<String> manyPaths = new ArrayList<String>();
    private View layoutBtns;

    /**
     * 压缩监听 Author:heiyue Email:heiyue623@126.com 2015-4-14下午4:45:26
     */
    class ZoomListener implements PhotoUtil.ImageZoomCallBack {
        // 多图选择标识
        private boolean isMany;

        public ZoomListener(boolean isMany) {
            this.isMany = isMany;
        }

        @Override
        public void onImgZoomStart() {

        }

        @Override
        public synchronized void onImgZoomSuccess(String newPath) {
            if (isMany) {
                // 多图业务执行
                successZoomSize++;
                manyPaths.add(newPath);
                // 最后一个压缩成功
                if (pickImgsCount > 0 && successZoomSize == pickImgsCount) {
                    // 并且不满最大值
                    successZoomSize = 0;
                    pickImgsCount = 0;
                    // 执行完成
                    Intent intent = new Intent();
                    intent.putStringArrayListExtra(MANY_PHOTO_PATH_ARR,
                            manyPaths);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            } else {
                currentPhotoPath = newPath;
                if (needCrop) {
                    // 需要裁剪,裁剪的时候注意 有点手机不支持保存路径和路径一致
                    currentPhotoPath = IMG_DIR + PhotoUtil.createDefaultName();
                    PhotoUtil.openCropImage(context,
                            Uri.fromFile(new File(newPath)),
                            Uri.fromFile(new File(currentPhotoPath)), 400, 400,
                            REQUEST_CROP);
                } else {
                    // 单图业务执行
                    onSingChooseSuccess();
                }
            }
        }

        @Override
        public void onImgZoomFail() {
            Toast.makeText(context, "图片获取失败", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    @Override
    public void finish() {
        super.finish();

    }

    /**
     * 从data中获取返回的路径
     *
     * @param data
     * @return
     */
    public static String getDataPath(Intent data) {
        if (data != null) {
            return data.getStringExtra(SINGLE_PHOTO_PATH);
        }
        return null;
    }

    /**
     * 启动获取单张图片
     *
     * @param context
     * @param request  请求参数
     * @param needCrop 是否需要裁剪
     */
    public static void startActivityForSinglePhoto(Activity context,
                                                   int request, boolean needCrop) {
        Intent intent = new Intent(context, TakePhotoActivity.class);
        intent.putExtra(TakePhotoActivity.NEED_CROP, needCrop);
        context.startActivityForResult(intent, request);
    }

    /**
     * 从data中获取多图选择的路径
     *
     * @param data
     * @return
     */
    public static ArrayList<String> getDataPathArr(Intent data) {
        if (data != null) {
            return data.getStringArrayListExtra(MANY_PHOTO_PATH_ARR);
        }
        return null;
    }

    /**
     * 单张选择完成
     */
    private void onSingChooseSuccess() {
        Intent intent = new Intent();
        Log.e("qizfeng", "onSingChooseSuccess:" + currentPhotoPath);
        intent.putExtra(SINGLE_PHOTO_PATH, currentPhotoPath);
        setResult(RESULT_OK, intent);
        finish();
    }
}
