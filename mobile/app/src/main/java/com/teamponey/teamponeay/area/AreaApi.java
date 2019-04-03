package com.teamponey.teamponeay.area;

import com.teamponey.teamponeay.area.Models.BasicResponse;
import com.teamponey.teamponeay.area.Models.ActionReaction.Actions;
import com.teamponey.teamponeay.area.Models.ActionReaction.CreationWidget;
import com.teamponey.teamponeay.area.Models.ActionReaction.Reactions;
import com.teamponey.teamponeay.area.Models.ActionReaction.Widget;
import com.teamponey.teamponeay.area.Models.Auth;
import com.teamponey.teamponeay.area.Models.OauthModel;
import com.teamponey.teamponeay.area.Models.Response;
import com.teamponey.teamponeay.area.Models.Services.Services;
import com.teamponey.teamponeay.area.Models.Toggle;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.POST;
import retrofit.http.Path;

public interface AreaApi {

    @POST("/auth/register")
    void register(
            @Body Auth auth,
            Callback<Response> cb
    );

    @POST("/auth/google")
    void google(
            @Body OauthModel auth,
            Callback<Response> cb
    );

    @POST("/auth/4")
    void gmail(
            @Header("Authorization") String auth,
            @Body OauthModel token,
            Callback<BasicResponse> cb
    );

    @POST("/auth/3")
    void clock(
            @Header("Authorization") String auth,
            @Body OauthModel token,
            Callback<BasicResponse> cb
    );

    @POST("/auth/2")
    void twitter(
            @Header("Authorization") String auth,
            @Body OauthModel token,
            Callback<BasicResponse> cb
    );

    @POST("/auth/1")
    void facebook(
            @Header("Authorization") String auth,
            @Body OauthModel token,
            Callback<BasicResponse> cb
    );

    @POST("/auth/0")
    void youtube(
            @Header("Authorization") String auth,
            @Body OauthModel token,
            Callback<BasicResponse> cb
    );

    @POST("/auth/login")
    void login(
            @Body Auth auth,
            Callback<Response> cb
    );

    @GET("/widgets/all")
    void getWidgets(
            @Header("Authorization") String auth,
            Callback<Response> cb
    );

    @GET("/widgets/services")
    void getServices(
            @Header("Authorization") String auth,
            Callback<Services> cb
    );

    @GET("/widgets/{service}/actions")
    void getActions(
            @Header("Authorization") String auth,
            @Path("service") int service,
            Callback<Actions> cb
    );

    @GET("/widgets/{service}/reactions")
    void getReactions(
            @Header("Authorization") String auth,
            @Path("service") int service,
            Callback<Reactions> cb
    );

    @POST("/widgets/add")
    void addWidget(
            @Header("Authorization") String auth,
            @Body CreationWidget data,
            Callback<Widget> cb
    );

    @POST("/widgets/toggle")
    void toggleWidget(
            @Header("Authorization") String auth,
            @Body Toggle info,
            Callback<BasicResponse> cb
    );

    @POST("/widgets/delete")
    void deleteWidget(
            @Header("Authorization") String auth,
            @Body Widget areaid,
            Callback<BasicResponse> cb
    );
}