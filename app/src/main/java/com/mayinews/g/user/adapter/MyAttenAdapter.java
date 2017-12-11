package com.mayinews.g.user.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.mayinews.g.R;
import com.mayinews.g.home.activity.ModelDetailActivity;
import com.mayinews.g.user.bean.MyAttenBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Administrator on 2017/11/14 0014.
 */

public class MyAttenAdapter extends RecyclerView.Adapter<MyAttenAdapter.MyViewHolder> {
    private Context context;
    private List<MyAttenBean.ResultBean> datas = new ArrayList<>();
    private LayoutInflater inflater;

    public MyAttenAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    public void addData(List<MyAttenBean.ResultBean> datas) {
        this.datas = datas;


    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.atten_item, viewGroup, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder myViewHolder, int i) {

        MyAttenBean.ResultBean resultBean = datas.get(i);
        String avatar = resultBean.getAvatar();
        Glide.with(context).load(buildGlideUrl("http://static.mayinews.com" + avatar)).into(myViewHolder.avatar);
        myViewHolder.nickname.setText(resultBean.getNickname());
        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ModelDetailActivity.class);
                String aid = resultBean.getId();
                intent.putExtra("aid",aid);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {


        @BindView(R.id.avatar)
        CircleImageView avatar;
        @BindView(R.id.nickname)
        TextView nickname;

         MyViewHolder(View view) {
             super(view);
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
