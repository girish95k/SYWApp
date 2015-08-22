package com.ieee.girish.syw2015;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.github.florent37.materialviewpager.MaterialViewPager;
import com.github.florent37.materialviewpager.MaterialViewPagerHelper;
import com.github.florent37.materialviewpager.header.HeaderDesign;
import com.ieee.girish.syw2015.fragment.*;
import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;


public class MainActivity extends AppCompatActivity {

    private MaterialViewPager mViewPager;

    ArrayList<DataObject> speakerList;
    ArrayList<DataObject> schedule;

    private DrawerLayout mDrawer;
    private ActionBarDrawerToggle mDrawerToggle;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        setTitle("");

        // Enable Local Datastore.
        //Parse.enableLocalDatastore(this);

        speakerList = new ArrayList<>();
        schedule = new ArrayList<>();



        ParseQuery<ParseObject> query = ParseQuery.getQuery("Speakers");
        //query.whereEqualTo("playerName", "Dan Stemkoski");
        query.orderByDescending("createdAt");
        //query.setLimit(10);
        final SweetAlertDialog pDialog = new SweetAlertDialog(MainActivity.this, SweetAlertDialog.PROGRESS_TYPE);
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
                        Log.e("s", object.getString("Name"));
                        if(object.getString("Name").equals("FullSchedule"))
                            schedule.add(new DataObject("", object.getString("Details").replaceAll("\n", System.getProperty("line.separator")), ""));
                        else
                            speakerList.add(new DataObject(object.getString("Name"), object.getString("Details")+"<br/>"+object.getString("Link"), object.getString("Image")));
                    }
                } else {
                    Log.d("score", "Error: " + e.getMessage());
                }

                mViewPager.getViewPager().setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {

                    @Override
                    public Fragment getItem(int position) {
                        switch (position % 4) {
                            case 0:
                                //return RecyclerViewFragment.newInstance(speakerList, MainActivity.this);
                                return ScheduleFragment.newInstance(schedule, MainActivity.this);
                            case 1:
                                return RecyclerViewFragment.newInstance(speakerList, MainActivity.this);
                            case 2:
                                return new ContactFragment();
                            case 3:
                                return new MapsActivity();
                            default:
                                return CarpaccioRecyclerViewFragment.newInstance();
                        }
                    }

                    @Override
                    public int getCount() {
                        return 4;
                    }

                    @Override
                    public CharSequence getPageTitle(int position) {
                        switch (position % 4) {
                            case 0:
                                return "Schedule";
                            case 1:
                                return "Speakers";
                            case 2:
                                return "Contact";
                            case 3:
                                return "Venue";
                        }
                        return "";
                    }
                });
            }
        });

        mViewPager = (MaterialViewPager) findViewById(R.id.materialViewPager);

        toolbar = mViewPager.getToolbar();
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        if (toolbar != null) {
            setSupportActionBar(toolbar);

            final ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.setDisplayHomeAsUpEnabled(true);
                actionBar.setDisplayShowHomeEnabled(true);
                actionBar.setDisplayShowTitleEnabled(true);
                actionBar.setDisplayUseLogoEnabled(false);
                actionBar.setHomeButtonEnabled(true);
            }
        }

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawer, 0, 0);
        mDrawer.setDrawerListener(mDrawerToggle);

        mViewPager.getViewPager().setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {

            @Override
            public Fragment getItem(int position) {
                switch (position % 4) {
                    case 0:
                        //return RecyclerViewFragment.newInstance(speakerList, MainActivity.this);
                        return ScheduleFragment.newInstance(schedule, MainActivity.this);
                    case 1:
                        return RecyclerViewFragment.newInstance(speakerList, MainActivity.this);
                    case 2:
                        return new ContactFragment();
                    case 3:
                        return new MapsActivity();
                    default:
                        return CarpaccioRecyclerViewFragment.newInstance();
                }
            }

            @Override
            public int getCount() {
                return 4;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                switch (position % 4) {
                    case 0:
                        return "Schedule";
                    case 1:
                        return "Speakers";
                    case 2:
                        return "Contact";
                    case 3:
                        return "Venue";
                }
                return "";
            }
        });

        mViewPager.setMaterialViewPagerListener(new MaterialViewPager.Listener() {
            @Override
            public HeaderDesign getHeaderDesign(int page) {
                switch (page) {
                    case 0:
                        return HeaderDesign.fromColorResAndUrl(
                                R.color.green,
                                //"https://fs01.androidpit.info/a/63/0e/android-l-wallpapers-630ea6-h900.jpg");
                                "https://lh3.googleusercontent.com/40yUP4WABsprKN2Sx4VTa7W0b8xxlXGbpeQPx74gOpy_MzN6Vo7uayOYJuYULmD40qKZdHJCW-FKXnJ3lNMEKUe2dsdGTBXUWoJh=w1896-h827-rw");
                    case 1:
                        return HeaderDesign.fromColorResAndUrl(
                                R.color.blue,
                                //"http://cdn1.tnwcdn.com/wp-content/blogs.dir/1/files/2014/06/wallpaper_51.jpg");
                                "https://lh6.googleusercontent.com/uCb1hNxuV4j32ohr5iKDwyN2EpFbmWarzDJIEKUIpmayjVz6_R79aj70bfoL4exhU885YSryn8f0-HBeNUNIhszWe630wHZuiD2l=w1896-h827-rw");
                    case 2:
                        return HeaderDesign.fromColorResAndUrl(
                                R.color.cyan,
                                //"http://www.droid-life.com/wp-content/uploads/2014/10/lollipop-wallpapers10.jpg");
                                //"https://lh3.googleusercontent.com/2FLHD_kiLf-2xKlg81Uux7NuBwBVEBjlaPJd3wa1XsuvAPWgZ59O3b9oZfikC5kfnBkwVhEJ85ADQ7ytO8lMELQ3RbEDHY72idwm=w1896-h827-rw");
                                "https://lh5.googleusercontent.com/ztk2zktvS1VkR3mEKwMxe3l_i9ldXxLWGWzsnCyGOrJcZXIepF8yqqMfYYsBYulCSsTq_FHqo_1NlN5b2RavD1GjcqmlednIR29O=w1896-h827-rw");
                    case 3:
                        return HeaderDesign.fromColorResAndUrl(
                                R.color.red,
                                //"http://www.tothemobile.com/wp-content/uploads/2014/07/original.jpg");
                                "https://lh3.googleusercontent.com/2FLHD_kiLf-2xKlg81Uux7NuBwBVEBjlaPJd3wa1XsuvAPWgZ59O3b9oZfikC5kfnBkwVhEJ85ADQ7ytO8lMELQ3RbEDHY72idwm=w1896-h827-rw");
                }

                //execute others actions if needed (ex : modify your header logo)

                return null;
            }
        });

        mViewPager.getViewPager().setOffscreenPageLimit(mViewPager.getViewPager().getAdapter().getCount());
        mViewPager.getPagerTitleStrip().setViewPager(mViewPager.getViewPager());

        View logo = findViewById(R.id.logo_white);
        if (logo != null)
            logo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mViewPager.notifyHeaderChanged();
                    //Toast.makeText(getApplicationContext(), "Yes, the title is clickable", Toast.LENGTH_SHORT).show();
                }
            });
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return mDrawerToggle.onOptionsItemSelected(item) ||
                super.onOptionsItemSelected(item);
    }

    public void notifications(View view)
    {
        startActivity(new Intent(MainActivity.this, NotificationsActivity.class));
    }
    public void about(View view)
    {
        startActivity(new Intent(MainActivity.this, AboutActivity.class));
    }
    public void attendees(View view)
    {
        startActivity(new Intent(MainActivity.this, AboutActivity.class));
    }

    @Override
    public void onBackPressed() {
        android.os.Process.killProcess(android.os.Process.myPid());
    }
}
