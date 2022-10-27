package com.radya.sfa.helper.image;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

public class BmpUtil {
	public static String getPath(Context context, Uri uri) {

		if ("content".equalsIgnoreCase(uri.getScheme())) {
			String[] projection = { "_data" };
			Cursor cursor = null;

			try {
				cursor = context.getContentResolver().query(uri,
						projection,
						null, null, null);
				int column_index = cursor.getColumnIndexOrThrow("_data");
				if (cursor.moveToFirst()) {
					return cursor.getString(column_index);
				}
			} catch (Exception e) {
				// Eat it
			}
		}
		else if ("giro_photo".equalsIgnoreCase(uri.getScheme())) {
			return uri.getPath();
		}

		return null;
	}
	
	public static Bitmap decodeSampledBitmapFromUri(Context context, Uri uri,
                                                    int reqWidth, int reqHeight) {
		File file = new File(getPath(context, uri));
		// check dimension
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(file.getAbsolutePath(), options);

		// calculate
		options.inSampleSize = calculateInSampleSize(options, reqWidth,
				reqHeight);

		// return sampled
		options.inJustDecodeBounds = false;
		return BitmapFactory.decodeFile(file.getAbsolutePath(), options);
	}
	
	public static Bitmap decodeSampledBitmapFromFile(Context context, File file,
                                                     int reqWidth, int reqHeight) {
		// check dimension
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(file.getAbsolutePath(), options);

		// calculate
		options.inSampleSize = calculateInSampleSize(options, reqWidth,
				reqHeight);

		// return sampled
		options.inJustDecodeBounds = false;
		return BitmapFactory.decodeFile(file.getAbsolutePath(), options);
	}
	
	public static Bitmap getBitmap(Context context, String tag, Uri url,
                                   int reqWidth, int reqHeight)
	{
		File cacheDir;
		// if the device has an SD card
		if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)) {
			cacheDir=new File(android.os.Environment.getExternalStorageDirectory(),".OCFL311");
		} else {
			// it does not have an SD card
		   	cacheDir=context.getCacheDir();
		}
		if(!cacheDir.exists())
		    	cacheDir.mkdirs();

		File f=new File(cacheDir, tag);

		try {
			InputStream is = null;
			if (url.toString().startsWith("content://com.google.android.gallery3d")) {
				is=context.getContentResolver().openInputStream(url);
			} else {
				is=new URL(url.toString()).openStream();
			}
			OutputStream os = new FileOutputStream(f);
			byte[] buffer = new byte[1024];
			int len;
			while ((len = is.read(buffer)) != -1) {
				os.write(buffer, 0, len);
			}
			os.close();
			return decodeSampledBitmapFromFile(context, f, reqWidth, reqHeight);
		} catch (Exception ex) {
//			Log.d(Utils.DEBUG_TAG, "Exception: " + ex.getMessage());
			// something went wrong
			ex.printStackTrace();
			return null;
		}
	}

	public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
		// Raw height and width of image
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;

		if (height > reqHeight || width > reqWidth) {

			// Calculate ratios of height and width to requested height and
			// width
			final int heightRatio = Math.round((float) height
					/ (float) reqHeight);
			final int widthRatio = Math.round((float) width / (float) reqWidth);

			// Choose the smallest ratio as inSampleSize value, this will
			// guarantee
			// a final image with both dimensions larger than or equal to the
			// requested height and width.
			inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
		}

		return inSampleSize;
	}

	public static Bitmap createBitmapWithAlphaMask(Bitmap bmpSource,
                                                   Bitmap bmpMask) {
		int width = bmpSource.getWidth();
		int height = bmpSource.getHeight();
		int size = width * height;

		if (width != bmpMask.getWidth() || height != bmpMask.getHeight())
			bmpMask = resize(bmpMask, width, height);

		int[] result = new int[size];
		int[] mask = new int[size];
		bmpSource.getPixels(result, 0, width, 0, 0, width, height);
		bmpMask.getPixels(mask, 0, width, 0, 0, width, height);

		int alphamask = 0xff000000;
		int colormask = 0x00ffffff;
		for (int i = 0; i < size; i++) {
			result[i] = (mask[i] & alphamask) | (result[i] & colormask);
		}

		// ensuring the bitmap is mutable
		Bitmap bmpResult = Bitmap.createBitmap(width, height,
				Config.ARGB_8888);
		bmpResult.setPixels(result, 0, width, 0, 0, width, height);

		return bmpResult;
	}

	public static Bitmap resize(Bitmap src, int w2, int h2) {
		int w1 = src.getWidth();
		int h1 = src.getHeight();
		int[] pxSource = new int[w1 * h1];
		int[] pxResult = new int[w2 * h2];

		src.getPixels(pxSource, 0, w1, 0, 0, w1, h1);
		double x_ratio = w1 / (double) w2;
		double y_ratio = h1 / (double) h2;
		double px, py;
		for (int i = 0; i < h2; i++) {
			for (int j = 0; j < w2; j++) {
				px = Math.floor(j * x_ratio);
				py = Math.floor(i * y_ratio);
				pxResult[(i * w2) + j] = pxSource[(int) ((py * w1) + px)];
			}
		}

		return Bitmap.createBitmap(pxResult, w2, h2, Config.ARGB_8888);
	}

	public static Bitmap drawableToBitmap(Drawable drawable) {
		if (drawable instanceof BitmapDrawable) {
			return ((BitmapDrawable) drawable).getBitmap();
		}

		int width = drawable.getIntrinsicWidth();
		width = width > 0 ? width : 1;
		int height = drawable.getIntrinsicHeight();
		height = height > 0 ? height : 1;

		Bitmap bitmap = Bitmap.createBitmap(width, height, Config.ARGB_8888);
		Canvas canvas = new Canvas(bitmap);
		drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
		drawable.draw(canvas);

		return bitmap;
	}

	public static Bitmap circleCrop(Bitmap bitmap) {
		int size = Math.min(bitmap.getWidth(), bitmap.getHeight());

		Bitmap output = Bitmap.createBitmap(size, size, Config.ARGB_8888);
		Canvas canvas = new Canvas(output);

		final int color = 0xff424242;
		final Paint paint = new Paint();
		final Rect rect = new Rect(0, 0, size, size);

		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);
		// canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
		canvas.drawCircle(size / 2, size / 2, size / 2, paint);
		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(bitmap, rect, rect, paint);
		// Bitmap _bmp = Bitmap.createScaledBitmap(output, 60, 60, false);
		// return _bmp;
		return output;
	}
}
