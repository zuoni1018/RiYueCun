package com.zuoni.riyuecun.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zuoni.riyuecun.R;
import com.zuoni.riyuecun.bean.model.ConsumptionRecord;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zangyi_shuai_ge on 2017/9/1
 */

public class RvRecordsOfConsumptionAdapter extends RecyclerView.Adapter<RvRecordsOfConsumptionAdapter.MyViewHolder> {

    private Context mContext;
    private List<ConsumptionRecord> mList;
    private LayoutInflater mInflater;
    private SpannableStringBuilder builder;
    private ForegroundColorSpan redSpan;

    public RvRecordsOfConsumptionAdapter(Context mContext, List<ConsumptionRecord> mList) {
        this.mContext = mContext;
        if (mList != null) {
            this.mList = mList;
        } else {
            this.mList = new ArrayList<>();
        }
        mInflater = LayoutInflater.from(mContext);
         redSpan = new ForegroundColorSpan(Color.RED);

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mView = mInflater.inflate(R.layout.rv_records_of_consumption_item, parent, false);
        return new MyViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.layoutMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent mIntent=new Intent(mContext, ConsumptionDetailsActivity.class);
//                mContext.startActivity(mIntent);
            }
        });
        DecimalFormat df = new DecimalFormat("######0.00");
        holder.CardMoney.setText(" ￥ " + df.format(mList.get(position).getCardMoney()));
        holder.ShopName.setText(mList.get(position).getShopName());
        holder.ShopTime.setText(mList.get(position).getShopTime());

        if (mList.get(position).getPhoneNumber() == null) {
            holder.tvPhone.setVisibility(View.GONE);
        } else {
            holder.tvPhone.setVisibility(View.VISIBLE);


            String showPhone = mList.get(position).getPhoneNumber();
            if (showPhone.length() > 8) {
                showPhone = showPhone.substring(0, 3) + " *** " + showPhone.substring(showPhone.length() - 3, showPhone.length());
            }

            builder= new SpannableStringBuilder("异号消费"+showPhone);
            builder.setSpan(redSpan, 0,4, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            holder.tvPhone.setText(builder);
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        LinearLayout layoutMain;

        TextView ShopName, ShopTime, CardMoney, tvPhone;

        MyViewHolder(View itemView) {
            super(itemView);
            layoutMain = (LinearLayout) itemView.findViewById(R.id.layoutMain);
            ShopName = (TextView) itemView.findViewById(R.id.ShopName);
            ShopTime = (TextView) itemView.findViewById(R.id.ShopTime);
            CardMoney = (TextView) itemView.findViewById(R.id.CardMoney);
            tvPhone = (TextView) itemView.findViewById(R.id.tvPhone);
        }
    }
}
