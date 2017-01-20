package com.example.try_frogcross;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

public class CarUtil extends MovingObjectUtil{
	public static final int CAR_DISTANCE = 300;
	
	private boolean isCarStartFromLeft = true;
	private int carX;
	private int carY;
	private int speedX;
	private Rect rectCar;
	public CarUtil(int startX, int startY, int speedX){
		this.speedX = speedX;
		this.carX = speedX;
		rectCar = new Rect(startX, startY, startX+BitmapUtil.carBitmap.getWidth(), startY + BitmapUtil.carBitmap.getHeight());
	}
	
	@Override
	public void onMove() {
		// TODO Auto-generated method stub
		carX += speedX;
	}

	@Override
	public void onDrawSelf(Canvas canvas) {
		// TODO Auto-generated method stub
		rectCar.left = carX;
		rectCar.right = carX+BitmapUtil.carBitmap.getWidth();
		canvas.drawBitmap(BitmapUtil.carBitmap, null, rectCar, null);
	}
	
	@Override
	public boolean isNeedCreateNewInstance(){
		boolean isNeedCreateNewInstance = false;
		if(isCarStartFromLeft && carX >= CAR_DISTANCE){
			isNeedCreateNewInstance = true;
		}else if(!isCarStartFromLeft && carX <= CommonUtil.screenWidth - CAR_DISTANCE){
			isNeedCreateNewInstance = true;
		}
		return isNeedCreateNewInstance; 
	}

	@Override
	public boolean isNeedRemoveInstance(){
		boolean isNeedRemoveInstance = false;
		if(isCarStartFromLeft && carX >= CommonUtil.screenWidth){
			isNeedRemoveInstance = true;
		}else if(!isCarStartFromLeft && carX <= 0){
			isNeedRemoveInstance = true;
		}
		return isNeedRemoveInstance; 
	}

	@Override
	int getTop() {
		// TODO Auto-generated method stub
		return rectCar.top;
	}
	
	@Override
	Rect getRect(){
		return rectCar;
	}
}
