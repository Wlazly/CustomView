package com.wlazly.customview;

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Wlazly on 2016/8/24 0024.
 */
public class ListViewAdapter extends BaseAdapter {

    private List<String> list;
    private Context context;

    public ListViewAdapter(Context context,List<String> list) {
        this.list = list;
        this.context = context;
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = null;
        if (convertView == null) {
            view = LayoutInflater.from(context).inflate(R.layout.list_item,null);
        }else {
            view = convertView;
        }
        Holder holder =(Holder) view.getTag();
        if (holder == null) {
            holder  = new Holder();
            holder.text = (TextView) view.findViewById(R.id.textView);
            view.setTag(holder);
        }
        holder.text.setText(list.get(position).toString());
        return  view;

    }



    public static class Holder {
        TextView text;
    }



}
