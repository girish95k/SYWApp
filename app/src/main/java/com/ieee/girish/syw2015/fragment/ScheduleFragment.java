package com.ieee.girish.syw2015.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.florent37.materialviewpager.MaterialViewPagerHelper;
import com.github.florent37.materialviewpager.adapter.RecyclerViewMaterialAdapter;
import com.ieee.girish.syw2015.DataObject;
import com.ieee.girish.syw2015.MyRecyclerViewAdapter;
import com.ieee.girish.syw2015.R;
import com.ieee.girish.syw2015.TestRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by florentchampigny on 24/04/15.
 */
public class ScheduleFragment extends Fragment {

    public static ArrayList<DataObject> speakerList;
    public static Context context;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;

    private static final int ITEM_COUNT = 100;

    private List<Object> mContentItems = new ArrayList<>();

    public static RecyclerViewFragment newInstance() {
        return new RecyclerViewFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recyclerview, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);

        //ArrayList<DataObject> contacts = new ArrayList<>();
        //contacts.add(new DataObject("Girish", "kdfjg", ""));
        mAdapter = new RecyclerViewMaterialAdapter(new MyRecyclerViewAdapter(speakerList, context));
        mRecyclerView.setAdapter(mAdapter);

        {
            for (int i = 0; i < ITEM_COUNT; ++i)
                mContentItems.add(new Object());
            mAdapter.notifyDataSetChanged();
        }

        MaterialViewPagerHelper.registerRecyclerView(getActivity(), mRecyclerView, null);
    }
    public static ScheduleFragment newInstance(ArrayList<DataObject> list, Context c)
    {
        ScheduleFragment r = new ScheduleFragment();
        speakerList = list;
        context = c;
        return  r;
    }
}
