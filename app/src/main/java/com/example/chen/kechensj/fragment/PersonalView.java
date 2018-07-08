package com.example.chen.kechensj.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.chen.kechensj.R;
import com.example.chen.kechensj.activity.AddressActivity;
import com.example.chen.kechensj.activity.CollectActivity;
import com.example.chen.kechensj.activity.HistoryActivity;
import com.example.chen.kechensj.activity.MyOrderActivity;
import com.example.chen.kechensj.activity.OrderActivity;
import com.example.chen.kechensj.activity.PersonSetActivity;
import com.example.chen.kechensj.application.MyApplication;
import com.example.chen.kechensj.bean.Address;
import com.example.chen.kechensj.bean.UserInfor;
import com.example.chen.kechensj.util.LogUtil;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.vondear.rxtools.RxSPTool;
import com.vondear.rxtools.view.RxToast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * Created by chen on 2018/1/31.
 */

public class PersonalView extends Fragment implements View.OnClickListener {

    @BindView(R.id.person_name)
    TextView setName;
    @BindView(R.id.person_uid)
    TextView setUid;
    @BindView(R.id.person_set)
    ImageView toSet;
    @BindView(R.id.person_indent)
    TextView toOrder;
    @BindView(R.id.person_collect)
    TextView toCollect;
    @BindView(R.id.person_address)
    TextView toAddress;
    @BindView(R.id.person_history)
    TextView toHistory;
    MyApplication myApplication = new MyApplication();
    String ipAddress=myApplication.getIpAddress();
    private View view;
    private Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.view_personal, null);
        unbinder = ButterKnife.bind(this, view);
        setSpAddress();
        getUserInfor();
        initListener();
        return view;
    }

    private void initListener() {
        toSet.setOnClickListener(this);
        toOrder.setOnClickListener(this);
        toAddress.setOnClickListener(this);
        toCollect.setOnClickListener(this);
        toHistory.setOnClickListener(this);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    public void getUserInfor() {
        Gson gson = new Gson();
        UserInfor userInfor = gson.fromJson(RxSPTool.getString(getActivity(), "userInfor"), UserInfor.class);
        if (userInfor != null) {
            if (userInfor.getData().getUser_account() != null) {
                setName.setText(userInfor.getData().getUser_account());
                setUid.setText(String.valueOf(userInfor.getData().getUser_id()));
            }
        }
    }

    private void setSpAddress() {
        final Gson gson = new Gson();
        UserInfor userInfor = gson.fromJson(RxSPTool.getString(getActivity(), "userInfor"), UserInfor.class);
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
                        RxSPTool.putString(getActivity(), "address",
                                result);
                    }

                    @Override
                    public void onError(Response<String> response) {
                        RxToast.warning("连接失败，请检查网络！");
                    }
                });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.person_set:
                Intent intent = new Intent(getActivity(), PersonSetActivity.class);
                startActivity(intent);
                break;
            case R.id.person_indent:
                Intent intent1 = new Intent(getActivity(), MyOrderActivity.class);
                startActivity(intent1);
                break;
            case R.id.person_history:
                Intent intent2 = new Intent(getActivity(), HistoryActivity.class);
                startActivity(intent2);
                break;
            case R.id.person_address:
                Intent intent3 = new Intent(getActivity(), AddressActivity.class);
                startActivity(intent3);
                break;
            case R.id.person_collect:
                Intent intent4 = new Intent(getActivity(), CollectActivity.class);
                startActivity(intent4);
                break;
            default:
                break;
        }
    }


}
