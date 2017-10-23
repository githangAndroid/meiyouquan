package com.mayinews.g.home.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.github.chrisbanes.photoview.PhotoView;

import java.util.List;

/**
 * Created by Administrator on 2017/9/14 0014.
 * 写真的图片Viewpager的适配器
 */

public class ImagesAdapter extends PagerAdapter{
    private Context context;
    private List<String> imageUrls;
    ImageOnClickListener listener;
    public interface ImageOnClickListener{
        public void onClick(int position);
    }

    public  void setImageListener(ImageOnClickListener listener){
        this.listener=listener;
    }

    public ImagesAdapter(Context context, List<String> imageUrls) {
        this.context = context;
        this.imageUrls = imageUrls;
    }

    @Override
    public int getCount() {
        return imageUrls.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }


    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
//        ImageView imageView = new ZoomImageView(context);
        PhotoView imageView = new PhotoView(context);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
//        imageView.setAdjustViewBounds(true);


        Log.e("TAG","PA"+imageUrls.get(position));
        Glide.with(context).load(buildGlideUrl("http://static.mayinews.com"+imageUrls.get(position)))

                .into(imageView);


        Log.e("TAG","listener="+listener);
        if(listener!=null){

            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    listener.onClick(position);
                }
            });

        }

            container.addView(imageView);
                return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

        container.removeView((View) object);
    }
    private GlideUrl buildGlideUrl(String url) {
        if (TextUtils.isEmpty(url)) {
            return null;
        } else {
            return new GlideUrl(url, new LazyHeaders.Builder().addHeader("Referer", "http://m.mayinews.com").build());
        }
    }


    /**
     * 设置添加屏幕的背景透明度
     *
     * @param bgAlpha
     */
    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = ((Activity)context).getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        ((Activity)context).getWindow().setAttributes(lp);
    }
}
