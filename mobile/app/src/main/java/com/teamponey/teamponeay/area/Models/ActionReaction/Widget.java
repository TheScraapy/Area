package com.teamponey.teamponeay.area.Models.ActionReaction;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Widget {
    @SerializedName("areaid")
    @Expose
    private Integer areaid;
    @SerializedName("feature")
    @Expose
    private String feature;
    @SerializedName("serviceIn")
    @Expose
    private String serviceIn;
    @SerializedName("serviceOut")
    @Expose
    private String serviceOut;
    @SerializedName("active")
    @Expose
    private Boolean active;

    public Integer getAreaid() {
        return areaid;
    }

    public void setAreaidd(Integer areaid) {
        this.areaid = areaid;
    }

    public String getFeature() {
        return feature;
    }

    public void setFeature(String feature) {
        this.feature = feature;
    }

    public String getServiceIn() {
        return serviceIn;
    }

    public void setServiceIn(String serviceIn) {
        this.serviceIn = serviceIn;
    }

    public String getServiceOut() {
        return serviceOut;
    }

    public void setServiceOut(String serviceOut) {
        this.serviceOut = serviceOut;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
