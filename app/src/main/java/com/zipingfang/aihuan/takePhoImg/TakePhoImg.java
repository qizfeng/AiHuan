package com.zipingfang.aihuan.takePhoImg;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.widget.Toast;

import com.zipingfang.aihuan.utils.ImageFileUtils;
import com.zipingfang.aihuan.utils.Lg;
import com.zipingfang.aihuan.utils.ScreenUtils;
import com.zipingfang.aihuan.utils.XmlUtils;

import java.io.File;

public class TakePhoImg {


    public static final int PHOTO_MODEL_SELECT_IMG = 1121;//预览图片
    public static final int PHOTO_MODEL_TAKE_RESULT = 1122;//预览图片

    public static final int PHOTO_MODEL_CUT_THE_FILE = 1123; //裁剪指定文件
    public static final int PHOTO_MODEL_CUT_BIG_IMG = 1124; //裁剪选择图片-大图

    public static final int PHOTO_MODEL_TAKE_PHO = 1125; //照相

    /**
     * 拍照或选择图片异常后，使用小图模式裁剪
     */
    private static final int ERR_IMG_SIZE = 200;

    public interface ITakePhoImg {
        void onTakePhoto(File imgFile, String imgName);

        void onSelectImage(File imgFile, String imgName);

        void onFailed(String errMsg);
    }

    /**
     * ScreenInfo.getInstance(this).getHeight();
     */
    public int mImageSize = 0;
    private int mScreenSize = 0;

    /**
     * 保存前的质量压缩，最大100
     */
    private int mQuality = 100;
    private String mImageFileShortName = null;
    private File mFImageFile = null;
    private static String mPreFullFile;
    private boolean mCutImg = false;
    private double mCutW = 1;
    private double mCutH = 1;

    Activity context;
    ITakePhoImg mTakePhoImgCallback;

    public TakePhoImg(Activity context, ITakePhoImg takePhoImg) {
        this.context = context;
        mScreenSize = ScreenUtils.getInstance(context).getHeight();
        this.mTakePhoImgCallback = takePhoImg;
    }

    private void initImageSize() {
        if (mImageSize == 0) {
            Lg.error("Image size=0, init size in TakePhoImg.java");
            mImageSize = ScreenUtils.getInstance(context).getWidth();
            if (mImageSize == 0) mImageSize = 720;
        }
    }

    /**
     * 设置图片路径和名称
     *
     * @param file
     */
    private void setImageFile(File file) {
        if (file == null) {
            mImageFileShortName = PhotoUtil.createDefaultName();
            mFImageFile = new File(getFileLocalPath(context) + mImageFileShortName);
        } else {
            mImageFileShortName = file.getName();
            mFImageFile = file;
        }

    }

    /**
     * 取得图片本地路径
     *
     * @param context
     * @return
     */
    public static String getFileLocalPath(Context context) {
        return ConstUtils.getFileLocalPath(context);
    }

    /**
     * 裁剪图片
     *
     * @param b
     */
    public void setCut(boolean b) {
        mCutImg = b;
    }

    /**
     * 高/宽的
     */
    public void setCut(boolean b, double height_width_Rate) {
        mCutImg = b;
        mCutW = 1;
        if (height_width_Rate > 0) mCutH = height_width_Rate;
    }

    public void setCutHWRate(boolean b) {
        mCutImg = b;
        mCutW = 1;
        mCutH = 1.0 * ScreenUtils.getInstance(context).getHeight() / ScreenUtils.getInstance(context).getWidth();
    }

    /**
     * 压缩质量: 30 to 100
     *
     * @param vQuality
     */
    public void setQuality(int vQuality) {
        mQuality = vQuality;
    }

    /**
     * 拍照或选择图片存在错误!!!!!
     *
     * @return
     */
    public boolean isNormalModel() {
        String prePhoResult = XmlUtils.getFromXml(context, Const_ErrorFlag, "0");
        return prePhoResult == null || "".equals(prePhoResult) || "0".equals(prePhoResult);
    }

    public void setNullModel() {
        XmlUtils.saveToXml(context, Const_ErrorFlag, "0"); //标记还原
    }

    public void setNormalModel() {
        XmlUtils.saveToXml(context, Const_ErrorFlag, "1"); //正常流程
    }

    public void setExceptionModel() {
        XmlUtils.saveToXml(context, Const_ErrorFlag, "2"); //出现异常
    }

    /**
     * 1. 选择图片
     * 也可以裁剪图片哦：
     * tpi.setCut(true);
     * tpi.mImageSize=150;
     * tpi.selectImage();
     */
    public void selectImage(File file) {
        initImageSize();
        setImageFile(file);    // 设置文件名称

        if (isNormalModel()) {//说明上次正常
            setNormalModel();
            PhotoUtil.pickPhoto(context, PHOTO_MODEL_SELECT_IMG);
        } else {
            error(">>>>>>>>>>>>>>selectImage/使用新模式，上面的应该出现了异常!");
            setExceptionModel();
            PhotoUtil.pickPhoto(context, PHOTO_MODEL_SELECT_IMG); //新方式
        }
    }

    private Uri m_temp_TakePath;

    /**
     * 2. 拍照并保存到指定路径
     */
    public void takePhoto(File file) {
        initImageSize();
        setImageFile(file);    // 设置文件名称

        mPreFullFile = mFImageFile.getAbsolutePath(); //拍照有时候会让上面值丢失
        debug("文件名称:" + mFImageFile.getAbsolutePath());

//		String prePhoResult=XmlUtils.getFromXml(context, Const_ErrorFlag, "0");
//		if ("0".equals(prePhoResult)){//说明上次拍照正常
        m_temp_TakePath = null;
        // 调用系统相机拍照
        m_temp_TakePath = PhotoUtil.openCameraCustomerPath(context,
                mFImageFile.getParent(), null, //拍照的时候，名称使用随机建立的，不可以和原图片一致
                PHOTO_MODEL_TAKE_PHO);
        debug("拍照后返回的新文件路径：" + m_temp_TakePath.getPath());
//			XmlUtils.saveToXml(context, Const_ErrorFlag, "1");
//		}
//		else{//这个方法也不通用,cancel it
//			error(">>>>>>>>>>>>>>使用新模式拍照，上面的应该出现了异常!");
//			takePhotoNew(); //这个方法也不通用，注释此功能代码!!!!!!!!!!!!!!!
//			XmlUtils.saveToXml(context, Const_ErrorFlag, "2");
//		}  
    }

    /**
     * 选择图片 - CUT
     * eg:
     * TakePhoImg tpi = getTakePhoImg();
     * tpi.setCut(true,1.3);
     * tpi.cutImage();
     */
    public void cutImage(File file) {
        initImageSize();
        setImageFile(file);    // 设置文件名称

        setNormalModel();
        PhotoUtil.cutBigImage(context, mFImageFile, mImageSize, PHOTO_MODEL_CUT_BIG_IMG, mCutW, mCutH);
    }

    /**
     * 裁剪指定文件>>选择图片的时候调用了
     * >>mImageSize=350; //太大了也有问题，只适合头像
     *
     * @param file
     */
    public void cutImageFile(File file) {
        if (file == null || !file.exists()) return;
        initImageSize();
        setImageFile(file);    // 设置文件名称

        mCutImg = true;

        if (!this.isNormalModel() && mCutW != mCutH) {
            error("因为正常模式有问题，减小图片大小来裁剪...");
            Bitmap bmp = ImageFileUtils.getBitmap(mFImageFile.getAbsolutePath());
            if (bmp.getWidth() < mImageSize) mImageSize = bmp.getWidth() / 2;
            if (bmp.getHeight() < mImageSize) mImageSize = bmp.getHeight() / 2;
            if (mImageSize > ERR_IMG_SIZE) mImageSize = ERR_IMG_SIZE; //350 def,太大了有问题-vivo
            bmp.recycle();

            debug("异常模式/图片裁剪大小:" + mImageSize);
        }

        Uri vuri = Uri.fromFile(file);
        PhotoUtil.openCropImage(context, vuri, mImageSize, mCutW, mCutH, PHOTO_MODEL_CUT_THE_FILE);
    }

    /**
     * 自定义拍照
     * @param file
     */
//	public void takePhotoNew(File file) {
//		initImageSize();
//		setImageFile(file);	// 设置文件名称	
//		 
//		mPreFullFile = mFImageFile.getAbsolutePath(); //拍照有时候会让上面值丢失
//  
//		// 新模式拍照:自定义模式拍照
//		Intent intent = new Intent(context, CameraActivity.class);
//		// 输出路径
//		intent.putExtra(MediaStore.EXTRA_OUTPUT, mFImageFile.getAbsolutePath());
//		context.startActivityForResult(intent, CameraActivity.PHOTO_MODEL_TAKE);  
//	}

    /**
     * 拍照错误后，切换拍照模式，不过一般都是没设置属性导致：
     * android:configChanges="orientation|screenSize"
     */
    private final static String Const_ErrorFlag = "Const_ErrorFlag_20150427";

    /**
     * 返回结果判定
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        String prePhoResult = XmlUtils.getFromXml(context, Const_ErrorFlag, "0");
        if ("1".equals(prePhoResult)) {//正常模式
            setNullModel();//标记还原
        } else {
            debug("选择照片出现异常，使用异常模式【Const_ErrorFlag】：" + prePhoResult);
        }

        if (resultCode != Activity.RESULT_OK) return;

        switch (requestCode) {
            case PHOTO_MODEL_SELECT_IMG:  //选择相册 ----------------------1.
                debug("选择相册 ----------------------1. ");
                doSelectImage(data);
                break;
            case PHOTO_MODEL_TAKE_PHO: //调用系统相机模式 -----------------2
                debug("调用系统相机模式 -------------------2 ");

                //三星手机出现这个异常情况，静态变量还是有值，重新利用原来的值试试
                if (mImageFileShortName == null && mPreFullFile != null) {
                    mFImageFile = new File(mPreFullFile);
                    mImageFileShortName = mFImageFile.getName();
                }

                //裁剪
                if (this.mCutImg && new File(m_temp_TakePath.getPath()).exists()) { //裁剪大小一定不能大于原图大小，否则无反应
                    debug("裁剪指定文件:" + m_temp_TakePath.getPath());
                    Uri from_uri = Uri.fromFile(new File(m_temp_TakePath.getPath()));
                    Uri saveto_uri = Uri.fromFile(mFImageFile);

                    if (!this.isNormalModel() && mCutW != mCutH) {//vivo手机必须使用下面方法，否则出现黑边
                        mImageSize = ERR_IMG_SIZE;
                        debug("异常模式/裁剪图片大小:" + mImageSize);
                    }

                    PhotoUtil.openCropImage(context, from_uri, saveto_uri, mImageSize,
                            mCutW, mCutH, PHOTO_MODEL_CUT_BIG_IMG);
                } else {
                    doTakePhoto();
                }

                break;
            case PHOTO_MODEL_CUT_BIG_IMG:    //--------------------------3  裁剪选择图片-支持大图
                debug("裁剪选择图片 -------------------------------3.1  ");
                doCutImageFile(mFImageFile);
                break;

            case PHOTO_MODEL_CUT_THE_FILE: // 裁剪指定图片
                debug("裁剪指定图片【异常模式】   -------------------------------3.2  ");
                doCutImageFile(mFImageFile);
                break;

//		case CameraActivity.PHOTO_MODEL_TAKE: //新拍照模式 --------------------------------------9.1
//			debug("新拍照模式 ------------------9.1 ");
//			doNewCustomer_TakePho(data);
//			break; 

            case PHOTO_MODEL_TAKE_RESULT: //预览图片--------------------------------------9.2
                debug("预览图片--------------------9.2 ");
                if (data == null) return;

                String fileName = data.getStringExtra("fileName");
                if (fileName == null || "".equals(fileName)) {
                    debug("重新拍>>");
                    takePhoto(mFImageFile);
                } else {
                    if (mTakePhoImgCallback != null) {
                        mTakePhoImgCallback.onTakePhoto(mFImageFile, mImageFileShortName);
                        mImageFileShortName = null;
                    } else {
                        debug("mTakePhoImgCallback is null!");
                    }
                }

                break;
            default:
                error("no nothing!");
                break;
        }
    }

    /**
     * 自定义的拍照模式
     */
//	private void doNewCustomer_TakePho(Intent data) {
//		Bitmap zipBitmap;
//		try{
//			if (data!=null){ 
//				String imageFullFile=mFImageFile.getAbsolutePath(); 
//				 
////				zipBitmap=ImageFileUtils.getBitmap(context,uri,this.mImageSize);//good 
//				if (this.mCutImg) //mScreenSize
//					zipBitmap=ImageFileUtils.getBitmap(imageFullFile, mScreenSize);//ok
//				else
//					zipBitmap=ImageFileUtils.getBitmap(imageFullFile, mImageSize);//ok
//    			int rotate=data.getIntExtra("rotate", 0);
//    			if (rotate!=0){
//    				Bitmap rBitmap=PhotoUtil.rotateBitmap(zipBitmap, Integer.valueOf(rotate));
//	    			PhotoUtil.saveImageFileByBitmap(null, imageFullFile, mQuality, rBitmap, false);
//	    			
//	    			if (!rBitmap.isRecycled()) rBitmap.recycle();
//    			}	   
//    			
//    			if (!zipBitmap.isRecycled()) zipBitmap.recycle();
//
//    			//提交结果 
//    			if (this.mCutImg){ //裁剪大小一定不能大于原图大小，否则无反应 
//    				cutImageFile(mFImageFile); 
//    			} 
//    			else if (mImageFileShortName!=null){	     
//	    			Intent intent=new Intent(context, CameraActResult.class);
//	    			intent.putExtra("fileName", mFImageFile.getAbsolutePath());
//	    			context.startActivityForResult(intent, PHOTO_MODEL_TAKE_RESULT); //预览图片
//	    		}else{
//	    			showMsg("拍照出现异常");
//	    		} 
//    		}else{
//    			error("新方法：拍照出现异常!");
//    			if (mTakePhoImgCallback!=null){
//					mTakePhoImgCallback.onFailed("拍照出现异常!");
//				}else{
//					debug("mTakePhoImgCallback is null!");
//				}
//    			return;
//    		} 
//		} catch (Exception e) {
//			error(e); 
//			if (mTakePhoImgCallback!=null){
//				mTakePhoImgCallback.onFailed(e+"");
//			}else{
//				debug("mTakePhoImgCallback is null!");
//			}
//			return;
//		}   
//	}

    /**
     * 开始拍照
     */
    private void doTakePhoto() {
        try {
            //三星手机出现这个异常情况，静态变量还是有值，重新利用原来的值试试
            if (mImageFileShortName == null && mPreFullFile != null) {
                mFImageFile = new File(mPreFullFile);
                mImageFileShortName = mFImageFile.getName();
            }

            //拍照后保存在m_temp_TakePath，下面压缩并保存到新路径 mFImageFile
            if (mImageFileShortName != null && new File(m_temp_TakePath.getPath()).exists()) {     //拍照文件存在，才继续
                String imageFullFile = m_temp_TakePath.getPath();
                debug(imageFullFile);

                //压缩下比较好，原始的太大
//					zipBitmap=ImageFileUtils.getBitmap(context,uri,this.mImageSize);//good 
                Bitmap zipBitmap = ImageFileUtils.getBitmap(imageFullFile, mImageSize);//ok

                //旋转图片, 保存到新文件
                int rotate = PhotoUtil.readPictureDegree(imageFullFile);
                if (rotate != 0) {
                    Bitmap rBitmap = PhotoUtil.rotateBitmap(zipBitmap, Integer.valueOf(rotate));
                    PhotoUtil.saveImageFileByBitmap(null, mFImageFile.getAbsolutePath(), mQuality, rBitmap, false);

                    if (!rBitmap.isRecycled()) rBitmap.recycle();
                } else {
                    PhotoUtil.saveImageFileByBitmap(null, mFImageFile.getAbsolutePath(), mQuality, zipBitmap, false);
                }

                if (!zipBitmap.isRecycled()) zipBitmap.recycle();

                //提交结果
                if (mTakePhoImgCallback != null) {
                    debug("send image:");
                    debug(imageFullFile);
                    debug(mFImageFile.getAbsolutePath());
                    mTakePhoImgCallback.onTakePhoto(mFImageFile, mImageFileShortName);
                    mImageFileShortName = null;
                } else {
                    error("mTakePhoImgCallback is null!");
                }
            } else {
                error("拍照异常了!!!!!!!!!!!!m_temp_TakePath is null!!!");
//	    			XmlUtils.saveToXml(context, Const_ErrorFlag, "1");//新方式小米2有问题，也不使用了
                showMsg("拍照出现异常, 请重新开机试试！");
            }

        } catch (Exception e) {
            error(e);
            if (mTakePhoImgCallback != null) {
                mTakePhoImgCallback.onFailed(e + "");
            } else {
                debug("mTakePhoImgCallback is null!");
            }
            return;
        }

    }

    /**
     * 裁剪文件
     *
     * @param mFImageFile
     */
    private void doCutImageFile(File mFImageFile) {
        Bitmap zipBitmap, rBitmap;
        try {
            zipBitmap = ImageFileUtils.getBitmap(mFImageFile.getAbsolutePath());
            if (zipBitmap != null) {
                //旋转图片, 保存到新文件
                String imageFullFile = mFImageFile.getAbsolutePath();
                int rotate = PhotoUtil.readPictureDegree(imageFullFile);
                if (rotate != 0) {
                    rBitmap = PhotoUtil.rotateBitmap(zipBitmap, Integer.valueOf(rotate));
                    PhotoUtil.saveImageFileByBitmap(null, imageFullFile, mQuality, rBitmap, false);

                    if (!rBitmap.isRecycled()) rBitmap.recycle();
                } else {
                    PhotoUtil.saveImageFileByBitmap(null, imageFullFile, mQuality, zipBitmap, false);
                }

                if (!zipBitmap.isRecycled()) zipBitmap.recycle();
            }


            if (mTakePhoImgCallback != null && mFImageFile.exists()) {
                mTakePhoImgCallback.onSelectImage(mFImageFile, mImageFileShortName);
                mImageFileShortName = null;
            } else {
                if (mTakePhoImgCallback == null)
                    debug("mTakePhoImgCallback is null!");
                else if (!mFImageFile.exists())
                    debug("no find:" + mFImageFile.getAbsolutePath());
            }
        } catch (Exception e) {
            error(e);
            if (mTakePhoImgCallback != null) {
                mTakePhoImgCallback.onFailed(e + "");
            } else {
                debug("mTakePhoImgCallback is null!");
            }
            return;
        }

    }

    /**
     * 选择图片
     * 1. 图片路径在data中
     *
     * @param data
     */
    private void doSelectImage(Intent data) {
        Uri uri = PhotoUtil.getPhotoPath(context, data);
        debug(uri.getPath() + ",size:" + new File(uri.getPath()).length() / 1024.0 + "k");

        Bitmap zipBitmap, rBitmap;
        try {
            String fromFile = uri.getPath();
            String toFile = mFImageFile.getAbsolutePath();


            //压缩下比较好，原始的太大
//			zipBitmap=ImageFileUtils.getBitmap(context,uri,this.mImageSize);//good
            if (this.mCutImg) //mScreenSize
                zipBitmap = ImageFileUtils.getBitmap(uri.getPath(), mScreenSize);//mScreenSize
            else
                zipBitmap = ImageFileUtils.getBitmap(uri.getPath(), mImageSize);//ok

            int rotate = PhotoUtil.readPictureDegree(fromFile);

            //旋转图片, 保存到新文件
            if (rotate != 0) {
                rBitmap = PhotoUtil.rotateBitmap(zipBitmap, Integer.valueOf(rotate));
                PhotoUtil.saveImageFileByBitmap(null, toFile, mQuality, rBitmap, false);
                if (!rBitmap.isRecycled()) rBitmap.recycle();
            } else {
                PhotoUtil.saveImageFileByBitmap(null, toFile, mQuality, zipBitmap, false);
            }

            if (!zipBitmap.isRecycled()) zipBitmap.recycle();

            //set resule:
            if (mImageFileShortName != null) {
                //提交结果
                if (this.mCutImg) { //裁剪大小一定不能大于原图大小，否则无反应
                    cutImageFile(mFImageFile);
                } else if (mTakePhoImgCallback != null) {
                    mTakePhoImgCallback.onSelectImage(mFImageFile, mImageFileShortName);
                    mImageFileShortName = null;
                } else {
                    debug("mTakePhoImgCallback is null!");
                }
            } else {
                showMsg("选择相册出现异常");
            }
        } catch (Exception e) {
            error(e);
            if (mTakePhoImgCallback != null) {
                mTakePhoImgCallback.onFailed(e + "");
            } else {
                debug("mTakePhoImgCallback is null!");
            }
            return;
        }
    }

    /**
     * @param string
     */
    private void showMsg(String string) {
        Toast.makeText(context, string, Toast.LENGTH_LONG).show();
    }

    private void error(Exception e) {
        error("" + e);
        e.printStackTrace();
    }

    private void error(String string) {
        System.out.println("___Error:" + string);
    }

    private void debug(String string) {
        System.out.println(string);
    }


}
