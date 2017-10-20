package com.zuoni.riyuecun.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.joooonho.SelectableRoundedImageView;
import com.zuoni.common.utils.DensityUtils;
import com.zuoni.common.utils.ScreenUtils;
import com.zuoni.riyuecun.GlobalVariable;
import com.zuoni.riyuecun.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zangyi_shuai_ge on 2017/9/1
 */

public class RvClubCardAdapter extends RecyclerView.Adapter<RvClubCardAdapter.MyViewHolder> {

    private Context mContext;
    private List<String> mList;
    private LayoutInflater mInflater;

    public RvClubCardAdapter(Context mContext, List<String> mList) {
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
        View mView = mInflater.inflate(R.layout.rv_club_card_item, parent, false);
        return new MyViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        setCardSize(holder.ivCard);

        Glide.with(mContext)
                .load(GlobalVariable.TEST_IMAGE_URL)
                .asBitmap()
                .into(holder.ivCard);


    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    private void setCardSize(SelectableRoundedImageView ivCard) {
        LinearLayout.LayoutParams l = (LinearLayout.LayoutParams) ivCard.getLayoutParams();
        l.width = (int) (((ScreenUtils.getScreenWidth(mContext)- DensityUtils.dp2px(mContext,48))/3));
        l.height = (int) (l.width * GlobalVariable.CARD_PROPORTION_1);
        ivCard.setLayoutParams(l);
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        SelectableRoundedImageView ivCard;

        MyViewHolder(View itemView) {
            super(itemView);
            ivCard = (SelectableRoundedImageView) itemView.findViewById(R.id.ivCard);
        }
    }
}