//  cordova-plugin-media-controller
//  Copyright © 2015 filfat Studios AB
//  Repo: https://github.com/filfat-Studios-AB/cordova-plugin-music-controller
package com.filfatstudios.musiccontroller;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaWebView;
import org.apache.cordova.PluginResult;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;
import android.app.PendingIntent;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.ComponentName;
import android.content.IntentFilter;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.R;
import android.view.View;

public class MusicController extends CordovaPlugin {
	private MusicControllerBroadcastReceiver mMessageReceiver;
	private AudioManager mAudioManager;
	private MusicControllerNotification notification;
	private PendingIntent mediaButtonPendingIntent;

	private void registerBroadcaster(MusicControllerBroadcastReceiver mMessageReceiver){
		final Context context = this.cordova.getActivity().getApplicationContext();
		context.registerReceiver((BroadcastReceiver)mMessageReceiver, new IntentFilter("music-controller-previous"));
		context.registerReceiver((BroadcastReceiver)mMessageReceiver, new IntentFilter("music-controller-pause"));
		context.registerReceiver((BroadcastReceiver)mMessageReceiver, new IntentFilter("music-controller-play"));
		context.registerReceiver((BroadcastReceiver)mMessageReceiver, new IntentFilter("music-controller-next"));
		context.registerReceiver((BroadcastReceiver)mMessageReceiver, new IntentFilter("music-controller-headset-button"));

		//Listen for headset plugg events
		context.registerReceiver((BroadcastReceiver)mMessageReceiver, new IntentFilter(Intent.ACTION_HEADSET_PLUG));
	}
	
	@Override
	public void initialize(CordovaInterface cordova, CordovaWebView webView) {
		super.initialize(cordova, webView);
		final Activity activity = this.cordova.getActivity();
		this.notification = new MusicControllerNotification(activity);
		this.mMessageReceiver = new MusicControllerBroadcastReceiver(this);
		registerBroadcaster(mMessageReceiver);
		
		//Listen for headset events
		final Context context=activity.getApplicationContext();
		this.mAudioManager = (AudioManager)context.getSystemService(Context.AUDIO_SERVICE);
		Intent headsetIntent = new Intent("music-controller-headset-button");
		this.mediaButtonPendingIntent = PendingIntent.getBroadcast(context, 0, headsetIntent, PendingIntent.FLAG_UPDATE_CURRENT);
		this.registerMediaButtonEvent();
	}

	@Override
	public boolean execute(final String action, final JSONArray args, final CallbackContext callbackContext) throws JSONException {
		final Context context=this.cordova.getActivity().getApplicationContext();
		final Activity activity=this.cordova.getActivity();

		if (action.equals("create")) {
			final JSONObject params = args.getJSONObject(0);
			final String track = params.getString("track");
			final String artist = params.getString("artist");
			final String album = (params.has("album") && !params.getString("album").isEmpty()) ? params.getString("album") : null;
			final String cover = params.getString("cover");
			final boolean isPlaying= params.getBoolean("isPlaying");
			this.cordova.getThreadPool().execute(new Runnable() {
				public void run() {
					notification.createMuiscController(track, artist, album, cover, isPlaying);
					callbackContext.success("success");
				}
			});
		}
		else if (action.equals("destroy")){
			this.notification.destroy();
			this.mMessageReceiver.stopListening();
			callbackContext.success("success");
		}
		else if (action.equals("watch")) {
			this.cordova.getThreadPool().execute(new Runnable() {
				public void run() {
					mMessageReceiver.setCallback(callbackContext);
				}
			});
		}
		return true;
	}

	@Override
	public void onDestroy() {
		this.notification.destroy();
		this.mMessageReceiver.stopListening();
		super.onDestroy();
	}

	@Override
	public void onReset() {
		onDestroy();
		super.onReset();
	}
	
	/*
		void registerMediaButtonEvent()
		Register media button event for broadcast
	*/
	public void registerMediaButtonEvent(){
		this.mAudioManager.registerMediaButtonEventReceiver(this.mediaButtonPendingIntent);
	}
	
	/*
		void unregisterMediaButtonEvent()
		Unregister media button event for broadcast
	*/
	public void unregisterMediaButtonEvent(){
		this.mAudioManager.unregisterMediaButtonEventReceiver(this.mediaButtonPendingIntent);
	}
}
