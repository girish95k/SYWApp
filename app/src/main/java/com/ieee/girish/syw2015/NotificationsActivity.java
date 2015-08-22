package com.ieee.girish.syw2015;

import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.github.florent37.materialviewpager.MaterialViewPagerHelper;
import com.github.florent37.materialviewpager.adapter.RecyclerViewMaterialAdapter;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;


public class NotificationsActivity extends ActionBarActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;

    private static final int ITEM_COUNT = 100;

    private List<Object> mContentItems = new ArrayList<>();

    ArrayList<DataObject> notifications;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);

        notifications = new ArrayList<>();

        mRecyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);



        ParseQuery<ParseObject> query = ParseQuery.getQuery("Notification");
        //query.whereEqualTo("playerName", "Dan Stemkoski");
        query.orderByDescending("createdAt");
        //query.setLimit(10);
        final SweetAlertDialog pDialog = new SweetAlertDialog(NotificationsActivity.this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading...");
        pDialog.setCancelable(true);
        pDialog.show();
        query.setCachePolicy(ParseQuery.CachePolicy.NETWORK_ELSE_CACHE);
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> speakers, ParseException e) {
                pDialog.dismiss();
                if (e == null) {
                    Log.d("speakers", "Retrieved " + speakers.size() + " speakers");
                    for (ParseObject object : speakers) {
                        Log.e("s", object.getString("NotString"));
                        notifications.add(new DataObject(object.getString("NotString"), "", ""));

                        mAdapter = new RecyclerViewMaterialAdapter(new MyRecyclerViewAdapter(notifications, NotificationsActivity.this));
                        mRecyclerView.setAdapter(mAdapter);

                        {
                            for (int i = 0; i < ITEM_COUNT; ++i)
                                mContentItems.add(new Object());
                            mAdapter.notifyDataSetChanged();
                        }

                        MaterialViewPagerHelper.registerRecyclerView(NotificationsActivity.this, mRecyclerView, null);
                    }
                } else {
                    Log.d("score", "Error: " + e.getMessage());
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_notifications, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
