package com.freeman.freetodo3.sample.recyclerview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.freeman.freetodo3.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SampleRecyclerView extends AppCompatActivity {

    private MyRecyclerViewAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sample_recycler_view);

        RecyclerView recyclerView = findViewById(R.id.sample_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new MyRecyclerViewAdapter();
        recyclerView.setAdapter(mAdapter);

        showRecyclerView();
    }

    private void showRecyclerView() {

        List<String> titles = Arrays.asList("태연1", "태연2", "태연3", "태연4", "태연5");
        List<String> contents = Arrays.asList("태연 이미지1","태연 이미지2","태연 이미지2","태연 이미지4","태연 이미지5");
        List<Integer> images = Arrays.asList(R.drawable.ty1, R.drawable.ty2, R.drawable.ty3, R.drawable.ty4, R.drawable.ty5);

        ArrayList<MyDataItem> items = new ArrayList<>();

        for (int i=0; i < titles.size(); i++) {
            MyDataItem item = new MyDataItem();
            item.setTitle(titles.get(i));
            item.setContents(contents.get(i));
            item.setResId(images.get(i));
            items.add(item);
        }

        mAdapter.setItems(items);
    }
}
