package com.radya.sfa.view.sidemenu;

import com.google.gson.annotations.SerializedName;

/**
 * Created by aderifaldi on 2018-03-22.
 */

public class SideMenu {

    @SerializedName("id")
    private int id;
    @SerializedName("menu_name")
    private String menuName;
    @SerializedName("is_selected")
    private boolean isSelected;

    public SideMenu(int id, String menuName, boolean isSelected) {
        this.id = id;
        this.menuName = menuName;
        this.isSelected = isSelected;
    }

    public int getId() {
        return id;
    }

    public String getMenuName() {
        return menuName;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
