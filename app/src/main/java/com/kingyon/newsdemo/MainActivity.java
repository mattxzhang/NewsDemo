package com.kingyon.newsdemo;

import android.os.Bundle;
import android.widget.TextView;

import com.kingyon.baseuilib.activities.BaseActivity;

public class MainActivity extends BaseActivity {
    TextView tvHello;

    @Override
    protected int getContentViewID() {
        return R.layout.activity_main;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        showProgress(null);
        tvHello = getView(R.id.tv_hello);
        tvHello.setText("say hello~");
    }
}
