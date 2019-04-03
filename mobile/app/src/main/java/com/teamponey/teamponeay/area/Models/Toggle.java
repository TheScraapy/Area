package com.teamponey.teamponeay.area.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Toggle {
    @SerializedName("areaid")
    @Expose
    private Integer areaid;
    @SerializedName("state")
    @Expose
    private Boolean state;

    public Integer getAreaid() {
        return areaid;
    }

    public void setAreaid(Integer areaid) {
        this.areaid = areaid;
    }

    public Boolean getState() {
        return state;
    }

    public void setState(Boolean state) {
        this.state = state;
    }
}
