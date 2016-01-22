package com.example.tacademy.simpleapplicationcomponent1;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.widget.Toast;

public class MyService extends Service {


    private static final String TAG = "MyService";
    public MyService() {
    }

    class MyServiceBinder extends IMyService.Stub{

        @Override
        public int getCount() throws RemoteException {
            return mCount;
        }
    }

    MyServiceBinder mBinder;

    @Override
    public IBinder onBind(Intent intent) {
       if(mBinder ==null){
           mBinder = new MyServiceBinder();
       }
        return mBinder;


    }
    int mCount =0;
    boolean isRunning = false;

    @Override
    public void onCreate() {
        super.onCreate();
        Toast.makeText(this, "onCreate...", Toast.LENGTH_SHORT).show();

        isRunning = true;

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (isRunning){

                    Log.i(TAG, "count : " + mCount);
                    mCount++;
                    try{
                        Thread.sleep(1000);
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if(intent != null){
            Toast.makeText(this, "onStartCommand...", Toast.LENGTH_SHORT).show();
            int count = intent.getIntExtra("count", 0);
            mCount = count;
        } // start_sticky 인 경우 intent 가 null인지 아닌지를 구분해서 정해주어야 함
        else {//서비스가 자동으로 다시 실행된 경우 intent는 null
            Log.i(TAG, "restart service...");
        }
        return Service.START_STICKY; // 앱이 종료가 되어서 서비스가 되도록 하는 것이 start_sticky 앱 종료되면 꺼지는 것이 start_not_sticky
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "onDestroy..." , Toast.LENGTH_SHORT).show();
        isRunning = false;
    }
}
