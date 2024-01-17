package com.radya.sfa.view.dashboard.news;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.viewpager.widget.PagerAdapter;

import com.radya.sfa.R;

import java.util.List;

/**
 * Created by RadyaLabs PC on 05/06/2017.
 */

public class DashboardNewsAdapter extends PagerAdapter {

    private List<DashboardNews.NewsData> data;
    private Activity context;

    public DashboardNewsAdapter(Activity context, List<DashboardNews.NewsData> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }


    @Override
    public Object instantiateItem(final ViewGroup container, final int position) {

        final Holder holder = new Holder();
        final DashboardNews.NewsData item = data.get(position);

        if (holder.itemView == null) {
            holder.mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            holder.itemView = holder.mLayoutInflater.inflate(R.layout.dashboard_news_item, container, false);
        }

        holder.txtNewsTitle = holder.itemView.findViewById(R.id.txtNewsTitle);
        holder.txtNewsBody = holder.itemView.findViewById(R.id.txtNewsBody);

        holder.txtNewsTitle.setText(item.getTitle());
        holder.txtNewsBody.setText(item.getBody());

        container.addView(holder.itemView);
        return holder.itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((RelativeLayout) object);
    }

    static class Holder {
        View itemView;
        LayoutInflater mLayoutInflater;
        TextView txtNewsTitle;
        TextView txtNewsBody;
    }

}