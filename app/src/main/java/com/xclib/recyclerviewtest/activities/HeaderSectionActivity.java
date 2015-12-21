package com.xclib.recyclerviewtest.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.eowise.recyclerview.stickyheaders.OnHeaderClickListener;
import com.eowise.recyclerview.stickyheaders.StickyHeadersBuilder;
import com.eowise.recyclerview.stickyheaders.StickyHeadersItemDecoration;
import com.xclib.recyclerviewtest.R;
import com.xclib.recyclerviewtest.adapter.TestHeaderSectionAdapter;
import com.xclib.recyclerviewtest.model.Person;

import java.util.ArrayList;
import java.util.List;

public class HeaderSectionActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_header_section);


        String[] personInfos = this.getResources().getStringArray(R.array.person_info);

        List<Person> personList = new ArrayList<>();
        for (String personName : personInfos) {
            personList.add(new Person(personName));
        }

        RecyclerView list = (RecyclerView) findViewById(R.id.list);

        list.setLayoutManager(new LinearLayoutManager(HeaderSectionActivity.this, LinearLayoutManager.VERTICAL, false));

        TestHeaderSectionAdapter testHeaderSectionAdapter = new TestHeaderSectionAdapter(this);
        testHeaderSectionAdapter.resetData(personList);

        StickyHeadersItemDecoration top = new StickyHeadersBuilder()
                .setAdapter(testHeaderSectionAdapter)
                .setRecyclerView(list)
                .setStickyHeadersAdapter(testHeaderSectionAdapter.getStickyHeadersAdapter())
                .setOnHeaderClickListener(new OnHeaderClickListener() {
                    @Override
                    public void onHeaderClick(View header, long headerId) {
                        TextView text = (TextView) header.findViewById(R.id.title);
                        Toast.makeText(getApplicationContext(), "Click on " + text.getText(), Toast.LENGTH_SHORT).show();
                    }
                })
                .build();

        list.setAdapter(testHeaderSectionAdapter);
        list.addItemDecoration(top);
    }

}