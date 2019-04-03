package com.teamponey.teamponeay.area.Services;

import android.content.Context;

import com.teamponey.teamponeay.area.AreaApi;
import com.teamponey.teamponeay.area.Constants;
import com.teamponey.teamponeay.area.Models.BasicResponse;
import com.teamponey.teamponeay.area.Models.ActionReaction.Actions;
import com.teamponey.teamponeay.area.Models.ActionReaction.CreationWidget;
import com.teamponey.teamponeay.area.Models.ActionReaction.Reactions;
import com.teamponey.teamponeay.area.Models.ActionReaction.Widget;
import com.teamponey.teamponeay.area.Models.Response;
import com.teamponey.teamponeay.area.Models.Services.Services;
import com.teamponey.teamponeay.area.Models.Toggle;
import com.teamponey.teamponeay.area.NetworkUtils;

import java.lang.ref.WeakReference;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;

import static com.teamponey.teamponeay.area.Constants.LOGGING;
import static com.teamponey.teamponeay.area.Constants.getClientToken;

public class WidgetService {

    private WeakReference<Context> mContext;

    public WidgetService(Context context) {
        this.mContext = new WeakReference<>(context);
    }

    private RestAdapter buildRestAdapter() {
        RestAdapter imgurAdapter = new RestAdapter.Builder()
                .setEndpoint(Constants.server)
                .build();
        if (LOGGING)
            imgurAdapter.setLogLevel(RestAdapter.LogLevel.FULL);
        return imgurAdapter;
    }

    public void GetWidgets(Callback<Response> callback) {
        final Callback<Response> cb = callback;
        if (!NetworkUtils.isConnected(mContext.get())) {
            cb.failure(null);
            return;
        }
        RestAdapter restAdapter = buildRestAdapter();
        restAdapter.create(AreaApi.class).getWidgets(
                getClientToken(),
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

    public void GetServices(Callback<Services> callback) {
        final Callback<Services> cb = callback;
        if (!NetworkUtils.isConnected(mContext.get())) {
            cb.failure(null);
            return;
        }
        RestAdapter restAdapter = buildRestAdapter();
        restAdapter.create(AreaApi.class).getServices(
                getClientToken(),
                new Callback<Services>() {
                    @Override
                    public void success(Services data, retrofit.client.Response response) {
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

    public void GetActions(Callback<Actions> callback, int serviceId) {
        final Callback<Actions> cb = callback;
        if (!NetworkUtils.isConnected(mContext.get())) {
            cb.failure(null);
            return;
        }
        RestAdapter restAdapter = buildRestAdapter();
        restAdapter.create(AreaApi.class).getActions(
                getClientToken(),
                serviceId,
                new Callback<Actions>() {
                    @Override
                    public void success(Actions data, retrofit.client.Response response) {
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

    public void GetReactions(Callback<Reactions> callback, int serviceId) {
        final Callback<Reactions> cb = callback;
        if (!NetworkUtils.isConnected(mContext.get())) {
            cb.failure(null);
            return;
        }
        RestAdapter restAdapter = buildRestAdapter();
        restAdapter.create(AreaApi.class).getReactions(
                getClientToken(),
                serviceId,
                new Callback<Reactions>() {
                    @Override
                    public void success(Reactions data, retrofit.client.Response response) {
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

    public void AddWidget(CreationWidget newWidget, Callback<Widget> callback) {
        final Callback<Widget> cb = callback;
        if (!NetworkUtils.isConnected(mContext.get())) {
            cb.failure(null);
            return;
        }
        RestAdapter restAdapter = buildRestAdapter();
        restAdapter.create(AreaApi.class).addWidget(
                getClientToken(),
                newWidget,
                new Callback<Widget>() {
                    @Override
                    public void success(Widget data, retrofit.client.Response response) {
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

    public void ToggleWidget(Toggle info, Callback<BasicResponse> callback) {
        final Callback<BasicResponse> cb = callback;
        if (!NetworkUtils.isConnected(mContext.get())) {
            cb.failure(null);
            return;
        }
        RestAdapter restAdapter = buildRestAdapter();
        restAdapter.create(AreaApi.class).toggleWidget(
                getClientToken(),
                info,
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

    public void DeleteWidget(Widget areaid, Callback<BasicResponse> callback) {
        final Callback<BasicResponse> cb = callback;
        if (!NetworkUtils.isConnected(mContext.get())) {
            cb.failure(null);
            return;
        }
        RestAdapter restAdapter = buildRestAdapter();
        restAdapter.create(AreaApi.class).deleteWidget(
                getClientToken(),
                areaid,
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