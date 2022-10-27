package com.radya.sfa.view.sidemenu;

import android.databinding.BaseObservable;
import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * Created by aderifaldi on 2018-03-22.
 */

public class SideMenuDataBinding extends BaseObservable {

    private SideMenu data;

    public SideMenuDataBinding(SideMenu data) {
        this.data = data;
    }

    public int getId() {
        return data.getId();
    }

    public String getMenuName() {
        return data.getMenuName();
    }

    public boolean isSelected() {
        return data.isSelected();
    }

    @BindingAdapter("imageUrl")
    public static void loadImage(ImageView imageView, int id) {
        Glide.with(imageView.getContext()).load(id).into(imageView);
    }

}
