package com.example.adhit.bikubiku.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.adhit.bikubiku.data.local.SaveUserData;
import com.example.adhit.bikubiku.data.local.SessionChatPsychology;
import com.example.adhit.bikubiku.service.ChattingService;

public class BootingCompleteReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        if ("android.intent.action.BOOT_COMPLETED".equals(intent.getAction()) && SessionChatPsychology.getInstance().isRoomChatPsychologyConsultationBuild()) {
            Intent serviceLauncher = new Intent(context, ChattingService.class);
            context.startService(serviceLauncher);
            Log.v("TEST", "Service loaded at start");
        }
    }
}
