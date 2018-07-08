package com.example.chen.kechensj.fragment;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.chen.kechensj.R;
import com.example.chen.kechensj.activity.OrderActivity;
import com.example.chen.kechensj.adapter.ShopCartAdapter;
import com.example.chen.kechensj.application.MyApplication;
import com.example.chen.kechensj.bean.EasyOrderBean;
import com.example.chen.kechensj.bean.MyOrderBean;
import com.example.chen.kechensj.bean.ShopCart;
import com.example.chen.kechensj.bean.ShopCartBean;
import com.example.chen.kechensj.bean.UserInfor;
import com.example.chen.kechensj.util.LogUtil;
import com.google.gson.Gson;

import com.google.gson.JsonObject;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.vondear.rxtools.RxActivityTool;
import com.vondear.rxtools.RxSPTool;
import com.vondear.rxtools.view.RxToast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * Created by chen on 2018/1/31.
 */

public class ShopCartView extends Fragment implements View.OnClickListener, ShopCartAdapter.OnResfreshListener, ShopCartAdapter.OnDeleteClickListener, ShopCartAdapter.OnEditClickListener {

    @BindView(R.id.refreshLayout)
    SmartRefreshLayout smartRefreshLayout;
    MyApplication myApplication = new MyApplication();
    String ipAddress = myApplication.getIpAddress();
    private View view;
    private TextView tvShopCartSubmit, tvShopCartSelect, tvShopCartTotalNum;
    private View mEmtryView;
    private RecyclerView rlvShopCart, rlvHotProducts;
    private ShopCartAdapter mShopCartAdapter;
    private LinearLayout llPay;
    private RelativeLayout rlHaveProduct;
    private List<ShopCartBean.CartlistBean> mAllOrderList = new ArrayList<>();
    private ArrayList<ShopCartBean.CartlistBean> mGoPayList = new ArrayList<>();
    private List<String> mHotProductsList = new ArrayList<>();
    private TextView tvShopCartTotalPrice;
    private int mCount, mPosition;
    private float mTotalPrice1;
    private boolean mSelect;
    private Unbinder unbinder;

    public static void isSelectFirst(List<ShopCartBean.CartlistBean> list) {
        if (list.size() > 0) {
            list.get(0).setIsFirst(1);
            for (int i = 1; i < list.size(); i++) {
                if (list.get(i).getShopId() == list.get(i - 1).getShopId()) {
                    list.get(i).setIsFirst(2);
                } else {
                    list.get(i).setIsFirst(1);
                }
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.view_shopcart, null);
        unbinder = ButterKnife.bind(this, view);
        initView();
        initListener();
        mShopCartAdapter.notifyDataSetChanged();

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @Override
    public void onStart() {
        super.onStart();
        getSCInfor();
    }



    private void getSCInfor() {
        Gson gson = new Gson();
        UserInfor userInfor = gson.fromJson(RxSPTool.getString(getActivity(), "userInfor"), UserInfor.class);
        String uid = String.valueOf(userInfor.getData().getUser_id());
        String url = "http://" + ipAddress + "/Kcsj/QueryShopCart";
        OkGo.<String>post(url)
                .tag(this)
                .params("uid", uid)
                .isMultipart(false)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        String result = response.body();
                        RxSPTool.putString(getActivity(), "shopCart",
                                result);
                        Gson gson = new Gson();
                        ShopCart shopCart = gson.fromJson(RxSPTool.getString(getActivity(), "shopCart"), ShopCart.class);
                        ShopCartBean.CartlistBean sb = new ShopCartBean.CartlistBean();
                        for (int i = 0; i < shopCart.getDate().size(); i++) {
                            sb.setShopId(Integer.valueOf(shopCart.getDate().get(i).getShopid()));
                            sb.setPrice(shopCart.getDate().get(i).getPrice());
                            sb.setDefaultPic(shopCart.getDate().get(i).getImage());
                            sb.setProductName(shopCart.getDate().get(i).getCname());
                            sb.setId(Integer.valueOf(shopCart.getDate().get(i).getCid()));
                            sb.setShopName(shopCart.getDate().get(i).getShopname());
                            sb.setColor("");
                            sb.setCount(Integer.valueOf(shopCart.getDate().get(i).getCount()));
                            mAllOrderList.add(sb);
                            sb = new ShopCartBean.CartlistBean();
                        }
                        isSelectFirst(mAllOrderList);
                    }

                    @Override
                    public void onError(Response<String> response) {
                        RxToast.warning("连接失败，请检查网络！");
                    }
                });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            //全选
            case R.id.shopcart_addselect:
                mSelect = !mSelect;
                if (mSelect) {
                    Drawable left = getResources().getDrawable(R.mipmap.shopcart_check_true);
                    tvShopCartSelect.setCompoundDrawablesWithIntrinsicBounds(left, null, null, null);
                    for (int i = 0; i < mAllOrderList.size(); i++) {
                        mAllOrderList.get(i).setSelect(true);
                        mAllOrderList.get(i).setShopSelect(true);
                    }
                } else {
                    Drawable left = getResources().getDrawable(R.mipmap.shopcart_unselected);
                    tvShopCartSelect.setCompoundDrawablesWithIntrinsicBounds(left, null, null, null);
                    for (int i = 0; i < mAllOrderList.size(); i++) {
                        mAllOrderList.get(i).setSelect(false);
                        mAllOrderList.get(i).setShopSelect(false);
                    }
                }
                mShopCartAdapter.notifyDataSetChanged();
                break;
            case R.id.shopcart_submit:
                float mTotalPrice = 0;
                int mTotalNum = 0;
                List<EasyOrderBean> idList = new ArrayList<>();
                EasyOrderBean easyOrderBean = new EasyOrderBean();
                mTotalPrice1 = 0;
                mGoPayList.clear();
                for (int i = 0; i < mAllOrderList.size(); i++) {
                    if (mAllOrderList.get(i).getIsSelect()) {
                        easyOrderBean = new EasyOrderBean();
                        easyOrderBean.setId(String.valueOf(mAllOrderList.get(i).getId()));
                        easyOrderBean.setCount(mAllOrderList.get(i).getCount());
                        idList.add(easyOrderBean);
                        mTotalPrice += Float.parseFloat(mAllOrderList.get(i).getPrice()) * mAllOrderList.get(i).getCount();
                        mTotalNum += 1;
                        mGoPayList.add(mAllOrderList.get(i));
                    }
                }
                mTotalPrice1 = mTotalPrice;
                Gson gson = new Gson();
                String sb = "{\"data\":" + gson.toJson(idList) + "}";
                RxSPTool.putString(getActivity(), "orderInfor", sb);
                Intent intent = new Intent(getActivity(), OrderActivity.class);
                intent.putExtra("allprice", String.valueOf(mTotalPrice));
                startActivity(intent);
                break;
            default:
                break;
        }
    }


    private void initListener() {
        tvShopCartSelect.setOnClickListener(this);
        tvShopCartSubmit.setOnClickListener(this);
        mShopCartAdapter.setResfreshListener(this);
        mShopCartAdapter.setOnEditClickListener(this);
        mShopCartAdapter.setOnDeleteClickListener(this);
    }

    private void Shuaxin() {

        mAllOrderList.clear();
        getSCInfor();
        smartRefreshLayout.finishRefresh(2000);
        RxToast.normal("数据已更新");

    }

    private void initView() {

        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mAllOrderList.clear();
                getSCInfor();
                smartRefreshLayout.finishRefresh(2000);
                RxToast.normal("数据已更新");
            }
        });
        smartRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                smartRefreshLayout.finishLoadMore(2000);
            }
        });

        tvShopCartSelect = view.findViewById(R.id.shopcart_addselect);
        tvShopCartTotalPrice = view.findViewById(R.id.shopcart_totalprice);
        tvShopCartTotalNum = view.findViewById(R.id.shopcart_totalnum);
        rlHaveProduct = view.findViewById(R.id.shopcart_have);
        rlvShopCart = view.findViewById(R.id.shopcart_recycler);
        mEmtryView = view.findViewById(R.id.shopcart_dw_view);
        tvShopCartSubmit = view.findViewById(R.id.shopcart_submit);
        llPay = view.findViewById(R.id.ll_pay);

        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.FILL_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        lp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
        llPay.setLayoutParams(lp);

        mEmtryView.setVisibility(View.GONE);
        rlvShopCart.setLayoutManager(new LinearLayoutManager(getActivity()));
        mShopCartAdapter = new ShopCartAdapter(getActivity(), mAllOrderList);
        rlvShopCart.setAdapter(mShopCartAdapter);
    }


    //实时监控全选按钮
    @Override
    public void onResfresh(boolean isSelect) {
        mSelect = isSelect;
        if (isSelect) {
            Drawable left = getResources().getDrawable(R.mipmap.shopcart_check_true);
            tvShopCartSelect.setCompoundDrawablesWithIntrinsicBounds(left, null, null, null);
        } else {
            Drawable left = getResources().getDrawable(R.mipmap.shopcart_unselected);
            tvShopCartSelect.setCompoundDrawablesWithIntrinsicBounds(left, null, null, null);
        }
        float mTotalPrice = 0;
        int mTotalNum = 0;
        mTotalPrice1 = 0;
        mGoPayList.clear();
        for (int i = 0; i < mAllOrderList.size(); i++)
            if (mAllOrderList.get(i).getIsSelect()) {
                mTotalPrice += Float.parseFloat(mAllOrderList.get(i).getPrice()) * mAllOrderList.get(i).getCount();
                mTotalNum += 1;
                mGoPayList.add(mAllOrderList.get(i));
            }
        mTotalPrice1 = mTotalPrice;
        tvShopCartTotalPrice.setText("总价：" + mTotalPrice);
        tvShopCartTotalNum.setText("共" + mTotalNum + "种商品");
    }

    //删除商品接口
    @Override
    public void onDeleteClick(View view, int position, int cartid) {
        mShopCartAdapter.notifyDataSetChanged();
        DelShopCart(String.valueOf(cartid));
    }

    private void DelShopCart(String cid) {
        Gson gson = new Gson();
        UserInfor userInfor = gson.fromJson(RxSPTool.getString(getActivity(), "userInfor"), UserInfor.class);
        String uid = String.valueOf(userInfor.getData().getUser_id());
        String url = "http://" + ipAddress + "/Kcsj/DelShopCart";
        OkGo.<String>post(url)
                .tag(this)
                .params("uid", uid)
                .params("cid", cid)
                .isMultipart(false)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        RxToast.normal("数据已删除");
                    }

                    @Override
                    public void onError(Response<String> response) {
                        RxToast.warning("连接失败，请检查网络！");
                    }
                });
    }

    //修改数量接口
    @Override
    public void onEditClick(int position, int cartid, int count) {
        mCount = count;
        mPosition = position;
    }


}
