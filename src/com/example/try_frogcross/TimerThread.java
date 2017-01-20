package com.example.try_frogcross;

import java.util.ArrayList;

import android.os.Handler;
import android.os.Message;

public class TimerThread extends Thread {
	private int time;
	private boolean flag = true;
	private Object Lock = new Object();
	private Handler handler;
	
	public TimerThread(int time) {
		this.time = time;
	}

//	public TimerThread(int time, ToolUtil tool) {
//		this.time = time;
//		this.tool = tool;
//	}
//
//	public TimerThread(int time, EffectUtil effect) {
//		this.time = time;
//		this.effect = effect;
//	}

	@Override
	public void run() {
		
		while (flag && time != 0) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			time--;
			Message message = new Message();
			message.arg1 = time;
			handler.sendMessage(message);
		}
	}
	
	public void setHandler(Handler handler){
		this.handler = handler;
	}

	public void cancel() {
		flag = false;
	}

	public int getCurrentTime() {
		return time;
	}

	public void setCurrentTime(int time) {
		synchronized (GameActivity.LOCK) {
			this.time = time;
		}
	}
}
