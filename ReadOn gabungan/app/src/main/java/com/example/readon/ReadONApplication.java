package com.example.readon;

import android.app.Application;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.lifecycle.ProcessLifecycleOwner;

import com.example.readon.backgroundservice.StatusService;
import com.example.readon.datamodel.APIResponse;
import com.example.readon.model.Friendlist;
import com.example.readon.model.User;
import com.example.readon.service.APIClient;
import com.google.android.gms.dynamic.LifecycleDelegate;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReadONApplication extends Application implements LifecycleObserver {
    @Override
    public void onCreate() {
        super.onCreate();
        ProcessLifecycleOwner.get().getLifecycle().addObserver(this);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public void onAppBackgrounded() {
        Log.d("<DEBUG>", "App in background");
        Intent intent = new Intent(this, StatusService.class);
        intent.putExtra("status", "off");
        startService(intent);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void onAppForeground() {
        Log.d("<DEBUG>", "App in foreground");
        Intent intent = new Intent(this, StatusService.class);
        intent.putExtra("status", "on");
        startService(intent);
    }

}
