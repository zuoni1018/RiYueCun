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
import com.zuoni.riyuecun.bean.gson.GetMessageList;
import com.zuoni.riyuecun.ui.activity.WebViewActivity;
import com.zuoni.riyuecun.util.ImageLoaderUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zangyi_shuai_ge on 2017/9/1
 */

public class RvMainNewsAdapter extends RecyclerView.Adapter<RvMainNewsAdapter.MyViewHolder> {

    private Context mContext;
    private List<GetMessageList.Model1Bean> mList;
    private LayoutInflater mInflater;

    public RvMainNewsAdapter(Context mContext, List<GetMessageList.Model1Bean> mList) {
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
        View mView = mInflater.inflate(R.layout.rv_main_news_item, parent, false);
        return new MyViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        ImageLoaderUtils.setStoredValueCardImage(mContext,mList.get(position).getMessageImage(),holder.MessageImage);

        holder.MessageDescribe.setText(mList.get(position).getMessageDescribe());
        holder.MessageName.setText(mList.get(position).getMessageName());

        holder.MessageImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent=new Intent(mContext, WebViewActivity.class);
                mIntent.putExtra("MessageId",mList.get(position).getMessageID()+"");
                mContext.startActivity(mIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        SelectableRoundedImageView MessageImage;
        TextView MessageName, MessageDescribe;

        MyViewHolder(View itemView) {
            super(itemView);

            MessageImage = (SelectableRoundedImageView) itemView.findViewById(R.id.MessageImage);
            MessageName = (TextView) itemView.findViewById(R.id.MessageName);
            MessageDescribe = (TextView) itemView.findViewById(R.id.MessageDescribe);
//            tvStartReadDate= (TextView) itemView.findViewById(R.id.tvStartReadDate);
//            tvRemark= (TextView) itemView.findViewById(R.id.tvRemark);
//            tvEndReadDate= (TextView) itemView.findViewById(R.id.tvEndReadDate);
//            layoutMain= (LinearLayout) itemView.findViewById(R.id.layoutMain);
        }
    }
}
