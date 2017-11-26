package com.example.kapusta.quest;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

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

    RealmStorage realmStorage;

    List<Message> list;
    List<Message> receivedMessage;
    List<String[]> arrayChoice1;
    List<String[]> arrayChoice2;
//    ArrayList<String> messages1;
    Runnable characterAdder;
    String[] btn1;
    String[] btn2;
    String[] messages1;
    String[] messages2;
    String[] messages3;
    String[] messages4;
    String[] messages5;
    String[] messages6;
    String[] messages7;
    String[] messages8;
    String[] messages9;
    String[] messages10;
    String choice1;
    String choice2;
    int number = 1;
    int version;
    SharedPreferences mSettings;
    MessageBroadcastReceiver messageBroadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        AlarmManager am = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, MessageBroadcastReceiver.class);
        PendingIntent pi = PendingIntent.getBroadcast(this, 1, intent, 0);
        am.set(AlarmManager.RTC_WAKEUP, 3000, pi);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);

        mSettings = getSharedPreferences("PREFERENCES", Context.MODE_PRIVATE);

        messageBroadcastReceiver = new MessageBroadcastReceiver();
        resView.setLayoutManager(linearLayoutManager);

        version = mSettings.getInt("Version", 1);
        number = mSettings.getInt("Number", 1);
        Log.d("TAG", "the version number is"+version);
        realmStorage = new RealmStorage();
        realmStorage.setVersion(version);
        Log.d("TAG", "the realmStorage version number is"+realmStorage.getVersion());

        receivedAdapter = new ReceivedAdapter(new ArrayList<>());
        resView.setAdapter(receivedAdapter);

        arrayChoice1 = new ArrayList<>();
        arrayChoice2 = new ArrayList<>();

        messages1 = getResources().getStringArray(R.array.messages1);
        messages2 = getResources().getStringArray(R.array.messages2);
        messages3 = getResources().getStringArray(R.array.messages3);
        messages4 = getResources().getStringArray(R.array.messages4);
        messages5 = getResources().getStringArray(R.array.messages5);
        messages6 = getResources().getStringArray(R.array.messages6);
        messages7 = getResources().getStringArray(R.array.messages7);
        messages8 = getResources().getStringArray(R.array.messages8);
        messages9 = getResources().getStringArray(R.array.messages9);
        messages10 = getResources().getStringArray(R.array.messages10);

        arrayChoice1.add(messages1);
        arrayChoice1.add(messages2);
        arrayChoice1.add(messages3);
        arrayChoice1.add(messages4);
        arrayChoice1.add(messages5);
        arrayChoice2.add(messages6);
        arrayChoice2.add(messages7);
        arrayChoice2.add(messages8);
        arrayChoice2.add(messages9);
        arrayChoice2.add(messages10);

        btn1 = getResources().getStringArray(R.array.btn1);
        btn2 = getResources().getStringArray(R.array.btn2);

        if(realmStorage.getVersion() == 1) {
            list = new ArrayList<>();
            ///////////////
            receivedTextOutput(arrayChoice1.get(0));
            //\\\\\\\\\\\\\\\\\\\\
            choice1 = btn1[0];
            choice2 = btn2[0];
            mSettings.edit().putString("Choice1", choice1).apply();
            mSettings.edit().putString("Choice2", choice2).apply();
        }
        else {
            list = new ArrayList<>();
            for (Message m: realmStorage.readeReminders(this)) list.add(m);
            for(Message m: list)
            receivedAdapter.addElement(m);
            choice1 = mSettings.getString("Choice1", " ");//+1
            choice2 = mSettings.getString("Choice2", " ");//+1
        }
        btn_choice1.setText(choice1);
        btn_choice2.setText(choice2);

        btn_choice1.setOnClickListener(view -> messageOutput(view));
        btn_choice2.setOnClickListener(view -> messageOutput(view));
    }

    @Override
    protected void onNewIntent(Intent intent) {
        //java.lang.NullPointerException: Attempt to invoke interface method
        // 'java.lang.Object java.util.List.get(int)' on a null object reference
//        at com.example.kapusta.quest.MainActivity.onNewIntent(MainActivity.java:149)
        receivedAdapter.addElement(receivedMessage.get(0));
        version=2;
        realmStorage.setVersion(version);
        mSettings.edit().putInt ("Version", version).apply();
        animateText(500);
        btn_choice1.setText(choice1);
        btn_choice2.setText(choice2);
    }

    private void receivedTextOutput(String[] messages){
        number++;
        mSettings.edit().putInt ("Number", number).apply();
        receivedMessage = new ArrayList<>();
        for(int i = 0; i<messages.length; i++){
            Message m = new Message();
            m.setId(list.size());
            m.setPosition(0);
            m.setText(messages[i]);
            m.setMessageTime(new Date().getTime());
            list.add(m);
            receivedMessage.add(m);
            realmStorage.saveReminder(this, m);
        }
    }
    //айди входящих сообщений перезаписываются, текст кнопки сохраняется после сообщений,
    //выводящихся ее нажатием
    private void messageOutput(View v){

        Message m = new Message();
        m.setId(list.size());
        m.setPosition(1);
        if (v==btn_choice1) m.setText(btn_choice1.getText().toString());
        else if (v==btn_choice2) m.setText(btn_choice2.getText().toString());

        m.setMessageTime(new Date().getTime());
        list.add(m);
        receivedAdapter.addElement(m);
        realmStorage.saveReminder(this, m);
        for(int i = 0; i < arrayChoice1.size(); i++){
            if (i == number) {
                if (v==btn_choice1) receivedTextOutput(arrayChoice1.get(i-1));
                else if (v==btn_choice2) receivedTextOutput(arrayChoice2.get(i-1));
                break;
            }
        }
        for(Message mes: realmStorage.readeReminders(this)){
            Log.d("TAG", mes.getText()+ "messageOutput"+mes.getId());
        }

        for(int i = 0; i<btn1.length; i++) {
            if (choice1 == btn1[i]) {
                if (i++ < btn1.length) {
                    choice1 = btn1[i++];
                    choice2 = btn2[--i];
                }
            }
        }
        messageBroadcastReceiver.sendNotification(getApplicationContext(), "string");
        mSettings.edit().putString("Choice1", choice1).apply();
        mSettings.edit().putString("Choice2", choice2).apply();
    }

    public void animateText(final long mDelay) {

        final Handler mHandler = new Handler();

        final int[] mIndex = {1};

        characterAdder = () -> {
            receivedAdapter.addElement(receivedMessage.get(mIndex[0]));
            resView.scrollToPosition(list.size() - 1);
            mIndex[0]++;
            if (mIndex[0] < receivedMessage.size()) {
                mHandler.postDelayed(characterAdder, mDelay);
            }
            resView.scrollToPosition(list.size() - 1);
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

