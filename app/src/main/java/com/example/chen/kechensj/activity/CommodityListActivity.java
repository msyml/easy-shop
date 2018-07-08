package com.example.chen.kechensj.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.LinearLayout;

import com.example.chen.kechensj.R;
import com.example.chen.kechensj.adapter.MyRecycleAdapter;
import com.example.chen.kechensj.bean.Commodity;
import com.example.chen.kechensj.bean.RecycleItem;
import com.google.gson.Gson;
import com.vondear.rxtools.RxSPTool;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CommodityListActivity extends AppCompatActivity implements View.OnClickListener {
    @BindView(R.id.recycler)
    RecyclerView recyclerView;
    @BindView(R.id.tosearch)
    LinearLayout toSearch;
    private List<RecycleItem> mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commodity_list);
        ButterKnife.bind(this);
        CommodityInfor();
        initListener();
    }

    private void CommodityInfor() {
        Gson gson = new Gson();
        Commodity commodity = gson.fromJson(RxSPTool.getString(this, "keyCommoDity"), Commodity.class);
        mData = new ArrayList<>();
        for (int i = 0; i < commodity.getDate().size(); i++) {
            mData.add(new RecycleItem(String.valueOf(commodity.getDate().get(i).getPid()),
                    commodity.getDate().get(i).getPimage(),
                    commodity.getDate().get(i).getPname(),
                    String.valueOf(commodity.getDate().get(i).getPrice()),
                    String.valueOf(commodity.getDate().get(i).getSale_number())));
        }
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tosearch:
                Intent intent = new Intent(this, SearchActivity.class);
                startActivity(intent);
                break;
        }
    }
}
