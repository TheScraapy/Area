package com.teamponey.teamponeay.area.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.teamponey.teamponeay.area.R;

public class ProfileFrag extends Fragment {
    public static ProfileFrag newInstance() {
        return (new ProfileFrag());
    }

    View view;

    TextView username;
    TextView language;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_profile, container, false);

        username = view.findViewById(R.id.user_username);
        language = view.findViewById(R.id.user_language);

        //Récupérer les infos du user, et les affichers en callback

        username.setText("Username");
        language.setText("Language");

        return view;
    }

}
