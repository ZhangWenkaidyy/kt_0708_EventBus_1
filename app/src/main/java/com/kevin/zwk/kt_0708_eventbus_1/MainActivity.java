package com.kevin.zwk.kt_0708_eventbus_1;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class MainActivity extends AppCompatActivity {
    private Button btn;
    private int count;
    private int i=2;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EventBus.getDefault().register(this);
        btn = (Button) findViewById(R.id.btn);
        textView= (TextView) findViewById(R.id.textView);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        while (count != 20) {
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            count++;
                            i++;
                            MyEventMsg myEventMsg = new MyEventMsg(count);
                            EventBus.getDefault().post(myEventMsg);
                            EventBus.getDefault().post(i);

                        }
                    }
                }).start();
            }
        });
    }

    @Subscribe(threadMode =ThreadMode.MAIN)
    //默认代表改方法在发送的线程中
    // 注解使用了ThreadMode.MAIN代表改方法运行于UI主线程
    public void getmsg(MyEventMsg myEventMsg) {
        btn.setText(myEventMsg.getCount() + "");
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getmsg2(Integer i) {
        Log.i("-----","------"+i);
        textView.setText(i+"");
    }


}
