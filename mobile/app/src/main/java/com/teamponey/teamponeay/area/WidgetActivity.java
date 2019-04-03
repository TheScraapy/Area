package com.teamponey.teamponeay.area;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.teamponey.teamponeay.area.Models.BasicResponse;
import com.teamponey.teamponeay.area.Models.ActionReaction.Widget;
import com.teamponey.teamponeay.area.Models.Toggle;
import com.teamponey.teamponeay.area.Services.WidgetService;

import retrofit.Callback;
import retrofit.RetrofitError;

public class WidgetActivity extends AppCompatActivity {

    private static WidgetActivity instance;
    private Switch widgetSwitch;
    public static WidgetActivity getInstance() {
        return instance;
    }
    public Toggle info;
    public Widget choosed;
    public Button deleteBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_widget);

        instance = this;

        Gson gson = new Gson();
        String JsonToWidget = getIntent().getStringExtra("ChoosedWidget");
        choosed = gson.fromJson(JsonToWidget, Widget.class);

        final TextView widgetServiceIn = findViewById(R.id.tvServiceIn);
        widgetServiceIn.setText(choosed.getServiceIn());

        final TextView widgetServiceOut = findViewById(R.id.tvServiceOut);
        widgetServiceOut.setText(choosed.getServiceOut());

        final TextView widgetServiceDesc = findViewById(R.id.tvDesc);
        widgetServiceDesc.setText(choosed.getFeature());

        widgetSwitch = findViewById(R.id.widget_switch);
        deleteBtn = findViewById(R.id.btnDelete);

        info = new Toggle();
        info.setState(choosed.getActive());
        info.setAreaid(choosed.getAreaid());

        updateSwitch(info);

        widgetSwitch.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                new WidgetService(getInstance()).ToggleWidget(info, new WidgetActivity.UiCallback());
                return true;
            }
        });

        widgetSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new WidgetService(getInstance()).ToggleWidget(info, new WidgetActivity.UiCallback());
            }
        });

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new WidgetService(getInstance()).DeleteWidget(choosed, new WidgetActivity.UiCallback());
                Intent intent = new Intent(WidgetActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }

    private class UiCallback implements Callback<BasicResponse> {

        @Override
        public void success(BasicResponse answer, retrofit.client.Response response) {
            info.setState(!info.getState());
            updateSwitch(info);
        }

        @Override
        public void failure(RetrofitError error) {
            updateSwitch(info);
            Toast.makeText(getApplicationContext(), "An error occured", Toast.LENGTH_LONG).show();
            if (error == null)
                Log.e("Area", "No internet connection");
            else
                Log.e("Area", "Error is: " + error);
        }
    }

    public void updateSwitch(Toggle info){
        if (info.getState() == true) {
            widgetSwitch.setChecked(true);
            widgetSwitch.setText("Active");
            widgetSwitch.setTextColor(getResources().getColor(R.color.active));
        } else {
            widgetSwitch.setChecked(false);
            widgetSwitch.setText("Unactive");
            widgetSwitch.setTextColor(getResources().getColor(R.color.unactive));
        }
    }

}