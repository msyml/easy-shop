package com.example.chen.kechensj.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.chen.kechensj.R;
import com.example.chen.kechensj.application.MyApplication;
import com.example.chen.kechensj.view.SearchView;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.vondear.rxtools.RxSPTool;
import com.vondear.rxtools.view.RxToast;

import butterknife.BindView;
import butterknife.ButterKnife;
import scut.carson_ho.searchview.ICallBack;
import scut.carson_ho.searchview.bCallBack;


public class SearchActivity extends AppCompatActivity {

    @BindView(R.id.search_view)
    SearchView searchView;
    MyApplication myApplication = new MyApplication();
    String ipAddress = myApplication.getIpAddress();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        initListener();
    }

    private void initListener() {
        searchView.setOnClickSearch(new ICallBack() {
            @Override
            public void SearchAciton(String string) {
                search(string);
            }
        });

        searchView.setOnClickBack(new bCallBack() {
            @Override
            public void BackAciton() {
                finish();
            }
        });
    }

    private void search(final String keyword) {
        final String url = "http://" + ipAddress + "/Kcsj/QueryCommodity";
        OkGo.<String>post(url)
                .tag(this)
                .params("name", keyword)
                .isMultipart(false)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(com.lzy.okgo.model.Response<String> response) {
                        String result = response.body();
                        RxSPTool.putString(SearchActivity.this, "keyCommoDity",
                                result);
                        Intent intent = new Intent(SearchActivity.this,CommodityListActivity.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onError(com.lzy.okgo.model.Response<String> response) {
                        RxToast.warning("连接失败，请检查网络！");
                    }
                });
    }
}
