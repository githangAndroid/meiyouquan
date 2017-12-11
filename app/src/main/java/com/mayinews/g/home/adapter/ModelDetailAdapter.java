package com.mayinews.g.home.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.mayinews.g.R;
import com.mayinews.g.app.MyApplication;
import com.mayinews.g.home.activity.PhotosActivity;
import com.mayinews.g.home.bean.ModelDetailBean1;
import com.mayinews.g.home.bean.ModelDetailBean2;
import com.mayinews.g.user.activity.LoginActivity;
import com.mayinews.g.utils.DisplayUtil;
import com.mayinews.g.utils.SPUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Administrator on 2017/9/11 0011.
 */

public class ModelDetailAdapter extends RecyclerView.Adapter<ModelDetailAdapter.MyViewHolder> {


    ImageView share;
    private LayoutInflater inflater;
    private Context context;
    ModelDetailBean1.ResultBean data;       // 描述时间等信息
    List<ModelDetailBean2.ResultBean> result = new ArrayList<>();   //完全的图片信息
//    private Adapter adapter;
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
        MyViewHolder holder = new MyViewHolder(view,context);


        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        beanPosition = position;
        ModelDetailBean2.ResultBean resultBean = result.get(position);
        Glide.with(context).load(buildGlideUrl("http://static.mayinews.com" + data.getAvatar())).into(holder.headView);
        holder.desc.setText(resultBean.getDescription());
        holder.username.setText(data.getNickname());
        long l = Long.parseLong(resultBean.getCreate_time());

        SimpleDateFormat time=new SimpleDateFormat("MM月dd日");
//        System.out.println(time.format(System.currentTimeMillis()));
//        Date date = new Date(l);
//        String upTime = date.getMonth() + 1 + "月" + (date.getDay() + 1) + "日";
        String upTime = time.format(l);
        holder.updateTime.setText(upTime);

        List<String> picture = resultBean.getPicture();
        String view = resultBean.getView();
        holder.view.setText("已被" + view + "人看过");
        holder.llNine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goPhoto(position, resultBean);
            }
        });
         //         设置图片九宫格


        if (picture.size() > 0) {

//                Glide.with(context).load(buildGlideUrl("http://static.mayinews.com"+picture.get(8))).into(holder.image9);

//             List<String> image = picture.subList(0, 8);
            List<String> image = picture;
//            adapter = new Adapter(context, image);
//            holder.ivMore.setAdapter(adapter);










            Glide.with(context).load(buildGlideUrl("http://static.mayinews.com"+picture.get(0))).into(holder.img1);
            Glide.with(context).load(buildGlideUrl("http://static.mayinews.com"+picture.get(1))).into(holder.img2);
            Glide.with(context).load(buildGlideUrl("http://static.mayinews.com"+picture.get(2))).into(holder.img3);
            Glide.with(context).load(buildGlideUrl("http://static.mayinews.com"+picture.get(3))).into(holder.img4);
            Glide.with(context).load(buildGlideUrl("http://static.mayinews.com"+picture.get(4))).into(holder.img5);
            Glide.with(context).load(buildGlideUrl("http://static.mayinews.com"+picture.get(5))).into(holder.img6);
            Glide.with(context).load(buildGlideUrl("http://static.mayinews.com"+picture.get(6))).into(holder.img7);
            Glide.with(context).load(buildGlideUrl("http://static.mayinews.com"+picture.get(7))).into(holder.img8);
            Glide.with(context).load(buildGlideUrl("http://static.mayinews.com"+picture.get(8))).into(holder.img9);
        }


    }

    private void goPhoto(int position, ModelDetailBean2.ResultBean resultBean) {
        String status = (String) SPUtils.get(context, MyApplication.LOGINSTATUES, "0");
        if (status.equals("1")) {
            //进入viewpager显示图片
            Intent intent = new Intent(context, PhotosActivity.class);
            String actor_avatar = data.getAvatar();

            List<String> images = result.get(position).getPicture();
            String desc = resultBean.getDescription();//描述
            String cover = resultBean.getCover();//封面
            intent.putExtra("avatar",actor_avatar);
            String title = resultBean.getTitle();
            String aid = data.getId(); //模特id
            intent.putExtra("desc",desc);
            intent.putExtra("cover",cover);
            intent.putExtra("title",title);
            intent.putExtra("aid",aid);

            intent.putExtra("gid",resultBean.getId());//专辑的id
            intent.putStringArrayListExtra("images", (ArrayList<String>) images);
            context.startActivity(intent);

        }else{

            context.startActivity(new Intent(context, LoginActivity.class));
        }
    }

    @Override
    public int getItemCount() {
        return result.size();
    }





    private GlideUrl buildGlideUrl(String url) {
        if (TextUtils.isEmpty(url)) {
            return null;
        } else {
            return new GlideUrl(url, new LazyHeaders.Builder().addHeader("Referer", "http://m.mayinews.com").build());
        }
    }




    static class MyViewHolder extends  RecyclerView.ViewHolder{
        private  Context context;
        @BindView(R.id.headView)
        CircleImageView headView;
        @BindView(R.id.username)
        TextView username;
        @BindView(R.id.update_time)
        TextView updateTime;
        @BindView(R.id.info)
        LinearLayout info;
        @BindView(R.id.desc)
        TextView desc;
        @BindView(R.id.single_imageview)
        ImageView singleImageview;
        @BindView(R.id.img1)
        ImageView img1;
        @BindView(R.id.img2)
        ImageView img2;
        @BindView(R.id.img3)
        ImageView img3;
        @BindView(R.id.img4)
        ImageView img4;
        @BindView(R.id.img5)
        ImageView img5;
        @BindView(R.id.img6)
        ImageView img6;
        @BindView(R.id.img7)
        ImageView img7;
        @BindView(R.id.img8)
        ImageView img8;
        @BindView(R.id.img9)
        ImageView img9;
        @BindView(R.id.ll_nine)
        LinearLayout llNine;
        @BindView(R.id.view)
        TextView view;
        @BindView(R.id.share)
        ImageView share;


        MyViewHolder(View view,Context context) {
            super(view);
            ButterKnife.bind(this, view);
            this.context=context;
            ViewGroup.LayoutParams layoutParams = llNine.getLayoutParams();
            layoutParams.height=getScreenWidth(context)- DisplayUtil.dp2px(context,40);
            llNine.setLayoutParams(layoutParams);
//            llNine.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Toast.makeText(context, "999", Toast.LENGTH_SHORT).show();
//                }
//            });

        }
    }

    /**
     * 获得屏幕宽度
     * @param context
     * @return
     * by Hankkin at:2015-10-07 21:16:13
     */

    public static int getScreenWidth(Context context) {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.widthPixels;
    }



}