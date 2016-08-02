package com.kingyon.newsdemo;

import android.os.Bundle;
import android.widget.TextView;

import com.kingyon.baseuilib.activities.BaseActivity;
import com.kingyon.newslib.uis.fragments.NewsTabFragment;

public class MainActivity extends BaseActivity {
    @Override
    protected int getContentViewID() {
        return R.layout.activity_main;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        getSupportFragmentManager().beginTransaction().add(R.id.layout_content,new NewsTabFragment()).commit();
    }
}
