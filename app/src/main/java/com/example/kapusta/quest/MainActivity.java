package com.example.kapusta.quest;

import android.app.AlarmManager;
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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R2.id.textView)
    TextView textView;

    @BindView(R2.id.rv)
    RecyclerView resView;

    @BindView(R2.id.btn_choice1)
    Button btn_choice1;

    @BindView(R2.id.btn_choice2)
    Button btn_choice2;

    ResViewAdapter adapter;
    List<Message> list;
    ArrayList<String> messages1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        AlarmManager am = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, MessageBroadcastReceiver.class);
        PendingIntent pi = PendingIntent.getBroadcast(this, 1, intent, 0);
        am.set(AlarmManager.RTC_WAKEUP, 3000, pi);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        resView.setLayoutManager(llm);

        String[] names = getResources().getStringArray(R.array.messages1);
        for(String e: names) {
            messages1 = new ArrayList<>();
            messages1.add(e);
        }
        for(int i = 0; i<messages1.size(); i++){
            Message m = new Message();
            m.setId(i);
            m.setText(messages1.get(i));
            list.add(m);
        }

        displayElements();

        btn_choice1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        btn_choice2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


//        getFragmentManager().beginTransaction().add(R.id.fragm_container, new QuestFragment()).commit();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        textView.setText(intent.getExtras().getString("text", "test1"));

    }

    private void displayElements(){
        adapter = new ResViewAdapter(new ArrayList<>());
        resView.setAdapter(adapter);
        for (Message e: list) {
            adapter.addElement(e);
        }
    }


//    public void getNotification(View v){
//        v.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//        Runnable runnable1 = new Runnable() {
//            @Override
//            public void run() {
//
//}
//        };
//                final Handler handler1 = new Handler();
//                handler1.postDelayed(runnable1, 2000);
//            }
//        });
//    }
//
//    public String generateText(){
//        return null;
//    }
}
