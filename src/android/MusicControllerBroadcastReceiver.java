//  cordova-plugin-music-controller
//  Copyright © 2015 filfat Studios AB
//  Repo: https://github.com/filfat-Studios-AB/cordova-plugin-music-controller
package com.filfatstudios.musiccontroller;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.BroadcastReceiver;
import android.os.Bundle;
import android.view.KeyEvent;

public class MusicControllerBroadcastReceiver extends BroadcastReceiver {
	private CallbackContext callback;
	private MusicController musicController;

	public MusicControllerBroadcastReceiver(MusicController musicController){
		this.musicController = musicController;
	}
	public void setCallback(CallbackContext callback){
		this.callback = callback;
	}
	public void stopListening(){
		if (this.callback != null){
			this.callback.success("music-controller-stop-listening");
			this.callback = null;
		}
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		if (this.callback != null){
			String eventType = intent.getAction(); //Get event type
			
			//Headset plugg event
			if (eventType.equals(Intent.ACTION_HEADSET_PLUG)) {
				int state = intent.getIntExtra("state", -1);
				switch (state) {
					case 0:
						this.callback.success("music-controller-headset-unplugged");
						this.musicController.registerMediaButtonEvent();
						break;
					case 1:
						this.callback.success("music-controller-headset-plugged");
						this.musicController.registerMediaButtonEvent();
						break;
					default:
						break;
				}
			}
			
			//Headset button event
			else if (eventType.equals("music-controller-headset-button")) {
				KeyEvent key = (KeyEvent)intent.getParcelableExtra(Intent.EXTRA_KEY_EVENT);
				if(key.getAction() == KeyEvent.ACTION_DOWN)
					this.callback.success(eventType);
			}
			
			//Other event
			else {
				this.callback.success(eventType);
			}
			
			this.callback = null; //Destroy the callback after use
		}
	}
}
