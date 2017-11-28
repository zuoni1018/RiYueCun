package com.zuoni.riyuecun.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.joooonho.SelectableRoundedImageView;
import com.zuoni.riyuecun.R;
import com.zuoni.riyuecun.bean.model.Coupon;
import com.zuoni.riyuecun.ui.activity.WebViewActivity2;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zangyi_shuai_ge on 2017/9/1
 */

public class RvMianItem02Adapter extends RecyclerView.Adapter<RvMianItem02Adapter.MyViewHolder> {

    private Context mContext;
    private List<Coupon> mList;
    private LayoutInflater mInflater;

    public RvMianItem02Adapter(Context mContext, List<Coupon> mList) {
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
        View mView = mInflater.inflate(R.layout.rv_mian_01_item, parent, false);
        return new MyViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.CouponDescribe.setText(mList.get(position).getCouponDescribe());
        holder.CouponName.setText(mList.get(position).getCouponName());
        holder.layout3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent=new Intent(mContext, WebViewActivity2.class);
                mIntent.putExtra("title",mList.get(position).getCouponName());
                mIntent.putExtra("CouponRelationId",mList.get(position).getCouponRealtionId());
                mContext.startActivity(mIntent);

            }
        });

        //最后一条数据
        if(position==mList.size()-1){
            holder.layout3.setBackgroundResource(R.drawable.setting_bg_02);
        }else {
            holder.layout3.setBackgroundResource(R.drawable.setting_bg_03);
        }

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        SelectableRoundedImageView ivCard;
        TextView CouponName, CouponDescribe;

        LinearLayout layout3;

        MyViewHolder(View itemView) {
            super(itemView);
            layout3 =  itemView.findViewById(R.id.layout3);
            CouponName = (TextView) itemView.findViewById(R.id.CouponName);
            CouponDescribe = (TextView) itemView.findViewById(R.id.CouponDescribe);
//            CardName = (TextView) itemView.findViewById(R.id.CardName);
        }
    }
}
