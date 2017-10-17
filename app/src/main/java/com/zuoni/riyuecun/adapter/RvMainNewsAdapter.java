package com.zuoni.riyuecun.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.joooonho.SelectableRoundedImageView;
import com.zuoni.riyuecun.GlobalVariable;
import com.zuoni.riyuecun.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zangyi_shuai_ge on 2017/9/1
 */

public class RvMainNewsAdapter extends RecyclerView.Adapter<RvMainNewsAdapter.MyViewHolder> {

    private Context mContext;
    private List<String> mList;
    private LayoutInflater mInflater;

    public RvMainNewsAdapter(Context mContext, List<String> mList) {
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

//        holder.layoutMain.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent mIntent=new Intent(mContext, HtAreaActivity.class);
//                mIntent.putExtra("AreaNo",mList.get(position).getAreaNo());
//                mContext.startActivity(mIntent);
//
//            }
//        });
//        holder.tvAreaName.setText(mList.get(position).getAreaName());
//        holder.tvPayType.setText(mList.get(position).getPayType());
//        holder.tvAreaNo.setText(mList.get(position).getAreaNo());
//        holder.tvStartReadDate.setText(mList.get(position).getStartReadDate());
//        holder.tvEndReadDate.setText(mList.get(position).getEndReadDate());
//        holder.tvRemark.setText(mList.get(position).getRemark());
        Glide.with(mContext)
                .load(GlobalVariable.TEST_IMAGE_URL)
                .asBitmap()
                .into(holder.zzzzz);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        SelectableRoundedImageView zzzzz;

        MyViewHolder(View itemView) {
            super(itemView);

            zzzzz= (SelectableRoundedImageView) itemView.findViewById(R.id.zzzzz);
//            tvPayType= (TextView) itemView.findViewById(R.id.tvPayType);
//            tvAreaNo= (TextView) itemView.findViewById(R.id.tvAreaNo);
//            tvStartReadDate= (TextView) itemView.findViewById(R.id.tvStartReadDate);
//            tvRemark= (TextView) itemView.findViewById(R.id.tvRemark);
//            tvEndReadDate= (TextView) itemView.findViewById(R.id.tvEndReadDate);
//            layoutMain= (LinearLayout) itemView.findViewById(R.id.layoutMain);
        }
    }
}
