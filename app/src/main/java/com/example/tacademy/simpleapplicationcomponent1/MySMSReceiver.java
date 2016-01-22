package com.example.tacademy.simpleapplicationcomponent1;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by Tacademy on 2016-01-22.
 */
public class MySMSReceiver extends BroadcastReceiver {

    public MySMSReceiver(){

    }
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent i = new Intent(context,MyService.class);
        i.putExtra("count", 1000);
        context.startService(i);
    }
}
