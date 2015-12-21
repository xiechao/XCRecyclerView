package com.xclib.recyclerviewtest.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.eowise.recyclerview.stickyheaders.OnHeaderClickListener;
import com.eowise.recyclerview.stickyheaders.StickyHeadersBuilder;
import com.eowise.recyclerview.stickyheaders.StickyHeadersItemDecoration;
import com.xclib.recyclerviewtest.R;
import com.xclib.recyclerviewtest.adapter.BigramHeaderAdapter;
import com.xclib.recyclerviewtest.adapter.PersonAdapter;
import com.xclib.recyclerviewtest.model.Person;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class HeaderSectionActivity  extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_header_section);


        String[] personInfos = this.getResources().getStringArray(R.array.person_info);

        List<Person> personList = new ArrayList<>();
        for (String personName : personInfos) {
            personList.add(new Person(personName, ""));
        }


        Collections.sort(personList, new Comparator<Person>() {
            @Override
            public int compare(Person lhs, Person rhs) {

                String lhsSortKey = lhs.getSortKey();
                String rhsSortKey = rhs.getSortKey();

                if (!TextUtils.isEmpty(lhsSortKey)) {
                    lhsSortKey = lhsSortKey.trim().toUpperCase();
                }

                if (lhsSortKey == null) {
                    lhsSortKey = "";
                }

                if (!TextUtils.isEmpty(rhsSortKey)) {
                    rhsSortKey = rhsSortKey.trim().toUpperCase();
                }

                if (rhsSortKey == null) {
                    rhsSortKey = "";
                }

                return lhsSortKey.compareTo(rhsSortKey);
            }
        });

        RecyclerView list = (RecyclerView) findViewById(R.id.list);

        list.setLayoutManager(new LinearLayoutManager(HeaderSectionActivity.this, LinearLayoutManager.VERTICAL, false));

        PersonAdapter<Person> personAdapter = new PersonAdapter<>(personList);

        StickyHeadersItemDecoration top = new StickyHeadersBuilder()
                .setAdapter(personAdapter)
                .setRecyclerView(list)
                .setStickyHeadersAdapter(new BigramHeaderAdapter<>(personList))
                .setOnHeaderClickListener(new OnHeaderClickListener() {
                    @Override
                    public void onHeaderClick(View header, long headerId) {
                        TextView text = (TextView) header.findViewById(R.id.title);
                        Toast.makeText(getApplicationContext(), "Click on " + text.getText(), Toast.LENGTH_SHORT).show();
                    }
                })
                .build();

        list.setAdapter(personAdapter);
        list.addItemDecoration(top);
    }

}