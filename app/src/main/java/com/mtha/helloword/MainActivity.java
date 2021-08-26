package com.mtha.helloword;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    //khai bao mot doi tuong handler
    private Handler handler;
    private TextView txtNumber;
    private Button btnStart;
    private static final int UP_NUMBER = 100;
    private static final int NUMBER_DONE =101;
    private boolean isUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getViews();
        processHandler();
    }

    private void getViews(){
        txtNumber = findViewById(R.id.txtNumber);
        btnStart = findViewById(R.id.btnStart);
        btnStart.setOnClickListener(this);
    }

    private void processHandler(){
        //khoi tao doi tuong handler
        handler = new Handler(getMainLooper()){
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                switch (msg.what){
                    case UP_NUMBER:
                        //thuc hien cap nhat gia tri len UI
                        isUpdate = true;
                        //cap nhat UI voi gia tri moi
                        txtNumber.setText(String.valueOf(msg.arg1));
                        break;
                    case NUMBER_DONE:
                        //cap nhat lai giao dien hien thi da ket thuc viec cap nhat
                        txtNumber.setText("SUCCESS!");
                        isUpdate = false;
                        break;
                    default:
                        break;
                }
            }
        };
    }
    @Override
    public void onClick(View view) {
        //thuc thi cong viec khi click vao doi tuong button
        switch (view.getId()){
            case R.id.btnStart:
                //thuc hien cong viec o day
                if(!isUpdate)
                    updNumber();
                break;
            default:
                break;
        }
    }

    private void updNumber(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                //viet xu ly cap nhat gia tri o luong nay
                for(int i=0; i<=10;i++){
                    //khai bao mot duong message de chua noi dung tin nhan can dua vao message pool
                    Message msg = new Message();
                    //gan cong viec cho doi tuong msg nay
                    msg.what =UP_NUMBER;
                    msg.arg1=i;
                    handler.sendMessage(msg);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
                //gui tin nhan thuc hien hoan thanh cong viec
                handler.sendEmptyMessage(NUMBER_DONE);
            }
        }).start();
    }
}