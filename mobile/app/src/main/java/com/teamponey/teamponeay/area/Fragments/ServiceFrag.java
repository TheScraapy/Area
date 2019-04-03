package com.teamponey.teamponeay.area.Fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.teamponey.teamponeay.area.GridView.ServiceGridView;
import com.teamponey.teamponeay.area.Models.Services.Services;
import com.teamponey.teamponeay.area.R;
import com.teamponey.teamponeay.area.Services.WidgetService;
import com.teamponey.teamponeay.area.WidgetCreationActivity;

import retrofit.Callback;
import retrofit.RetrofitError;

import static com.teamponey.teamponeay.area.WidgetCreationActivity.FRAGMENT_CLOCK;
import static com.teamponey.teamponeay.area.WidgetCreationActivity.FRAGMENT_FACEBOOK;
import static com.teamponey.teamponeay.area.WidgetCreationActivity.FRAGMENT_GMAIL;
import static com.teamponey.teamponeay.area.WidgetCreationActivity.FRAGMENT_TWITTER;
import static com.teamponey.teamponeay.area.WidgetCreationActivity.FRAGMENT_YOUTUBE;

public class ServiceFrag extends Fragment {
    public static ServiceFrag newInstance() {
        return (new ServiceFrag());
    }

    View view;
    GridView gridView;

    private class UiCallback implements Callback<Services> {

        @Override
        public void success(final Services answer, retrofit.client.Response response) {


            ServiceGridView adapterViewAndroid = new ServiceGridView(getContext(), answer.getServices());

            gridView = view.findViewById(R.id.grid_view_services);
            gridView.setAdapter(adapterViewAndroid);
            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int i, long id) {

                    if (WidgetCreationActivity.newWidget.getAction() == -1) {
                        WidgetCreationActivity.newWidget.setServiceIn(answer.getServices().get(i).getId());
                    } else {
                        WidgetCreationActivity.newWidget.setServiceOut(answer.getServices().get(i).getId());
                    }
                    if (answer.getServices().get(i).getTitle().equals("Facebook")) {
                        WidgetCreationActivity.getInstance().showFragment(FRAGMENT_FACEBOOK);
                    }
                    else if (answer.getServices().get(i).getTitle().equals("Twitter")) {
                        WidgetCreationActivity.getInstance().showFragment(FRAGMENT_TWITTER);
                    }
                    else if (answer.getServices().get(i).getTitle().equals("Youtube")) {
                        WidgetCreationActivity.getInstance().showFragment(FRAGMENT_GMAIL);
                    }
                    else if (answer.getServices().get(i).getTitle().equals("Clock")) {
                        WidgetCreationActivity.getInstance().showFragment(FRAGMENT_CLOCK);
                    }
                    else if (answer.getServices().get(i).getTitle().equals("Gmail")) {
                        WidgetCreationActivity.getInstance().showFragment(FRAGMENT_GMAIL);
                    }
                    else if (answer.getServices().get(i).getTitle().equals("Calendar")) {
                        WidgetCreationActivity.getInstance().showFragment(FRAGMENT_GMAIL);
                    }

                }
            });
        }

        @Override
        public void failure(RetrofitError error) {
            if (error == null)
                Log.e("AREA", getResources().getString(R.string.no_internet));
            else
                Log.e("AREA", getResources().getString(R.string.error, error));
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_service, container, false);
        new WidgetService(getContext()).GetServices(new UiCallback());return view;
    }

}