package com.example.try_frogcross;

import android.app.Activity;
import android.graphics.Matrix;
import android.hardware.display.DisplayManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;

public class GameActivity extends Activity implements OnClickListener{
	GameView gameView;
	ImageButton upBtn, downBtn, leftBtn, rightBtn;
	ProgressBar timeProgressBar;
	ImageView thousand_digit, hundred_digit, ten_digit, single_digit;
	
	public static final Object LOCK = new Object();
	
	public static final int GAME_MAX_LEVEL = 2;
	public int currentGameLevel;
	public static final int GAME_MAX_TIME = 30;
	
	private TimerThread timerThread;
	
	private Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			timeProgressBar.setProgress(msg.arg1);
			timeProgressBar.invalidate();
		};
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game);
		
		gameView = (GameView)findViewById(R.id.gameView);
		
		upBtn = (ImageButton)findViewById(R.id.imageButton1);		
		leftBtn = (ImageButton)findViewById(R.id.imageButton2);
		rightBtn = (ImageButton)findViewById(R.id.imageButton3);
		downBtn = (ImageButton)findViewById(R.id.imageButton4);
		
		timeProgressBar = (ProgressBar)findViewById(R.id.timeProgressBar);
		
		thousand_digit = (ImageView)findViewById(R.id.thousand_digit);
		hundred_digit = (ImageView)findViewById(R.id.hundred_digit);
		ten_digit = (ImageView)findViewById(R.id.ten_digit);
		single_digit = (ImageView)findViewById(R.id.single_digit);
		
		upBtn.setOnClickListener(this);
		downBtn.setOnClickListener(this);
		leftBtn.setOnClickListener(this);
		rightBtn.setOnClickListener(this);
		
		timeProgressBar.setMax(GAME_MAX_TIME);
		timeProgressBar.setProgress(GAME_MAX_TIME);
		
		gameView.setCurrentGameLevel(currentGameLevel);
		gameView.setHander(handler);
		
		initTimer();
	}
	
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.imageButton1:
			
			gameView.frogUtil.setOffsetXY(0, -GameView.FrogMoveY);
			
			break;
		case R.id.imageButton2:
			gameView.frogUtil.setOffsetXY(-GameView.FrogMoveX, 0);
			break;
		case R.id.imageButton3:
			gameView.frogUtil.setOffsetXY(GameView.FrogMoveX, 0);
			break;
		case R.id.imageButton4:
			gameView.frogUtil.setOffsetXY(0, GameView.FrogMoveY);
			
			break;
		}
		gameView.frogUtil.onMove();
	}
	
	private void initTimer(){
		timerThread = new TimerThread(30);
		timerThread.setHandler(handler);
		timerThread.start();
	}
	
}
