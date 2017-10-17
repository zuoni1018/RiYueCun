package com.zuoni.riyuecun.ui.activity.base;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zuoni.riyuecun.R;

/**
 * Created by zangyi_shuai_ge on 2017/10/16
 */

public abstract class BaseTitleActivity extends BaseActivity {
    private RelativeLayout layoutLeft;
    private TextView tvTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        layoutLeft = (RelativeLayout) findViewById(R.id.layoutLeft);
        tvTitle = (TextView) findViewById(R.id.tvTitle);

        layoutLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myFinish();
            }
        });
    }

    public void setTitle(String title) {
        tvTitle.setText(title);
    }
}
