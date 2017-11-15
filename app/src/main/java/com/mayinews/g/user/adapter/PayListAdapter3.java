package com.mayinews.g.user.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mayinews.g.R;
import com.mayinews.g.user.bean.YouBiPayBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/10/31 0031.
 */

public class PayListAdapter3 extends BaseAdapter {

    private Context context;
    private List<YouBiPayBean.DataBean> data = new ArrayList<>();
    private LayoutInflater inflater;

    public PayListAdapter3(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    public void addData(List<YouBiPayBean.DataBean> data) {
        this.data = data;

    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        YouBiPayBean.DataBean dataBean = data.get(position);


        if (convertView == null) {

            convertView = inflater.inflate(R.layout.youbi_listview_item, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else{
        viewHolder = (ViewHolder) convertView.getTag();
     }
       //设置数据
        Glide.with(context).load(dataBean.getImgUrl()).into(viewHolder.image);
        viewHolder.youbiPay.setText(dataBean.getTitle());
        viewHolder.youbiGive.setText(dataBean.getInfo());
        viewHolder.price.setText("¥ "+dataBean.getPrice());
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.image)
        ImageView image;
        @BindView(R.id.youbi_pay)
        TextView youbiPay;
        @BindView(R.id.youbi_give)
        TextView youbiGive;
        @BindView(R.id.price)
        TextView price;
        @BindView(R.id.ll_item)
        LinearLayout llItem;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}