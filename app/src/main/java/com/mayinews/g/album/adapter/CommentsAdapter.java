package com.mayinews.g.album.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.mayinews.g.R;
import com.mayinews.g.album.bean.CommentBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Administrator on 2017/10/27 0027.
 */

public class CommentsAdapter extends BaseAdapter {
    private Context context;
    List<CommentBean.ResultBean> comments = new ArrayList<>();

    public void addData(List<CommentBean.ResultBean> comments) {
        this.comments = comments;

    }

    public CommentsAdapter(Context context) {
        this.context = context;

    }

    @Override
    public int getCount() {
        return comments.size();
    }

    @Override
    public Object getItem(int position) {
        return comments.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        CommentBean.ResultBean data = comments.get(position);
        ViewHolder viewHolder=null;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.comment_list_pop_item,null);
            viewHolder=new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else{

            viewHolder= (ViewHolder) convertView.getTag();
        }


        Glide.with(context).load(buildGlideUrl(data.getAvatar())).into(viewHolder.headView);
        viewHolder.tvNickname.setText(data.getNickname());
        viewHolder.tvComment.setText(data.getComment());




        return convertView;
    }



    static class ViewHolder {
        @BindView(R.id.headView)
        CircleImageView headView;
        @BindView(R.id.tv_nickname)
        TextView tvNickname;
        @BindView(R.id.tv_comment)
        TextView tvComment;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }


    private GlideUrl buildGlideUrl(String url) {
        if (TextUtils.isEmpty(url)) {
            return null;
        } else {
            return new GlideUrl(url, new LazyHeaders.Builder().addHeader("Referer", "http://m.mayinews.com").build());
        }
    }
}
