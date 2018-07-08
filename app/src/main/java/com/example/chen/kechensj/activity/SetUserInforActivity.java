package com.example.chen.kechensj.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
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
import com.example.chen.kechensj.util.OkHttpUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.vondear.rxtools.view.RxToast;
import com.vondear.rxtools.view.dialog.RxDialogSure;
import com.vondear.rxtools.view.dialog.RxDialogSureCancel;

import java.io.IOException;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Request;

public class SetUserInforActivity extends FragmentActivity implements View.OnClickListener, TextWatcher {

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.help)
    TextView toHelp;
    @BindView(R.id.account)
    EditText setAccount;
    @BindView(R.id.pwCheckBox)
    CheckBox seePw;
    @BindView(R.id.password)
    EditText setPassword;
    @BindView(R.id.next)
    TextView toMain;
    MyApplication myApplication = new MyApplication();
    String ipAddress = myApplication.getIpAddress();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_user_infor);
        ButterKnife.bind(this);
        initListener();
    }

    private void initListener() {
        back.setOnClickListener(this);
        toHelp.setOnClickListener(this);
        setAccount.addTextChangedListener(this);
        setPassword.addTextChangedListener(this);
        seePw.setOnClickListener(this);
        toMain.setOnClickListener(this);
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
            case R.id.pwCheckBox:
                if (seePw.isChecked()) {
                    setPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    setPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                break;
            case R.id.next:
                String account = setAccount.getText().toString();
                String password = setPassword.getText().toString();
                String phone = getIntent().getStringExtra("phone");
                checkAccount(account, phone, password);
                break;
        }
    }

    private void checkAccount(final String account, final String phone, final String password) {
        String url = "http://" + ipAddress + "/Kcsj/CheckAccount";
        OkGo.<String>post(url)
                .tag(this)
                .params("account", account)
                .isMultipart(false)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        String result = response.body();
                        if (result.equals("true")) {
                            final RxDialogSure rxDialogSure = new RxDialogSure(SetUserInforActivity.this);
                            rxDialogSure.getTitleView().setText("提示");
                            rxDialogSure.getContentView().setText("该用户名已被使用！");
                            rxDialogSure.getSureView().setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    rxDialogSure.cancel();
                                }
                            });
                            rxDialogSure.show();
                        } else {
                            final RxDialogSureCancel rxDialogSureCancel = new RxDialogSureCancel(SetUserInforActivity.this);//提示弹窗
                            rxDialogSureCancel.getTitleView().setText("提示");
                            rxDialogSureCancel.getContentView().setText("账户名一旦确定不可更改，是否确认?");
                            rxDialogSureCancel.getSureView().setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    register(account, phone, password);
                                }
                            });
                            rxDialogSureCancel.getCancelView().setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    rxDialogSureCancel.cancel();
                                }
                            });
                            rxDialogSureCancel.show();
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        RxToast.warning("连接失败，请检查网络！");
                    }
                });
    }

    private void register(String account, String phone, String password) {
        String url = "http://" + ipAddress + "/Kcsj/AddUser";
        OkGo.<String>post(url)
                .tag(this)
                .params("account", account)
                .params("phone",phone)
                .params("password",password)
                .isMultipart(false)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        String result = response.body();
                        if (result.equals("true")) {
                            RxToast.normal("注册成功，将自动跳转到登陆界面");
                            Intent intent = new Intent(SetUserInforActivity.this, LoginActivity.class);
                            startActivity(intent);
                        } else {
                            RxToast.error("注册失败，请稍后再试！");
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
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
        if (setPassword.getText().toString().length() > 5 && setAccount.getText().toString().length() > 4) {
            toMain.setClickable(true);
            toMain.setBackgroundResource(R.drawable.shape_login_true);
        } else {
            toMain.setClickable(false);
            toMain.setBackgroundResource(R.drawable.shape_login_false);
        }
    }

}
