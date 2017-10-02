package com.example.batrakov.activitytaskgrid;


import android.content.Intent;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.ArrayList;

public class GridLayoutActivity extends AppCompatActivity {

    private ArrayList<Cat> mListData;
    private RecyclerView mGridView;
    private CatAdapter mGridAdapter;
    private GridLayoutManager mManager;
    private static final String CAT_ARRAY = "cat array";
    private static final String CAT_INDEX = "cat index";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.grid_layout);
        mGridView = (RecyclerView) findViewById(R.id.gridView);
        mListData = new ArrayList<>();
        if (mGridAdapter == null) {
            mGridAdapter = new CatAdapter(mListData);
        }
        mManager = new GridLayoutManager(this, 2);
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            mManager.setSpanCount(2);
        } else {
            mManager.setSpanCount(3);
        }

        mGridView.setLayoutManager(mManager);
        mGridView.setAdapter(mGridAdapter);
        mGridAdapter.replaceData(mListData);

        ArrayList<String> stringArrayList;
        if (this.getIntent().hasExtra(CAT_ARRAY)) {
            stringArrayList = this.getIntent().getStringArrayListExtra(CAT_ARRAY);
            for (int i = 0; i < stringArrayList.size(); i += 3) {
                mListData.add(new Cat(stringArrayList.get(i),
                        stringArrayList.get(i + 1),
                        stringArrayList.get(i + 2)));
            }
        }
    }

    private class CatHolder extends RecyclerView.ViewHolder {

        private View mView;
        private TextView mName;
        private TextView mBreed;
        private TextView mAge;
        private Cat mCat;

        private CatHolder(View itemView) {
            super(itemView);
            mView = itemView;
            mName = mView.findViewById(R.id.name);
            mBreed = mView.findViewById(R.id.breed);
            mAge = mView.findViewById(R.id.age);
        }

        void bindView(Cat aCat) {
            mCat = aCat;
            mName.setText(mCat.getName());
            mBreed.setText(mCat.getBreed());
            mAge.setText(mCat.getAge());
        }
    }

    private class CatAdapter extends RecyclerView.Adapter<CatHolder> {

        private ArrayList<Cat> mList;

        CatAdapter(ArrayList<Cat> aList) {
            mList = aList;
        }

        void replaceData(ArrayList<Cat> aList) {
            mList = aList;
            notifyDataSetChanged();
        }

        @Override
        public CatHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View rowView = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_item, parent, false);
            rowView.setOnClickListener(mOnClickListener);
            return new CatHolder(rowView);
        }

        private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                Intent intent = new Intent();
                intent.putExtra(CAT_INDEX, mGridView.getChildLayoutPosition(view) + 1);
                setResult(RESULT_OK, intent);
                finish();
            }
        };

        @Override
        public void onBindViewHolder(CatHolder holder, int position) {
            Cat cat = mList.get(position);
            holder.bindView(cat);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public int getItemCount() {
            return mList.size();
        }
    }
}
