package com.mayinews.g.home.adapter;

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
import com.mayinews.g.home.bean.RankBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Administrator on 2017/9/28 0028.
 */

public class RankListAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;

    private List<RankBean.DataBean> data;
    private final int BEFORETYPE = 0;//定义第一种类型
    private final int NORMALTYPE = 1;//定义第二种类型

    public RankListAdapter(Context context, List<RankBean.DataBean> data) {
        this.context = context;
        this.data = data;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        int itemViewType = getItemViewType(position);
        MyViewHolder1 viewHolder1 = null;
        MyViewHolder2 viewHolder2 = null;
        RankBean.DataBean dataBean = data.get(position);
        //设置view和绑定数据
        if (convertView == null) {
            //确定类型
            switch (itemViewType) {

                case BEFORETYPE:

                    convertView = inflater.inflate(R.layout.rank_item1, parent, false);
                    viewHolder1 = new MyViewHolder1(convertView);
//                    viewHolder1.borderImage.setImageResource(R.drawable.number_one);
                    Glide.with(context).load(dataBean.getCoverPath()).into(viewHolder1.image);
                    viewHolder1.name.setText(dataBean.getUser_name());
                    viewHolder1.readCount.setText("订阅量" + dataBean.getSeeCount());
                    if (position == 0) {
                        viewHolder1.rootView.setBackgroundColor(context.getResources().getColor(R.color.yello_one));
                    } else if (position == 1) {
                        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) viewHolder1.rootView.getLayoutParams();
                        layoutParams.topMargin = 10;
                        layoutParams.bottomMargin = 10;
                        viewHolder1.rootView.setLayoutParams(layoutParams);
                        viewHolder1.rootView.setBackgroundColor(context.getResources().getColor(R.color.blue_two));
                    } else if (position == 2) {
                        viewHolder1.rootView.setBackgroundColor(context.getResources().getColor(R.color.purple_three));
                    }
                    //绑定数据
                    break;
                case NORMALTYPE:

                    convertView = inflater.inflate(R.layout.rank_item2, parent, false);
                    viewHolder2 = new MyViewHolder2(convertView);
                    //绑定数据

                    viewHolder2.tvNumber.setText("NO." + (position + 1));
                    Glide.with(context).load(dataBean.getCoverPath()).into(viewHolder2.image);
                    viewHolder2.name.setText(dataBean.getUser_name());
                    viewHolder2.readCount.setText("订阅量" + dataBean.getSeeCount());
                    if (position == (data.size() - 1)) {

                        viewHolder2.divider.setVisibility(View.GONE);
                    }
                    break;
            }

        }


        return convertView;
    }


    @Override
    public int getItemViewType(int position) {
        if (position < 3) {
            return BEFORETYPE;
        } else {
            return NORMALTYPE;
        }

    }


    @Override
    public int getViewTypeCount() {
        return 2;
    }

    /**
     * q前三个位置的type的viewHolder
     */
    class MyViewHolder1 {

        @BindView(R.id.image)
        CircleImageView image;

        @BindView(R.id.rootView)
        LinearLayout rootView;
        @BindView(R.id.name)
        TextView name;
        @BindView(R.id.read_count)
        TextView readCount;

        public MyViewHolder1(View view) {

            ButterKnife.bind(this, view);
        }
    }

    class MyViewHolder2 {
        @BindView(R.id.tv_number)
        TextView tvNumber;
        @BindView(R.id.image)
        CircleImageView image;
        @BindView(R.id.name)
        TextView name;
        @BindView(R.id.read_count)
        TextView readCount;
        @BindView(R.id.divider)
        View divider;

        MyViewHolder2(View view) {

            ButterKnife.bind(this, view);
        }
    }


}
