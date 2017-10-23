package com.mayinews.g.home.adapter;

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

import com.mayinews.g.utils.DisplayUtil;

/**
 * Created by Administrator on 2017/8/30 0030.
 * vlayout 主页下面的RecyclerView的适配器
 */

public class VRcAdapter extends RecyclerView.Adapter<VRcAdapter.MyViewHolder> {
    private Context context;
    private LayoutInflater inflater;
    private List<HomeReBean.ResultBean> datas=new ArrayList<>();

    //定义接口
    public interface OnItemClickLitener
    {
        //点击事件
        void onItemClick(View view, int position);
    }

    private OnItemClickLitener mOnItemClickLitener;

    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener)
    {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }

    public VRcAdapter(Context context) {
        this.context = context;

        inflater=LayoutInflater.from(context);
    }

    public void addData( List<HomeReBean.ResultBean>  datas){
        this.datas = datas;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.i("TAG","onCreateViewHolder");
        View view = inflater.inflate(R.layout.home_recycler_item, parent, false);


        MyViewHolder myViewHolder = new MyViewHolder(view);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        Log.i("TAG","onBindViewHolder");
        HomeReBean.ResultBean dataBean = datas.get(position);
        float width  = (getScreenWidth(context)-DisplayUtil.dp2px(context,15))/2;
        ViewGroup.LayoutParams layoutParams = holder.item_iv.getLayoutParams();
        layoutParams.height=(int)(width*16/9);
        holder.item_iv.setLayoutParams(layoutParams);
        Log.e("TAG","item宽度"+width);
        Log.e("TAG","item高度"+(int)(width*16/9));
        Glide.with(context).load(buildGlideUrl("http://static.mayinews.com"+dataBean.getCover())).into(holder.item_iv);
        holder.item_tv.setText(dataBean.getActor());


        //如果设置了回调，则设置点击事件
        if(mOnItemClickLitener!=null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                     mOnItemClickLitener.onItemClick(holder.itemView,position);
                }
            });
        }


    }

    @Override
    public int getItemCount() {

        return datas.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView item_iv;
        TextView item_tv;

        public MyViewHolder(View itemView) {
            super(itemView);
            item_iv = (ImageView) itemView.findViewById(R.id.item_iv);
            item_tv = (TextView) itemView.findViewById(R.id.item_tv);
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








