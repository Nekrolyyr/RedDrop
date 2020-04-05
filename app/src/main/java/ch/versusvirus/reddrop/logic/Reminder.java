package ch.versusvirus.reddrop.logic;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

import ch.versusvirus.reddrop.R;
import ch.versusvirus.reddrop.logic.model.DonationListEntry;
import ch.versusvirus.reddrop.ui.HomeActivity;

public class Reminder {

    private static final String CHANNEL_ID = "1001";

    private Context context;

    public Reminder(Context context){
        this.context = context;
        createNotificationChannel();
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if(Objects.requireNonNull(context.getSystemService(NotificationManager.class)).getNotificationChannel(CHANNEL_ID) == null){
                return;
            }
            CharSequence name = context.getString(R.string.channel_name);
            String description = context.getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            assert notificationManager != null;
            notificationManager.createNotificationChannel(channel);

        }
    }

    public Notification specialNotification(String title, String content, Intent intent){
        return new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_logo_red)
                .setContentTitle(title)
                .setContentText(content)
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(content))
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setContentIntent(PendingIntent.getActivity(context, 0, intent, 0))
                .setAutoCancel(true)
                .build();
    }

    public Notification bloodReserveNotification(String title, String content){
        return new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_logo_red)
                .setContentTitle(title)
                .setContentText(content)
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(content))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true)
                .build();
    }

    public void scheduleNotification(DonationListEntry loaction, String startTime) {
        try {
            Intent intent = new Intent(context, HomeActivity.class);
            Notification notification = specialNotification("Donate Blood. Today at: " + startTime, loaction.getVillageInfo() + " " + loaction.getAdditionalInfo(), intent);
            Intent notificationIntent = new Intent(context, MyNotificationPublisher.class);
            int notificationId = 2001;
            notificationIntent.putExtra(MyNotificationPublisher.NOTIFICATION_ID, notificationId);
            notificationIntent.putExtra(MyNotificationPublisher.NOTIFICATION, notification);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, notificationId, notificationIntent, PendingIntent.FLAG_CANCEL_CURRENT);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new SimpleDateFormat("dd.MM.yyyyy", Locale.ENGLISH).parse(loaction.getDate()));
            calendar.add(Calendar.HOUR_OF_DAY, Integer.parseInt(startTime.split(":")[0]));
            calendar.add(Calendar.HOUR_OF_DAY, Integer.parseInt(startTime.split(":")[1]));
            long futureInMillis = calendar.getTimeInMillis();
            AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            alarmManager.set(AlarmManager.RTC_WAKEUP, futureInMillis, pendingIntent);
        } catch (ParseException | NullPointerException e) {
            System.out.println("Cannot start alarm");
        }
    }


}
