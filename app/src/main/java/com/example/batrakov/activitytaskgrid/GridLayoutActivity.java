package com.example.batrakov.activitytaskgrid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class GridLayoutActivity extends AppCompatActivity {

    ArrayList<Cat> mListData;
    GridView mGridView;
    GridAdapter mGridAdapter;
    private static final String CAT_ARRAY = "cat array";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.grid_layout);
        mGridView = (GridView) findViewById(R.id.gridView);
        mListData = new ArrayList<>();
        if (mGridAdapter == null){
            mGridAdapter = new GridAdapter(mListData);
        }

        ArrayList<String> stringArrayList;
        if (this.getIntent().hasExtra(CAT_ARRAY)){

            stringArrayList = this.getIntent().getStringArrayListExtra(CAT_ARRAY);

            for (int i = 0; i < stringArrayList.size(); i+=3) {
                mListData.add(new Cat(stringArrayList.get(i),
                        stringArrayList.get(i+1),
                        stringArrayList.get(i+2)));
            }
            mGridView.setAdapter(mGridAdapter);
            mGridAdapter.replaceData(mListData);
        }
                
    }


    private class GridAdapter extends BaseAdapter{

        private ArrayList<Cat> mListData;

        GridAdapter(ArrayList<Cat> aListData){
            mListData = aListData;
        }

        void replaceData(ArrayList<Cat> aListData){
            mListData = aListData;
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return mListData.size();
        }

        @Override
        public Object getItem(int i) {
            return mListData.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            View rowView = view;
            if (rowView == null) {
                LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
                rowView = inflater.inflate(R.layout.grid_item, viewGroup, false);
            }
            final Cat cat = (Cat) getItem(i);

            TextView name = rowView.findViewById(R.id.name);
            name.setText(cat.getName());
            TextView breed = rowView.findViewById(R.id.breed);
            breed.setText(cat.getBreed());
            TextView age = rowView.findViewById(R.id.age);
            age.setText(cat.getAge());
            return rowView;
        }
    }
}
