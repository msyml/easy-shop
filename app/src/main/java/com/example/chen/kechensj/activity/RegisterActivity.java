package com.example.chen.kechensj.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.chen.kechensj.R;
import com.example.chen.kechensj.application.MyApplication;
import com.example.chen.kechensj.util.OkHttpUtils;
import com.vondear.rxtools.view.RxToast;

import java.io.IOException;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Request;

public class RegisterActivity extends FragmentActivity implements View.OnClickListener, TextWatcher {

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.help)
    TextView toHelp;
    @BindView(R.id.phoneNumber)
    EditText phone;
    @BindView(R.id.cleanPhone)
    ImageView cleanPhone;
    @BindView(R.id.code)
    EditText code;
    @BindView(R.id.sendCode)
    TextView sendCode;
    @BindView(R.id.toNext)
    TextView toNext;
    MyApplication myApplication=new MyApplication();
    String ipAddress=myApplication.getIpAddress();

    int codeNumber;

    private TextWatcher editclick = new TextWatcher() {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }


        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        /*验证码实时监听：
         *1、同意并注册按钮是否能点击；
         * 2、同意并注册按钮的背景颜色*/
        @Override
        public void afterTextChanged(Editable s) {
            if (code.getText().length() >= 4) {
                toNext.setClickable(true);
                toNext.setBackgroundResource(R.drawable.shape_login_true);
            } else {
                toNext.setClickable(false);
                toNext.setBackgroundResource(R.drawable.shape_login_false);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        initListener();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void initListener() {
        phone.addTextChangedListener(this);
        back.setOnClickListener(this);
        toHelp.setOnClickListener(this);
        sendCode.setOnClickListener(this);
        toNext.setOnClickListener(this);
        cleanPhone.setOnClickListener(this);
        code.addTextChangedListener(editclick);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            /* 左上返回：结束活动 */
            case R.id.back:
                finish();
                break;
            /* 右上帮助：跳转到帮助活动 */
            case R.id.help:
                Intent intent = new Intent(this, HelpActivity.class);
                startActivity(intent);
                break;
            /* 发送验证码：
             * 1、判断是否符合手机号；
             * 2、判断数据库中是否已存在该手机号
             * 3、验证码输入框获取焦点，输入验证码*/
            case R.id.sendCode:
                /* replaceAll是去除字符串中所有的空格 */
                String moblienumber = phone.getText().toString().replaceAll(" ", "");
                codeNumber = randomNumber();
                chePhone(moblienumber);
                break;
            case R.id.toNext:
                String getCode = code.getText().toString();
                if (getCode.equals(String.valueOf(codeNumber))) {
                    Intent intent1 = new Intent(this, SetUserInforActivity.class);
                    intent1.putExtra("phone", phone.getText().toString().replaceAll(" ", ""));
                    startActivity(intent1);
                } else {
                    RxToast.error("验证码错误，请重新输入！");
                }
                break;
            case R.id.cleanPhone:
                phone.setText("");
                break;
        }
    }

    private void chePhone(final String mphone) {
        String url = "http://"+ipAddress+"/Kcsj/CheckPhone";
        final HashMap<String, String> params = new HashMap<>();
        params.put("phone", mphone);
        OkHttpUtils.getFormRequest(url, params, new OkHttpUtils.DataCallBack() {
            @Override
            public void requestSuccess(String result) throws Exception {
                if (result.equals("true")) {
                    RxToast.normal("该手机号已被注册！");
                    phone.setText("");
                } else if (result.equals("null")) {
                    phone.setText("");
                    RxToast.normal("这不是一个手机号!");
                } else if (result.equals("false")) {
                    code.setFocusable(true);
                    code.setFocusableInTouchMode(true);
                    code.requestFocus();
                    getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                    RxToast.normal("验证码为：" + codeNumber);
                    new CountDownTimer(60 * 1000, 1000) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                            // TODO Auto-generated method stub
                            sendCode.setClickable(false);
                            sendCode.setText(millisUntilFinished / 1000 + "s后重新发送");
                        }

                        @Override
                        public void onFinish() {
                            sendCode.setClickable(true);
                            sendCode.setText("获取验证码");
                        }
                    }.start();
                }
            }

            @Override
            public void requestFailure(Request request, IOException e) {
                RxToast.warning("连接失败，请检查网络！");
            }
        });
    }


    /* 生成一个六位随机数 */
    private int randomNumber() {
        return (int) ((Math.random() * 9 + 1) * 100000);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        //手机号格式化xxx xxxx xxxx
        if (s == null || s.length() == 0) return;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            if (i != 3 && i != 8 && s.charAt(i) == ' ') {
                continue;
            } else {
                sb.append(s.charAt(i));
                if ((sb.length() == 4 || sb.length() == 9) && sb.charAt(sb.length() - 1) != ' ') {
                    sb.insert(sb.length() - 1, ' ');
                }
            }
        }
        if (!sb.toString().equals(s.toString())) {
            int index = start + 1;
            if (sb.charAt(start) == ' ') {
                if (before == 0) {
                    index++;
                } else {
                    index--;
                }
            } else {
                if (before == 1) {
                    index--;
                }
            }
            phone.setText(sb.toString());
            phone.setSelection(index);
        }
    }

    /* 手机号码实时监听：
     * 1、发送验证码按钮是否能点击；
     * 2、清除手机号的图片是否显示；
     * 3、发送验证码的背景；
     * 4、发送验证码的字体颜色*/
    @Override
    public void afterTextChanged(Editable s) {
        if (phone.getText().length() > 0) {
            sendCode.setClickable(true);
            cleanPhone.setVisibility(View.VISIBLE);
            sendCode.setBackgroundResource(R.drawable.shape_sendcode_true);
            sendCode.setTextColor(getResources().getColor(R.color.menuCenter));
        } else {
            sendCode.setClickable(false);
            cleanPhone.setVisibility(View.INVISIBLE);
            sendCode.setBackgroundResource(R.drawable.shape_sendcode_false);
            sendCode.setTextColor(getResources().getColor(R.color.bt_menu_center));
        }
    }

}
