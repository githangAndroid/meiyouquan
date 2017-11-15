package com.mayinews.g.album.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.mayinews.g.R;
import com.mayinews.g.home.bean.HomeReBean;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;
import com.mayinews.g.utils.DisplayUtil;

/**
 * Created by Administrator on 2017/8/31 0031.
 */

public class MyLrecyclerAdapter extends RecyclerView.Adapter<MyLrecyclerAdapter.MyViewHolder> {
    //item的背景颜色
       private int[] colors={R.color.canary, R.color.babyblue,R.color.amaranth,R.color.modena};
       private Context context;
       private LayoutInflater iflater;
       private List<HomeReBean.ResultBean>  datas = new ArrayList<>();
         public interface AvaListener{
            public void onAvaListener(int position);
         }
    AvaListener listener;
          public void setAvaListener(AvaListener listener){
              this.listener=listener;
          }
    public MyLrecyclerAdapter(Context context) {
        this.context = context;
        iflater=LayoutInflater.from(context);

    }

    //填充数据
    public void addData(List<HomeReBean.ResultBean>  datas){
        this.datas=datas;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = iflater.inflate(R.layout.album_lrecycler_item, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(view);
          return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        int i = new Random().nextInt(4);
        holder.itemView.setBackgroundColor(colors[i]);

        float width  = (getScreenWidth(context)- DisplayUtil.dp2px(context,15))/2;
        ViewGroup.LayoutParams layoutParams = holder.item_iv.getLayoutParams();
        layoutParams.height=(int)(width*3/2);
        holder.item_iv.setLayoutParams(layoutParams);
        Log.e("TAG","item宽度"+width);
        Log.e("TAG","item高度"+(int)(width*16/9));

        HomeReBean.ResultBean dataBean = datas.get(position);
        Glide.with(context).load(buildGlideUrl("http://static.mayinews.com"+dataBean.getCover())).into(holder.item_iv);
        Glide.with(context).load(buildGlideUrl("http://static.mayinews.com"+dataBean.getActor_avatar())).into(holder.avatar);

        holder.item_tv.setText(dataBean.getActor());
         if(listener!=null){
             holder.avatar.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View view) {
                     listener.onAvaListener(position);
                 }
             });

         }
    }

    @Override
    public int getItemCount() {
        return datas==null?0:datas.size();
    }

    class MyViewHolder extends  RecyclerView.ViewHolder{
        CircleImageView avatar;
        ImageView item_iv;
        TextView  item_tv;
//        TextView share;
        public MyViewHolder(View itemView) {
            super(itemView);
            item_iv= (ImageView) itemView.findViewById(R.id.item_iv);
            item_tv= (TextView) itemView.findViewById(R.id.item_tv);
            avatar= (CircleImageView) itemView.findViewById(R.id.avatar);
//            share= (TextView) itemView.findViewById(R.id.share);
//
//            share.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    showShare();
//                }
//            });
        }
    }
    private GlideUrl buildGlideUrl(String url) {
        if (TextUtils.isEmpty(url)) {
            return null;
        } else {
            return new GlideUrl(url, new LazyHeaders.Builder().addHeader("Referer", "http://m.mayinews.com").build());
        }
    }



    public static int getScreenWidth(Context context) {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.widthPixels;
    }
}
