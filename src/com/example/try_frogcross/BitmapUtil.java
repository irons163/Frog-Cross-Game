package com.example.try_frogcross;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class BitmapUtil {
	static Context context;
	public static Bitmap frogUpBitmap;
	public static Bitmap frogDownBitmap;
	public static Bitmap frogLeftBitmap;
	public static Bitmap frogRightBitmap;
	public static Bitmap carBitmap;
	public static Bitmap woodBitmap;
	public static Bitmap boatBitmap;
	
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
	}
	
}
