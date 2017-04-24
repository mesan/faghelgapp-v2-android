package no.mesan.faghelg.service.push;

import android.app.ActivityManager;
import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.squareup.picasso.Picasso;

import java.util.List;

import no.mesan.faghelg.view.MainActivity;
import no.mesan.faghelg.view.login.LoginActivity;
import no.mesan.faghelgapps.R;

public class GcmIntentService extends IntentService {
	public static final int NOTIFICATION_ID = 1;
	private NotificationManager mNotificationManager;
	protected NotificationCompat.Builder builder;

	public GcmIntentService() {
		super("GcmIntentService");
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		Bundle extras = intent.getExtras();
		GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);
		String messageType = gcm.getMessageType(intent);

		if (!extras.isEmpty()) {
			if (GoogleCloudMessaging.MESSAGE_TYPE_SEND_ERROR.equals(messageType)) {
				// sendNotification("Send error: " + extras.toString());
			} else if (GoogleCloudMessaging.MESSAGE_TYPE_DELETED.equals(messageType)) {
				// sendNotification("Deleted messages on server: " +
				// extras.toString());
			} else if (GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE.equals(messageType)) {
				String title = extras.getString("title");
				String content = extras.getString("content");
				String imageUrl = extras.getString("imageUrl");

				Log.d(this.getClass().getSimpleName(), "Title: " + title + " -- Content: " + content + " -- imageUrl: " + imageUrl);

				sendNotification(title, content, imageUrl);
			}
		}

		// Release the wake lock provided by the WakefulBroadcastReceiver.
		GcmBroadcastReceiver.completeWakefulIntent(intent);
	}

	private void sendNotification(String title, String content, String imageUrl) {
		mNotificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);

		Intent intent;

		ActivityManager manager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
		List<ActivityManager.RunningTaskInfo> runningTaskInfo = manager.getRunningTasks(1);

		if (runningTaskInfo.size() > 0) {
			ComponentName componentInfo = runningTaskInfo.get(0).topActivity;
			String activityName = componentInfo.getClassName();

			if (MainActivity.class.getName().equals(activityName)) {
				intent = new Intent(this, MainActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
			} else {
				intent = getLoginIntent();
			}
		} else {
			intent = getLoginIntent();
		}

		NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this)
				.setSmallIcon(R.mipmap.ic_stat_icon_notif)
				.setContentTitle(title)
				.setStyle(new NotificationCompat.BigTextStyle()
						.bigText(title)
						.setBigContentTitle("@" + content)
				)
				.setContentText("@" + content)
				.setTicker(title)
				.setGroup("faghelgapp-notification-group")
				.setGroupSummary(true)
				.setPriority(android.support.v7.app.NotificationCompat.PRIORITY_HIGH)
				.setAutoCancel(true);

		try {
			if (!TextUtils.isEmpty(imageUrl)) {
				Bitmap bmp = Picasso.with(getApplicationContext()).load(imageUrl).get();
				mBuilder.setLargeIcon(bmp);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		int notificationId = (int) (Math.random()*10000);

		PendingIntent contentIntent = PendingIntent.getActivity(this, notificationId, intent, PendingIntent.FLAG_UPDATE_CURRENT);
		mBuilder.setContentIntent(contentIntent);

		mNotificationManager.notify(notificationId, mBuilder.build());
	}

	private Intent getLoginIntent() {
		Intent intent = new Intent(this, LoginActivity.class);
		intent.setAction("android.intent.action.MAIN");
		intent.addCategory("android.intent.category.LAUNCHER");
		intent.putExtra("from.notification", 1);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
		return intent;
	}
}