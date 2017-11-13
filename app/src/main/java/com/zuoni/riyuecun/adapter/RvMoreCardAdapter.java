package com.zuoni.riyuecun.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.joooonho.SelectableRoundedImageView;
import com.zuoni.riyuecun.R;
import com.zuoni.riyuecun.bean.model.ElectronicCard;
import com.zuoni.riyuecun.ui.activity.PaymentActivity;
import com.zuoni.riyuecun.util.ImageLoaderUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zangyi_shuai_ge on 2017/9/1
 */

public class RvMoreCardAdapter extends RecyclerView.Adapter<RvMoreCardAdapter.MyViewHolder> {

    private Context mContext;
    private List<ElectronicCard> mList;
    private LayoutInflater mInflater;

    public RvMoreCardAdapter(Context mContext, List<ElectronicCard> mList) {
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
        ImageLoaderUtils.setStoredValueCardImage(mContext,mList.get(position).getCardImage(),holder.ivCard);

        holder.tvMoney.setText(" ￥" + mList.get(position).getCardMoney());

        holder.CardName.setText("储值卡(" + mList.get(position).getCardName() + ")" + "有效期至" + mList.get(position).getEffectiveTime());

        holder.ivCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent(mContext, PaymentActivity.class);
                mIntent.putExtra("electronicCard", mList.get(position));
                mContext.startActivity(mIntent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        SelectableRoundedImageView ivCard;
        TextView tvMoney, CardName;

        MyViewHolder(View itemView) {
            super(itemView);
            ivCard = (SelectableRoundedImageView) itemView.findViewById(R.id.ivCard);
            tvMoney = (TextView) itemView.findViewById(R.id.tvMoney);
            CardName = (TextView) itemView.findViewById(R.id.CardName);
        }
    }
}
