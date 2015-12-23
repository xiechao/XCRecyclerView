package com.xclib.recyclerviewtest.activities;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;

import com.daimajia.swipe.util.Attributes;
import com.eowise.recyclerview.stickyheaders.StickyHeadersBuilder;
import com.eowise.recyclerview.stickyheaders.StickyHeadersItemDecoration;
import com.xclib.recyclerview.DividerItemDecoration;
import com.xclib.recyclerview.PinnedSectionTitleIndicator;
import com.xclib.recyclerview.XCRecycleView;
import com.xclib.recyclerviewtest.R;
import com.xclib.recyclerviewtest.adapter.PinnedSectionSwipeAdapter;
import com.xclib.recyclerviewtest.model.Person;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import xyz.danoz.recyclerviewfastscroller.vertical.VerticalRecyclerViewFastScroller;

public class PinnedSectionSwipeActivity extends BaseActivity {


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

        // Connect the recycler to the scroller (to let the scroller scroll the list)
        fastScroller.setRecyclerView(recyclerView);

        // Connect the scroller to the recycler (to let the recycler scroll the scroller's handle)
        recyclerView.addOnScrollListener(fastScroller.getOnScrollListener());

        // Connect the section indicator to the scroller
        fastScroller.setSectionIndicator(fastScrollerSectionTitleIndicator);

//        setRecyclerViewLayoutManager(recyclerView);


        String[] personInfos = this.getResources().getStringArray(R.array.person_info);

        List<Person> personList = new ArrayList<>();
        for (String personName : personInfos) {
            personList.add(new Person(personName));
        }

        recyclerView.setLayoutManager(new LinearLayoutManager(PinnedSectionSwipeActivity.this, LinearLayoutManager.VERTICAL, false));

        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        PinnedSectionSwipeAdapter pinnedSectionSwipeAdapter = new PinnedSectionSwipeAdapter(this);
        pinnedSectionSwipeAdapter.resetData(personList);
        pinnedSectionSwipeAdapter.setMode(Attributes.Mode.Single);

        StickyHeadersItemDecoration top = new StickyHeadersBuilder()
                .setAdapter(pinnedSectionSwipeAdapter)
                .setRecyclerView(recyclerView)
                .setStickyHeadersAdapter(pinnedSectionSwipeAdapter.getStickyHeadersAdapter())
//                .setOnHeaderClickListener(new OnHeaderClickListener() {
//                    @Override
//                    public void onHeaderClick(View header, long headerId) {
//                        TextView text = (TextView) header.findViewById(R.id.title);
//                        Toast.makeText(getApplicationContext(), "Click on " + text.getText(), Toast.LENGTH_SHORT).show();
//                    }
//                })
                .build();

        recyclerView.setAdapter(pinnedSectionSwipeAdapter);
        recyclerView.addItemDecoration(top);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        recyclerView.removeOnScrollListener(fastScroller.getOnScrollListener());
    }
}