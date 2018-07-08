package com.example.chen.kechensj.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.example.chen.kechensj.R;
import com.example.chen.kechensj.activity.SearchActivity;
import com.example.chen.kechensj.adapter.MyRecycleAdapter;
import com.example.chen.kechensj.bean.Commodity;
import com.example.chen.kechensj.bean.RecycleItem;
import com.example.chen.kechensj.view.NestedScrollView;
import com.google.gson.Gson;
import com.vondear.rxtools.RxSPTool;
import com.vondear.rxtools.view.RxToast;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * Created by chen on 2018/1/31.
 */

public class HomeView extends Fragment implements OnBannerListener, View.OnClickListener {

    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.recycler)
    RecyclerView recyclerView;
    @BindView(R.id.scroll)
    NestedScrollView scrollView;
    @BindView(R.id.tosearch)
    LinearLayout toSearch;
    private View view;
    private Unbinder unbinder;
    private ArrayList<String> list_path;
    private List<RecycleItem> mData;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.view_home, null);
        unbinder = ButterKnife.bind(this, view);
        initDate();
        initListener();
        return view;
    }

    private void initDate() {
        CommodityInfor();
        addBanner();
    }

    private void CommodityInfor() {
        Gson gson = new Gson();
        Commodity commodity = gson.fromJson(RxSPTool.getString(getActivity(), "commoDity"), Commodity.class);
        mData = new ArrayList<>();
        for (int i = 0; i < commodity.getDate().size(); i++) {
            mData.add(new RecycleItem(String.valueOf(commodity.getDate().get(i).getPid()),
                    commodity.getDate().get(i).getPimage(),
                    commodity.getDate().get(i).getPname(),
                    String.valueOf(commodity.getDate().get(i).getPrice()),
                    String.valueOf(commodity.getDate().get(i).getSale_number())));
        }
    }

    private void addBanner() {
        //放图片地址的集合
        list_path = new ArrayList<>();
        list_path.add("https://img.alicdn.com/tfs/TB1McFxqvuSBuNkHFqDXXXfhVXa-520-280.jpg_q90_.webp");
        list_path.add("https://img.alicdn.com/simba/img/TB1zU5nx4SYBuNjSsphSuvGvVXa.jpg");
        list_path.add("https://img.alicdn.com/simba/img/TB1kANBgTCWBKNjSZFtSuuC3FXa.jpg");
        list_path.add("https://img.alicdn.com/tfs/TB1pAG0yN9YBuNjy0FfXXXIsVXa-520-280.png_q90_.webp");
        //设置内置样式，共有六种可以点入方法内逐一体验使用。
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        //设置图片加载器，图片加载器在下方
        banner.setImageLoader(new MyLoader());
        //设置图片网址或地址的集合
        banner.setImages(list_path);
        //设置轮播的动画效果，内含多种特效，可点入方法内查找后内逐一体验
        banner.setBannerAnimation(Transformer.Default);
        //设置轮播间隔时间
        banner.setDelayTime(3000);
        //设置是否为自动轮播，默认是“是”。
        banner.isAutoPlay(true);
        //设置指示器的位置，小点点，左中右。
        banner.setIndicatorGravity(BannerConfig.CENTER)
                //以上内容都可写成链式布局，这是轮播图的监听。比较重要。方法在下面。
                .setOnBannerListener(this)
                //必须最后调用的方法，启动轮播图。
                .start();

    }

    private void initListener() {
        toSearch.setOnClickListener(this);
        recyclerView.setNestedScrollingEnabled(false);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        MyRecycleAdapter myRecycleAdapter = new MyRecycleAdapter(mData);
        recyclerView.setAdapter(myRecycleAdapter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @Override
    public void OnBannerClick(int position) {
        RxToast.normal("点击了第" + position + 1 + "张图片");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tosearch:
                Intent intent = new Intent(getActivity(), SearchActivity.class);
                startActivity(intent);
                break;
        }
    }

    private class MyLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            Glide.with(context).load((String) path).into(imageView);
        }
    }
}
