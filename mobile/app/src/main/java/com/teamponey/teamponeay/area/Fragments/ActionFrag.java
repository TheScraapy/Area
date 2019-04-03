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

import com.teamponey.teamponeay.area.GridView.ItemGridView;
import com.teamponey.teamponeay.area.GridView.WidgetGridView;
import com.teamponey.teamponeay.area.Models.ActionReaction.Actions;
import com.teamponey.teamponeay.area.R;
import com.teamponey.teamponeay.area.Services.WidgetService;
import com.teamponey.teamponeay.area.WidgetCreationActivity;

import retrofit.Callback;
import retrofit.RetrofitError;

public class ActionFrag extends Fragment {
    public static ActionFrag newInstance() {
        return (new ActionFrag());
    }

    GridView gridView;
    View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_action, container, false);
        new WidgetService(getContext()).GetActions(new ActionFrag.UiCallback(), WidgetCreationActivity.newWidget.getServiceIn());
        return view;

    }

    private class UiCallback implements Callback<Actions> {

        @Override
        public void success(final Actions answer, retrofit.client.Response response) {

            ItemGridView adapterViewAndroid = new ItemGridView(getContext(), answer.getActions());
            gridView = view.findViewById(R.id.grid_view_action);
            gridView.setAdapter(adapterViewAndroid);
            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int i, long id) {

                    WidgetCreationActivity.newWidget.setActionId(answer.getActions().get(i).getId());

                    if (answer.getActions().get(i).getIsConfigurable())
                        WidgetCreationActivity.getInstance().showFragment(WidgetCreationActivity.FRAGMENT_CONFIGURE);
                    else
                        WidgetCreationActivity.getInstance().showFragment(WidgetCreationActivity.FRAGMENT_SERVICE);

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
}
