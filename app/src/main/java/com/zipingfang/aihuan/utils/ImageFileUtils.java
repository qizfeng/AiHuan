package com.zipingfang.aihuan.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 图片常用方法类 
 *
 */
public class ImageFileUtils {  
	
	/**
	 * 保存Image的方法，有sd卡存储到sd卡，没有就存储到手机目录
	 * @param fileName 
	 * @param bitmap   
	 * @throws IOException
	 */
	public static void savaBitmap(String fileName, Bitmap bitmap) throws IOException {
		if(bitmap == null){
			return;
		}
		File file = new File(fileName);
		file.createNewFile();
		FileOutputStream fos = new FileOutputStream(file);
		bitmap.compress(CompressFormat.JPEG, 100, fos);
		fos.flush();
		fos.close();
	}
	
	/**
	 * 从手机或者sd卡获取Bitmap
	 * @param fileName
	 * @return
	 */
	public static Bitmap getBitmap(String fileName){
		return getBitmap(fileName,500);
	}
	public static Bitmap getBitmap(String fileName, int imgSize){
		Bitmap smallBitmap = getCompressBm(fileName, imgSize);
		return smallBitmap;//BitmapFactory.decodeFile(filename); //OutOfMemoryError
	}

	/**
	 * 根据路径，不失真压缩 - max:500*500
	 * 
	 * @param filePath
	 * @return
	 */
	public static Bitmap getCompressBm(String filePath, int imgSize) {
		Bitmap bm = null;
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(filePath, options);
		// Calculate inSampleSize
		options.inSampleSize = calculateInSampleSize(options, imgSize, imgSize);
		// Decode bitmap with inSampleSize set
		options.inJustDecodeBounds = false;
		bm = BitmapFactory.decodeFile(filePath, options);
		return bm;
	}

	/**
	 * 根据路径，不失真压缩 - max:500*500
	 * >>>>>>>>>>>con.getInputStream():此方法需要两次连接下载哦，流只可以使用一次
	 * http://wang-peng1.iteye.com/blog/1028320
	 * @param stream
	 * @return
	 */
	public static Bitmap getCompressStream(InputStream stream, int imgSize) {
		Bitmap bm = null;
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeStream(stream, null, options);
		// Calculate inSampleSize
		options.inSampleSize = ImageFileUtils.calculateInSampleSize(options, imgSize, imgSize);
		// Decode bitmap with inSampleSize set
		options.inJustDecodeBounds = false;
		bm = BitmapFactory.decodeStream(stream, null, options);
		return bm;
	}

	/* 
     * 得到图片字节流 数组大小 
     * */  
    public static byte[] readStream(InputStream inStream) throws Exception {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];        
        int len = 0;        
        while( (len=inStream.read(buffer)) != -1){        
            outStream.write(buffer, 0, len);        
        }        
        outStream.close();        
        inStream.close();        
        return outStream.toByteArray();        
    }   
    
    /**
     * bitmap = ImageFileUtils.getCompressByteArray(ImageFileUtils.readStream(stream), 100);
     * @param bytes
     * @param imgSize
     * @return
     */
	public static Bitmap getCompressByteArray(byte[] bytes, int imgSize) {
		Bitmap bm = null;
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeByteArray(bytes, 0, bytes.length, options);
		// Calculate inSampleSize
		options.inSampleSize = ImageFileUtils.calculateInSampleSize(options, imgSize, imgSize);
		// Decode bitmap with inSampleSize set
		options.inJustDecodeBounds = false;
		bm = BitmapFactory.decodeByteArray(bytes, 0, bytes.length, options);
		if (bm==null) Lg.error("ImageFileUtils.getCompressByteArray error!!!!!!!inSampleSize="+options.inSampleSize+",imgSize="+imgSize);
		return bm;
	}
	
	/**
	* 以最省内存的方式读取本地资源的图片
	* http://263229365.iteye.com/blog/1562924
	* @param context
	*@param resId
	* @return
	*/
	public static Bitmap readBitMap(Context context, int resId){
		BitmapFactory.Options opt = new BitmapFactory.Options();
		opt.inPreferredConfig = Bitmap.Config.RGB_565;
		opt.inPurgeable = true;
		opt.inInputShareable = true;
		//获取资源图片
		InputStream is = context.getResources().openRawResource(resId);
		return BitmapFactory.decodeStream(is,null,opt);
	}
	
	/**
	 * 计算压缩比例
	 * 
	 * @param options
	 * @param reqWidth
	 * @param reqHeight
	 * @return
	 */
	public static int calculateInSampleSize(BitmapFactory.Options options,
			int reqWidth, int reqHeight) {
		// Raw height and width of image
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;

		if (height > reqHeight || width > reqWidth) {
			final int heightRatio = Math.round((float) height
					/ (float) reqHeight);
			final int widthRatio = Math.round((float) width / (float) reqWidth);
			inSampleSize = heightRatio < widthRatio ? widthRatio : heightRatio;
		}

		return inSampleSize;
	}

	/**
	 * 旋转
	 * @param bmp
	 * @param rotateValue
	 * @return
	 * @throws Exception
	 */
	public static Bitmap rotateBitmap(Bitmap bmp, int rotateValue) throws Exception {
		if (bmp==null) return null;
		// 定义矩阵对象
		Matrix matrix = new Matrix();
		// 缩放原图
		matrix.postScale(1f, 1f);
		// 向左旋转45度，参数为正则向右旋转
		matrix.postRotate(90);
		//bmp.getWidth(), 500分别表示重绘后的位图宽高
		Bitmap dstbmp = Bitmap.createBitmap(bmp, 0, 0, bmp.getWidth(), bmp.getHeight(), matrix, true);
		return dstbmp;
	} 

	public static int readPictureDegree(String path) {
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
			Lg.error(e);
		}
		return degree;
	}   
	
	/**
	 * 压缩
	 * @param bitmap
	 * @param width
	 * @param height
	 * @return
	 */
	public static Bitmap zoomBitmap(Bitmap bitmap, int width, int height) {
		int w = bitmap.getWidth();
		int h = bitmap.getHeight();
		Matrix matrix = new Matrix();
		float scaleWidth = ((float) width / w);
		float scaleHeight = ((float) height / h);
		matrix.postScale(scaleWidth, scaleHeight);
		Bitmap newbmp = Bitmap.createBitmap(bitmap, 0, 0, w, h, matrix, true);
		return newbmp;
	}

	/**
	 * 裁剪
	 *  int x=0;
		int y=0;
		int w=bitmap.getWidth();
		int h=bitmap.getHeight();
		if (w>h){
			x=(w-h)/2;
			w=h;
		}
		else if (w<h){
			y=(h-w)/2;
			h=w;
		}
		Bitmap bmp=imageCut(bitmap,x,y,w,h);
	 * @param bmp
	 * @param left
	 * @param top
	 * @param resultWidth
	 * @param resultHeight
	 * @return
	 */
	public static Bitmap imageCut(Bitmap bmp, int left, int top, int resultWidth, int resultHeight)
	{
		Bitmap resultBmp = null;
		int colors[] = new int[resultWidth * resultHeight];
		bmp.getPixels(colors, 0, resultWidth, left, top, resultWidth, resultHeight);
		if(bmp.isRecycled())
			bmp.recycle();
		resultBmp = Bitmap.createBitmap(colors, resultWidth, resultHeight, Bitmap.Config.ARGB_8888);
		return resultBmp;
	}
	
	/**
	 * 判断文件是否存在
	 * @param fileName
	 * @return
	 */
	public static boolean isFileExists(String fileName){
		File file = new File(fileName);
		return file.exists() && file.isFile(); 
	}
	
	/**
	 * 获取文件的大小
	 * @param fileName
	 * @return
	 */
	public static long getFileSize(String fileName) {
		return new File(fileName).length();
	}
  
	/** 
	 * ImageFileUtils.getLocalImageFile(imgFile);
     * @return // /mnt/sdcard/files/a.jpg
     */
	public static String getLocalImageFile(Context context, String imgFile)
	{ 
		return FileUtils.getLocalFileName(context,imgFile);
	} 
	 
	private static void debug(String string)
	{
		Lg.debug(string);
	}

}
