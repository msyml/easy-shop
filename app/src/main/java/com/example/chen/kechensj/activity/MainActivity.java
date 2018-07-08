package com.example.chen.kechensj.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.RadioGroup;

import com.example.chen.kechensj.R;
import com.example.chen.kechensj.fragment.HomeView;
import com.example.chen.kechensj.fragment.PersonalView;
import com.example.chen.kechensj.fragment.ShopCartView;


public class MainActivity extends FragmentActivity implements RadioGroup.OnCheckedChangeListener {

    private RadioGroup radioGroup;
    private HomeView homeView;
    private ShopCartView shoppingCarView;
    private PersonalView personalView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initListener();
        initFragment();
    }


    private void initListener() {
        radioGroup.setOnCheckedChangeListener(this);
    }

    private void initView() {
        radioGroup = findViewById(R.id.main_radiogroup);
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        switch (i) {
            case R.id.main_rbtn_home:
                hideAllFragment();
                showFragment(homeView);
                break;
            case R.id.main_rbtn_shoppingcar:
                hideAllFragment();
                showFragment(shoppingCarView);
                break;
            case R.id.main_rbtn_personal:
                hideAllFragment();
                showFragment(personalView);
                break;
            default:
                break;
        }
    }

    private void hideAllFragment() {
        hideFragment(homeView);
        hideFragment(shoppingCarView);
        hideFragment(personalView);

    }

    //初始化fragment
    private void initFragment() {
        homeView = new HomeView();
        shoppingCarView = new ShopCartView();
        personalView = new PersonalView();

        addFragment(homeView);
        addFragment(shoppingCarView);
        addFragment(personalView);

        hideFragment(homeView);
        hideFragment(shoppingCarView);
        hideFragment(personalView);

        showFragment(homeView);
    }

    //创建视图
    private void addFragment(Fragment fragment) {
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.main_frame, fragment);
        fragmentTransaction.commit();
    }

    //显示和隐藏视图（动态）
    private void hideFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().hide(fragment).commit();
    }

    private void showFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().show(fragment).commit();
    }
}
