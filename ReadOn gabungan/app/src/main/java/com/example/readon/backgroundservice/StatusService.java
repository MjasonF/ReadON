package com.example.readon.backgroundservice;

import android.app.IntentService;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.JobIntentService;

import com.example.readon.ReadONApplication;
import com.example.readon.SignUpActivity;
import com.example.readon.datamodel.APIResponse;
import com.example.readon.model.User;
import com.example.readon.pref.AppPreference;
import com.example.readon.service.APIClient;

import org.json.JSONObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StatusService extends Service {

    private Looper serviceLooper;
    private ServiceHandler serviceHandler;

    private final class ServiceHandler extends Handler {
        public ServiceHandler(Looper looper) {
            super(looper);
        }
        @Override
        public void handleMessage(Message msg) {
            Log.d("<DEBUG>", "handleMessage: before try");
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                // Restore interrupt status.
                Thread.currentThread().interrupt();
            }
            Log.d("<DEBUG>", "handleMessage: ");
            stopSelf(msg.arg1);
        }
    }


    @Override
    public void onCreate() {
        HandlerThread thread = new HandlerThread("ServiceStartArguments",
                Process.THREAD_PRIORITY_BACKGROUND);
        thread.start();
        serviceLooper = thread.getLooper();
        serviceHandler = new ServiceHandler(serviceLooper);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("<DEBUG>", "onStartCommand: ");
        User user = new User();
        AppPreference pref = new AppPreference(this);
        String userId = pref.getUserId();
        Boolean isOnline = user.getOnline();
        user.setUserId(userId);
        user.setOnline(isOnline);
        if(userId.equals("")) return START_STICKY;
        if (intent.getStringExtra("status").equals("on")) {
            APIClient.client().statusonline(user).enqueue(new Callback<APIResponse>() {
                @Override
                public void onResponse(Call<APIResponse> call, Response<APIResponse> response) {
                    stopSelf();
                }

                @Override
                public void onFailure(Call<APIResponse> call, Throwable t) {
                    stopSelf();
                }
            });
        }
        else {
            APIClient.client().statusoffline(user).enqueue(new Callback<APIResponse>() {
                @Override
                public void onResponse(Call<APIResponse> call, Response<APIResponse> response) {
                    stopSelf();
                }

                @Override
                public void onFailure(Call<APIResponse> call, Throwable t) {
                    stopSelf();
                }
            });
        }

        return START_NOT_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        Log.d("<DEBUG>", "onTaskRemoved: ");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("<DEBUG>", "onDestroy: ");
    }
}
