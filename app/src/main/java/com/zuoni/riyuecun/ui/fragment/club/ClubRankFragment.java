package com.zuoni.riyuecun.ui.fragment.club;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.zuoni.riyuecun.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by zangyi_shuai_ge on 2017/10/20
 */

public class ClubRankFragment extends Fragment {
    @BindView(R.id.webView)
    WebView webView;
    Unbinder unbinder;
    private View view;
    private String webString = "";

    public void setWebString(String webString) {
        this.webString = webString;
    }

    public ClubRankFragment(String webString) {
        this.webString = webString;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_club_rank, null);
        unbinder = ButterKnife.bind(this, view);
        webView.setHorizontalScrollBarEnabled(false);
        WebSettings webSettings = webView.getSettings();
        webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小
        webSettings  .setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webSettings  .setLoadWithOverviewMode(true);
        webView.loadDataWithBaseURL("about:blank",webString, "text/html", "utf-8", null);

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
