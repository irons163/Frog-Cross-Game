package com.example.try_frogcross;

public abstract class MovingObjectUtil implements IMovingObject, IDrawSelf{
	
	int speedX;
	
	abstract boolean isNeedCreateNewInstance();
	abstract boolean isNeedRemoveInstance();
	abstract int getTop();
}
