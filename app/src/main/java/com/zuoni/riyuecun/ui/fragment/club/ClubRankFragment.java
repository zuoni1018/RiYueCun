package com.zuoni.riyuecun.ui.fragment.club;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zuoni.riyuecun.R;

/**
 * Created by zangyi_shuai_ge on 2017/10/20
 */

public class ClubRankFragment extends Fragment {
    private View view;
    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_club_rank,null);
        return view;
    }
}
