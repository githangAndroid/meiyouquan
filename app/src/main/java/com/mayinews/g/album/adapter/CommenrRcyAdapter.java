package com.mayinews.g.album.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.mayinews.g.R;
import com.mayinews.g.album.bean.FollowingBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 评论列表popwindow上面的RecyclerView的adapter
 */

public class CommenrRcyAdapter extends RecyclerView.Adapter<CommenrRcyAdapter.MyViewHolder> {


    private Context context;
    List<FollowingBean.ResultBean> data = new ArrayList<>();


    public CommenrRcyAdapter(Context context, List<FollowingBean.ResultBean> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        View view = View.inflate(context, R.layout.comment_recycler_item, viewGroup);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder myViewHolder, int i) {
        FollowingBean.ResultBean resultBean = data.get(i);
        Glide.with(context).load(buildGlideUrl(resultBean.getAvatar())).into(myViewHolder.headView);

    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        CircleImageView headView;

        public MyViewHolder(View itemView) {
            super(itemView);
            headView= (CircleImageView) itemView.findViewById(R.id.headView);
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
