package com.zuoni.riyuecun.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
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

    public RvRecordsOfConsumptionAdapter(Context mContext, List<ConsumptionRecord> mList) {
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
        DecimalFormat df   = new DecimalFormat("######0.00");
        holder.CardMoney.setText(" ￥ "+df.format(mList.get(position).getCardMoney()));
        holder.ShopName.setText(mList.get(position).getShopName());
        holder.ShopTime.setText(mList.get(position).getShopTime());

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        LinearLayout layoutMain;

        TextView ShopName,ShopTime,CardMoney;
        MyViewHolder(View itemView) {
            super(itemView);
            layoutMain=(LinearLayout)itemView.findViewById(R.id.layoutMain);
            ShopName= (TextView) itemView.findViewById(R.id.ShopName);
            ShopTime= (TextView) itemView.findViewById(R.id.ShopTime);
            CardMoney= (TextView) itemView.findViewById(R.id.CardMoney);
        }
    }
}
