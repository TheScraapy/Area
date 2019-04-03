package com.teamponey.teamponeay.area.Services;

import android.content.Context;

import com.teamponey.teamponeay.area.AreaApi;
import com.teamponey.teamponeay.area.Constants;
import com.teamponey.teamponeay.area.Models.Auth;
import com.teamponey.teamponeay.area.Models.BasicResponse;
import com.teamponey.teamponeay.area.Models.OauthModel;
import com.teamponey.teamponeay.area.Models.Response;
import com.teamponey.teamponeay.area.NetworkUtils;

import java.lang.ref.WeakReference;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;

import static com.teamponey.teamponeay.area.Constants.LOGGING;
import static com.teamponey.teamponeay.area.Constants.getClientToken;

public class AuthService {

    private WeakReference<Context> mContext;

    public AuthService(Context context) {
        this.mContext = new WeakReference<>(context);
    }

    private RestAdapter buildRestAdapter() {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(Constants.server)
                .build();
        if (LOGGING)
            restAdapter.setLogLevel(RestAdapter.LogLevel.FULL);
        return restAdapter;
    }

    public void register(Auth auth, Callback<Response> callback) {
        final Callback<Response> cb = callback;
        if (!NetworkUtils.isConnected(mContext.get())) {
            cb.failure(null);
            return;
        }
        RestAdapter restAdapter = buildRestAdapter();
        restAdapter.create(AreaApi.class).register(
                auth,
                new Callback<Response>() {
                    @Override
                    public void success(Response data, retrofit.client.Response response) {
                        if (cb != null)
                            cb.success(data, response);
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        if (cb != null)
                            cb.failure(error);
                    }
                });
    }

    public void login(Auth auth, Callback<Response> callback) {
        final Callback<Response> cb = callback;
        if (!NetworkUtils.isConnected(mContext.get())) {
            cb.failure(null);
            return;
        }
        RestAdapter restAdapter = buildRestAdapter();
        restAdapter.create(AreaApi.class).login(
                auth,
                new Callback<Response>() {
                    @Override
                    public void success(Response data, retrofit.client.Response response) {
                        if (cb != null)
                            cb.success(data, response);
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        if (cb != null)
                            cb.failure(error);
                    }
                });
    }


    public void google(OauthModel token, Callback<Response> callback) {
        final Callback<Response> cb = callback;
        if (!NetworkUtils.isConnected(mContext.get())) {
            cb.failure(null);
            return;
        }
        RestAdapter restAdapter = buildRestAdapter();
        restAdapter.create(AreaApi.class).google(
                token,
                new Callback<Response>() {
                    @Override
                    public void success(Response data, retrofit.client.Response response) {
                        if (cb != null)
                            cb.success(data, response);
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        if (cb != null)
                            cb.failure(error);
                    }
                });
    }

    public void facebook(OauthModel token, Callback<BasicResponse> callback) {
        final Callback<BasicResponse> cb = callback;
        if (!NetworkUtils.isConnected(mContext.get())) {
            cb.failure(null);
            return;
        }
        RestAdapter restAdapter = buildRestAdapter();
        restAdapter.create(AreaApi.class).facebook(
                getClientToken(),
                token,
                new Callback<BasicResponse>() {
                    @Override
                    public void success(BasicResponse data, retrofit.client.Response response) {
                        if (cb != null)
                            cb.success(data, response);
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        if (cb != null)
                            cb.failure(error);
                    }
                });
    }

    public void twitter(OauthModel token, Callback<BasicResponse> callback) {

        final Callback<BasicResponse> cb = callback;
        if (!NetworkUtils.isConnected(mContext.get())) {
            cb.failure(null);
            return;
        }

        RestAdapter restAdapter = buildRestAdapter();
        restAdapter.create(AreaApi.class).twitter(
                getClientToken(),
                token,
                new Callback<BasicResponse>() {
                    @Override
                    public void success(BasicResponse data, retrofit.client.Response response) {
                        if (cb != null)
                            cb.success(data, response);
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        if (cb != null)
                            cb.failure(error);
                    }
                });
    }

    public void gmail(OauthModel token, Callback<BasicResponse> callback) {

        final Callback<BasicResponse> cb = callback;
        if (!NetworkUtils.isConnected(mContext.get())) {
            cb.failure(null);
            return;
        }

        RestAdapter restAdapter = buildRestAdapter();
        restAdapter.create(AreaApi.class).gmail(
                getClientToken(),
                token,
                new Callback<BasicResponse>() {
                    @Override
                    public void success(BasicResponse data, retrofit.client.Response response) {
                        if (cb != null)
                            cb.success(data, response);
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        if (cb != null)
                            cb.failure(error);
                    }
                });
    }
}