package com.radya.sfa.view.settings;

import android.widget.ImageView;

import androidx.databinding.BaseObservable;
import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.radya.sfa.view.sidemenu.SideMenu;

/**
 * Created by aderifaldi on 2018-03-22.
 */

public class SettingsMenuDataBinding extends BaseObservable {

    private SettingsMenu data;

    public SettingsMenuDataBinding(SettingsMenu data) {
        this.data = data;
    }

    public int getId() {
        return data.getId();
    }

    public String getMenuName() {
        return data.getMenuName();
    }

    @BindingAdapter("imageUrl")
    public static void loadImage(ImageView imageView, int id) {
        Glide.with(imageView.getContext()).load(id).into(imageView);
    }

}
