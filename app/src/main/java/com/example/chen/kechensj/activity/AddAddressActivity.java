package com.example.chen.kechensj.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.chen.kechensj.R;
import com.example.chen.kechensj.application.MyApplication;
import com.example.chen.kechensj.bean.Address;
import com.example.chen.kechensj.bean.UserInfor;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.vondear.rxtools.RxSPTool;
import com.vondear.rxtools.view.RxToast;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddAddressActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.et_receiver)
    EditText recrvier;
    @BindView(R.id.et_phoneNumber)
    EditText phoneNumber;
    @BindView(R.id.et_detailAddress)
    EditText address;
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.save)
    TextView save;
    @BindView(R.id.title)
    TextView title;
    MyApplication myApplication = new MyApplication();
    String ipAddress = myApplication.getIpAddress();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address);
        ButterKnife.bind(this);
        initListener();
        initData();
    }

    private void initData() {
        String action = getIntent().getStringExtra("action");
        if (action.equals("update")) {
            String aid = getIntent().getStringExtra("aid");
            Gson gson = new Gson();
            Address maddress = gson.fromJson(RxSPTool.getString(this, "address"), Address.class);
            for (int i = 0; i < maddress.getDate().size(); i++) {
                if (maddress.getDate().get(i).getAid() == Integer.valueOf(aid)) {
                    recrvier.setText(maddress.getDate().get(i).getName());
                    title.setText("修改收货地址");
                    phoneNumber.setText(maddress.getDate().get(i).getMobile());
                    address.setText(maddress.getDate().get(i).getAddress());
                }
            }
        }
    }

    private void initListener() {
        back.setOnClickListener(this);
        save.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                Intent intent = new Intent(this,AddressActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.save:
                String action = getIntent().getStringExtra("action");
                if (action.equals("add")) {
                    addAddress();
                } else {
                    String aid = getIntent().getStringExtra("aid");
                    updateAddress(aid);
                }
                break;
        }
    }

    private void updateAddress(String aid) {
        String url = "http://" + ipAddress + "/Kcsj/UpdateAddress";
        String name = recrvier.getText().toString();
        String mobile = phoneNumber.getText().toString();
        String saddress = address.getText().toString();
        OkGo.<String>post(url)
                .tag(this)
                .params("aid", aid)
                .params("name", name)
                .params("address", saddress)
                .params("mobile", mobile)
                .isMultipart(false)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Intent intent = new Intent(AddAddressActivity.this, AddressActivity.class);
                        startActivity(intent);
                        finish();
                        RxToast.normal("修改完成，请刷新界面");
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                    }
                });
    }


    public void addAddress() {
        String url = "http://" + ipAddress + "/Kcsj/AddAddress";
        Gson gson = new Gson();
        String name = recrvier.getText().toString();
        String mobile = phoneNumber.getText().toString();
        String saddress = address.getText().toString();
        UserInfor userInfor = gson.fromJson(RxSPTool.getString(this, "userInfor"), UserInfor.class);
        String id = String.valueOf(userInfor.getData().getUser_id());
        OkGo.<String>post(url)
                .tag(this)
                .params("uid", id)
                .params("name", name)
                .params("address", saddress)
                .params("mobile", mobile)
                .isMultipart(false)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        if (response.body().equals("true")) {
                            Intent intent = new Intent(AddAddressActivity.this, AddressActivity.class);
                            startActivity(intent);
                            finish();
                            RxToast.normal("添加完成，请刷新界面");
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                    }
                });
    }
}
