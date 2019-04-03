package com.teamponey.teamponeay.area.Fragments;

import android.content.Intent;
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
import com.teamponey.teamponeay.area.MainActivity;
import com.teamponey.teamponeay.area.Models.ActionReaction.Reactions;
import com.teamponey.teamponeay.area.Models.ActionReaction.Widget;
import com.teamponey.teamponeay.area.Models.Response;
import com.teamponey.teamponeay.area.R;
import com.teamponey.teamponeay.area.Services.WidgetService;
import com.teamponey.teamponeay.area.WidgetCreationActivity;

import retrofit.Callback;
import retrofit.RetrofitError;

public class ReactionFrag extends Fragment {
    public static ReactionFrag newInstance() {
        return (new ReactionFrag());
    }

    GridView gridView;
    View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_reaction, container, false);
        new WidgetService(getContext()).GetReactions(new ReactionFrag.UiCallback(), WidgetCreationActivity.newWidget.getServiceOut());
        return view;
    }

    private class UiCallback implements Callback<Reactions> {

        @Override
        public void success(final Reactions answer, retrofit.client.Response response) {

            ItemGridView adapterViewAndroid = new ItemGridView(getContext(), answer.getReactions());

            gridView = view.findViewById(R.id.grid_view_reaction);
            gridView.setAdapter(adapterViewAndroid);
            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int i, long id) {

                    WidgetCreationActivity.newWidget.setReactionId(answer.getReactions().get(i).getId());
                    if (answer.getReactions().get(i).getIsConfigurable()) {
                        WidgetCreationActivity.getInstance().showFragment(WidgetCreationActivity.FRAGMENT_CONFIGURE);
                    }
                    else {
                        new WidgetService(getContext()).AddWidget(WidgetCreationActivity.newWidget, new ReactionFrag.WidgetCreationCallback());
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

    private class WidgetCreationCallback implements Callback<Widget> {

        @Override
        public void success(final Widget answer, retrofit.client.Response response) {
            Intent intent = new Intent(getActivity(), MainActivity.class);
            startActivity(intent);
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
