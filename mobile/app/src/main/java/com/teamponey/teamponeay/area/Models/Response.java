package com.teamponey.teamponeay.area.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.teamponey.teamponeay.area.Models.ActionReaction.Widget;

import java.util.List;

public class Response {
    @SerializedName("widgets")
    @Expose
    private List<Widget> data = null;
    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("token")
    @Expose
    private String token;

    public List<Widget> getData() {
        return data;
    }

    public void setData(List<Widget> data) {
        this.data = data;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
