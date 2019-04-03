package com.teamponey.teamponeay.area.Fragments.Oauth;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.teamponey.teamponeay.area.R;
import com.teamponey.teamponeay.area.WidgetCreationActivity;


public class ClockFrag extends Fragment {
    public static ClockFrag newInstance() {
        return (new ClockFrag());
    }

    View view;
    EditText etClock;
    Button btnClock;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_clock, container, false);

        etClock = view.findViewById(R.id.etData);
        etClock.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {}

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                WidgetCreationActivity.newWidget.setActionData(etClock.getText().toString());
            }
        });

        btnClock = view.findViewById(R.id.btnSend);
        btnClock.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                WidgetCreationActivity.newWidget.setActionId(0);
                WidgetCreationActivity.getInstance().showFragment(WidgetCreationActivity.FRAGMENT_SERVICE);
            }
        });

        return view;
    }
}
