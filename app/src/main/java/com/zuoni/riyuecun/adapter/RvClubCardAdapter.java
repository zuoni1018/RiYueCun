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
import com.zuoni.riyuecun.bean.gson.GetMyCardList;
import com.zuoni.riyuecun.callback.ItemOnClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zangyi_shuai_ge on 2017/9/1
 */

public class RvClubCardAdapter extends RecyclerView.Adapter<RvClubCardAdapter.MyViewHolder> {

    private Context mContext;
    private List<GetMyCardList.ListDataBean> mList;
    private LayoutInflater mInflater;
    private ItemOnClickListener itemOnClickListener;

    public void setItemOnClickListener(ItemOnClickListener itemOnClickListener) {
        this.itemOnClickListener = itemOnClickListener;
    }

    public RvClubCardAdapter(Context mContext, List<GetMyCardList.ListDataBean> mList) {
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
                .load(mList.get(position).getImgUrl())
                .asBitmap()
                .into(holder.ivCard);

        holder.ivCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemOnClickListener.onClick(mList.get(position),position);
            }
        });


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
