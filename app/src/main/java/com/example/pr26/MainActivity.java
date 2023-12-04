package com.example.pr26;


import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.app.Person;

public class MainActivity extends AppCompatActivity {
    // Идентификатор уведомления
    private static final int NOTIFY_ID = 123;
    private int counter = 123;

    // Идентификатор канала
    private static String CHANNEL_ID = "Channel";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        createNotificationChannel();
        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            @SuppressLint("MissingPermission")
            public void onClick(View v) {
                Uri soundUri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.sound);
                //Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);


                long[] vibrate = new long[] { 1000, 1000, 1000, 1000, 1000 };

                Intent notificationIntent = new Intent(MainActivity.this, MainActivity.class);
                PendingIntent contentIntent = PendingIntent.getActivity(MainActivity.this,
                        0, notificationIntent,
                        PendingIntent.FLAG_CANCEL_CURRENT | PendingIntent.FLAG_IMMUTABLE);

                String bigText = "Всем привет я уведомление. "
                        + "Не читай меня, я стесняюсь. ";


                Person FirstPers = new Person.Builder().setName("Первый").build();
                Person SecondPers = new Person.Builder().setName("Второй").build();

                NotificationCompat.MessagingStyle messagingStyle = new NotificationCompat.MessagingStyle
                        (FirstPers)
                        .setConversationTitle("Android chat")
                        .addMessage("Привет!", System.currentTimeMillis(), FirstPers)
                        .addMessage("Как ваши дела?", System
                                        .currentTimeMillis(),
                                FirstPers)
                        .addMessage("Хорошего дня!", System.currentTimeMillis(),
                                SecondPers)
                        .addMessage("Прям суперский", System.currentTimeMillis(), SecondPers)
                        .addMessage("Ты сделал домашнее задание?", System.currentTimeMillis(),
                                SecondPers)
                        .addMessage("Шерше ля фам", System.currentTimeMillis(),
                                FirstPers);



                NotificationCompat.Builder builder =
                        new NotificationCompat.Builder(MainActivity.this, CHANNEL_ID)
                                .setSmallIcon(R.drawable.first_pic)
                                //.setSmallIcon(android.R.drawable.stat_sys_download)
                                .setContentTitle("Уведомление")
                                .setContentText("Пора отдохнуть")
                                .setPriority(NotificationCompat.PRIORITY_HIGH)
                                .setContentIntent(contentIntent)
                                .setLargeIcon(BitmapFactory.decodeResource(getResources(),
                                        R.drawable.second_pic))
                                .setAutoCancel(true)
                                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                                .setVibrate(vibrate)
                                .setLights(Color.RED,0,1)
                                //.addAction(R.drawable.first_pic, "Открыть", contentIntent)
                                //.addAction(R.drawable.first_pic, "Отказаться", contentIntent)
                                //.addAction(R.drawable.second_pic, "Другой вариант", contentIntent)
                                .setStyle(new NotificationCompat.BigTextStyle().bigText(bigText))
//                                .setStyle(new NotificationCompat.BigPictureStyle()
//                                        .bigPicture(BitmapFactory.decodeResource(getResources(),
//                                                R.drawable.second_pic)))
                               //.setStyle(new NotificationCompat.InboxStyle()
                               //         .addLine("Первое сообщение").addLine("Второе сообщение")
                               //         .addLine("Третье сообщение").addLine("Четвертое сообщение")
                                //        .setSummaryText("+2 more"))
                                .setStyle(messagingStyle)
                                .setColor(Color.GREEN);


                NotificationManagerCompat notificationManager =
                        NotificationManagerCompat.from(MainActivity.this);
                notificationManager.notify(NOTIFY_ID, builder.build());

                // Теперь у уведомлений будут новые идентификаторы
                notificationManager.notify(counter++, builder.build());


            }

        });
    }
    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Channel";
            String description = "Channel for me notifications";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}