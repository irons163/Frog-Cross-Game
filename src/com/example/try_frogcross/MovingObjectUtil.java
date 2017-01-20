package com.example.try_frogcross;

import android.graphics.Rect;

public abstract class MovingObjectUtil implements IMovingObject, IDrawSelf{
	
	int speedX;
	
	abstract boolean isNeedCreateNewInstance();
	abstract boolean isNeedRemoveInstance();
	abstract int getTop();
	abstract Rect getRect();
    
    void getInt(){
//        return 0;
    }
}
