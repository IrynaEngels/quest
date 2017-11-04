package com.example.kapusta.quest;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R2.id.button)
    Button button;

    @BindView(R2.id.textView)
    Button textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

         getNotification(button);

    }

    public void getNotification(View v){
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
        Runnable runnable1 = new Runnable() {
            @Override
            public void run() {
                Context context = getApplicationContext();

                Intent intent = new Intent(context, MainActivity.class);
                intent.addFlags((Intent.FLAG_ACTIVITY_CLEAR_TOP |
                        Intent.FLAG_ACTIVITY_SINGLE_TOP |
                        Intent.FLAG_ACTIVITY_FORWARD_RESULT));
                intent.putExtra("text","test");
                PendingIntent pendingIntent = PendingIntent.getActivity(context, 0 /* Request code */, intent,
                        PendingIntent.FLAG_UPDATE_CURRENT);

                NotificationCompat.Builder notificationBuilder = (NotificationCompat.Builder) new NotificationCompat.Builder(context)
//                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setSmallIcon(R.drawable.message)
                        .setContentTitle("New message")
                        .setContentText("You're faggot!")
                        .setAutoCancel(true)
                        .setDefaults(Notification.DEFAULT_SOUND|Notification.DEFAULT_VIBRATE).setAutoCancel(true)
                        .setContentIntent(pendingIntent);

                NotificationManager notificationManager =
                        (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

                notificationManager.notify(0, notificationBuilder.build());
}
        };
                final Handler handler1 = new Handler();
                handler1.postDelayed(runnable1, 6000);
            }
        });
    }

    public String generateText(){
        return null;
    }
}
