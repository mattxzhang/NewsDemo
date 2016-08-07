package com.kingyon.newsdemo;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.kingyon.baseuilib.activities.BaseActivity;
import com.kingyon.newslib.uis.fragments.NewsTabFragment;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;

public class MainActivity extends BaseActivity {
    @Override
    protected int getContentViewID() {
        return R.layout.activity_main;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        getSupportFragmentManager().beginTransaction().add(R.id.layout_content,new NewsTabFragment()).commit();
//        Observable.just("hehe","haha").subscribe(new Action1<String>() {
//            @Override
//            public void call(String s) {
//                Log.d("MainActivity",s);
//            }
//        });
//        View viewById = findViewById(R.id.layout_content);
//
//        Observable.create(new ClickSubscribe(viewById))
//                .throttleFirst(500, TimeUnit.MILLISECONDS)
//                .subscribe(new Action1<Void>() {
//            @Override
//            public void call(Void aVoid) {
//                Log.d("MainActivity","hello");
//            }
//        });
    }

    class ClickSubscribe implements Observable.OnSubscribe<Void> {
        View view;

        public ClickSubscribe(View view) {
            this.view = view;
        }

        @Override
        public void call(final Subscriber<? super Void> subscriber) {
            View.OnClickListener listener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    subscriber.onNext(null);
                }
            };
            view.setOnClickListener(listener);
        }
    }
}
