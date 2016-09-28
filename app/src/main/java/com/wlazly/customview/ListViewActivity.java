package com.wlazly.customview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.wlazly.customview.inherit.CustomListView;
import java.util.ArrayList;
import java.util.List;


public class ListViewActivity extends AppCompatActivity implements CustomListView.RefreshListener {

    private CustomListView listView;
    private List<String> list;
    private ListViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);
        listView = (CustomListView) findViewById(R.id.listview);
        list = new ArrayList<String>();
        list.add("1");
        list.add("2");
        list.add("3");
        list.add("4");
        list.add("5");
        adapter = new ListViewAdapter(this,list);
        listView.setAdapter(adapter);
        listView.setOnRefreshListener(this);
    }

    @Override
    public void onRefresh() {
        android.os.Handler handler = new android.os.Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                list.add(0,"0");
                adapter.notifyDataSetChanged();
                listView.compleRefresh();
            }
        },2000);

    }

}
