package com.teamponey.teamponeay.area.Models.ActionReaction;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CreationWidget {
    @SerializedName("serviceOut")
    @Expose
    private Integer serviceOut;
    @SerializedName("serviceIn")
    @Expose
    private Integer serviceIn;
    @SerializedName("action")
    @Expose
    private int action;
    @SerializedName("reaction")
    @Expose
    private int reaction;
    @SerializedName("actionData")
    @Expose
    private String actionData;
    @SerializedName("reactionData")
    @Expose
    private String reactionData;
    @SerializedName("active")
    @Expose
    private Boolean active;

    public int getAction() {
        return action;
    }

    public void setActionId(int action) {
        this.action = action;
    }

    public int getReactionId() {
        return reaction;
    }

    public void setReactionId(int reactionId) {
        this.reaction = reactionId;
    }

    public String getActionData() {
        return actionData;
    }

    public void setActionData(String actionData) {
        this.actionData = actionData;
    }

    public String getReactionData() {
        return reactionData;
    }

    public void setReactionData(String reactionData) {
        this.reactionData = reactionData;
    }

    public Integer getServiceOut() {
        return serviceOut;
    }

    public void setServiceOut(Integer serviceOut) {
        this.serviceOut = serviceOut;
    }

    public Integer getServiceIn() {
        return serviceIn;
    }

    public void setServiceIn(Integer serviceIn) {
        this.serviceIn = serviceIn;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
