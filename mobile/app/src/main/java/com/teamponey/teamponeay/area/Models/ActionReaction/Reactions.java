package com.teamponey.teamponeay.area.Models.ActionReaction;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Reactions {
    @SerializedName("reactions")
    @Expose
    private List<Item> reactions = null;

    public List<Item> getReactions() {
        return reactions;
    }

    public void setReactions(List<Item> reactions) {
        this.reactions = reactions;
    }
}
