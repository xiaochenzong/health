package com.example.health.adapter;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.health.R;
import com.example.health.mode.ShopData;

import java.util.ArrayList;


public class ShoppingAdapter extends RecyclerView.Adapter<ShoppingAdapter.ViewHolder> {

    private ArrayList<ShopData> mDataList;
    private Context mContext;

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView goodsName;
        TextView goodsPrice;
        ViewPager viewPager;
        LinearLayout linearLayout;
        public ViewHolder(View view) {
            super(view);
            goodsName = view.findViewById(R.id.tvName);
            goodsPrice = view.findViewById(R.id.tvPrice);
            viewPager = view.findViewById(R.id.viewPager);
            linearLayout = view.findViewById(R.id.llShopping);
        }

    }

    public ShoppingAdapter(ArrayList<ShopData> dataList, Context context) {
        mDataList = dataList;
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.shopping_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        final ShopData data = mDataList.get(position);
        holder.goodsName.setText(data.name);
        holder.goodsPrice.setText(data.price);
        MyPagerAdapter pagerAdapter = new MyPagerAdapter(data.ivList, mContext);
        holder.viewPager.setAdapter(pagerAdapter);
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (shoppingListener!=null){
                    shoppingListener.shoppingClick(data);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    private ShoppingListener shoppingListener;

    public void setShoppingListener(ShoppingListener listener) {
        shoppingListener = listener;
    }

    public interface ShoppingListener {
        void shoppingClick(ShopData data);
    }
}