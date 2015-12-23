package com.xclib.recyclerviewtest.activities;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.eowise.recyclerview.stickyheaders.OnHeaderClickListener;
import com.eowise.recyclerview.stickyheaders.StickyHeadersBuilder;
import com.eowise.recyclerview.stickyheaders.StickyHeadersItemDecoration;
import com.xclib.recyclerview.DividerItemDecoration;
import com.xclib.recyclerview.PinnedSectionTitleIndicator;
import com.xclib.recyclerview.XCRecycleView;
import com.xclib.recyclerviewtest.R;
import com.xclib.recyclerviewtest.adapter.PinnedSectionAdapter;
import com.xclib.recyclerviewtest.model.Person;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import xyz.danoz.recyclerviewfastscroller.vertical.VerticalRecyclerViewFastScroller;

public class PinnedSectionActivity extends BaseActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.recycler_view)
    XCRecycleView recyclerView;
    @Bind(R.id.fast_scroller)
    VerticalRecyclerViewFastScroller fastScroller;
    @Bind(R.id.fast_scroller_section_title_indicator)
    PinnedSectionTitleIndicator fastScrollerSectionTitleIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pinned_section);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Pinned Section");
        }

        // Connect the recycler to the scroller (to let the scroller scroll the list)
        fastScroller.setRecyclerView(recyclerView);

        // Connect the scroller to the recycler (to let the recycler scroll the scroller's handle)
        recyclerView.addOnScrollListener(fastScroller.getOnScrollListener());

        // Connect the section indicator to the scroller
        fastScroller.setSectionIndicator(fastScrollerSectionTitleIndicator);

        String[] personInfos = this.getResources().getStringArray(R.array.person_info);

        List<Person> personList = new ArrayList<>();
        for (String personName : personInfos) {
            personList.add(new Person(personName));
        }

        recyclerView.setLayoutManager(new LinearLayoutManager(PinnedSectionActivity.this, LinearLayoutManager.VERTICAL, false));

        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        PinnedSectionAdapter pinnedSectionAdapter = new PinnedSectionAdapter(this);
        pinnedSectionAdapter.setItems(personList);

        StickyHeadersItemDecoration top = new StickyHeadersBuilder()
                .setAdapter(pinnedSectionAdapter)
                .setRecyclerView(recyclerView)
                .setStickyHeadersAdapter(pinnedSectionAdapter.getStickyHeadersAdapter())
                .setOnHeaderClickListener(new OnHeaderClickListener() {
                    @Override
                    public void onHeaderClick(View header, long headerId) {
                        TextView text = (TextView) header.findViewById(R.id.title);
                        Toast.makeText(getApplicationContext(), "Click on " + text.getText(), Toast.LENGTH_SHORT).show();
                    }
                })
                .build();

        recyclerView.setAdapter(pinnedSectionAdapter);
        recyclerView.addItemDecoration(top);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        recyclerView.removeOnScrollListener(fastScroller.getOnScrollListener());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}