package com.example.try_frogcross;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

public class BitmapUtil {
	static Context context;
	public static Bitmap frogUpBitmap;
	public static Bitmap frogDownBitmap;
	public static Bitmap frogLeftBitmap;
	public static Bitmap frogRightBitmap;
	public static Bitmap carBitmap;
	public static Bitmap woodBitmap;
	public static Bitmap boatBitmap;
	public static Bitmap goalBitmap;
	
	public static void initBitmap(Context context){
		BitmapUtil.context = context;
		initBitmap();
	}
	
	private static void initBitmap(){
		frogUpBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.frog_up, null);
		frogDownBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.frog_down, null);
		frogLeftBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.frog_left, null);
		frogRightBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.frog_right, null);
		carBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_launcher, null);
		woodBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_launcher, null);
		boatBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_launcher, null);
		goalBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.ball2, null);
	}
	
	public static void createSpeficalFrogBitmap(int width, int height){
		frogUpBitmap = getSpeficalBitmap(frogUpBitmap, width, height);
		frogDownBitmap = getSpeficalBitmap(frogDownBitmap, width, height);
		frogLeftBitmap = getSpeficalBitmap(frogLeftBitmap, width, height);
		frogRightBitmap = getSpeficalBitmap(frogRightBitmap, width, height);
	}
	
	public static void createSpeficalGoalBitmap(int width, int height){
		goalBitmap = getSpeficalBitmap(goalBitmap, width, height);
	}
	
	
	private static void getSpeficalBitmap(Drawable drawable, int width, int height){
		Bitmap bitmap = Bitmap.createBitmap(width, height,
				Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(bitmap); // 新建畫布，用空白bitmap當畫布
		drawable.setBounds(0, 0, width, height);// 設定drawable的邊界(原圖片有自己的長寬)
		drawable.draw(canvas); // 在畫布上畫上此drawable(此時bitmap已經被畫上東西，不是空白了)
//		frogUpBitmap.
	}
	
	private static Bitmap getSpeficalBitmap(Bitmap srcBitmap, int width, int height){
		Bitmap bitmap = Bitmap.createBitmap(width, height,
				Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(bitmap); // 新建畫布，用空白bitmap當畫布
//		drawable.setBounds(0, 0, width, height);// 設定drawable的邊界(原圖片有自己的長寬)
//		drawable.draw(canvas); // 在畫布上畫上此drawable(此時bitmap已經被畫上東西，不是空白了)
//		frogUpBitmap = Bitmap.createBitmap(frogUpBitmap, 0, 0, width, height);
		BitmapDrawable bitmapDrawable = new BitmapDrawable(srcBitmap);
		bitmapDrawable.setBounds(0, 0, width, height);
		bitmapDrawable.draw(canvas);
		return bitmap;
	}
	
	public static Bitmap createTensBitmap(int count){
		return getTensCountBmp(count);
	}
	
	public static Bitmap createSingleDigitsBitmap(int count){
		return getSingleDigitsBmp(count);
	}
	
	private static Bitmap getTensCountBmp(int count) {
		Bitmap gameTimeTensCountBmp = null;
		switch ((count / 10) % 10) {
		case 0:
			gameTimeTensCountBmp = BitmapFactory.decodeResource(context.getResources(),
					R.drawable.second_0);
			break;
		case 1:
			gameTimeTensCountBmp = BitmapFactory.decodeResource(context.getResources(),
					R.drawable.second_1);
			break;
		case 2:
			gameTimeTensCountBmp = BitmapFactory.decodeResource(context.getResources(),
					R.drawable.second_2);
			break;
		case 3:
			gameTimeTensCountBmp = BitmapFactory.decodeResource(context.getResources(),
					R.drawable.second_3);
			break;
		case 4:
			gameTimeTensCountBmp = BitmapFactory.decodeResource(context.getResources(),
					R.drawable.second_4);
			break;
		case 5:
			gameTimeTensCountBmp = BitmapFactory.decodeResource(context.getResources(),
					R.drawable.second_5);
			break;
		case 6:
			gameTimeTensCountBmp = BitmapFactory.decodeResource(context.getResources(),
					R.drawable.second_6);
			break;
		case 7:
			gameTimeTensCountBmp = BitmapFactory.decodeResource(context.getResources(),
					R.drawable.second_7);
			break;
		case 8:
			gameTimeTensCountBmp = BitmapFactory.decodeResource(context.getResources(),
					R.drawable.second_8);
			break;
		case 9:
			gameTimeTensCountBmp = BitmapFactory.decodeResource(context.getResources(),
					R.drawable.second_9);
			break;

		default:
			break;
		}
		return gameTimeTensCountBmp;
	}

	private static Bitmap getSingleDigitsBmp(int count) {
		Bitmap bitmap = null;
		switch (count % 10) {
		case 0:
			bitmap = BitmapFactory.decodeResource(context.getResources(),
					R.drawable.second_0);
			break;
		case 1:
			bitmap = BitmapFactory.decodeResource(context.getResources(),
					R.drawable.second_1);
			break;
		case 2:
			bitmap = BitmapFactory.decodeResource(context.getResources(),
					R.drawable.second_2);
			break;
		case 3:
			bitmap = BitmapFactory.decodeResource(context.getResources(),
					R.drawable.second_3);
			break;
		case 4:
			bitmap = BitmapFactory.decodeResource(context.getResources(),
					R.drawable.second_4);
			break;
		case 5:
			bitmap = BitmapFactory.decodeResource(context.getResources(),
					R.drawable.second_5);
			break;
		case 6:
			bitmap = BitmapFactory.decodeResource(context.getResources(),
					R.drawable.second_6);
			break;
		case 7:
			bitmap = BitmapFactory.decodeResource(context.getResources(),
					R.drawable.second_7);
			break;
		case 8:
			bitmap = BitmapFactory.decodeResource(context.getResources(),
					R.drawable.second_8);
			break;
		case 9:
			bitmap = BitmapFactory.decodeResource(context.getResources(),
					R.drawable.second_9);
			break;
		default:
			bitmap = BitmapFactory.decodeResource(context.getResources(),
					R.drawable.second_0);
			break;
		}
		return bitmap;
	}
	
}
