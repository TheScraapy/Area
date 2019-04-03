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
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;

import com.teamponey.teamponeay.area.GridView.ItemGridView;
import com.teamponey.teamponeay.area.MainActivity;
import com.teamponey.teamponeay.area.Models.ActionReaction.Reactions;
import com.teamponey.teamponeay.area.Models.ActionReaction.Widget;
import com.teamponey.teamponeay.area.R;
import com.teamponey.teamponeay.area.Services.WidgetService;
import com.teamponey.teamponeay.area.WidgetCreationActivity;

import retrofit.Callback;
import retrofit.RetrofitError;

public class ConfigureFrag extends Fragment {
    public static ConfigureFrag newInstance() {
        return (new ConfigureFrag());
    }

    View view;
    EditText editText;
    Button btn;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_configure, container, false);

        editText = view.findViewById(R.id.etDataConf);

        btn = view.findViewById(R.id.btnSendConf);
        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                 if (WidgetCreationActivity.newWidget.getReactionId() == -1) {
                     Log.d("AreaArea", "Ici");
                     WidgetCreationActivity.newWidget.setActionData(editText.getText().toString());
                     WidgetCreationActivity.getInstance().showFragment(WidgetCreationActivity.FRAGMENT_SERVICE);
                 }
                 else {
                     Log.d("AreaArea", "la");
                     WidgetCreationActivity.newWidget.setReactionData(editText.getText().toString());
                     new WidgetService(getContext()).AddWidget(WidgetCreationActivity.newWidget, new ConfigureFrag.WidgetCreationCallback());
                 }
            }
        });

        return view;
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
