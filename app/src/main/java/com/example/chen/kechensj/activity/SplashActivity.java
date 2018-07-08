package com.example.chen.kechensj.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.chen.kechensj.R;
import com.example.chen.kechensj.application.MyApplication;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.vondear.rxtools.RxSPTool;

public class SplashActivity extends AppCompatActivity {

    private TextView mCountDownTextView;
    private int loginStatus;
    private MyCountDownTimer mCountDownTimer;
    MyApplication myApplication = new MyApplication();
    String ipAddress = myApplication.getIpAddress();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        CommodityInfor();
        mCountDownTextView = findViewById(R.id.start_skip_count_down);
        loginStatus = RxSPTool.getInt(this, "loginStatus");
        mCountDownTextView.setText("3s 跳过");
        //创建倒计时类
        mCountDownTimer = new MyCountDownTimer(3000, 1000);
        mCountDownTimer.start();
    }

    private void CommodityInfor() {
        final String url = "http://" + ipAddress + "/Kcsj/QueryAllCommodity";
        OkGo.<String>get(url)
                .tag(this)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(com.lzy.okgo.model.Response<String> response) {
                        String result = response.body();
                        RxSPTool.putString(SplashActivity.this, "commoDity",
                                result);
                    }

                    @Override
                    public void onError(com.lzy.okgo.model.Response<String> response) {
                    }
                });
    }

    @Override
    protected void onDestroy() {
        if (mCountDownTimer != null) {
            mCountDownTimer.cancel();
        }
        super.onDestroy();
    }

    class MyCountDownTimer extends CountDownTimer {
        /**
         * @param millisInFuture    表示以「 毫秒 」为单位倒计时的总数
         *                          例如 millisInFuture = 1000 表示1秒
         * @param countDownInterval 表示 间隔 多少微秒 调用一次 onTick()
         *                          例如: countDownInterval = 1000 ; 表示每 1000 毫秒调用一次 onTick()
         */

        public MyCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }


        public void onFinish() {
            if (loginStatus == 0) {
                Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(intent);
            } else if (loginStatus==200){
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
            }else {
                Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        }

        public void onTick(long millisUntilFinished) {
            long a = (millisUntilFinished / 1000) + 1;
            mCountDownTextView.setText(a + "s 跳过");
        }

    }


}
