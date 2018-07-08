package com.example.chen.kechensj.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.chen.kechensj.R;
import com.example.chen.kechensj.bean.UserInfor;
import com.google.gson.Gson;
import com.vondear.rxtools.RxSPTool;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PersonSetActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.exit)
    TextView exit;
    @BindView(R.id.account)
    TextView name;
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.update)
    RelativeLayout toUpdate;
    @BindView(R.id.setinfor)
    RelativeLayout toInfor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_set);
        ButterKnife.bind(this);
        Gson gson = new Gson();
        UserInfor userInfor = gson.fromJson(RxSPTool.getString(this, "userInfor"), UserInfor.class);
        name.setText(userInfor.getData().getUser_account());
        initListener();
    }

    private void initListener() {
        exit.setOnClickListener(this);
        back.setOnClickListener(this);
        toInfor.setOnClickListener(this);
        toUpdate.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.exit:
                RxSPTool.putInt(PersonSetActivity.this, "loginStatus", 0);
                Intent intent = new Intent(PersonSetActivity.this, LoginActivity.class);
                finish();
                startActivity(intent);
                break;
            case R.id.update:
                Intent intent1 = new Intent(this, UpdatePWActivity.class);
                startActivity(intent1);
                break;
            case R.id.setinfor:
                Intent intent2 = new Intent(this,UserInforActivity.class);
                startActivity(intent2);
                break;
            default:
                break;
        }
    }
}
