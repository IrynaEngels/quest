package com.example.kapusta.quest;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.kapusta.quest.RealmClasss.Message;
import com.example.kapusta.quest.RealmClasss.RealmStorage;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {


    @BindView(R2.id.rv)
    RecyclerView resView;

    @BindView(R2.id.btn_choice1)
    Button btn_choice1;

    @BindView(R2.id.btn_choice2)
    Button btn_choice2;

    ReceivedAdapter receivedAdapter;
    SentAdapter sentAdapter;

    RealmStorage realmStorage;

    List<Message> list;
    ArrayList<String> messages1;
    Runnable characterAdder;

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
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);

        MessageBroadcastReceiver messageBroadcastReceiver = new MessageBroadcastReceiver();
        resView.setLayoutManager(linearLayoutManager);

        realmStorage = new RealmStorage();

        receivedAdapter = new ReceivedAdapter(new ArrayList<>());
        resView.setAdapter(receivedAdapter);


        String[] names = getResources().getStringArray(R.array.messages1);
        messages1 = new ArrayList<>();
        for(String e: names) {
            messages1.add(e);
        }
        list = new ArrayList<>();
        for(int i = 0; i<messages1.size(); i++){
            Message m = new Message();
            m.setId(i);
            m.setPosition(0);
            m.setText(messages1.get(i));
            m.setMessageTime(new Date().getTime());
            list.add(m);
        }
        for(int i=0;i<list.size();i++){
            Log.d("TAG", list.get(i).getText());
        }
//        receivedAdapter.addElement(list.get(0));

        btn_choice1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Message m = new Message();
                m.setId(list.size());
                m.setPosition(1);
                m.setText(btn_choice1.getText().toString());
                m.setMessageTime(new Date().getTime());
                list.add(m);
                receivedAdapter.addElement(m);
                messageBroadcastReceiver.sendNotification(getApplicationContext(), "string");
            }
        });
        btn_choice2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Message m = new Message();
                m.setId(list.size());
                m.setPosition(1);
                m.setText(btn_choice2.getText().toString());
                m.setMessageTime(new Date().getTime());
                list.add(m);
                receivedAdapter.addElement(m);
            }
        });


    }

    @Override
    protected void onNewIntent(Intent intent) {
//        textView.setText(intent.getExtras().getString("text", "test1"));
        receivedAdapter.addElement(list.get(0));
        animateText(2000);
        btn_choice1.setText("some");
        btn_choice2.setText("other");
    }

    public void animateText(final long mDelay) {

        final Handler mHandler = new Handler();

        final int[] mIndex = {1};

        characterAdder = () -> {
            receivedAdapter.addElement(list.get(mIndex[0]));
            mIndex[0]++;
            if (mIndex[0] < list.size()) {
                mHandler.postDelayed(characterAdder, mDelay);
            }
        };

        mHandler.removeCallbacks(characterAdder);
        mHandler.postDelayed(characterAdder, mDelay);

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

