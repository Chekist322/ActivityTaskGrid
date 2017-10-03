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

/**
 * Main app activity.
 * Provide browsing list as grid.
 */
public class GridLayoutActivity extends AppCompatActivity {

    private RecyclerView mGridView;
    private CatAdapter mGridAdapter;
    private ArrayList<Cat> mGridData;
    private GridLayoutManager mManager;
    private static final String CAT_ARRAY = "cat array";
    private static final String CAT_INDEX = "cat index";
    private static final int PORTRAIT_COL_SPAN = 2;
    private static final int LANDSCAPE_COL_SPAN = 3;
    private static final int PARSE_STEP = 3;

    @Override
    protected void onSaveInstanceState(Bundle aOutState) {
        super.onSaveInstanceState(aOutState);
        aOutState.putSerializable(CAT_ARRAY, mGridData);
    }

    @Override
    protected void onCreate(Bundle aSavedInstanceState) {
        super.onCreate(aSavedInstanceState);
        if (aSavedInstanceState != null) {
            initialize();
            if (aSavedInstanceState.getSerializable(CAT_ARRAY) instanceof ArrayList) {
                mGridData = (ArrayList<Cat>) aSavedInstanceState.getSerializable(CAT_ARRAY);
                mGridView.setLayoutManager(mManager);
                mGridView.setAdapter(mGridAdapter);
                mGridAdapter.replaceData(mGridData);
            }
        } else {
        if (this.getIntent().hasExtra(CAT_ARRAY)) {
            initialize();
            mGridData = new ArrayList<>();
            parseArrayList();
            mGridView.setLayoutManager(mManager);
            mGridView.setAdapter(mGridAdapter);
            mGridAdapter.replaceData(mGridData);
            }
        }
    }

    /**
     * Parse incoming String ArrayList to Cat ArrayList.
     */
    private void parseArrayList() {
        ArrayList<String> stringArrayList;
        stringArrayList = this.getIntent().getStringArrayListExtra(CAT_ARRAY);
        for (int i = 0; i < stringArrayList.size(); i += PARSE_STEP) {
            mGridData.add(new Cat(stringArrayList.get(i),
                    stringArrayList.get(i + 1),
                    stringArrayList.get(i + 2)));
        }
    }

    /**
     *  Variables initialization.
     */
    private void initialize() {
        setContentView(R.layout.grid_layout);
        mGridView = (RecyclerView) findViewById(R.id.gridView);
        mGridAdapter = new CatAdapter(mGridData);
        mManager = new GridLayoutManager(this, PORTRAIT_COL_SPAN);
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            mManager.setSpanCount(PORTRAIT_COL_SPAN);
        } else {
            mManager.setSpanCount(LANDSCAPE_COL_SPAN);
        }
    }

    /**
     * Holder for RecyclerView Adapter.
      */
    private final class CatHolder extends RecyclerView.ViewHolder {

        private View mView;
        private TextView mName;
        private TextView mBreed;
        private TextView mAge;
        private Cat mCat;

        /**
         * Constructor.
         * @param aItemView item view
         */
        private CatHolder(View aItemView) {
            super(aItemView);
            mView = itemView;
            mName = mView.findViewById(R.id.name);
            mBreed = mView.findViewById(R.id.breed);
            mAge = mView.findViewById(R.id.age);
        }

        /**
         * View fill.
         * @param aCat cat from list
         */
        void bindView(Cat aCat) {
            mCat = aCat;
            mName.setText(mCat.getName());
            mBreed.setText(mCat.getBreed());
            mAge.setText(mCat.getAge());
        }
    }

    /**
     * Adapter for RecyclerView.
     */
    private class CatAdapter extends RecyclerView.Adapter<CatHolder> {

        private ArrayList<Cat> mList;

        /**
         * Constructor.
         * @param aList target list for fill.
         */
        CatAdapter(ArrayList<Cat> aList) {
            mList = aList;
        }

        /**
         * List updating.
         * @param aList new target list.
         */
        void replaceData(ArrayList<Cat> aList) {
            mList = aList;
            notifyDataSetChanged();
        }

        @Override
        public CatHolder onCreateViewHolder(ViewGroup aParent, int aViewType) {
            View rowView = LayoutInflater.from(aParent.getContext()).inflate(R.layout.grid_item, aParent, false);
            rowView.setOnClickListener(mOnClickListener);
            return new CatHolder(rowView);
        }

        private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(final View aView) {
                Intent intent = new Intent();
                intent.putExtra(CAT_INDEX, mGridView.getChildLayoutPosition(aView) + 1);
                setResult(RESULT_OK, intent);
                finish();
            }
        };

        @Override
        public void onBindViewHolder(CatHolder aHolder, int aPosition) {
            Cat cat = mList.get(aPosition);
            aHolder.bindView(cat);
        }

        @Override
        public long getItemId(int aIndex) {
            return aIndex;
        }

        @Override
        public int getItemCount() {
            return mList.size();
        }
    }
}
