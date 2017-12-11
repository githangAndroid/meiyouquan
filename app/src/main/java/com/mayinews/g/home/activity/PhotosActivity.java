package com.mayinews.g.home.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.support.annotation.RequiresApi;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.mayinews.g.R;
import com.mayinews.g.album.adapter.CommentsAdapter;
import com.mayinews.g.album.bean.CommentBean;
import com.mayinews.g.app.MyApplication;
import com.mayinews.g.home.adapter.ImagesAdapter;
import com.mayinews.g.home.bean.SingleAlbum;
import com.mayinews.g.utils.Constant;
import com.mayinews.g.utils.SPUtils;
import com.mayinews.g.view.CanotSlidingViewpager;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.BitmapCallback;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;

public class PhotosActivity extends AppCompatActivity implements View.OnClickListener {
    List<String> data;
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.currentPage)
    TextView currentPage;
    @BindView(R.id.totalPage)
    TextView totalPage;
    @BindView(R.id.user_avatar)
    CircleImageView userAvatar;
    @BindView(R.id.tv_comment)
    TextView tvComment;
    @BindView(R.id.viewPager)
    CanotSlidingViewpager viewPager;
    @BindView(R.id.tob_bottom)
    RelativeLayout tobBottom;
    @BindView(R.id.rootView)
    RelativeLayout rootView;
    @BindView(R.id.rl_comments)
    RelativeLayout rlComments;
    @BindView(R.id.rl_collection)
    RelativeLayout rlCollection;
    @BindView(R.id.rl_zan)
    RelativeLayout rlZan;
    @BindView(R.id.rl_shared)
    RelativeLayout rlShared;
    @BindView(R.id.top_view)
    RelativeLayout topView;
    @BindView(R.id.tv_nodata)
    TextView tvNodata;
    @BindView(R.id.progress)
    ProgressBar progress;
    private static final int THUMB_SIZE = 150;
    @BindView(R.id.iv_coll)
    ImageView ivColl;
    private String aid;//艺人id
    private boolean isShow = false;//图片上下的布局是否显示,默认不显示

    private ImagesAdapter adapter = null;
    private PopupWindow popupWindow = null;

    private PopupWindow commentPopWindow = null;  //评论的pop
    private PopupWindow commentListPopWindow = null;
    private ListView comList;//评论的listView
    private RecyclerView comRecyclerView;//评论页面显示收藏的RecyclerView


    private PopupWindow window;//水印的pop


    private boolean isShowed;//标记水印水否显示过
    private TextView tvDesc;  //水印的描述

    private List<CommentBean.ResultBean> comments=new ArrayList<CommentBean.ResultBean>();//评论数据
    private String token;  //token；
    private String avatar;  //头像地址；
    private String nickname;  //用户昵称；
    private int imagePosition;
    private CommentsAdapter commentAdapter = new CommentsAdapter(this);  //评论适配器
    private PopupWindow SharedPopupWindow;
    private IWXAPI api;
    private String cover; //封面
    private String gid;//专辑得id
    private String desc;//描述
    private String title; //标题

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //去除title
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //去掉Activity上面的状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_photos);
        ButterKnife.bind(this);
        api = WXAPIFactory.createWXAPI(this, Constant.APPID);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                currentPage.setText(position + 1 + "");
                imagePosition = position;
                //禁止滑动
//                if (position == 8) {
//                    //禁止左滑
//                    viewPager.setScrollble(false);
//                    //显示
//                } else {
//
//                    viewPager.setScrollble(true);
//                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {


            }


        });


        viewPager.setLeftScrLisener(new CanotSlidingViewpager.LeftScr() {
            @Override
            public void leftScr() {
                //显示Pop
                if (!popupWindow.isShowing()) {
                    popupWindow.showAtLocation(rootView, Gravity.CENTER, 0, 0);
                }


            }
        });






        /*
               初始化各种PopWindow
         */
        initPop();     //充Vip的popwindow
        showComPop();   // 发送评论的popWindow
//        initListPop();//评论列表的popwindow
        showPopwindow();//水印
        Intent intent = getIntent();

//        data = intent.getStringArrayListExtra("images");

//        if (data != null) {
//            String avatar = intent.getStringExtra("avatar");
//            int size = data.size();
//            Log.e("TAG", "size" + size);
//            totalPage.setText("/" + size);
//            desc = intent.getStringExtra("desc");
//
//            gid = intent.getStringExtra("gid");
//            aid = intent.getStringExtra("aid");
//            cover = intent.getStringExtra("cover");
//            title = intent.getStringExtra("title");
//            Glide.with(this).load(buildGlideUrl("http://static.mayinews.com" + avatar)).into(userAvatar);
//            initImageViewPager();
//        } else {
            aid = intent.getStringExtra("aid");
            desc = intent.getStringExtra("desc");
            String avatar =  desc = intent.getStringExtra("avatar");

            gid = intent.getStringExtra("gid");

            cover = intent.getStringExtra("cover");
            title = intent.getStringExtra("title");
            Log.e("TAG","AAAAA="+avatar);
            Glide.with(this).load(buildGlideUrl("http://static.mayinews.com" + avatar)).into(userAvatar);
            //请求数据
            OkHttpUtils.get()
                    .url("http://g.mayinews.com/api/getdoc/id/" + gid)
                    .build().execute(new StringCallback() {
                @Override
                public void onError(Call call, Exception e, int id) {

                }

                @Override
                public void onResponse(String response, int id) {


                    SingleAlbum singleAlbum = JSON.parseObject(response, SingleAlbum.class);
                    if (singleAlbum.getStatus() == 200) {

                        data = singleAlbum.getResult().getPicture();
                        if (data != null && data.size() > 0) {
                            gid = singleAlbum.getResult().getId();
                            totalPage.setText("/" + data.size());
                            desc = singleAlbum.getResult().getDescription();

                            initImageViewPager();
                        } else {
                            currentPage.setVisibility(View.GONE);
                            window.dismiss();
                            viewPager.setVisibility(View.GONE);
                            //没有数据或者出错
                            tvNodata.setVisibility(View.VISIBLE);
                            tobBottom.setVisibility(View.GONE);
                        }

                    } else {
                        currentPage.setVisibility(View.GONE);
                        window.dismiss();
                        viewPager.setVisibility(View.GONE);
                        //没有数据或者出错
                        tvNodata.setVisibility(View.VISIBLE);
                    }


                }
            });


//        }


        //获取用户信息
        getUserInfo();

        //获取当前专辑是否被收藏过

        isCollection();

//        initImageViewPager();
//        ();

        Log.e("TAG", "COVER=" + cover + "  desc=" + desc + "   title=" + title + "  aid=" + aid+"  gid="+gid);
        userAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(aid!=null && !aid.equals("")){
                    Intent intent1 = new Intent(PhotosActivity.this, ModelDetailActivity.class);
                    intent1.putExtra("aid",aid);
                    startActivity(intent1);
                }

            }
        });
    }

    private void isCollection() {
        OkHttpUtils.get().url("http://g.mayinews.com/api/favor/gid/" + gid)
                .addHeader("Authorization", "Bearer " + token)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            int status = jsonObject.optInt("status");
                            if (status == 200) {
                                int result = jsonObject.optInt("result");
                                if (result == 1) {
                                    //收藏过
                                    ivColl.setImageResource(R.drawable.collect_press);
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                });
    }

    private void getUserInfo() {
        token = (String) SPUtils.get(this, MyApplication.TOKEN, "");
        avatar = (String) SPUtils.get(this, MyApplication.USERAVATAR, "");
        nickname = (String) SPUtils.get(this, MyApplication.USERNICKNAME, "");


    }


    //显示水印的
    private void showPopwindow() {

        isShow = false;
        window = new PopupWindow(this);
        final View inflate = View.inflate(this, R.layout.watermark_photo, null);

        window.setContentView(inflate);

        window.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        window.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
//        window.setFocusable(true);
        window.setOutsideTouchable(true);  //点击外部消失
        window.setBackgroundDrawable((new BitmapDrawable()));


//        AlphaAnimation alphaAnimation = new AlphaAnimation(1f, 0.5f);
//        alphaAnimation.setDuration(500);
//        alphaAnimation.setFillAfter(true);
//        inflate.startAnimation(alphaAnimation);
        tvDesc = (TextView) inflate.findViewById(R.id.describe);

//        backgroundAlpha(0.5f);

        window.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {

                tobBottom.setVisibility(View.GONE);

            }
        });

        inflate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                window.dismiss();
            }
        });

    }

//    @Override
//
//    public void onWindowFocusChanged(boolean hasFocus) {
//
//
//        super.onWindowFocusChanged(hasFocus);
//
//        if (hasFocus && !isShowed) {
//
//            //显示popwindow
//            showPopwindow();
//
//            isShowed = true;
//
//        }
//
//    }


    @Override

    public void onWindowFocusChanged(boolean hasFocus) {


        super.onWindowFocusChanged(hasFocus);

        if (hasFocus && !isShowed) {

            //显示popwindow
            View view = View.inflate(this, R.layout.activity_photos, null);

            window.showAtLocation(view, Gravity.BOTTOM, 0, 0);

            isShowed = true;

        }

    }

    private void initImageViewPager() {
//        showPopwindow();
        tvDesc.setText(desc);
        adapter = new ImagesAdapter(this, data);


        adapter.setImageListener(new ImagesAdapter.ImageOnClickListener() {

            @Override
            public void onClick(int position) {
                if (isShow) {
                    tobBottom.setVisibility(View.GONE);
                } else {
                    tobBottom.setVisibility(View.VISIBLE);
                }

                isShow = !isShow;
            }


        });
        viewPager.setAdapter(adapter);
    }


    @OnClick({R.id.back, R.id.tv_comment, R.id.rl_comments, R.id.rl_shared, R.id.rl_collection})
    public void listener(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.tv_comment:
//                Toast.makeText(PhotosActivity.this, "评论", Toast.LENGTH_SHORT).show();
                //显示评论的pop
//                showComPop();
                showCommentPop();
                break;
            case R.id.rl_comments:

                //进入评论的pop,显示在上面状态栏下
                initListPop();//评论列表的popwindow


                break;

            case R.id.rl_shared:
                showSharedPop();
//                showShare();
                break;
            case R.id.rl_collection:
                //收藏专辑
                String token = (String) SPUtils.get(this, MyApplication.TOKEN, "");
                OkHttpUtils.post().url(Constant.FAVOR).addHeader("Authorization", "Bearer " + token)
                        .addParams("gid", gid)
                        .build()
                        .execute(new StringCallback() {
                            @Override
                            public void onError(Call call, Exception e, int id) {
                                Toast.makeText(PhotosActivity.this, "系统错误,稍后再试", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onResponse(String response, int id) {

                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    int status = jsonObject.optInt("status");
                                    if (status == 200) {
                                        JSONObject result = jsonObject.getJSONObject("result");
                                        String del = result.optString("del");
                                        if (del.equals("1")) {
                                            Toast.makeText(PhotosActivity.this, "取消收藏成功", Toast.LENGTH_SHORT).show();
                                            ivColl.setImageResource(R.drawable.collect);
                                        } else {
                                            ivColl.setImageResource(R.drawable.collect_press);
                                            Toast.makeText(PhotosActivity.this, "收藏成功", Toast.LENGTH_SHORT).show();
                                        }


                                    } else {

                                        Toast.makeText(PhotosActivity.this, "系统错误,稍后再试", Toast.LENGTH_SHORT).show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        });


//
                break;


        }
    }

    //显示评论的pop
    private void showCommentPop() {
        commentPopWindow.showAtLocation(rootView, Gravity.BOTTOM, 0, 0);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                showInputMethod();
            }
        },100);

    }

    private void initListPop() {
//        commentAdapter = new CommentsAdapter(this);
        View view = LayoutInflater.from(this).inflate(R.layout.comment_list_pop, null, false);
        commentListPopWindow = new PopupWindow(PhotosActivity.this);
        commentListPopWindow.setContentView(view);
        commentListPopWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));
        commentListPopWindow.setFocusable(true);
        commentListPopWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        commentListPopWindow.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        comList = (ListView) view.findViewById(R.id.listView);
        comList.setAdapter(commentAdapter);
        comRecyclerView = (RecyclerView) view.findViewById(R.id.com_RecyclerView);
        comRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        TextView com = (TextView) view.findViewById(R.id.com_comment);
        com.setOnClickListener(this);

        //请求最近关注的

//        OkHttpUtils.get().url(Constant.GETFOLLOW)
//                .addHeader("Authorization", "Bearer " + token)
//                .build()
//                .execute(new StringCallback() {
//                    @Override
//                    public void onError(Call call, Exception e, int id) {
//
//                    }
//
//                    @Override
//                    public void onResponse(String response, int id) {
//                        FollowingBean followingBean = JSON.parseObject(response, FollowingBean.class);
//                        int status = followingBean.getStatus();
//                        if (status == 200) {
//
//                            List<FollowingBean.ResultBean> result = followingBean.getResult();
//                            comRecyclerView.setAdapter(new CommenrRcyAdapter(PhotosActivity.this, result));
//
//
//                        }
//                    }
//                });


        //请求评论列表
        OkHttpUtils.get().url(Constant.GETCOMMENTS + gid).build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        CommentBean commentBean = JSON.parseObject(response, CommentBean.class);
                        if (commentBean.getStatus() == 200) {

                            comments = commentBean.getResult();
                            if (comments != null) {
                                commentAdapter.addData(comments);
                                commentAdapter.notifyDataSetChanged();
                            }
                            else {
                                comments = new ArrayList<CommentBean.ResultBean>();
                            }

                        }
                    }
                });


        commentListPopWindow.showAsDropDown(topView);


    }

    @RequiresApi(api = Build.VERSION_CODES.DONUT)
    private void showComPop() {

        View view = LayoutInflater.from(this).inflate(R.layout.comment_pop, null, false);
        commentPopWindow = new PopupWindow(PhotosActivity.this);
        commentPopWindow.setContentView(view);
        commentPopWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));
        commentPopWindow.setFocusable(true);
        commentPopWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        commentPopWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        //让popupwindow不被输入法隐藏
        commentPopWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        final TextView commentContent = (TextView) view.findViewById(R.id.comment_content);
        TextView send = (TextView) view.findViewById(R.id.send);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = commentContent.getText().toString();

                if (text != null && !(text.trim().equals(""))) {
                    //请求接口发送评论
                    postComment(text);
                    commentContent.setText("");


                } else {

                    Toast.makeText(PhotosActivity.this, "评论不能为空", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
    /**
     * EditText获取焦点并显示软键盘
     */
    public static void showSoftInputFromWindow(Activity activity, EditText editText) {
        editText.setFocusable(true);
        editText.setFocusableInTouchMode(true);
        editText.requestFocus();
        activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
    }
    private void postComment(String text) {
        String token = (String) SPUtils.get(this, MyApplication.TOKEN, "");
        //请求评论列表
        OkHttpUtils.post().url(Constant.POSTCOMMENTS + gid)
                .addHeader("Authorization", "Bearer " + token)
                .addParams("text", text)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Toast.makeText(PhotosActivity.this, "评论失败，请稍后再试", Toast.LENGTH_SHORT).show();
                        commentPopWindow.dismiss();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            int status = jsonObject.optInt("status");
                            if (status == 200) {


                                //评论成功
                                CommentBean.ResultBean resultBean = new CommentBean.ResultBean();
                                resultBean.setAvatar(avatar);
                                resultBean.setNickname(nickname);
                                resultBean.setComment(text);
                                comments.add(0, resultBean);
                                commentAdapter.addData(comments);
                                commentAdapter.notifyDataSetChanged();
                                commentPopWindow.dismiss();

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                });

    }

    private void initPop() {
        View view = LayoutInflater.from(this).inflate(R.layout.image_pop, null, false);
        popupWindow = new PopupWindow(PhotosActivity.this);
        popupWindow.setContentView(view);
        popupWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));
        popupWindow.setFocusable(true);
        popupWindow.setWidth(getScreenWidth(this) * 3 / 4);
        popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        TextView paySingle = (TextView) view.findViewById(R.id.pay_single);
        TextView payVip = (TextView) view.findViewById(R.id.pay_vip);
        TextView collection = (TextView) view.findViewById(R.id.collection);
        TextView cancle = (TextView) view.findViewById(R.id.cancle);
        paySingle.setOnClickListener(this);
        payVip.setOnClickListener(this);
        collection.setOnClickListener(this);
        cancle.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.pay_single:
                Toast.makeText(PhotosActivity.this, "购买单张专辑", Toast.LENGTH_SHORT).show();
                break;
            case R.id.pay_vip:
                Toast.makeText(PhotosActivity.this, "购买Vip", Toast.LENGTH_SHORT).show();
                break;
            case R.id.collection:
                Toast.makeText(PhotosActivity.this, "收藏", Toast.LENGTH_SHORT).show();
                break;
            case R.id.cancle:
                popupWindow.dismiss();
                break;
            case R.id.com_comment:
                //显示评论的pop
                showCommentPop();
                break;


        }

    }


    private GlideUrl buildGlideUrl(String url) {
        if (TextUtils.isEmpty(url)) {
            return null;
        } else {
            return new GlideUrl(url, new LazyHeaders.Builder().addHeader("Referer", "http://m.mayinews.com").build());
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        Log.e("TAG", "onSaveInstanceState");
    }


    @Override
    public void onBackPressed() {

        finish();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (window.isShowing()) {
            window.dismiss();
        }
    }


    public static int getScreenWidth(Context context) {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.widthPixels;
    }

    public void showSharedPop() {
        View view = LayoutInflater.from(this).inflate(R.layout.shared_dialog, null);
        View parent = LayoutInflater.from(this).inflate(R.layout.activity_photos, null);
        SharedPopupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);


        LinearLayout llWx = (LinearLayout) view.findViewById(R.id.ll_wx);//微信好友
        LinearLayout llPengyou = (LinearLayout) view.findViewById(R.id.ll_pengyou);//微信朋友圈
        TextView cancle = (TextView) view.findViewById(R.id.cancle);//微信朋友圈
        llWx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharePicture("haoyou");
            }
        });
        llPengyou.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharePicture("pengyouquan");
            }
        });
        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPopupWindow.dismiss();
            }
        });
        SharedPopupWindow.setOutsideTouchable(true);
        SharedPopupWindow.setFocusable(true);
        //让pop可以点击外面消失掉
        SharedPopupWindow.setBackgroundDrawable(new ColorDrawable(0));
        SharedPopupWindow.showAtLocation(parent, Gravity.BOTTOM, 0, 0);

        SharedPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                backgroundAlpha(1);
            }
        });
        backgroundAlpha(0.5f);
    }

    //分享图片
    private void sharePicture(String type) {
        OkHttpUtils.get().url("http://static.mayinews.com" + cover)
                .addHeader("Referer", "http://m.mayinews.com")
                .build().execute(new BitmapCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                Log.e("TAG", "error" + e.getMessage());
            }

            @Override
            public void onResponse(Bitmap bitmap, int id) {
                WXWebpageObject webpage = new WXWebpageObject();
                webpage.webpageUrl = "http://meiyou.130game.com/share/" + aid;
                WXMediaMessage msg = new WXMediaMessage(webpage);
                msg.title = title;
                msg.description = desc;
                Log.e("TAG", "bmp" + bitmap);
                byte[] bytes = bitmap2Bytes(bitmap, 32);
//                msg.thumbData = bytes;
                Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                Bitmap thumbBmp = Bitmap.createScaledBitmap(bmp, 80, THUMB_SIZE, true);
                msg.setThumbImage(thumbBmp);
                thumbBmp.recycle();
                //构造一个req
                SendMessageToWX.Req req = new SendMessageToWX.Req();
                req.transaction = buildTransaction("webpage");
                req.message = msg;
                if (type.equals("haoyou")) {
                    req.scene = SendMessageToWX.Req.WXSceneSession;
                } else if (type.equals("pengyouquan")) {
                    req.scene = SendMessageToWX.Req.WXSceneTimeline;
                }

                api.sendReq(req);
                //dialog消失
                SharedPopupWindow.dismiss();
                backgroundAlpha(1);
            }
        });

//        finish();


    }

    /**
     * Bitmap转换成byte[]并且进行压缩,压缩到不大于maxkb
     *
     * @param bitmap
     * @param
     * @return
     */
    public static byte[] bitmap2Bytes(Bitmap bitmap, int maxkb) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, output);
        int options = 100;
        while (output.toByteArray().length > maxkb && options != 10) {
            output.reset(); //清空output
            bitmap.compress(Bitmap.CompressFormat.JPEG, options, output);//这里压缩options%，把压缩后的数据存放到output中
            options -= 10;
        }
        return output.toByteArray();
    }

    private String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }

    /**
     * 设置添加屏幕的背景透明度
     *
     * @param bgAlpha
     */
    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        getWindow().setAttributes(lp);
    }

    private void showInputMethod() {
        //自动弹出键盘
        InputMethodManager inputManager = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        //强制隐藏Android输入法窗口
        // inputManager.hideSoftInputFromWindow(edit.getWindowToken(),0);
    }
}
