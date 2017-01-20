package com.example.try_frogcross;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameView extends SurfaceView implements SurfaceHolder.Callback{
	
	private int currentGameLevel = 1;
	private Handler handler = new Handler();
	private boolean isGameRun = true;
	private ArrayList<MovingObjectUtil> carUtils; 
	private ArrayList<MovingObjectUtil> woodUtils;
	private ArrayList<MovingObjectUtil> boatUtils;
	private static final int CAR_LINE_NUM = 3;
	private static final int WOOD_LINE_NUM = 1;
	private static final int BOAT_LINE_NUM = 1;
	private static final int TOTAL_MOVING_OBJECT_LINE_NUM = CAR_LINE_NUM+WOOD_LINE_NUM+BOAT_LINE_NUM;
	private static final int RELEX_LINE_NUM = 2;
	private static final int GOAL_LINE_NUM = 1;
	private static final int TOTAL_LINE_NUM = TOTAL_MOVING_OBJECT_LINE_NUM + RELEX_LINE_NUM + GOAL_LINE_NUM;
	private static final int START_LINE_POSITION = TOTAL_LINE_NUM - 1;
	private static final int MID_RELEX_LINE_POSITION = WOOD_LINE_NUM + BOAT_LINE_NUM + GOAL_LINE_NUM;
	private static final int[] RELEX_LINE_POSITIONS = new int[]{START_LINE_POSITION, MID_RELEX_LINE_POSITION};
	
	private ArrayList<ArrayList<MovingObjectUtil>> carLines = new ArrayList<ArrayList<MovingObjectUtil>>(CAR_LINE_NUM);
	private ArrayList<ArrayList<MovingObjectUtil>> woodLines = new ArrayList<ArrayList<MovingObjectUtil>>(WOOD_LINE_NUM);
	private ArrayList<ArrayList<MovingObjectUtil>> boatLines = new ArrayList<ArrayList<MovingObjectUtil>>(BOAT_LINE_NUM);
	private static final int CAR_MOVE_SPEEDX = 3;
	private static final int WOOD_MOVE_SPEEDX = 2;
	private static final int BOAT_MOVE_SPEEDX = 1;
	private SurfaceHolder surfaceHolder; 
	
	public static int[] MovingObjectLinesTop;
//	public static final int CAR_LINE1_TOP = (int) (CommonUtil.screenHeight/4.0*3/8*6);
//	public static final int CAR_LINE2_TOP = (int) (CommonUtil.screenHeight/4.0*3/8*5);
//	public static final int CAR_LINE3_TOP = (int) (CommonUtil.screenHeight/4.0*3/8*4);
//	public static final int WOOD_LINE_TOP = (int) (CommonUtil.screenHeight/4.0*3/8*2);
//	public static final int BOAT_LINE_TOP = (int) (CommonUtil.screenHeight/4.0*3/8*1);
	
	public static final int MOVEX_DISTANCE = (int) (CommonUtil.screenWidth/9.0);
	public static final int LINE_DISTANCE = (int) (CommonUtil.screenHeight/4.0*3/8*1);
	public static final int FrogMoveX = MOVEX_DISTANCE;
	public static final int FrogMoveY =  LINE_DISTANCE;
	private int frogStartX ;
	private int frogStartY = (int) (CommonUtil.screenHeight/4.0*3/8*START_LINE_POSITION);
	
	public FrogUtil frogUtil;
	
	private static final int HOLE_NUM = 5; 
	private ArrayList<GoalUtil> goals = new ArrayList<GoalUtil>();
	private static final int HOLE_WIDTH = MOVEX_DISTANCE;
	
	
	
	public GameView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		MovingObjectLinesTop = new int[TOTAL_MOVING_OBJECT_LINE_NUM];
		exit: for(int i=GOAL_LINE_NUM, movingObjectLinesPosition=0; i< TOTAL_LINE_NUM; i++, movingObjectLinesPosition++){
			Log.e("a", CommonUtil.screenHeight/4.0*3/(TOTAL_LINE_NUM)*i+"");		
			for(int relexLinePosition : RELEX_LINE_POSITIONS){
				if(i == relexLinePosition){
					movingObjectLinesPosition--;
					continue exit;
				}
			}
			MovingObjectLinesTop[movingObjectLinesPosition] = (int) (CommonUtil.screenHeight/4.0*3/((TOTAL_LINE_NUM))*i);
		}
		
		for(int i=0; i<CAR_LINE_NUM; i++){
			carUtils = new ArrayList<MovingObjectUtil>();
			carUtils.add(new CarUtil(-BitmapUtil.carBitmap.getWidth(), MovingObjectLinesTop[MovingObjectLinesTop.length-1-i], CAR_MOVE_SPEEDX));
			carLines.add(carUtils);
		}
		for(int i=0; i<WOOD_LINE_NUM; i++){
			woodUtils = new ArrayList<MovingObjectUtil>();
			woodUtils.add(new WoodUtil(-BitmapUtil.carBitmap.getWidth(), MovingObjectLinesTop[MovingObjectLinesTop.length-1-CAR_LINE_NUM-i], WOOD_MOVE_SPEEDX));
			woodLines.add(woodUtils);
		}	
		for(int i=0; i<BOAT_LINE_NUM; i++){
			boatUtils = new ArrayList<MovingObjectUtil>();
			boatUtils.add(new BoatUtil(-BitmapUtil.carBitmap.getWidth(), MovingObjectLinesTop[i] , BOAT_MOVE_SPEEDX));
			boatLines.add(boatUtils);
		}	
		
		initFrog();
		initGoals();
		
		
		surfaceHolder = getHolder();
		surfaceHolder.addCallback(this);
	}
	
	private void initFrog(){
		BitmapUtil.createSpeficalFrogBitmap(LINE_DISTANCE/2, LINE_DISTANCE/2);
		frogStartX = (CommonUtil.screenWidth - BitmapUtil.frogUpBitmap.getWidth()) / 2;
		frogUtil = new FrogUtil(frogStartX, frogStartY);
	}
	
	private void initGoals(){
		BitmapUtil.createSpeficalGoalBitmap(HOLE_WIDTH, HOLE_WIDTH);
		for(int i = 0; i < HOLE_NUM; i++){
			goals.add(new GoalUtil(MOVEX_DISTANCE * i*2, 0));
		}
	}
	
	private void process(){
		doUtilMoveAndCheckCreateAndRemoveUtilOrNot(carLines);
		doUtilMoveAndCheckCreateAndRemoveUtilOrNot(woodLines);
		doUtilMoveAndCheckCreateAndRemoveUtilOrNot(boatLines);
		checkFrogSuccessArrival();
		checkGameWinOrLose();
//		doCarMoveAndCheckCreateAndRemoveCarOrNot();
//		doWoodMove();
//		doBoatMove();
	}
	
	private void draw(){
		Canvas canvas = surfaceHolder.lockCanvas();
		canvas.drawColor(Color.WHITE);
		
		for(GoalUtil goalUtil : goals){
			goalUtil.onDrawSelf(canvas);
		}
		
		frogUtil.onDrawSelf(canvas);
		
		for(int i=0; i<CAR_LINE_NUM; i++){
			ArrayList<MovingObjectUtil> carUtils = carLines.get(i);
			for(MovingObjectUtil carUtil : carUtils){
				carUtil.onDrawSelf(canvas);
			}		
		}
		for(int i=0; i<WOOD_LINE_NUM; i++){
			ArrayList<MovingObjectUtil> woodUtils = woodLines.get(i);
			for(MovingObjectUtil woodUtil : woodUtils){
				woodUtil.onDrawSelf(canvas);
			}
		}	
		for(int i=0; i<BOAT_LINE_NUM; i++){
			ArrayList<MovingObjectUtil> boatUtils = boatLines.get(i);
			for(MovingObjectUtil biatUtil : boatUtils){
				biatUtil.onDrawSelf(canvas);
			}
		}
		
		
		surfaceHolder.unlockCanvasAndPost(canvas);
	}
	
	Thread gameThread = new Thread(new Runnable() {
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			while(isGameRun){
				if(!isCollision)
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
	
	boolean isCollision = false;
	
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
			if(!isCollision)
				isCollision = frogUtil.isCollisionWith(movingObjectUtil);
//			movingObjectUtil.onMove(CAR_MOVE_SPEEDX, 0);
			movingObjectUtil.onMove();
			if(carPosition==LastCatPosition)
				isNeedCreateNewInstance = movingObjectUtil.isNeedCreateNewInstance();
			if(carPosition==firstCarPosition){
				isNeedRemoveInstance = movingObjectUtil.isNeedRemoveInstance();
			}
		}
		if(isNeedCreateNewInstance){
//			CarUtil carUtil = new CarUtil(-BitmapUtil.carBitmap.getWidth(), movingObjectUtilarrayList.get(LastCatPosition).getTop(), WOOD_MOVE_SPEEDX);
			movingObjectUtilarrayList.add(createNewMoveObject((MovingObjectUtil)movingObjectUtilarrayList.get(LastCatPosition)));
		}
		if(isNeedRemoveInstance){
			movingObjectUtilarrayList.remove(movingObjectUtilarrayList.get(firstCarPosition));
		}
	}
	
//	enum MoveObjectType{
//		CAR, WOOD, BOAT
//	}
	
	private MovingObjectUtil createNewMoveObject(MovingObjectUtil lastMovingObjectUtil){
		MovingObjectUtil newMovingObjectUtil = null;
		if (lastMovingObjectUtil instanceof CarUtil) {
			newMovingObjectUtil = new CarUtil(-BitmapUtil.carBitmap.getWidth(), lastMovingObjectUtil.getTop(), CAR_MOVE_SPEEDX);
			
		} else if (lastMovingObjectUtil instanceof WoodUtil) {
			newMovingObjectUtil = new WoodUtil(-BitmapUtil.woodBitmap.getWidth(), lastMovingObjectUtil.getTop(), WOOD_MOVE_SPEEDX);
			
		} else if (lastMovingObjectUtil instanceof BoatUtil) {
			newMovingObjectUtil = new BoatUtil(-BitmapUtil.boatBitmap.getWidth(), lastMovingObjectUtil.getTop(), BOAT_MOVE_SPEEDX);
			
		}
		return newMovingObjectUtil;
	}
	
	private void checkFrogSuccessArrival(){
		isCollision = frogUtil.isSuccessArrival(goals);
	}
	
	private void checkGameWinOrLose(){
		boolean isFrogSuccessArrivalAllGoal = true;
		for(GoalUtil goalUtil : goals){
			if(!goalUtil.isForgSuccessArrival()){
				isFrogSuccessArrivalAllGoal = false;
				break;
			}
				
		}
		
		if(isFrogSuccessArrivalAllGoal){
			if(currentGameLevel == GameActivity.GAME_MAX_LEVEL){
				isGameRun = false;
//				showAllClearAnim
			}
			return;
		}else{
//			time = 0;
		}
	}
	
	public void setCurrentGameLevel(int currentGameLevel){
		this.currentGameLevel = currentGameLevel;
	}
	
	public int getCurrentGameLevel(){
		return currentGameLevel;
	}
	
	public void setHander(Handler handler){
		this.handler = handler;
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
