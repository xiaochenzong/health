package com.example.health.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.health.R;

import java.util.ArrayList;

public class MyPagerAdapter extends PagerAdapter {
    private ArrayList<Integer> viewLists;

    private Context mContex;

    public MyPagerAdapter(ArrayList<Integer> viewLists, Context context) {
        super();
        this.viewLists = viewLists;
        this.mContex = context;
    }

    @Override
    public int getCount() {
        return viewLists.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.view_pager_item, container, false);
        ImageView imageView = view.findViewById(R.id.ivGoods);
        imageView.setImageResource(viewLists.get(position));
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}