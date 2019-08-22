package com.mbia.cyrille.localnotif;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private int currentNotifId = 0;
    private EditText NotifText, NotifTitle;
    private Button sendSimpleNotif, sendExpandLayoutNotif, sendNotifAction, sendMaxPriorityNotif, sendMinPriorityNotif, sendCombinedNotif, clearAllNotif;
    private NotificationManager notifManager;
    private NotificationCompat.Builder notifBuilder;  //Afin de gérer plus facilement tous les drapeaux de notifications
    private String notifTitle;
    private String notifText;
    private Bitmap icon;
    private int combinedNotifCounter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getAllWidgetReference();
        bindWidgetWithAnEvent();
    }

    private void getAllWidgetReference() {
        NotifText = (EditText) findViewById(R.id.NotifText);
        NotifTitle = (EditText) findViewById(R.id.NotifTitle);

        sendSimpleNotif = (Button) findViewById(R.id.SendSimpleNotif);
        sendExpandLayoutNotif = (Button) findViewById(R.id.SendExpandLayoutNotif);
        sendNotifAction = (Button) findViewById(R.id.SendNotidWithActionBtn);
        sendMaxPriorityNotif =  (Button) findViewById(R.id.SendMaxPriorityNotif);
        sendMinPriorityNotif =  (Button) findViewById(R.id.SendMinPriorityNotif);
        sendCombinedNotif =  (Button) findViewById(R.id.SendCombinedNotif);
        clearAllNotif =  (Button) findViewById(R.id.ClearAllNotif);

         notifManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);

         icon  = BitmapFactory.decodeResource(this.getResources(), R.mipmap.ic_launcher);
    }

    private void bindWidgetWithAnEvent() {
        sendSimpleNotif.setOnClickListener(this);
        sendExpandLayoutNotif.setOnClickListener(this);
        sendNotifAction.setOnClickListener(this);
        sendMaxPriorityNotif.setOnClickListener(this);
        sendMinPriorityNotif.setOnClickListener(this);
        sendCombinedNotif.setOnClickListener(this);
        clearAllNotif.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        setNotificationData();
        switch (view.getId()){
            case R.id.SendSimpleNotif:
                setDataForSimpleNotification();
                break;
            case R.id.SendExpandLayoutNotif:
                setDataForEpandLayoutNotification();
                break;
            case R.id.SendNotidWithActionBtn:
                setDataForNotificationWithActionBtn();
                break;

            case R.id.SendMaxPriorityNotif:
                setDataForMaxPriorityNotif();
                break;
            case R.id.SendMinPriorityNotif:
                setDataForMinPriorityNotif();
                break;
            case R.id.SendCombinedNotif:
                setDataForCombinedNotif();
                break;
            case R.id.ClearAllNotif:
                setDataForClearAllNotif();
                break;
        }

    }

    private void setDataForClearAllNotif() {
        if (notifManager != null) {
            currentNotifId = 0;
            notifManager.cancelAll();
        }
    }

    private void setDataForCombinedNotif() {
        combinedNotifCounter++;
        notifBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(icon)
                .setContentTitle(notifTitle)
                .setGroup("group_emails")
                .setGroupSummary(true)
                .setContentText(combinedNotifCounter + " Nouveau message");

        NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
        inboxStyle.setBigContentTitle(notifTitle);
        inboxStyle.setSummaryText("cyrille@itdreamreal.com");
        for (int i = 0; i < combinedNotifCounter; i++) {
            inboxStyle.addLine("Ceci est le test" + i);
        }
        currentNotifId = 500;
        notifBuilder.setStyle(inboxStyle);

        sendNotif();
    }

    private void setDataForMinPriorityNotif() {
        notifBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(icon)
                .setContentTitle(notifTitle)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(notifText))
                .setPriority(Notification.PRIORITY_MIN)
                .setContentText(notifText);
        sendNotif();
    }

    private void setDataForMaxPriorityNotif() {
        notifBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(icon)
                .setContentTitle(notifTitle)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(notifText))
                .setPriority(Notification.PRIORITY_MAX)
                .setContentText(notifText);
        sendNotif();
    }

    private void setDataForNotificationWithActionBtn() {
        notifBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(icon)
                .setContentTitle(notifTitle)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(notifText))
                .setContentText(notifText);
        Intent answerIntent = new Intent(this, AnswerRecActivity.class);
        answerIntent.setAction("Oui");
        PendingIntent pendingIntentOui = PendingIntent.getActivity(this, 1, answerIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        notifBuilder.addAction(R.drawable.thumbs_up, "Oui", pendingIntentOui);

        answerIntent.setAction("Non");
        PendingIntent pendingIntentNo = PendingIntent.getActivity(this, 1, answerIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        notifBuilder.addAction(R.drawable.thumbs_down, "Non", pendingIntentNo);

        sendNotif();
    }

    private void setDataForEpandLayoutNotification() {
        notifBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(icon)
                .setContentTitle(notifTitle)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(notifText))
                .setContentText(notifText);
        sendNotif();
    }

    private void setDataForSimpleNotification() {
        notifBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(icon)
                .setContentTitle(notifTitle)
                .setContentText(notifText);
        sendNotif();
    }

    private void sendNotif() {
        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this,0,notificationIntent,PendingIntent.FLAG_UPDATE_CURRENT);

        notifBuilder.setContentIntent(contentIntent);
        Notification notification = notifBuilder.build();
        notification.flags |= Notification.FLAG_AUTO_CANCEL;
        notification.defaults |= Notification.DEFAULT_SOUND;

        currentNotifId++;
        int notificationId = currentNotifId;

        if (notificationId == Integer.MAX_VALUE - 1)
            notificationId = 0;

        notifManager.notify(notificationId, notification);
    }

    private void setNotificationData() {
        notifTitle = this.getString(R.string.app_name);
        notifText = "Salut..C'est un test de notification";
        if (!NotifText.getText().toString().equals("")) {
            notifText = NotifText.getText().toString();
        }
        if (!NotifTitle.getText().toString().equals("")) {
            notifTitle = NotifTitle.getText().toString();
        }

    }
}
