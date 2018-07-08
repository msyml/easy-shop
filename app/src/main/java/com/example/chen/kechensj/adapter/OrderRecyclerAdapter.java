package com.example.chen.kechensj.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.chen.kechensj.R;
import com.example.chen.kechensj.bean.OrderBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OrderRecyclerAdapter extends RecyclerView.Adapter<OrderRecyclerAdapter.MyViewHolder> {

    private Context context;
    private List<OrderBean.CommodityBean> data;
    private View heanView;

    public OrderRecyclerAdapter(Context context, List<OrderBean.CommodityBean> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(context).inflate(R.layout.item_order, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Glide.with(context).load(data.get(position).getDefaultPic()).into(holder.cImage);
        if (position > 0) {
            if (data.get(position).getShopId() == data.get(position - 1).getShopId()) {
                holder.headView.setVisibility(View.GONE);
            } else {
                holder.headView.setVisibility(View.VISIBLE);
            }
        } else {
            holder.headView.setVisibility(View.VISIBLE);
        }
        holder.cName.setText(data.get(position).getProductName());
        holder.cNumber.setText("x" + data.get(position).getCount());
        holder.cPrice.setText("￥" + String.valueOf(data.get(position).getPrice()));
        holder.shopName.setText(data.get(position).getShopName());
        holder.shopid = data.get(position).getShopId();
        holder.cid = data.get(position).getId();
    }

    @Override
    public int getItemCount() {
        int count = (data == null ? 0 : data.size());
        if (heanView != null) {
            count++;
        }
        return count;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        public View mview;
        int shopid;
        int cid;
        @BindView(R.id.item_shopcart_shopname)
        TextView shopName;
        @BindView(R.id.commodity_image)
        ImageView cImage;
        @BindView(R.id.commodity_name)
        TextView cName;
        @BindView(R.id.commodity_number)
        TextView cNumber;
        @BindView(R.id.commodity_price)
        TextView cPrice;
        @BindView(R.id.item_shopcart_header)
        LinearLayout headView;

        public MyViewHolder(View view) {
            super(view);
            mview = view;
            ButterKnife.bind(this, itemView);
        }
    }

}

