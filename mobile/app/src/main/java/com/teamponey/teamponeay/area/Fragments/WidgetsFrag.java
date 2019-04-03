package com.teamponey.teamponeay.area.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;

import com.google.gson.Gson;
import com.teamponey.teamponeay.area.GridView.WidgetGridView;
import com.teamponey.teamponeay.area.Models.Response;
import com.teamponey.teamponeay.area.R;
import com.teamponey.teamponeay.area.Services.WidgetService;
import com.teamponey.teamponeay.area.WidgetActivity;
import com.teamponey.teamponeay.area.WidgetCreationActivity;

import retrofit.Callback;
import retrofit.RetrofitError;

public class WidgetsFrag extends Fragment {

    View view;
    GridView gridView;
    Button button;

    private class BasicCallback implements Callback<Response> {
        @Override
        public void success(Response answer, retrofit.client.Response response) {
        }

        @Override
        public void failure(RetrofitError error) {
        }
    }

    private class UiCallback implements Callback<Response> {

        @Override
        public void success(Response answer, retrofit.client.Response response) {

            if (answer.getData() != null) {
                WidgetGridView adapterViewAndroid = new WidgetGridView(view.getContext(), answer.getData());

                gridView = view.findViewById(R.id.grid_view_image_text);
                gridView.setAdapter(adapterViewAndroid);
                gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view,
                                            int i, long id) {
                        Intent WidgetActivity = new Intent(getActivity(), WidgetActivity.class);
                        Gson gson = new Gson();
                        String WidgetToJson = gson.toJson(gridView.getAdapter().getItem(i));
                        WidgetActivity.putExtra("ChoosedWidget", WidgetToJson);
                        startActivity(WidgetActivity);
                    }
                });
            }
        }

        @Override
        public void failure(RetrofitError error) {
            if (error == null)
                Log.e("AREA", getResources().getString(R.string.no_internet));
            else
                Log.e("AREA", getResources().getString(R.string.error, error));
        }
    }



    public static WidgetsFrag newInstance() {
        return (new WidgetsFrag());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.frag_widgets, container, false);
        new WidgetService(getContext()).GetWidgets(new WidgetsFrag.UiCallback());

        button = view.findViewById(R.id.tvWidgetClickable);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent WidgetCreationActivity = new Intent(getActivity(), WidgetCreationActivity.class);
                startActivity(WidgetCreationActivity);
            }

        });

        return view;
    }

}
