package com.radya.sfa.view.settings;

import com.google.gson.annotations.SerializedName;

/**
 * Created by aderifaldi on 2018-03-22.
 */

public class SettingsMenu {

    @SerializedName("id")
    private int id;
    @SerializedName("menu_name")
    private String menuName;

    public SettingsMenu(int id, String menuName) {
        this.id = id;
        this.menuName = menuName;
    }

    public int getId() {
        return id;
    }

    public String getMenuName() {
        return menuName;
    }

}
