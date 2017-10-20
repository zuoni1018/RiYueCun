package com.zuoni.riyuecun.ui.fragment.club;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zuoni.riyuecun.R;
import com.zuoni.riyuecun.adapter.RvClubCardAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by zangyi_shuai_ge on 2017/10/19
 */

public class ClubRightFragment extends Fragment {
    Unbinder unbinder;
    @BindView(R.id.mRecyclerView)
    RecyclerView mRecyclerView;
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_club_right, null);
        unbinder = ButterKnife.bind(this, view);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));

        List<String> mList=new ArrayList<>();
        for (int i = 0; i <5 ; i++) {
            mList.add("");
        }
        RvClubCardAdapter adapter=new RvClubCardAdapter(getContext(),mList);
        mRecyclerView.setAdapter(adapter);


        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    private void jumpToActivity(Class<?> cls) {
        Intent mIntent = new Intent(getContext(), cls);
        startActivity(mIntent);
    }
}
