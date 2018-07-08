package com.example.chen.kechensj.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.chen.kechensj.R;
import com.example.chen.kechensj.adapter.OrderRecyclerAdapter;
import com.example.chen.kechensj.adapter.ShopCartAdapter;
import com.example.chen.kechensj.application.MyApplication;
import com.example.chen.kechensj.bean.Address;
import com.example.chen.kechensj.bean.MyOrderBean;
import com.example.chen.kechensj.bean.OrderBean;
import com.example.chen.kechensj.bean.ShopCart;
import com.example.chen.kechensj.bean.ShopCartBean;
import com.example.chen.kechensj.bean.UserInfor;
import com.example.chen.kechensj.util.LogUtil;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.vondear.rxtools.RxSPTool;
import com.vondear.rxtools.view.RxToast;
import com.vondear.rxtools.view.dialog.RxDialogLoading;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OrderActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.check_address)
    RelativeLayout checkAddress;
    @BindView(R.id.tijiao)
    TextView submit;
    @BindView(R.id.recycler)
    RecyclerView recyclerView;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.phonenumber)
    TextView phoneNumber;
    @BindView(R.id.allprice)
    TextView allprice;
    @BindView(R.id.address)
    TextView taddress;
    MyApplication myApplication = new MyApplication();
    String ipAddress = myApplication.getIpAddress();
    private List<OrderBean.CommodityBean> mAllOrderList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        ButterKnife.bind(this);
        initDate();
        initView();
        initListener();
    }

    private void initListener() {
        back.setOnClickListener(this);
        submit.setOnClickListener(this);
    }

    private void initView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        OrderRecyclerAdapter orderRecyclerAdapter = new OrderRecyclerAdapter(this, mAllOrderList);
        recyclerView.setAdapter(orderRecyclerAdapter);
    }

    private void initDate() {
        mAllOrderList.clear();
        Gson gson = new Gson();
        allprice.setText(getIntent().getStringExtra("allprice"));
        if (RxSPTool.getString(this, "address")!=null){
            Address address = gson.fromJson(RxSPTool.getString(this, "address"), Address.class);
            ShopCart shopCart = gson.fromJson(RxSPTool.getString(this, "shopCart"), ShopCart.class);
            MyOrderBean myOrderBean = gson.fromJson(RxSPTool.getString(this, "orderInfor"), MyOrderBean.class);
            name.setText(address.getDate().get(0).getName());
            phoneNumber.setText(address.getDate().get(0).getMobile());
            taddress.setText(address.getDate().get(0).getAddress());
            OrderBean.CommodityBean commodityBean = new OrderBean.CommodityBean();
            for (int i = 0; i < myOrderBean.getData().size(); i++) {
                for (int j = 0; j < shopCart.getDate().size(); j++) {
                    if (myOrderBean.getData().get(i).getId().equals(shopCart.getDate().get(j).getCid())) {
                        commodityBean.setShopName(shopCart.getDate().get(j).getShopname());
                        commodityBean.setCount(myOrderBean.getData().get(i).getCount());
                        commodityBean.setId(Integer.valueOf(myOrderBean.getData().get(i).getId()));
                        commodityBean.setDefaultPic(shopCart.getDate().get(j).getImage());
                        commodityBean.setPrice(shopCart.getDate().get(j).getPrice());
                        commodityBean.setProductName(shopCart.getDate().get(j).getCname());
                        commodityBean.setShopId(Integer.valueOf(shopCart.getDate().get(j).getShopid()));
                        commodityBean.setProductId(Integer.valueOf(myOrderBean.getData().get(i).getId()));
                        mAllOrderList.add(commodityBean);
                        commodityBean = new OrderBean.CommodityBean();
                    }
                }
            }
        }else {
            RxToast.error("用户暂无收货地址，请先去填写地址");
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.tijiao:
                submitOrder();
                break;
        }
    }

    private void submitOrder() {
        Gson gson = new Gson();
        UserInfor userInfor = gson.fromJson(RxSPTool.getString(this, "userInfor"), UserInfor.class);
        Address address = gson.fromJson(RxSPTool.getString(this, "address"), Address.class);
        String url = "http://" + ipAddress + "/Kcsj/AddOrderFrom";
        OkGo.<String>post(url)
                .tag(this)
                .params("uid", userInfor.getData().getUser_id())
                .params("cid", RxSPTool.getString(this, "orderInfor"))
                .params("address", address.getDate().get(0).getAddress())
                .isMultipart(false)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(com.lzy.okgo.model.Response<String> response) {
                        Intent intent = new Intent(OrderActivity.this, MyOrderActivity.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onError(com.lzy.okgo.model.Response<String> response) {
                        RxToast.warning("连接失败，请检查网络！");
                    }
                });
    }
}
