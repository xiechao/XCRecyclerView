package com.xclib.recyclerviewtest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    @Bind(R.id.btn_linear_layout_manager)
    Button btnLinearLayoutManager;
    @Bind(R.id.btn_relative_layout_manager)
    Button btnRelativeLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @OnClick({R.id.btn_linear_layout_manager})
    public void onClickLinearLayout(View view) {
        Intent intent = new Intent(this, ManagerLinearLayoutActivity.class);

        startActivity(intent);
    }

    @OnClick({R.id.btn_relative_layout_manager})
    public void onClickRelativeLayout(View view) {
        Intent intent = new Intent(this, ManagerGridLayoutActivity.class);

        startActivity(intent);
    }
}