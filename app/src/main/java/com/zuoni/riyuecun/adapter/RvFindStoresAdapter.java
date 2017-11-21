package com.zuoni.riyuecun.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zuoni.riyuecun.R;
import com.zuoni.riyuecun.bean.model.Store;
import com.zuoni.riyuecun.ui.activity.FindStoresActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zangyi_shuai_ge on 2017/9/1
 */

public class RvFindStoresAdapter extends RecyclerView.Adapter<RvFindStoresAdapter.MyViewHolder> {

    private Context mContext;
    private List<Store> mList;
    private LayoutInflater mInflater;

    public RvFindStoresAdapter(Context mContext, List<Store> mList) {
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
        View mView = mInflater.inflate(R.layout.rv_find_stores_item, parent, false);
        return new MyViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.layoutMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mIntent=new Intent(mContext, FindStoresActivity.class);
                mIntent.putExtra("store",mList.get(position));
                mContext.startActivity(mIntent);
            }
        });
        holder.Adress.setText(mList.get(position).getAdress());
        holder.StoreName.setText(mList.get(position).getStoreName());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        LinearLayout layoutMain;
        TextView Adress,StoreName;


        MyViewHolder(View itemView) {
            super(itemView);
            layoutMain=(LinearLayout)itemView.findViewById(R.id.layoutMain);
            Adress=itemView.findViewById(R.id.Adress);
            StoreName=itemView.findViewById(R.id.StoreName);
        }
    }
}
