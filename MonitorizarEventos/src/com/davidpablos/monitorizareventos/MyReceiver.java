package com.davidpablos.monitorizareventos;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

public class MyReceiver extends BroadcastReceiver {

	public static final String INCOMING_CALL = "android.intent.action.PHONE_STATE";
	public static final String RECEIVE_SMS = "android.provider.Telephony.SMS_RECEIVED";
	public static final String AIRPLANE_MODE = "android.intent.action.AIRPLANE_MODE";
	public static final String EXTRA_STATE = TelephonyManager.EXTRA_STATE;
	public static final String INCOMING_NUMBER = TelephonyManager.EXTRA_INCOMING_NUMBER;
	public static final String RINGING = "RINGING";

	@Override
	public void onReceive(Context context, Intent intent) {

		String action = intent.getAction();
		String type = intent.getStringExtra(EXTRA_STATE);

		if (action.equals(INCOMING_CALL)) {
			if (type.equals(RINGING)) {
				String number = intent.getStringExtra(INCOMING_NUMBER);
				Log.d("TAG", "Incoming call from number: " + number);
			}
		} else if (action.equals(RECEIVE_SMS)) {
			Log.d("TAG", "SMS RECEIVED");
			final Bundle bundle = intent.getExtras();
			if (bundle != null) {

				final Object[] pdusObj = (Object[]) bundle.get("pdus");

				for (int i = 0; i < pdusObj.length; i++) {

					SmsMessage currentMessage = SmsMessage
							.createFromPdu((byte[]) pdusObj[i]);
					String phoneNumber = currentMessage
							.getDisplayOriginatingAddress();

					String senderNum = phoneNumber;
					String message = currentMessage.getDisplayMessageBody();
					String notification = "SMS from number: " + senderNum
							+ "; Message text: " + message;

					Log.d("TAG", notification);

					// Show alert
					int duration = Toast.LENGTH_LONG;
					Toast toast = Toast.makeText(context, notification,
							duration);
					toast.show();

				} // end for loop
			} // bundle is null
		} else if (action.equals(AIRPLANE_MODE)) {
			Log.d("TAG", "AIRPLANE MODE");
		}
	}

}
