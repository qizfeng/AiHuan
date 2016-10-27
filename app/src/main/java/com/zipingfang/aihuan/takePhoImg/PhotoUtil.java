package com.zipingfang.aihuan.takePhoImg;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.provider.MediaStore.MediaColumns;

import com.zipingfang.aihuan.utils.ImageFileUtils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

/**
 * 拍照或选取
 *  
 */
public class PhotoUtil {

	/** 拍照 */
	public static final int PHOTO_MODEL_TAKE = 201; 
	/** 本地相册 */
	public static final int PHOTO_MODEL_ALBUM = 202;
	public static final int PHOTO_MODEL_CROP = 203;
	
	 

	/**
	 * 打开相册
	 * 1. 相册获取图片路径: Uri uri=PhotoUtil.getPhotoPath(context, data);	 * 
	 * @param activity
	 */
	public static void pickPhoto(Activity activity) {
		pickPhoto(activity, PHOTO_MODEL_ALBUM);
	}
	//Uri uri=PhotoUtil.getPhotoPath(context, data);
	public static void pickPhoto(Activity activity, int requestCode) {
//		Intent intent = new Intent(Intent.ACTION_GET_CONTENT);//huoshi云测结果出现很多机型有问题，选择图片后没反应
//		intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");  
		// 此方法比较兼容
		Intent intent = new Intent(Intent.ACTION_PICK,
			android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		activity.startActivityForResult(intent, requestCode);
	}
	//Fragment
	public static void pickPhoto(Fragment activity) {
		pickPhoto(activity, PHOTO_MODEL_ALBUM);
	}
	public static void pickPhoto(Fragment activity, int requestCode) {
		// Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
		// intent.setType("image/*");
		// 此方法比较兼容
		Intent intent = new Intent(Intent.ACTION_PICK,
			android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		activity.startActivityForResult(intent, requestCode);
	}
	//取得本地相册 - 输出到正方形 >> 文件直接保存在outputFileName!!!
	public static void pickPhotoToRect(Activity activity, File outputFileName, int imageSize) {
		pickPhotoToRect(activity,outputFileName,imageSize,PHOTO_MODEL_CROP,false);//freeCut=false:正方形
	}
	public static void pickPhotoToRect(Activity activity, File outputFileName,
									   int imageSize, int requestCode, boolean freeCut) {//freeCut=false:正方形
		System.out.println("调用[正方形 ]裁剪方式");

		Intent intent = new Intent(Intent.ACTION_PICK);
		intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
		intent.putExtra("output", Uri.fromFile(outputFileName));

		intent.putExtra("crop", "true");
		if (!freeCut){
			intent.putExtra("aspectX", 1);//必须这样才可以正方形裁剪
			intent.putExtra("aspectY", 1);
		}
		intent.putExtra("outputX", imageSize);// 输出图片大小
		intent.putExtra("outputY", imageSize);

		intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
		intent.putExtra("noFaceDetection", true); // 不需要定位到脸部
		activity.startActivityForResult(intent, requestCode);
	}

	/**
	 * 大图片裁剪
	 *   use eg: Bitmap newBmp = ImageFileUtils.getBitmap(mFImageFile.getAbsolutePath());
	 * @param activity
	 * @param outputFileName
	 * @param imageSize
	 * @param requestCode
	 * @param cutX
	 * @param cutY
	 */
	public static void cutBigImage(Activity activity, File outputFileName,
								   int imageSize, int requestCode, double cutX, double cutY) {
		System.out.println("cutBigImage()："+cutX+","+cutY+",imageSize="+imageSize);

//		Uri imageUri = Uri.parse("file://"+outputFileName.getAbsolutePath());
		//or:
		Uri imageUri = Uri.fromFile(outputFileName);

//			Intent intent = new Intent(Intent.ACTION_GET_CONTENT);//huoshi云测结果出现很多机型有问题，选择图片后没反应
//			intent.setDataAndType(MediaStore.Images.Media.INTERNAL_CONTENT_URI, "image/*");
//			// 此方法比较兼容
//			Intent intent = new Intent(Intent.ACTION_PICK,
//					android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//			activity.startActivityForResult(intent, requestCode);

		activity.startActivityForResult(getCutIntent(Intent.ACTION_PICK,
				imageUri,imageUri,imageSize, cutX,cutY, requestCode), requestCode);
	}

	/**
	 * 裁剪图片
	 * @param prop： Intent.ACTION_PICK or:
	 * @param from_uri
	 * @param imageSize
	 * @param cutX
	 * @param cutY
	 * @param requestCode
	 * @return
	 */
	private static Intent getCutIntent(String prop, Uri from_uri, Uri saveto_uri,
									   int imageSize, double cutX, double cutY, int requestCode) {
		System.out.println(">>>>cutX="+cutX+",cutY="+cutY+",imageSize="+imageSize
				+",requestCode="+requestCode);

		// 裁剪图片意图
		if (prop==null || "".equals(prop)) prop="com.android.camera.action.CROP";
		Intent intent = new Intent(prop, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		intent.setDataAndType(from_uri, "image/*");//打开这个文件
		intent.putExtra(MediaStore.EXTRA_OUTPUT, saveto_uri);//输出到指定文件
		intent.putExtra("crop", "true");
		
		// 裁剪框的比例，w/h
		if (cutX==cutY){
			intent.putExtra("aspectX", 1); //必须这样才可以正方形裁剪
			intent.putExtra("aspectY", 1); 
		}else{ 
			intent.putExtra("aspectX", (int)(cutX*10)); //int表示不可以再修改高宽
			intent.putExtra("aspectY", (int)(cutY*10)); //int表示不可以再修改高宽
		}
		
		// 裁剪后输出图片的尺寸大小
		intent.putExtra("outputX", (int)(imageSize*cutX));// 输出图片大小
		intent.putExtra("outputY", (int)(imageSize*cutY));// 输出图片大小
		// 图片格式
		intent.putExtra("scale", true);
		intent.putExtra("return-data", false);
		intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
		intent.putExtra("noFaceDetection", true); // 不需要定位到脸部
		
		return intent;
	}
	
	
	/**
	 * 打开相机
	 */
	public static void openCamera(Activity activity) {
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		activity.startActivityForResult(intent, PHOTO_MODEL_TAKE);
	}
	public static void openCamera(Activity activity, int requestCode) {
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		activity.startActivityForResult(intent, requestCode);
	}
	//Fragment
	public static void openCamera(Fragment activity) {
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		activity.startActivityForResult(intent, PHOTO_MODEL_TAKE);
	}
	public static void openCamera(Fragment activity, int requestCode) {
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		activity.startActivityForResult(intent, requestCode);
	}
	/**
	 * 拍照并保存到指定路径
	 * 
	 * @param activity
	 * @param cacheDir 拍照存放的路径文件夹
	 * @param imageName
	 *            要保存的文件名，如果为空，则默认生成以日期个时代文件名
	 * @return 文件保存的路径uri
	 */
	public static Uri openCameraCustomerPath(Activity activity, String cacheDir, String imageName) {
		return openCameraCustomerPath(activity,cacheDir,imageName,PHOTO_MODEL_TAKE);
	}
	public static Uri openCameraCustomerPath(Activity activity, String cacheDir,
											 String imageName, int requestCode) {
		if (imageName==null || imageName.length()==0) {
			imageName = createDefaultName();
		}
		File file = new File(cacheDir);
		// 路径不存在则创建路径
		if (!file.exists()) {
			file.mkdirs();
		}
		 
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		Uri uri = Uri.fromFile(new File(cacheDir + File.separator + imageName));
		intent.putExtra(MediaStore.EXTRA_OUTPUT, uri); //拍照后输出到这uri文件上
		activity.startActivityForResult(intent, requestCode);
		return uri;
	}
	public static void openCameraCustomerPath(Activity activity, String filePath) {
		// 跳转到系统拍照
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(filePath)));
		activity.startActivityForResult(intent, PHOTO_MODEL_TAKE);
	}


	/**
	 * 拍照后，可调用此,打开图片裁剪  
	 * eg:
	 *  Uri imageUri = Uri.parse("file://"+mFImageFile.getAbsolutePath());
	 *  //or: Uri imageUri = Uri.fromFile(mFImageFile);
		PhotoUtil.openCropImage(context, imageUri, mImageSize, mImageSize, mCutW,mCutH, PHOTO_MODEL_CUT_PHO);
	 * @param activity
	 * @param uri
	 * @param requestCode
	 */
	public static void openCropImage(Activity activity, Uri uri, int imageSize, int requestCode) {
		openCropImage(activity,uri, imageSize, 1,1, requestCode);
	}
	public static void openCropImage(Activity activity, Uri uri
			, int imageSize, double cutX, double cutY
			, int requestCode) { 
		String prop="com.android.camera.action.CROP";
		activity.startActivityForResult(getCutIntent(prop,uri, uri, 
				imageSize, cutX, cutY, requestCode), requestCode);  
	} 
	public static void openCropImage(Activity activity, Uri from_uri, Uri saveto_uri
			, int imageSize, double cutX, double cutY
			, int requestCode) { 
		String prop="com.android.camera.action.CROP";
		activity.startActivityForResult(getCutIntent(prop,from_uri, saveto_uri, 
				imageSize, cutX, cutY, requestCode), requestCode);  
	} 
	//fragment
	public static void openCropImage(Fragment activity, Uri uri
			, int imageSize, double cutX, double cutY
			, int requestCode) {
		String prop="com.android.camera.action.CROP";
		activity.startActivityForResult(getCutIntent(prop,uri, uri, 
				imageSize, cutX, cutY, requestCode), requestCode); 
	}

	
	/**
	 * 默认创建以时间格式的图片名称
	 * @return
	 */
	public static String createDefaultName(){
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss",
			Locale.getDefault());
		Random rd=new Random();
		return simpleDateFormat.format(new Date(System.currentTimeMillis()))+"_"+rd.nextInt(100) + ".jpg";
	}
	
	
	/**
	 * 拍照获取的结果
	 * @param data
	 * @return
	 */
	public static Bitmap getBitmapFromResult(Intent data) {
		if (data!=null) {
			Bundle bundle = data.getExtras();
			if (bundle!=null) {
				Bitmap bitmap = (Bitmap) bundle.get("data");
				// 获取相机返回的数据，并转换为Bitmap图片格式
				return bitmap;
			}
		}
		return null;
		
	}
	
	/**
	 * 压缩图片进度回调
	 * 
	 */
	public interface ImageZoomCallBack {
		void onImgZoomStart();

		void onImgZoomSuccess(String newPath);

		void onImgZoomFail();
	}

	/**
	 * 压缩图片
	 * 
	 * @param context
	 * @param imageOldPath 原文件路径
	 * @param newPath 新的文件路径
	 * @param width 图片最大的宽度
	 * @param height 图片最大的高度
	 * @param quality 图书压缩的质量30
	 *            -100 数值越大压缩质量越高
	 * @param callBack
	 *            压缩的回调
	 */
	public static void zoomImage(final Context context, final Uri imageOldPath,
								 final String newPath, final int width, final int height, final int quality,
								 final ImageZoomCallBack callBack, final boolean chkRotate) {
		
		final Handler handler = new Handler() {
			@Override
			public void handleMessage(android.os.Message msg) {
				switch (msg.what) {
				case IMAGE_COMPRESS_START:
					if (callBack != null) {
						callBack.onImgZoomStart();
					}
					break;
				case IMAGE_COMPRESS_SUCCESS:
					if (callBack != null) {
						callBack.onImgZoomSuccess(newPath);
					}
					break;
				case IMAGE_COMPRESS_FAIL:
					if (callBack != null) {
						callBack.onImgZoomFail();
					}
					break;
				default:
					break;
				}
			};
		};
		new Thread(new Runnable() {
			@Override
			public void run() {
				handler.sendEmptyMessage(IMAGE_COMPRESS_START);
				try {
					Bitmap pathBitmap = getPathBitmap(context, imageOldPath, width, height);
					if (pathBitmap != null) {
						try{
							saveImageFileByBitmap(imageOldPath.getPath()
									,newPath, quality, pathBitmap, chkRotate);
							handler.sendEmptyMessage(IMAGE_COMPRESS_SUCCESS);
						}catch(Exception e){
							error(e);
							handler.sendEmptyMessage(IMAGE_COMPRESS_FAIL);
						} 
					}
				} catch (Exception e) {
					error(e);
					handler.sendEmptyMessage(IMAGE_COMPRESS_FAIL);
				}
			}
		}).start();
	} 
	private static final int IMAGE_COMPRESS_SUCCESS = 200; //图片压缩成功
	private static final int IMAGE_COMPRESS_START = 100;   //开始压缩图片 
	private static final int IMAGE_COMPRESS_FAIL = 404;    //图片压缩失败


	/**
	 * 相册获取图片路径 >>>强于 data.getData()
	 * 
	 * @param context
	 * @param data
	 * @return
	 */
	public static Uri getPhotoPath(Context context, Intent data) {
		if (data == null) {
			return null;
		}
		Uri path = null;
		Uri uri = data.getData();
		if (uri != null) {
			Cursor c = null;
			try {
				String[] filePathColumns = { MediaColumns.DATA };
				c = context.getContentResolver().query(uri, filePathColumns, null, null, null);
				c.moveToFirst();
				int columnIndex = c.getColumnIndex(filePathColumns[0]);
				String imagePath = c.getString(columnIndex);
				if (imagePath != null) {
					path = Uri.fromFile(new File(imagePath));
				}
			} catch (Exception e) {
				error(e);
			} finally {
				if (c != null) {
					c.close();
				}
			}
		}
		return path;
	}

	/**
	 * 显示需要压缩大图片大小
	 * 
	 * @param context
	 * @param imageFilePath
	 * @param dw
	 *            需要压缩的宽度
	 * @param dh
	 *            需要压缩高度
	 * @return
	 * @throws FileNotFoundException
	 */
	public static Bitmap getPathBitmap(Context context, Uri imageFilePath, int dw, int dh)
		throws FileNotFoundException {
		// 获取屏幕的宽和高
		/**
		 * 为了计算缩放的比例，我们需要获取整个图片的尺寸，而不是图片
		 * BitmapFactory.Options类中有一个布尔型变量inJustDecodeBounds，将其设置为true
		 * 这样，我们获取到的就是图片的尺寸，而不用加载图片了。
		 * 当我们设置这个值的时候，我们接着就可以从BitmapFactory.Options的outWidth和outHeight中获取到值
		 */
		BitmapFactory.Options op = new BitmapFactory.Options();
		op.inJustDecodeBounds = true;
		op.inSampleSize = 1;
		// 由于使用了MediaStore存储，这里根据URI获取输入流的形式
		Bitmap pic = BitmapFactory.decodeStream(
			context.getContentResolver().openInputStream(imageFilePath), null, op);

//		int wRatio = (int) Math.ceil(op.outWidth / (float) dw); // 计算宽度比例
//		int hRatio = (int) Math.ceil(op.outHeight / (float) dh); // 计算高度比例
		int wRatio = (int) ((float)op.outWidth/(float)dw);
		
		/**
		 * 接下来，我们就需要判断是否需要缩放以及到底对宽还是高进行缩放。 如果高和宽不是全都超出了屏幕，那么无需缩放。
		 * 如果高和宽都超出了屏幕大小，则如何选择缩放呢》 这需要判断wRatio和hRatio的大小
		 * 大的一个将被缩放，因为缩放大的时，小的应该自动进行同比率缩放。 缩放使用的还是inSampleSize变量
		 */
//		if (wRatio > 1 && hRatio > 1) {
//			if (wRatio >= hRatio) {
//				op.inSampleSize = wRatio;
//			} else {
//				op.inSampleSize = hRatio;
//			}
//		}
		//仅限制宽度
		if (wRatio>1) {
			op.inSampleSize = wRatio;
		}
		op.inJustDecodeBounds = false; // 注意这里，一定要设置为false，因为上面我们将其设置为true来获取图片尺寸了
		pic = BitmapFactory.decodeStream(context.getContentResolver()
			.openInputStream(imageFilePath), null, op);

		return pic;
	}

	/**
	 * JPG格式保存压缩图片
	 * 
	 * @param savaFilePath
	 *            图片保存路径
	 * @param quality
	 *            压缩比例 最小30 最大100
	 * @param bitmap
	 * @return
	 * @throws Exception
	 */
	public static void saveImageFileByBitmap(String oldPath, String savaFilePath,
											 int quality, Bitmap bitmap, boolean chkRotate) throws Exception {
		BufferedOutputStream bos = null;
		try {
			// 判断图片是否旋转了，旋转图片
			if (chkRotate && oldPath!=null) {
				bitmap = rotateBitmap(bitmap, readPictureDegree(oldPath));
			}
			File image = new File(savaFilePath);
			if (!image.exists()) {
				image.createNewFile();
			}
			bos = new BufferedOutputStream(new FileOutputStream(image));
			if (quality < 30) {
				quality = 30;
			}
			if (quality > 100) {
				quality = 100;
			}
			bitmap.compress(Bitmap.CompressFormat.JPEG, quality, bos);
			bos.flush(); 
		} finally {
			try {
				bos.close();
				//
				bitmap.recycle();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} 
	}

	/**
	 * 判断图片的旋转角度
	 * 
	 * @param path
	 * @return
	 */
	public static int readPictureDegree(String path) {
		return ImageFileUtils.readPictureDegree(path);
	}

	/**
	 * 旋转图片的角度
	 * 
	 * @param bmp
	 * @param degress
	 * @return
	 */
	public static Bitmap rotateBitmap(Bitmap bmp, int degress) {
		try {
			return ImageFileUtils.rotateBitmap(bmp, degress);
		} catch (Exception e) {
			e.printStackTrace();
			return bmp;
		}
	}


	private static void error(Exception e) {
		e.printStackTrace();
	}
}
