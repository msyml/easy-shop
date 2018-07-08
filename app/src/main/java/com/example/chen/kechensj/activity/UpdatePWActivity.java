package com.example.chen.kechensj.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.chen.kechensj.R;
import com.example.chen.kechensj.application.MyApplication;
import com.example.chen.kechensj.bean.UserInfor;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.vondear.rxtools.RxSPTool;
import com.vondear.rxtools.view.RxToast;
import com.vondear.rxtools.view.dialog.RxDialogSure;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UpdatePWActivity extends AppCompatActivity implements View.OnClickListener, TextWatcher {

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.help)
    TextView toHelp;
    @BindView(R.id.oldPWCheck)
    CheckBox oldCheck;
    @BindView(R.id.oldPW)
    EditText oldPW;
    @BindView(R.id.pwCheckBox)
    CheckBox newCheck;
    @BindView(R.id.password)
    EditText newPW;
    @BindView(R.id.next)
    TextView toNext;
    MyApplication myApplication = new MyApplication();
    String ipAddress = myApplication.getIpAddress();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_pw);
        ButterKnife.bind(this);
        initListener();
    }

    private void initListener() {
        back.setOnClickListener(this);
        toHelp.setOnClickListener(this);
        oldCheck.setOnClickListener(this);
        newCheck.setOnClickListener(this);
        toNext.setOnClickListener(this);
        newPW.addTextChangedListener(this);
        oldPW.addTextChangedListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.help:
                Intent intent = new Intent(this, HelpActivity.class);
                startActivity(intent);
                break;
            case R.id.oldPWCheck:
                if (oldCheck.isChecked()) {
                    oldPW.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    oldPW.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                break;
            case R.id.pwCheckBox:
                if (newCheck.isChecked()) {
                    newPW.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    newPW.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                break;
            case R.id.next:
                String oldPassword = oldPW.getText().toString();
                String newPassword = newPW.getText().toString();
                updatePassword(oldPassword, newPassword);
                break;
        }
    }

    private void updatePassword(String oldPassword, final String newPassword) {
        final Gson gson = new Gson();
        UserInfor userInfor = gson.fromJson(RxSPTool.getString(this, "userInfor"), UserInfor.class);
        final RxDialogSure rxDialogSure = new RxDialogSure(UpdatePWActivity.this);
        rxDialogSure.getTitleView().setText("提示");
        int id = userInfor.getData().getUser_id();
        final String url = "http://" + ipAddress + "/Kcsj/UpdatePassword";
        String cookie = String.valueOf(RxSPTool.getInt(this, "loginStatus"));
        OkGo.<String>post(url)//
                .tag(this)//
                .headers("cookie", cookie)
                .params("id", id)
                .params("oldpassword", oldPassword)
                .params("newpassword", newPassword)
                .isMultipart(false)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(com.lzy.okgo.model.Response<String> response) {
                        String result = response.body();
                        final UserInfor userInfor = gson.fromJson(result, UserInfor.class);
                        if (userInfor.getMsg().equals("false")) {
                            rxDialogSure.getContentView().setText("修改密码失败，请稍后再试！");
                            rxDialogSure.getSureView().setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    rxDialogSure.cancel();
                                }
                            });
                            rxDialogSure.show();
                        } else if (userInfor.getMsg().equals("true")) {
                            rxDialogSure.getContentView().setText("修改密码成功，请重新登录！");
                            rxDialogSure.getSureView().setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    RxSPTool.putInt(UpdatePWActivity.this, "loginStatus", 0);
                                    RxSPTool.clearPreference(UpdatePWActivity.this, "userInfor", "userInfor");
                                    Intent intent = new Intent(UpdatePWActivity.this, LoginActivity.class);
                                    startActivity(intent);
                                }
                            });
                            rxDialogSure.show();
                        } else  if (userInfor.getMsg().equals("登录状态失效")){
                            rxDialogSure.getContentView().setText("登录状态已失效，请重新登录！");
                            rxDialogSure.getSureView().setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    RxSPTool.putInt(UpdatePWActivity.this, "loginStatus", 0);
                                    RxSPTool.clearPreference(UpdatePWActivity.this, "userInfor", "userInfor");
                                    Intent intent = new Intent(UpdatePWActivity.this, LoginActivity.class);
                                    startActivity(intent);
                                }
                            });
                            rxDialogSure.show();
                        }

                    }

                    @Override
                    public void onError(com.lzy.okgo.model.Response<String> response) {
                        RxToast.warning("连接失败，请检查网络！");
                    }
                });
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (newPW.getText().toString().length() > 5 && oldPW.getText().toString().length() > 5) {
            toNext.setClickable(true);
            toNext.setBackgroundResource(R.drawable.shape_login_true);
        } else {
            toNext.setClickable(false);
            toNext.setBackgroundResource(R.drawable.shape_login_false);
        }
    }


}
