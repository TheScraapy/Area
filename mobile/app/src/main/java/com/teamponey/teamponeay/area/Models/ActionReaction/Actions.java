package com.teamponey.teamponeay.area.Models.ActionReaction;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Actions {

    @SerializedName("actions")
    @Expose
    private List<Item> actions = null;

    public List<Item> getActions() {
        return actions;
    }

    public void setActions(List<Item> actions) {
        this.actions = actions;
    }
}
