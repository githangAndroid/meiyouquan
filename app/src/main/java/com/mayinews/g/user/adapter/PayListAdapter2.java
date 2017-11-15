package com.mayinews.g.user.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mayinews.g.R;
import com.mayinews.g.user.bean.PayDataBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Administrator on 2017/10/31 0031.
 */

public class PayListAdapter2 extends BaseExpandableListAdapter {
    private Context context;
    private List<PayDataBean> data = new ArrayList<>();//包含每组的组数据和每组详细信息
    private LayoutInflater inflater;
    int[] colors={R.color.appcolor,R.color.pressed_false_green,R.color.babyblue,R.color.pressed_true_green};
    public void addData(List<PayDataBean> data) {
        this.data = data;

    }

    public PayListAdapter2(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getGroupCount() {
        return data.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return data.get(groupPosition).getMcVipDescribes().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return data.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return data.get(groupPosition).getMcVipDescribes();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    //        分组和子选项是否持有稳定的ID, 就是说底层数据的改变会不会影响到它们。
    @Override
    public boolean hasStableIds() {
        return true;
    }

    //        获取显示指定分组的视图
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupViewHolder groupViewHolder = null;
        PayDataBean payDataBean = data.get(groupPosition);
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.exlistview_group_item, parent, false);
            groupViewHolder = new GroupViewHolder(convertView);
            convertView.setTag(groupViewHolder);
        } else {

            groupViewHolder = (GroupViewHolder) convertView.getTag();
        }

        //处理组数据
//        Random random = new Random();
//        int i = random.nextInt(4);
//        groupViewHolder.llItem.setBackgroundColor(colors[i]);
        Glide.with(context).load(payDataBean.getIconUrl()).into(groupViewHolder.image);
        groupViewHolder.title.setText(payDataBean.getName());
        groupViewHolder.desc.setText(payDataBean.getInfo());
        groupViewHolder.price.setText("¥ " + payDataBean.getPrice());


        return convertView;
    }

    //        获取显示指定分组中的指定子选项的视图
    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildViewHolder childViewHolder = null;
        PayDataBean.McVipDescribesBean mcVipDescribesBean = data.get(groupPosition).getMcVipDescribes().get(childPosition);
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.exlistview_child_item, parent, false);
            childViewHolder = new ChildViewHolder(convertView);
            convertView.setTag(childViewHolder);
        } else {
            childViewHolder = (ChildViewHolder) convertView.getTag();

        }

        //设置数据
        Glide.with(context).load(mcVipDescribesBean.getIconUrl()).into(childViewHolder.circleImage);
        childViewHolder.desc.setText(mcVipDescribesBean.getInfo());

        return convertView;
    }

    //        指定位置上的子元素是否可选中
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }


    //组viewHolder
    static class GroupViewHolder {
        @BindView(R.id.image)
        ImageView image;
        @BindView(R.id.title)
        TextView title;
        @BindView(R.id.desc)
        TextView desc;
        @BindView(R.id.price)
        TextView price;
        @BindView(R.id.open_close)
        TextView openClose;
        @BindView(R.id.ll_item)
        LinearLayout llItem;
        @BindView(R.id.circle_image1)
        CircleImageView circleImage1;
        @BindView(R.id.desc1)
        TextView desc1;
        @BindView(R.id.circle_image3)
        CircleImageView circleImage3;
        @BindView(R.id.desc3)
        TextView desc3;
        @BindView(R.id.circle_image4)
        CircleImageView circleImage4;
        @BindView(R.id.desc4)
        TextView desc4;
        @BindView(R.id.circle_image5)
        CircleImageView circleImage5;
        @BindView(R.id.desc5)
        TextView desc5;
        @BindView(R.id.circle_image6)
        CircleImageView circleImage6;
        @BindView(R.id.desc6)
        TextView desc6;
        @BindView(R.id.ll_six)
        LinearLayout llSix;

        GroupViewHolder(View view) {

            ButterKnife.bind(this, view);
        }
    }

    //child ViewHolder
    static class ChildViewHolder {
        @BindView(R.id.circle_image)
        CircleImageView circleImage;
        @BindView(R.id.desc)
        TextView desc;

        ChildViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}