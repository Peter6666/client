package com.logic.client.bean;

import java.io.Serializable;

/**
 * @author logic.    Email:2778500267@qq.com
 * @data 2018/5/2
 * @desc
 */

public class NewsChannelTabs implements Serializable{

    public static final int CURRENT_CHANNEL=0;
    public static final int ADD_CHANNEL=1;
    private String name;
    private boolean isSelect;
    private int index;
    private boolean isFixed;
    private int type;

    public NewsChannelTabs() {
    }

    public NewsChannelTabs(String name, boolean isSelect, int index, boolean isFixed,int type) {
        this.name = name;
        this.isSelect = isSelect;
        this.index = index;
        this.isFixed = isFixed;
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public boolean isFixed() {
        return isFixed;
    }

    public void setFixed(boolean fixed) {
        isFixed = fixed;
    }
}
