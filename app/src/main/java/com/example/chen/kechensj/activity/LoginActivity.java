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
import com.example.chen.kechensj.bean.UserInfor;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.vondear.rxtools.RxSPTool;
import com.vondear.rxtools.view.RxToast;
import com.vondear.rxtools.view.dialog.RxDialogLoading;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends FragmentActivity implements View.OnClickListener, TextWatcher {

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.help)
    TextView help;
    @BindView(R.id.account)
    EditText account;
    @BindView(R.id.cleanAccount)
    ImageView cleanAccount;
    @BindView(R.id.pwCheckBox)
    CheckBox pwCheck;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.cleanPassword)
    ImageView cleanPassword;
    @BindView(R.id.login)
    TextView login;
    @BindView(R.id.setpw)
    TextView toForget;
    @BindView(R.id.rigister)
    TextView toRegister;
    MyApplication myApplication = new MyApplication();
    String ipAddress = myApplication.getIpAddress();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        initListener();
        CommodityInfor();
    }

    private void initListener() {
        back.setOnClickListener(this);
        help.setOnClickListener(this);
        pwCheck.setOnClickListener(this);
        toRegister.setOnClickListener(this);
        toForget.setOnClickListener(this);
        cleanAccount.setOnClickListener(this);
        login.setOnClickListener(this);
        cleanPassword.setOnClickListener(this);
        account.addTextChangedListener(this);
        password.addTextChangedListener(this);
    }

    private void okgoLogin(final String account, final String password) {
        final RxDialogLoading rxDialogLoading = new RxDialogLoading(this);
        rxDialogLoading.show();
        final String url = "http://" + ipAddress + "/Kcsj/LoginServlet";
        OkGo.<String>post(url)//
                .tag(this)//
                .headers("cookie", "status")//
                .params("account", account)
                .params("password", password)
                .isMultipart(false)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(com.lzy.okgo.model.Response<String> response) {
                        String result = response.body();
                        Gson gson = new Gson();
                        UserInfor userInfor = gson.fromJson(result, UserInfor.class);
                        if (userInfor.getData().getUser_id() > 0) {
                            RxSPTool.putString(LoginActivity.this, "userInfor"
                                    , result);
                            RxSPTool.putInt(LoginActivity.this, "loginStatus"
                                    , userInfor.getStatus());
                            Intent intent5 = new Intent(LoginActivity.this,
                                    MainActivity.class);
                            rxDialogLoading.cancel();
                            finish();
                            startActivity(intent5);
                        } else {
                            rxDialogLoading.cancel();
                            RxToast.error("帐号或密码不存在");
                        }
                    }

                    @Override
                    public void onError(com.lzy.okgo.model.Response<String> response) {
                        rxDialogLoading.cancel();
                        RxToast.warning("连接失败，请检查网络！");
                    }
                });
    }

    private void CommodityInfor() {
        final String url = "http://" + ipAddress + "/Kcsj/QueryAllCommodity";
        OkGo.<String>get(url)
                .tag(this)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(com.lzy.okgo.model.Response<String> response) {
                        String result = response.body();
                        RxSPTool.putString(LoginActivity.this, "commoDity",
                                result);
                    }

                    @Override
                    public void onError(com.lzy.okgo.model.Response<String> response) {
                    }
                });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login:
                String ac = account.getText().toString();
                String pw = password.getText().toString();
                /*checkLogin(ac, pw);*/
                okgoLogin(ac, pw);
                break;
            case R.id.back:
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;
            case R.id.help:
                Intent intent1 = new Intent(this, HelpActivity.class);
                startActivity(intent1);
                break;
            case R.id.cleanAccount:
                account.setText(null);
                break;
            case R.id.cleanPassword:
                password.setText(null);
                break;
            case R.id.pwCheckBox:
                if (pwCheck.isChecked()) {
                    password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                break;
            case R.id.setpw:
                Intent intent2 = new Intent(this, ForgetPWActivity.class);
                startActivity(intent2);
                break;
            case R.id.rigister:
                Intent intent3 = new Intent(this, RegisterActivity.class);
                startActivity(intent3);
                break;
            default:
                break;
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (account.getText().length() != 0 && password.getText().length() != 0) {
            login.setClickable(true);
            login.setBackgroundResource(R.drawable.shape_login_true);
        } else {
            login.setClickable(false);
            login.setBackgroundResource(R.drawable.shape_login_false);
        }
        if (account.getText().length() != 0) {
            cleanAccount.setVisibility(View.VISIBLE);
        } else {
            cleanAccount.setVisibility(View.INVISIBLE);
        }
        if (password.getText().length() != 0) {
            cleanPassword.setVisibility(View.VISIBLE);
        } else {
            cleanPassword.setVisibility(View.INVISIBLE);
        }
    }
}
