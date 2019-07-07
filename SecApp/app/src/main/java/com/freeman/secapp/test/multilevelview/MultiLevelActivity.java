package com.freeman.secapp.test.multilevelview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;

import com.freeman.secapp.R;
import com.multilevelview.models.RecyclerViewItem;
//import com.multilevelview.MultiLevelRecyclerView;
//import com.multilevelview.models.RecyclerViewItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MultiLevelActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_level);

        final MultiLevelRecyclerView multiLevelRecyclerView = findViewById(R.id.rv_list);
        multiLevelRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<Item> items = (List<Item>) recursivePopulateFakeData(0, 12);

        MyAdapter adapter = new MyAdapter(this, items, multiLevelRecyclerView);
        multiLevelRecyclerView.setAdapter(adapter);
        multiLevelRecyclerView.setToggleItemOnClick(false);
        multiLevelRecyclerView.setAccordion(false);
        multiLevelRecyclerView.openTill(0,1,2,3);
    }

    private List<?> recursivePopulateFakeData(int levelNumber, int depth) {
        List<RecyclerViewItem> items = new ArrayList<>();

        String title;
        switch (levelNumber) {
            case 1:
                title = "PQRST %d";
                break;
            case 2:
                title = "XYZ %d";
                break;
            default:
                title = "AAAA %d";
                break;
        }

        for (int i=0; i<depth; i++) {
            Item item = new Item(levelNumber);
            item.setText(String.format(Locale.KOREA, title, i));
            item.setSecondText(String.format(Locale.KOREA, title.toLowerCase(), i));
            if (depth % 2 == 0) {
                item.addChildren((List<RecyclerViewItem>) recursivePopulateFakeData(levelNumber+1, depth/2));
            }
            items.add(item);
        }

        return items;
    }
}
