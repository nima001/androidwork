package com.example.newspapersubsystem;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;
import android.widget.Toast;

public class CodenumReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String s = intent.getStringExtra("phone");

        SmsManager sms = SmsManager.getDefault();

        //产生6位随机数
        int mcontent=(int)Math.ceil(((Math.random()*9+1)*100000));
        URL.code=mcontent;
        sms.sendTextMessage(s, null,"您的验证码是"+mcontent+"。如非本人操作，请忽略。", null, null);
        abortBroadcast();
    }
}
