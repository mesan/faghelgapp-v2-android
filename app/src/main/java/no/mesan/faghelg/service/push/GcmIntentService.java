package no.mesan.faghelg.service.push;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;

import no.mesan.faghelg.view.MainActivity;
import no.mesan.faghelg.view.login.LoginActivity;
import no.mesan.faghelgapps.R;
import timber.log.Timber;

public class GcmIntentService extends IntentService {
	public static final int NOTIFICATION_ID = 1;
	private NotificationManager mNotificationManager;
	protected NotificationCompat.Builder builder;
	private final Handler mainThread = new Handler(Looper.getMainLooper());
	public static boolean hasReceived = false;

	public GcmIntentService() {
		super("GcmIntentService");
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		Bundle extras = intent.getExtras();
		GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);
		String messageType = gcm.getMessageType(intent);

		Timber.d(messageType);

		if (!extras.isEmpty()) {
			if (GoogleCloudMessaging.MESSAGE_TYPE_SEND_ERROR.equals(messageType)) {
				// sendNotification("Send error: " + extras.toString());
			} else if (GoogleCloudMessaging.MESSAGE_TYPE_DELETED.equals(messageType)) {
				// sendNotification("Deleted messages on server: " +
				// extras.toString());
			} else if (GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE.equals(messageType)) {

				String title = extras.getString("title");
				String content = extras.getString("content");

				Timber.d(title);
				Timber.d(content);

				// Post notification of received message.
				Log.d(this.getClass().getSimpleName(), "Title: " + title + " -- Content: " + content);
				if (!hasReceived) {

					hasReceived = true;
					SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
					//boolean shouldShowNotification = sharedPref.getBoolean(getApplicationContext().getString(R.string.settings_push_key), true);
					boolean shouldShowNotification = true;
					if (shouldShowNotification) {
						sendNotification(title, content);
					}
				}
			}
		}

		// Release the wake lock provided by the WakefulBroadcastReceiver.
		GcmBroadcastReceiver.completeWakefulIntent(intent);
	}

	// Put the message into a notification and post it.
	// This is just one simple example of what you might choose to do with
	// a GCM message.
	private void sendNotification(String title, String content) {
		mNotificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);

		Intent intent = new Intent(this, LoginActivity.class);
		intent.setAction("android.intent.action.MAIN");
		intent.addCategory("android.intent.category.LAUNCHER");
		intent.putExtra("from.notification", 1);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);

		PendingIntent contentIntent = PendingIntent.getActivity(this, NOTIFICATION_ID, intent, PendingIntent.FLAG_UPDATE_CURRENT);

		NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this).setSmallIcon(R.mipmap.ic_launcher)
				.setContentTitle(title).setStyle(new NotificationCompat.BigTextStyle().bigText(content)).setContentText(content).setTicker(title).setAutoCancel(true);

		mBuilder.setContentIntent(contentIntent);
		mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
	}
}