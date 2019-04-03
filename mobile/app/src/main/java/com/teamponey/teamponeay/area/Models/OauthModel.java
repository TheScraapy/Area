package com.teamponey.teamponeay.area.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OauthModel {
    @SerializedName("access_token")
    @Expose
    public String access_token;

}