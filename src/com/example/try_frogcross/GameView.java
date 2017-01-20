package com.example.try_frogcross;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameView extends SurfaceView implements SurfaceHolder.Callback{
	
	private boolean isGameRun = true;
	private ArrayList<MovingObjectUtil> carUtils; 
	private ArrayList<MovingObjectUtil> woodUtils;
	private ArrayList<MovingObjectUtil> boatUtils;
	private static final int CAR_LINE_NUM = 2;
	private static final int WOOD_LINE_NUM = 2;
	private static final int BOAT_LINE_NUM = 2;
	private static final int TOTAL_LINE_NUM = CAR_LINE_NUM+WOOD_LINE_NUM+BOAT_LINE_NUM;
	private ArrayList<ArrayList<MovingObjectUtil>> carLines = new ArrayList<ArrayList<MovingObjectUtil>>(CAR_LINE_NUM);
	private ArrayList<ArrayList<MovingObjectUtil>> woodLines = new ArrayList<ArrayList<MovingObjectUtil>>(WOOD_LINE_NUM);
	private ArrayList<ArrayList<MovingObjectUtil>> boatLines = new ArrayList<ArrayList<MovingObjectUtil>>(BOAT_LINE_NUM);
	private static final int CAR_MOVE_SPEEDX = 1;
	private static final int WOOD_MOVE_SPEEDX = 1;
	private static final int BOAT_MOVE_SPEEDX = 1;
	private SurfaceHolder surfaceHolder; 
	
	public static int[] MovingObjectLinesTop;
	public static final int CAR_LINE1_TOP = CommonUtil.screenHeight/12*10;
	public static final int CAR_LINE2_TOP = CommonUtil.screenHeight/12*8;
	public static final int CAR_LINE3_TOP = CommonUtil.screenHeight/12*6;
	public static final int WOOD_LINE_TOP = CommonUtil.screenHeight/12*4;
	public static final int BOAT_LINE_TOP = CommonUtil.screenHeight/12*2;
	
	public GameView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		MovingObjectLinesTop = new int[TOTAL_LINE_NUM];
		for(int i=0; i<MovingObjectLinesTop.length; i++){
			Log.e("a", CommonUtil.screenHeight/(TOTAL_LINE_NUM+1*2)*(i+1)*2+"");
			MovingObjectLinesTop[i] = CommonUtil.screenHeight/((TOTAL_LINE_NUM+1)*2)*(i+1)*2;
		}
		
		for(int i=0; i<CAR_LINE_NUM; i++){
			carUtils = new ArrayList<MovingObjectUtil>();
			carUtils.add(new CarUtil(-BitmapUtil.carBitmap.getWidth(), MovingObjectLinesTop[MovingObjectLinesTop.length-1-i], CAR_MOVE_SPEEDX));
			carLines.add(carUtils);
		}
		for(int i=0; i<WOOD_LINE_NUM; i++){
			woodUtils = new ArrayList<MovingObjectUtil>();
			woodUtils.add(new CarUtil(-BitmapUtil.carBitmap.getWidth(), MovingObjectLinesTop[MovingObjectLinesTop.length-1-CAR_LINE_NUM-i], WOOD_MOVE_SPEEDX));
			woodLines.add(woodUtils);
		}	
		for(int i=0; i<BOAT_LINE_NUM; i++){
			boatUtils = new ArrayList<MovingObjectUtil>();
			boatUtils.add(new CarUtil(-BitmapUtil.carBitmap.getWidth(), i , BOAT_MOVE_SPEEDX));
			boatLines.add(boatUtils);
		}	
		
		surfaceHolder = getHolder();
		surfaceHolder.addCallback(this);
	}
	
	private void process(){
		doUtilMoveAndCheckCreateAndRemoveUtilOrNot(carLines);
//		doUtilMoveAndCheckCreateAndRemoveUtilOrNot(woodLines);
//		doUtilMoveAndCheckCreateAndRemoveUtilOrNot(boatLines);
//		doCarMoveAndCheckCreateAndRemoveCarOrNot();
//		doWoodMove();
//		doBoatMove();
	}
	
	private void draw(){
		Canvas canvas = surfaceHolder.lockCanvas();
		canvas.drawColor(Color.WHITE);
		for(int i=0; i<CAR_LINE_NUM; i++){
			ArrayList<MovingObjectUtil> carUtils = carLines.get(i);
			for(MovingObjectUtil carUtil : carUtils){
				carUtil.onDrawSelf(canvas);
			}		
		}
//		for(int i=0; i<WOOD_LINE_NUM; i++){
//			ArrayList<MovingObjectUtil> carUtils = woodLines.get(i);
//			for(MovingObjectUtil carUtil : carUtils){
//				carUtil.onDrawSelf(canvas);
//			}
//		}	
//		for(int i=0; i<BOAT_LINE_NUM; i++){
//			ArrayList<MovingObjectUtil> carUtils = boatLines.get(i);
//			for(MovingObjectUtil carUtil : carUtils){
//				carUtil.onDrawSelf(canvas);
//			}
//		}
		surfaceHolder.unlockCanvasAndPost(canvas);
	}
	
	Thread gameThread = new Thread(new Runnable() {
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			while(isGameRun){
				process();
				draw();
			}
		}
	});
	
	private void doUtilMoveAndCheckCreateAndRemoveUtilOrNot(ArrayList<ArrayList<MovingObjectUtil>> arrayList){
		for(int i=0; i<arrayList.size(); i++){
			doMovingObjectLineMove(i, arrayList.get(i));
		}
	}
	
	private void doMovingObjectLineMove(int carLinePosition, ArrayList<MovingObjectUtil> movingObjectUtilarrayList){
		boolean isNeedCreateNewInstance = false;
		boolean isNeedRemoveInstance = false;
//		Object theInstanceForCheckType = arrayList.get(0);
//		if (theInstanceForCheckType instanceof CarUtil) {
//			ArrayList<CarUtil> carUtils = arrayList;		
//		}else if(theInstanceForCheckType instanceof WoodUtil){
//			
//		}else{
//			
//		}
//		ArrayList<CarUtil> carUtils = carLines.get(carLinePosition);
		int firstCarPosition = 0;
		int LastCatPosition = movingObjectUtilarrayList.size()-1;
		for(int carPosition=0; carPosition<movingObjectUtilarrayList.size(); carPosition++){
			MovingObjectUtil movingObjectUtil = movingObjectUtilarrayList.get(carPosition);
			movingObjectUtil.onMove();
			if(carPosition==LastCatPosition)
				isNeedCreateNewInstance = movingObjectUtil.isNeedCreateNewInstance();
			if(carPosition==firstCarPosition){
				isNeedRemoveInstance = movingObjectUtil.isNeedRemoveInstance();
			}
		}
		if(isNeedCreateNewInstance){
			CarUtil carUtil = new CarUtil(-BitmapUtil.carBitmap.getWidth(), movingObjectUtilarrayList.get(LastCatPosition).getTop(), WOOD_MOVE_SPEEDX);
			movingObjectUtilarrayList.add(carUtil);
		}
		if(isNeedRemoveInstance){
			movingObjectUtilarrayList.remove(movingObjectUtilarrayList.get(firstCarPosition));
		}
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		gameThread.start();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		
	}

	
}
