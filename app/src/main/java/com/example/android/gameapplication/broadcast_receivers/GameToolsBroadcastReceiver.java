package com.example.android.gameapplication.broadcast_receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.android.gameapplication.R;

/**
 * @Author: Rizky Paskalis Totong
 * @Date: 27/10/22
 * Broadcast Receiver to reset game tools
 */
public class GameToolsBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i("AlarmGameTools","BroadcastReceiver::OnReceive()");
        SharedPreferences sharedPref = context.getSharedPreferences("gameToolsQty", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(String.valueOf(R.string.copter), context.getResources().getInteger(R.integer.tool_default_quantity));
        editor.putInt(String.valueOf(R.string.rocket), context.getResources().getInteger(R.integer.tool_default_quantity));
        editor.putInt(String.valueOf(R.string.clear_monsters), context.getResources().getInteger(R.integer.tool_default_quantity));
        editor.apply();
    }
}
