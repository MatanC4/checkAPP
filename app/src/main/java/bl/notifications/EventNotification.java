package bl.notifications;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import com.example.matka.check.Category.CategoryActivity;
import com.example.matka.check.R;

/**
 * Created by Daniel_m on 28/03/2017.
 */

public class EventNotification extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent newIntent = new Intent(context, CategoryActivity.class);
        newIntent.putExtra(BroadCastTags.EVENT_ID,(intent.getLongExtra(BroadCastTags.EVENT_ID, -1)));
        newIntent.putExtra(BroadCastTags.CATEGORY_NAME,(intent.getSerializableExtra(BroadCastTags.EVENT_TITLE)));
        Log.d("NOTIFICATION", "On");
        long[] pattern = {0, 300, 0};
        PendingIntent pi = PendingIntent.getActivity(context, 0, newIntent, 0);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.common_google_signin_btn_icon_dark_focused)
                .setContentTitle("Event due date")
                .setContentText(intent.getStringExtra(BroadCastTags.EVENT_TITLE))
                .setVibrate(pattern)
                .setAutoCancel(true);
        mBuilder.setContentIntent(pi);
        mBuilder.setDefaults(Notification.DEFAULT_SOUND);
        NotificationManagerCompat mNotificationManager = NotificationManagerCompat.from(context);
        mNotificationManager.notify(0, mBuilder.build());
    }
}
