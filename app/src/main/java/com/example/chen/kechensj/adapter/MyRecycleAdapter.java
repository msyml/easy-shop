package com.example.chen.kechensj.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.chen.kechensj.R;
import com.example.chen.kechensj.application.MyApplication;
import com.example.chen.kechensj.bean.RecycleItem;
import com.example.chen.kechensj.bean.UserInfor;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.vondear.rxtools.RxSPTool;
import com.vondear.rxtools.view.RxToast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyRecycleAdapter extends RecyclerView.Adapter<MyRecycleAdapter.ViewHolder> {
    MyApplication myApplication = new MyApplication();
    String ipAddress = myApplication.getIpAddress();
    private List<RecycleItem> mList;
    private Context mcontext;
    private List<DateBean> date;

    public MyRecycleAdapter(List<RecycleItem> mList) {
        this.mList = mList;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (mcontext == null) {
            mcontext = parent.getContext();
        }
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycle,
                parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        final RecycleItem recycleItem = mList.get(position);
        holder.id = recycleItem.getId();
        Glide.with(mcontext).load(recycleItem.getImage()).into(holder.imageView);
        holder.name.setText(recycleItem.getName());
        holder.price.setText("￥" + recycleItem.getPrice());
        holder.number.setText(recycleItem.getNumber() + "人已付款");
        holder.addShopCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Gson gson = new Gson();
                UserInfor userInfor=gson.fromJson(RxSPTool.getString(mcontext, "userInfor")
                        , UserInfor.class);
                String uid=String.valueOf(userInfor.getData().getUser_id());
                String cid=recycleItem.getId();
                addShopCart(uid,cid);
            }
        });
    }

    private void addShopCart(String uid, String cid) {
        String url = "http://" + ipAddress + "/Kcsj/AddShopCart";
        OkGo.<String>post(url)
                .tag(this)
                .params("uid", uid)
                .params("cid", cid)
                .params("count", "1")
                .isMultipart(false)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        String result=response.body();
                        if (result.equals("false")){
                            RxToast.error("添加购物车失败，请稍后再试！");
                        }else if (result.equals("true")){
                            RxToast.normal("宝贝已在购物车等待！");
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        RxToast.warning("连接失败，请检查网络！");
                    }
                });
    }

    @Override
    public int getItemCount() {
        return null == mList ? 0 : mList.size();
    }

    public List<DateBean> getDate() {
        return date;
    }

    public void setDate(List<DateBean> date) {
        this.date = date;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public RecycleItem mItem;
        public String id;
        @BindView(R.id.imageView)
        ImageView imageView;
        @BindView(R.id.name)
        TextView name;
        @BindView(R.id.price)
        TextView price;
        @BindView(R.id.number)
        TextView number;
        @BindView(R.id.addshopcar)
        ImageView addShopCar;


        public ViewHolder(final View itemView) {
            super(itemView);
            mView = itemView;
            ButterKnife.bind(this, itemView);
        }
    }

    public static class DateBean {
        /**
         * cid : 1
         * uid : 1
         * shopid : 1
         * shopname : 日用品店
         * image : https://g-search3.alicdn.com/img/bao/uploaded/i4/i2/2212142507/TB1WIxgcVYM8KJjSZFuXXcf7FXa_!!0-item_pic.jpg_250x250.jpg_.webp
         * cname : 创意家居生活日用品卧室客厅房间装饰品懒人小百货新奇特时尚实用
         * count : 1
         * price : 123.45
         */

        private String cid;
        private String uid;
        private String shopid;
        private String shopname;
        private String image;
        private String cname;
        private String count;
        private String price;

        public String getCid() {
            return cid;
        }

        public void setCid(String cid) {
            this.cid = cid;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getShopid() {
            return shopid;
        }

        public void setShopid(String shopid) {
            this.shopid = shopid;
        }

        public String getShopname() {
            return shopname;
        }

        public void setShopname(String shopname) {
            this.shopname = shopname;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getCname() {
            return cname;
        }

        public void setCname(String cname) {
            this.cname = cname;
        }

        public String getCount() {
            return count;
        }

        public void setCount(String count) {
            this.count = count;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }
    }
}
