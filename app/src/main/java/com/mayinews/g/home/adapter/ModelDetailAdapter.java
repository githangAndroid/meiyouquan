package com.mayinews.g.home.adapter;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.mayinews.g.R;
import com.mayinews.g.app.MyApplication;
import com.mayinews.g.home.activity.PhotosActivity;
import com.mayinews.g.home.bean.HomeReBean;
import com.mayinews.g.home.bean.ModelDetailBean1;
import com.mayinews.g.home.bean.ModelDetailBean2;
import com.mayinews.g.user.activity.LoginActivity;
import com.mayinews.g.utils.SPUtils;
import com.w4lle.library.NineGridAdapter;
import com.w4lle.library.NineGridlayout;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Administrator on 2017/9/11 0011.
 */

public class ModelDetailAdapter extends RecyclerView.Adapter<ModelDetailAdapter.MyViewHolder> {


    ImageView share;
    private LayoutInflater inflater;
    private Context context;
    ModelDetailBean1.ResultBean data;       // 描述时间等信息
    List<ModelDetailBean2.ResultBean> result=new ArrayList<>();   //完全的图片信息
    private Adapter adapter;
    private int beanPosition;


    public ModelDetailAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }


    public void addData(ModelDetailBean1.ResultBean data, List<ModelDetailBean2.ResultBean> result) {
        this.data = data;
        this.result = result;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //创建ItemView
        View view = inflater.inflate(R.layout.model_detail_item, parent, false);
        MyViewHolder holder = new MyViewHolder(view);



        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        beanPosition=position;
        ModelDetailBean2.ResultBean resultBean = result.get(position);
        Glide.with(context).load(buildGlideUrl("http://static.mayinews.com"+data.getAvatar())).into(holder.headView);
        holder.desc.setText(resultBean.getDescription());
        holder.username.setText(data.getNickname());
        long l = Long.parseLong(resultBean.getCreate_time());
        Date date = new Date(l);
        String upTime = date.getMonth()+1 +"月"+(date.getDay()+1)+"日";
        holder.updateTime.setText(upTime);
        List<String> picture = resultBean.getPicture();
        String view = resultBean.getView();
        holder.view.setText("已被"+view+"人看过");

        //设置图片九宫格



        if (picture.size() > 0) {

//                Glide.with(context).load(buildGlideUrl("http://static.mayinews.com"+picture.get(8))).into(holder.image9);

//             List<String> image = picture.subList(0, 8);
             List<String> image = picture;
            adapter = new Adapter(context, image);
             holder.ivMore.setAdapter(adapter);
             holder.ivMore.setOnItemClickListerner(new NineGridlayout.OnItemClickListerner() {
                 @Override
                 public void onItemClick(View view, int position) {


                     String status = (String) SPUtils.get(context, MyApplication.LOGINSTATUES, "0");
                     if (status.equals("1")) {
                         //进入viewpager显示图片
                         Intent intent = new Intent(context, PhotosActivity.class);
                         String actor_avatar = data.getAvatar();
                         String description = data.getDesc();
                         List<String> images = result.get(beanPosition).getPicture();

                         intent.putExtra("avatar",actor_avatar);
                         intent.putExtra("desc",description);
                         intent.putExtra("gid",data.getId());//专辑的id
                         intent.putStringArrayListExtra("images", (ArrayList<String>) images);
                         context.startActivity(intent);

                     }else{

                         context.startActivity(new Intent(context, LoginActivity.class));
                     }

                 }
             });






        }



    }

    @Override
    public int getItemCount() {
        return result.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {

        public CircleImageView headView;
        public TextView username;
        public TextView updateTime;
        LinearLayout images;
        public TextView desc;
       public  NineGridlayout ivMore;
        public TextView view;

        public MyViewHolder(View itemView) {
            super(itemView);
            headView = (CircleImageView) itemView.findViewById(R.id.headView);
            username = (TextView) itemView.findViewById(R.id.username);
            updateTime = (TextView) itemView.findViewById(R.id.update_time);
            desc = (TextView) itemView.findViewById(R.id.desc);
            ivMore = (NineGridlayout) itemView.findViewById(R.id.nine_grid);
             view = (TextView) itemView.findViewById(R.id.view);




        }
    }



    private GlideUrl buildGlideUrl(String url) {
        if (TextUtils.isEmpty(url)) {
            return null;
        } else {
            return new GlideUrl(url, new LazyHeaders.Builder().addHeader("Referer", "http://m.mayinews.com").build());
        }
    }




    class Adapter extends NineGridAdapter {

        public Adapter(Context context, List list) {
            super(context, list);
        }

        @Override
        public int getCount() {
            return (list == null) ? 0 : list.size();
        }

        @Override
        public String getUrl(int position) {
            return getItem(position) == null ? null : (String)getItem(position);
        }

        @Override
        public Object getItem(int position) {
            return (list == null) ? null : list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int i, View view) {
            ImageView iv = null;
            if (view != null && view instanceof ImageView) {
                iv = (ImageView) view;
            } else {
                iv = new ImageView(context);
            }
            iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
            iv.setBackgroundColor(context.getResources().getColor((android.R.color.transparent)));
            String url = getUrl(i);
            Glide.with(context).load(buildGlideUrl("http://static.mayinews.com"+url)).into(iv);
//            Picasso.with(context).load(getUrl(i)).placeholder(new ColorDrawable(Color.parseColor("#f5f5f5"))).into(iv);
            if (!TextUtils.isEmpty(url)) {
                iv.setTag(url);
            }
            return iv;
        }
    }



}