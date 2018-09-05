package com.yangzhenyu.customview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

public class GuideActivity extends AppCompatActivity {

    private RelativeLayout rootView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        rootView = (RelativeLayout) findViewById(R.id.root_view);

        GuideView guideView = new GuideView(this);
        rootView.addView(guideView);
    }
}
