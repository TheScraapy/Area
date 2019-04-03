package com.teamponey.teamponeay.area.GridView;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.teamponey.teamponeay.area.Models.ActionReaction.Widget;
import com.teamponey.teamponeay.area.R;

import java.util.List;

public class WidgetGridView extends BaseAdapter {

    private Context mContext;
    private List<Widget> widgetsData;

    public WidgetGridView(Context context, List<Widget> widgets) {
        mContext = context;
        widgetsData = widgets;
    }

    @Override
    public int getCount() {
        return widgetsData.size();
    }

    @Override
    public Object getItem(int i) {
        return widgetsData.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        View gridViewAndroid;
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            gridViewAndroid = new View(mContext);
            gridViewAndroid = inflater.inflate(R.layout.gridview_layout, null);

            TextView textServiceIn = (TextView) gridViewAndroid.findViewById(R.id.tvServiceIn);
            textServiceIn.setText(widgetsData.get(i).getServiceIn());

            TextView textServiceOut = (TextView) gridViewAndroid.findViewById(R.id.tvServiceOut);
            textServiceOut.setText(widgetsData.get(i).getServiceOut());

            TextView textViewAndroid = (TextView) gridViewAndroid.findViewById(R.id.android_gridview_text);
            textViewAndroid.setText(widgetsData.get(i).getFeature());

        } else {
            gridViewAndroid = (View) convertView;
        }

        return gridViewAndroid;
    }

}
