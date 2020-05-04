package com.example.health.ui.adapter;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.health.R;
import com.example.health.ui.mode.PurchaseData;
import com.example.health.ui.mode.ShopData;

import java.util.ArrayList;


public class PurchaseAdapter extends RecyclerView.Adapter<PurchaseAdapter.ViewHolder> {

    private ArrayList<PurchaseData> mDataList;
    private Context mContext;

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView goodsName;
        TextView goodsPrice;
        android.support.constraint.ConstraintLayout constraintLayout;

        public ViewHolder(View view) {
            super(view);
            goodsName = view.findViewById(R.id.tvName);
            goodsPrice = view.findViewById(R.id.tvPrice);
            constraintLayout = view.findViewById(R.id.clPurchase);
        }

    }

    public PurchaseAdapter(ArrayList<PurchaseData> dataList, Context context) {
        mDataList = dataList;
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.purchase_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        final PurchaseData data = mDataList.get(position);
        holder.goodsName.setText(data.name);
        holder.goodsPrice.setText(data.price);
        if (data.getISselect()) {
            holder.constraintLayout.setSelected(true);
        }
        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                data.setISselect(true);
                notifyDataSetChanged();
                if (purchaseListener != null) {
                    purchaseListener.shoppingClick(data);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    private PurchaseListener purchaseListener;

    public void setPurchaseListener(PurchaseListener listener) {
        purchaseListener = listener;
    }

    public interface PurchaseListener {
        void shoppingClick(PurchaseData data);
    }
}