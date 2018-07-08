package com.example.chen.kechensj.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.chen.kechensj.R;
import com.example.chen.kechensj.activity.AddAddressActivity;
import com.example.chen.kechensj.activity.AddressActivity;
import com.example.chen.kechensj.application.MyApplication;
import com.example.chen.kechensj.bean.Address;
import com.example.chen.kechensj.bean.MyAddress;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.vondear.rxtools.RxActivityTool;
import com.vondear.rxtools.view.RxToast;

import java.util.List;

public class AddressAdapter extends BaseAdapter {

    private List<MyAddress> mList;
    private LayoutInflater inflater;
    MyApplication myApplication=new MyApplication();
    String ipAddress=myApplication.getIpAddress();

    public AddressAdapter(List<MyAddress> mList, Context context) {
        this.mList = mList;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return null == mList.get(position) ? 0 : mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (viewHolder == null) {
            convertView = inflater.inflate(R.layout.item_address, null);
            viewHolder = new ViewHolder();
            viewHolder.delBtn = convertView.findViewById(R.id.listDelete);
            viewHolder.EditBtn = convertView.findViewById(R.id.listUpdate);
            viewHolder.name = convertView.findViewById(R.id.name);
            viewHolder.phone = convertView.findViewById(R.id.number);
            viewHolder.address = convertView.findViewById(R.id.address);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final MyAddress address = mList.get(position);
        viewHolder.name.setText(address.getName());
        viewHolder.phone.setText(address.getMobile());
        viewHolder.address.setText(address.getAddress());
        viewHolder.delBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i=0;i<mList.size();i++){
                    if (mList.get(i).getAid()==address.getAid()){
                        mList.remove(i);
                    }
                }
                DelAddress(String.valueOf(address.getAid()));
            }
        });
        viewHolder.EditBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(inflater.getContext(), AddAddressActivity.class);
                intent.putExtra("action","update");
                intent.putExtra("aid",String.valueOf(address.getAid()));
                inflater.getContext().startActivity(intent);
            }
        });
        return convertView;
    }


    private void DelAddress(String aid) {
        String url = "http://" + ipAddress + "/Kcsj/DelAddress";
        OkGo.<String>post(url)
                .tag(this)
                .params("aid", aid)
                .isMultipart(false)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {

                    }

                    @Override
                    public void onError(Response<String> response) {
                        RxToast.warning("连接失败，请检查网络！");
                    }
                });
    }


    public class ViewHolder {
        private TextView name;
        private TextView phone;
        private TextView address;
        private LinearLayout delBtn;
        private LinearLayout EditBtn;
    }
}
