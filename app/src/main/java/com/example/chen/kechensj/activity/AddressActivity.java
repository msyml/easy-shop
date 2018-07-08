package com.example.chen.kechensj.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.chen.kechensj.R;
import com.example.chen.kechensj.adapter.AddressAdapter;
import com.example.chen.kechensj.application.MyApplication;
import com.example.chen.kechensj.bean.Address;
import com.example.chen.kechensj.bean.MyAddress;
import com.example.chen.kechensj.bean.UserInfor;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.vondear.rxtools.RxSPTool;
import com.vondear.rxtools.view.RxToast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddressActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.addList)
    ListView listView;
    @BindView(R.id.createNew)
    TextView addAddress;
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.smartRefresh)
    SmartRefreshLayout smartRefreshLayout;
    List<MyAddress> mList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);
        ButterKnife.bind(this);
        initListener();
    }

    private void initListener() {
        addAddress.setOnClickListener(this);
        back.setOnClickListener(this);
        AddressAdapter addressAdapter = new AddressAdapter(getData(), this);
        listView.setAdapter(addressAdapter);
        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mList.clear();
                setSpAddress();

                smartRefreshLayout.finishRefresh(1000);
                RxToast.normal("数据已更新");
            }
        });
        smartRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                smartRefreshLayout.finishLoadMore(2000);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.createNew:
                Intent intent = new Intent(this, AddAddressActivity.class);
                intent.putExtra("action","add");
                startActivity(intent);
                finish();
                break;
            case R.id.back:
                Intent intent1 = new Intent(this,MainActivity.class);
                startActivity(intent1);
                finish();
                break;
            default:
                break;
        }
    }

    MyApplication myApplication=new MyApplication();
    String ipAddress=myApplication.getIpAddress();

    private void setSpAddress() {
        final Gson gson = new Gson();
        UserInfor userInfor = gson.fromJson(RxSPTool.getString(this, "userInfor"), UserInfor.class);
        String uid = String.valueOf(userInfor.getData().getUser_id());
        String url = "http://" + ipAddress + "/Kcsj/QueryAddress";
        OkGo.<String>post(url)
                .tag(this)
                .params("uid", uid)
                .isMultipart(false)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        String result = response.body();
                        RxSPTool.putString(AddressActivity.this, "address",
                                result);
                        AddressAdapter addressAdapter = new AddressAdapter(getData(), AddressActivity.this);
                        listView.setAdapter(addressAdapter);
                    }

                    @Override
                    public void onError(Response<String> response) {
                        RxToast.warning("连接失败，请检查网络！");
                    }
                });
    }

    public List<MyAddress> getData() {

        MyAddress myAddress = new MyAddress();
        Gson gson = new Gson();
        Address address = gson.fromJson(RxSPTool.getString(this, "address"), Address.class);
        for (int i = 0; i < address.getDate().size(); i++) {
            myAddress = new MyAddress();
            myAddress.setUid(address.getDate().get(i).getUid());
            myAddress.setAid(address.getDate().get(i).getAid());
            myAddress.setAddress(address.getDate().get(i).getAddress());
            myAddress.setCheck(address.getDate().get(i).getCheck());
            myAddress.setMobile(address.getDate().get(i).getMobile());
            myAddress.setName(address.getDate().get(i).getName());
            mList.add(myAddress);
        }
        return mList;
    }
}
