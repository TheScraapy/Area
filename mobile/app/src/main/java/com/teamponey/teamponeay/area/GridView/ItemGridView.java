package com.teamponey.teamponeay.area.GridView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.teamponey.teamponeay.area.Models.ActionReaction.Item;
import com.teamponey.teamponeay.area.R;

import java.util.List;

public class ItemGridView extends BaseAdapter {

    private Context mContext;
    private List<Item> itemsData;

    public ItemGridView(Context context, List<Item> items) {
        mContext = context;
        itemsData = items;
    }

    @Override
    public int getCount() {
        return itemsData.size();
    }

    @Override
    public Object getItem(int i) {
        return itemsData.get(i);
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
            TextView title = gridViewAndroid.findViewById(R.id.tvService);
            title.setText(itemsData.get(i).getTitle());

            TextView desc = gridViewAndroid.findViewById(R.id.tvDesc);
            desc.setText(itemsData.get(i).getDesc());
        }
        else
            gridViewAndroid = (View) convertView;

        return gridViewAndroid;
    }

}
