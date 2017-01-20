package com.example.try_frogcross;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.CountDownTimer;

public class GoalUtil implements IDrawSelf{
	private int x, y;
	private Rect rectGoal;
	private boolean isForgSuccessArrival = false;


	public GoalUtil(int x, int y){
		this.x = x;
		this.y = y;
		rectGoal = new Rect(x, y, x + BitmapUtil.goalBitmap.getWidth(), y + BitmapUtil.goalBitmap.getHeight());
	}

	@Override
	public void onDrawSelf(Canvas canvas) {
		// TODO Auto-generated method stub
		canvas.drawBitmap(BitmapUtil.goalBitmap, null, rectGoal, null);
	}
	
	public Rect getRect(){
		return rectGoal;
	}
	
	public boolean isForgSuccessArrival() {
		return isForgSuccessArrival;
	}

	public void setForgSuccessArrival(boolean isForgSuccessArrival) {
		this.isForgSuccessArrival = isForgSuccessArrival;
	}

	
}
