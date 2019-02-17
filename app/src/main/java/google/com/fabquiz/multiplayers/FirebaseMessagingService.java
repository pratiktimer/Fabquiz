package google.com.fabquiz.multiplayers;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.google.firebase.messaging.RemoteMessage;

import google.com.fabquiz.R;

/**
 * Created by Pratik on 3/12/2018.
 */

public class FirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService {
    private Intent resultintent;
    String goto2;
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        String messagetitle=remoteMessage.getNotification().getTitle();
        String messagebody=remoteMessage.getNotification().getBody();

        String click_action=remoteMessage.getNotification().getClickAction();
        goto2 =remoteMessage.getData().get("message");
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this,getString(R.string.default_notification_channel_id))
                .setSmallIcon (R.mipmap.notification)
                .setContentTitle(messagetitle)
                .setContentText(messagebody)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        resultintent=new Intent(click_action);
        resultintent.putExtra("message", goto2);
// Build a PendingIntent for the reply action to trigger.
        PendingIntent replyPendingIntent =
                PendingIntent.getBroadcast(getApplicationContext(),
                        0,
                        resultintent,
                        PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(replyPendingIntent);
        int mnotificationid=(int) System.currentTimeMillis();
        NotificationManager mManager=(NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        mManager.notify(mnotificationid,mBuilder.build());
    }

}
