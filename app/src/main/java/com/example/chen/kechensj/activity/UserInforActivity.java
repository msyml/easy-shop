package com.example.chen.kechensj.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.chen.kechensj.R;
import com.example.chen.kechensj.application.MyApplication;
import com.example.chen.kechensj.bean.UserInfor;
import com.example.chen.kechensj.util.LogUtil;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.vondear.rxtools.RxSPTool;
import com.vondear.rxtools.view.RxToast;
import com.vondear.rxtools.view.dialog.RxDialogWheelYearMonthDay;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UserInforActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.uiUid)
    TextView setUid;
    @BindView(R.id.uiAccount)
    TextView setAccount;
    @BindView(R.id.uiSex)
    TextView setSex;
    @BindView(R.id.uiBirth)
    TextView setBirth;
    @BindView(R.id.uiSum)
    TextView setSum;
    @BindView(R.id.uiRegister)
    TextView setRegister;
    @BindView(R.id.setSex)
    RelativeLayout updateSex;
    @BindView(R.id.setBirth)
    RelativeLayout updateBirth;
    MyApplication myApplication = new MyApplication();
    String ipAddress = myApplication.getIpAddress();
    private RxDialogWheelYearMonthDay mRxDialogWheelYearMonthDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_infor);
        ButterKnife.bind(this);
        initListener();
        UserData();
    }

    private void UserData() {
        Gson gson = new Gson();
        UserInfor userInfor = gson.fromJson(RxSPTool.getString(this, "userInfor"), UserInfor.class);
        setUid.setText(String.valueOf(userInfor.getData().getUser_id()));
        setSex.setText(userInfor.getData().getUser_sex());
        setAccount.setText(userInfor.getData().getUser_account());
        setBirth.setText(userInfor.getData().getUser_birth());
        setSum.setText(String.valueOf(userInfor.getData().getUser_sum()));
        setRegister.setText(userInfor.getData().getUser_regis_time());
    }

    private void initListener() {
        back.setOnClickListener(this);
        updateBirth.setOnClickListener(this);
        updateSex.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Gson gson = new Gson();
        UserInfor userInfor=gson.fromJson(RxSPTool.getString(this, "userInfor"), UserInfor.class);
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.setBirth:
                String oldBirth = userInfor.getData().getUser_birth();
                String id = String.valueOf(userInfor.getData().getUser_id());
                initWheelYearMonthDayDialog(oldBirth, id);
                mRxDialogWheelYearMonthDay.show();
                break;
            case R.id.setSex:
                String oldSex = userInfor.getData().getUser_sex();
                id = String.valueOf(userInfor.getData().getUser_id());
                ShowChoise(oldSex, id);
                break;
        }
    }

    private void updateBirth(final String oldbirth, final String newbirth, String id) {
        final String url = "http://" + ipAddress + "/Kcsj/UpdateBirth";
        String cookie = String.valueOf(RxSPTool.getInt(this, "loginStatus"));
        OkGo.<String>post(url)//
                .tag(this)//
                .headers("cookie", cookie)
                .params("id", id)
                .params("oldbirth", oldbirth)
                .params("newbirth", newbirth)
                .isMultipart(false)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(com.lzy.okgo.model.Response<String> response) {
                        String result = response.body();
                        Gson gson = new Gson();
                        UserInfor userInfor = gson.fromJson(result, UserInfor.class);
                        if (userInfor.getMsg().equals("false")) {
                            RxToast.error("修改生日失败，请稍后再试！");
                        } else if (userInfor.getMsg().equals("true")) {
                            RxToast.normal("修改生日成功！");
                        } else if (userInfor.getMsg().equals("登录状态失效")) {
                            RxToast.warning("登录状态已失效，请重新登录！");
                            RxSPTool.putInt(UserInforActivity.this, "loginStatus", 0);
                            RxSPTool.clearPreference(UserInforActivity.this, "userInfor", "userInfor");
                            Intent intent = new Intent(UserInforActivity.this, LoginActivity.class);
                            startActivity(intent);
                        }
                    }

                    @Override
                    public void onError(com.lzy.okgo.model.Response<String> response) {
                        RxToast.warning("连接失败，请检查网络！");
                    }
                });
    }

    private void ShowChoise(final String oldSex, final String id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, android.R.style.Theme_Holo_Light_DarkActionBar);
        builder.setTitle("选择性别");
        final String[] sexs = {"男", "女", "未知"};
        builder.setItems(sexs, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                updateSex(oldSex, sexs[which], id);
            }
        });
        builder.show();
    }


    private void updateSex(final String oldsex, final String newsex, String id) {
        final String url = "http://" + ipAddress + "/Kcsj/UpdateSex";
        LogUtil.e(url);
        String cookie = String.valueOf(RxSPTool.getInt(this, "loginStatus"));
        OkGo.<String>post(url)//
                .tag(this)//
                .headers("cookie", cookie)
                .params("id", id)
                .params("oldsex", oldsex)
                .params("newsex", newsex)
                .isMultipart(false)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(com.lzy.okgo.model.Response<String> response) {
                        String result = response.body();
                        Gson gson = new Gson();
                        UserInfor userInfor = gson.fromJson(result, UserInfor.class);
                        if (userInfor.getMsg().equals("false")) {
                            RxToast.error("修改性别失败，请稍后再试！");
                        } else if (userInfor.getMsg().equals("true")) {
                            setSex.setText(newsex);
                            RxToast.normal("修改性别成功！");
                        } else if (userInfor.getMsg().equals("登录状态失效")) {
                            RxToast.warning("登录状态已失效，请重新登录！");
                            RxSPTool.putInt(UserInforActivity.this, "loginStatus", 0);
                            RxSPTool.clearPreference(UserInforActivity.this, "userInfor", "userInfor");
                            Intent intent = new Intent(UserInforActivity.this, LoginActivity.class);
                            startActivity(intent);
                        }
                    }

                    @Override
                    public void onError(com.lzy.okgo.model.Response<String> response) {
                        RxToast.warning("连接失败，请检查网络！");
                    }
                });
    }

    private void initWheelYearMonthDayDialog(final String oldBirth, final String id) {
        mRxDialogWheelYearMonthDay = new RxDialogWheelYearMonthDay(this, 1949, 2018);
        mRxDialogWheelYearMonthDay.getCheckBoxDay().setVisibility(View.GONE);
        mRxDialogWheelYearMonthDay.getSureView().setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View arg0) {
                        setBirth.setText(
                                mRxDialogWheelYearMonthDay.getSelectorYear() + "年"
                                        + mRxDialogWheelYearMonthDay.getSelectorMonth() + "月"
                                        + mRxDialogWheelYearMonthDay.getSelectorDay() + "日");
                        mRxDialogWheelYearMonthDay.cancel();
                        updateBirth(oldBirth, mRxDialogWheelYearMonthDay.getSelectorYear() + "年"
                                + mRxDialogWheelYearMonthDay.getSelectorMonth() + "月"
                                + mRxDialogWheelYearMonthDay.getSelectorDay() + "日", id);
                    }
                });
        mRxDialogWheelYearMonthDay.getCancleView().setOnClickListener(
                new View.OnClickListener() {

                    @Override
                    public void onClick(View arg0) {
                        mRxDialogWheelYearMonthDay.cancel();
                    }
                });
        // ------------------------------------------------------------------选择日期结束
    }


}
