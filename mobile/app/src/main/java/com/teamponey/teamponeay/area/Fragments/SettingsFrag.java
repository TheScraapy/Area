package com.teamponey.teamponeay.area.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.teamponey.teamponeay.area.R;

public class SettingsFrag extends Fragment {
    public static SettingsFrag newInstance() {
        return (new SettingsFrag());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.frag_settings, container, false);

    }

}
