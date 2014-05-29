package com.davidpablos.earthquakes;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class MyReceiver extends BroadcastReceiver {
      
    @Override
    public void onReceive(Context context, Intent intent) {
    	Log.d("TAG", "MyReceiver");
//       Intent service1 = new Intent(context, MyAlarmService.class);
//       context.startService(service1);
        
    }   
}