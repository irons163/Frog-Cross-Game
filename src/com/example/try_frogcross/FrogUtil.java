package com.example.try_frogcross;

import java.util.ArrayList;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

public class FrogUtil implements IDrawSelf, IMovingObject {
	private int frogX, frogY;
	private Bitmap bitmap;
	private Rect rectPlayer;
	private int offsetX, offsetY;

	public FrogUtil(int startX, int startY) {
		this.frogX = startX;
		this.frogY = startY;
		bitmap = BitmapUtil.frogUpBitmap;
		rectPlayer = new Rect(frogX, frogY, frogX + bitmap.getWidth(), frogY
				+ bitmap.getHeight());
	}

	public void setOffsetXY(int offsetX, int offsetY) {
		this.offsetX = offsetX;
		this.offsetY = offsetY;
	}

	@Override
	public void onMove() {
		// TODO Auto-generated method stub
		frogX += offsetX;
		frogY += offsetY;
	}

	@Override
	public void onDrawSelf(Canvas canvas) {
		// TODO Auto-generated method stub
		rectPlayer.set(frogX, frogY, frogX + bitmap.getWidth(),
				frogY + bitmap.getHeight());
		canvas.drawBitmap(bitmap, null, rectPlayer, null);
	}

	public void setBitmap(Bitmap bitmap) {
		this.bitmap = bitmap;
	}

	public boolean isCollisionWith(MovingObjectUtil movingObjectUtil) {
		Rect rectMovingObjec = movingObjectUtil.getRect();
		if (Rect.intersects(rectPlayer, rectMovingObjec))
			return true;
		else
			return false;
	}

	public boolean isSuccessArrival(ArrayList<GoalUtil> goals) {
		boolean isSuccessArrival = false;
		for (GoalUtil goalUtil : goals) {
			if (Rect.intersects(rectPlayer, goalUtil.getRect()) && !goalUtil.isForgSuccessArrival()){
				isSuccessArrival = true;
				goalUtil.setForgSuccessArrival(isSuccessArrival);
				break;
			}
		}
		return isSuccessArrival;
	}
}
