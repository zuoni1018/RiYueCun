package com.zuoni.riyuecun.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.joooonho.SelectableRoundedImageView;
import com.zuoni.riyuecun.GlobalVariable;
import com.zuoni.riyuecun.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zangyi_shuai_ge on 2017/9/1
 */

public class RvMoreCardAdapter extends RecyclerView.Adapter<RvMoreCardAdapter.MyViewHolder> {

    private Context mContext;
    private List<String> mList;
    private LayoutInflater mInflater;

    public RvMoreCardAdapter(Context mContext, List<String> mList) {
        this.mContext = mContext;
        if (mList != null) {
            this.mList = mList;
        } else {
            this.mList = new ArrayList<>();
        }
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mView = mInflater.inflate(R.layout.rv_more_card_item, parent, false);
        return new MyViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        Glide.with(mContext)
                .load(GlobalVariable.TEST_IMAGE_URL)
                .asBitmap()
                .into(holder.ivCard);
        holder.tvMoney.setText(mList.get(position));


    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        SelectableRoundedImageView ivCard;
        TextView tvMoney;

        MyViewHolder(View itemView) {
            super(itemView);
            ivCard = (SelectableRoundedImageView) itemView.findViewById(R.id.ivCard);
            tvMoney = (TextView) itemView.findViewById(R.id.tvMoney);
        }
    }
}
