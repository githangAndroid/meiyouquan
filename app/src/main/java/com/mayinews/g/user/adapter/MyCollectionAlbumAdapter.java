package com.mayinews.g.user.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.mayinews.g.R;
import com.mayinews.g.home.activity.PhotosActivity;
import com.mayinews.g.user.bean.MyAlbumCollectionBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/11/6 0006.
 */

public class MyCollectionAlbumAdapter extends RecyclerView.Adapter<MyCollectionAlbumAdapter.MyViewHolder> {

    //定义接口回调
    public interface  CancleAttenListener{
        void deleteData(boolean isChecked,int position);  //删除数据
    }
    private CancleAttenListener cancleAttenListener;
    // 暴露接口

    public void  setCancleAttenListener(CancleAttenListener cancleAttenListener){
        this.cancleAttenListener=cancleAttenListener;

    }
    private Context context;
    private LayoutInflater inflater;
    private List<MyAlbumCollectionBean.ResultBean> data = new ArrayList<>();
    private boolean isChoose = false;//是否出现选择的样式

    public MyCollectionAlbumAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    public void addData(List<MyAlbumCollectionBean.ResultBean> data) {


        this.data = data;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.mycollection_album_item, viewGroup, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder myViewHolder, int i) {
        //绑定数据
        MyAlbumCollectionBean.ResultBean resultBean = data.get(i);
        Log.e("TAG","   AVATAR   "+resultBean.getCover());
        Glide.with(context).load(buildGlideUrl("http://static.mayinews.com" + resultBean.getCover()))
                .into(myViewHolder.imageView);
        if (!isChoose) {
            myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    //进入详情页

                    Intent intent = new Intent(context, PhotosActivity.class);
                    intent.putExtra("gid", resultBean.getId());
                    intent.putExtra("cover",resultBean.getCover());
                    context.startActivity(intent);
                }
            });

        }

        //在onBindViewHolder中做显示和隐藏的操作.

        myViewHolder.setIsRecyclable(false); // 为了条目不复用
        if (isChoose) {
            myViewHolder.coverImage.setVisibility(View.VISIBLE);

        } else {
            myViewHolder.coverImage.setVisibility(View.GONE);
        }


        myViewHolder.coverImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(myViewHolder.checkBox.isChecked()){
                    myViewHolder.checkBox.setChecked(false);
                }else{
                    myViewHolder.checkBox.setChecked(true);
                }

            }
        });


        myViewHolder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                   cancleAttenListener.deleteData(isChecked,i);
            }
        });
    }

    @Override
    public int getItemCount() {

        return data == null ? 0 : data.size();
    }


    static class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.imageView)
        ImageView imageView;
        @BindView(R.id.checkBox)
        CheckBox checkBox;
        @BindView(R.id.cover_image)
        RelativeLayout coverImage;
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


    public void setEditMode(boolean isChoose) {
        this.isChoose = isChoose;
        notifyDataSetChanged();

    }

}
