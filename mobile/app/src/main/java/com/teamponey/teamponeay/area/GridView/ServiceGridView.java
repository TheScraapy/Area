package com.teamponey.teamponeay.area.GridView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.teamponey.teamponeay.area.Models.Services.Service;
import com.teamponey.teamponeay.area.R;

import java.util.List;

public class ServiceGridView extends BaseAdapter {

    private Context mContext;
    private List<Service> servicesData;

    public ServiceGridView(Context context, List<Service> services) {
        mContext = context;
        servicesData = services;
    }

    @Override
    public int getCount() {
        return servicesData.size();
    }

    @Override
    public Object getItem(int i) {
        return servicesData.get(i);
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
            gridViewAndroid = inflater.inflate(R.layout.service_gridview_layout, null);
            TextView title = (TextView) gridViewAndroid.findViewById(R.id.tvService);
            title.setText(servicesData.get(i).getTitle());
        }
        else
            gridViewAndroid = (View) convertView;

        return gridViewAndroid;
    }

}
