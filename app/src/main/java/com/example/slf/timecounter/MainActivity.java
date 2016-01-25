package com.example.slf.timecounter;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;

import android.os.Bundle;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends Activity implements View.OnClickListener{
    private Button counter1,counter2,counter3,counter4;
    private int i1=0,i2=0,i3=0,i4=0;
    private final Timer timer = new Timer();
    private TimerTask task,task2;
    Time t=new Time();
    private int second1=0,second2=0;
    private int minute1=0,minute2=0;
    private int hour1=0,hour2=0;
    private int sec=0,min=0,ho=0;
    private Handler handler=new Handler() {
        public void handleMessage(Message msg){
            switch (msg.what){
                case 1:
                    t.setToNow(); // 取得系统时间。
                    int second_one=t.second-second1+(t.minute-minute1)*60+(t.hour-hour1)*3600;
                    String msecond;
                    if (i1>=950){
                        i1=0;
                    }
                    if (i1<10){
                        msecond="00"+i1;
                    }else if (i1<100){
                        msecond="0"+i1;
                    }else {
                        msecond=""+i1;
                    }
                    counter1.setText(changeToString(second_one)+"    "+msecond);
                    i1++;
                    break;
                case 2:
                    // or Time t=new Time("GMT+8"); 加上Time Zone资料。
                    t.setToNow(); // 取得系统时间。
                    int second_two=t.second-second2+(t.minute-minute2)*60+(t.hour-hour2)*3600;
                    String msecond2;
                    if(i2>=950){
                        i2=0;
                    }
                    if (i2<10){
                        msecond2="00"+i2;
                    }else if (i2<100){
                        msecond2="0"+i2;
                    }else {
                        msecond2=""+i2;
                    }
                    counter2.setText(changeToString(second_two) + "    " + msecond2);
                    i2++;
                    break;
                case 3:
                    Log.d("test", "33333333");
                    break;
                case 4:
                    Log.d("test","4444444");
                    break;
            }
        }


    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }
    //在这个方法把秒转化成分钟+秒的字符串
    private  String changeToString(int second_time){
        String secStr,hoStr,minStr;
        String finalStr;
        ho=second_time/3600;
        min=(second_time-ho*3600)/60;
        sec=(second_time-ho*3600-min*60);
        if (ho<10){
            hoStr="0"+ho;
        }else {
            hoStr=""+ho;
        }
        if (min<10){
            minStr="0"+min;
        }else {
            minStr=""+min;
        }if (sec<10){
            secStr="0"+sec;
        }else {
            secStr=""+sec;
        }
        finalStr=hoStr+":"+minStr+":"+secStr;
        return finalStr;
    }

    private void init() {
        counter1= (Button) findViewById(R.id.counter1);
        counter1.setOnClickListener(this);
        counter2= (Button) findViewById(R.id.counter2);
        counter2.setOnClickListener(this);
        counter3= (Button) findViewById(R.id.counter3);
        counter3.setOnClickListener(this);
        counter4= (Button) findViewById(R.id.counter4);
        counter4.setOnClickListener(this);
        task=initTimerTask(1);
        task2=initTimerTask(2);

    }

    //减少代码量,方便初始化
    public TimerTask initTimerTask(final int x){
        return new TimerTask() {
            @Override
            public void run() {
// TODO Auto-generated method stub
                Message message = new Message();
                message.what = x;
                handler.sendMessage(message);
            }
        };
    }

    @Override
    public void onClick(View v) {
        SharedPreferences pref=getSharedPreferences("StopJuage", MODE_PRIVATE);
        switch (v.getId()){
            case R.id.counter1:
                Boolean judge1=pref.getBoolean("counter1", false);
                Log.d("test", "here");
                if (!judge1){
                    t.setToNow(); // 取得系统时间。
                    second1=t.second;
                    minute1=t.minute;
                    hour1=t.hour;
                    try{
                        timer.schedule(task, 1, 1);
                    }catch (Exception e){
                        task.run();
                    }
                    SharedPreferences.Editor editor=getSharedPreferences("StopJuage",MODE_PRIVATE).edit();
                    editor.putBoolean("counter1", true);
                    editor.commit();
                }else {
                    task.cancel();
                    task=initTimerTask(1);
                    SharedPreferences.Editor editor=getSharedPreferences("StopJuage",MODE_PRIVATE).edit();
                    editor.putBoolean("counter1", false);
                    editor.commit();
                }

                break;
            case R.id.counter2:
                Boolean judge2=pref.getBoolean("counter2", false);
                Log.d("test", "here");
                if (!judge2){
                    t.setToNow(); // 取得系统时间。
                    second2=t.second;
                    minute2=t.minute;
                    hour2=t.hour;
                    try{
                        timer.schedule(task2, 1, 1);
                    }catch (Exception e){
                        task2.run();
                    }
                    SharedPreferences.Editor editor=getSharedPreferences("StopJuage",MODE_PRIVATE).edit();
                    editor.putBoolean("counter2", true);
                    editor.commit();
                }else {
                    task2.cancel();
                    task2=initTimerTask(2);
                    SharedPreferences.Editor editor=getSharedPreferences("StopJuage",MODE_PRIVATE).edit();
                    editor.putBoolean("counter2", false);
                    editor.commit();
                }

                break;
            case R.id.counter3:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Message message=new Message();
                        message.what=3;
                        handler.sendMessage(message);
                    }
                }).start();
                break;
            case R.id.counter4:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Message message=new Message();
                        message.what=4;
                        handler.sendMessage(message);
                    }
                }).start();
                break;
            default:
                break;
        }
    }
}
