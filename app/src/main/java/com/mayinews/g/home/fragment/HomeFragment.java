package com.mayinews.g.home.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.github.jdsjlzx.ItemDecoration.SpacesItemDecoration;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.mayinews.g.GlideImageLoader;
import com.mayinews.g.R;
import com.mayinews.g.app.MyApplication;
import com.mayinews.g.home.activity.ModelDetailActivity;
import com.mayinews.g.home.activity.MoreRankActivity;
import com.mayinews.g.home.activity.SearchActivity;
import com.mayinews.g.home.adapter.VAdapter;
import com.mayinews.g.home.adapter.VRcAdapter;
import com.mayinews.g.home.bean.HomeReBean;
import com.mayinews.g.home.bean.ModelTagBean;
import com.mayinews.g.home.bean.RankListBean;
import com.mayinews.g.user.activity.LoginActivity;
import com.mayinews.g.utils.Constant;
import com.mayinews.g.utils.SPUtils;
import com.mayinews.g.view.FullyGridLayoutManager;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.FileCallBack;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zhy.magicviewpager.transformer.ScaleInTransformer;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.Call;
import com.mayinews.g.utils.DisplayUtil;
import com.mayinews.g.utils.NetworkUtils;

import org.json.JSONException;
import org.json.JSONObject;

import static com.bumptech.glide.gifdecoder.GifHeaderParser.TAG;
import static com.mayinews.g.utils.NetworkUtils.isNetworkAvailable;

/**
 * Created by 盖航_ on 2017/8/29.
 */

public class HomeFragment extends Fragment implements VRcAdapter.OnItemClickLitener {

    @BindView(R.id.homeView)
    LinearLayout homeView;
    @BindView(R.id.progress)
    ProgressBar progress;
    Unbinder unbinder;
    @BindView(R.id.tv_moveRank)
    TextView tvMoveRank;
    @BindView(R.id.id_viewpager)
    ViewPager mViewPager;
    @BindView(R.id.recyclerView)
    LRecyclerView recyclerView;
//    private List<String> imageViews = new ArrayList<>();
    private List<Integer> imageViews = new ArrayList<>();

    private VAdapter mAdapter;
    private VRcAdapter vRcAdapter;
    private Banner banner;
    private  List<ModelTagBean.ResultBean> datas;
    private ImageView image;
    private TextView title;
    private TextView search;
    private List<HomeReBean.ResultBean> data;    //每一個圖片的信息

    private int ScreenWidth;
    private int currentVersion;//当前的版本
    private List<RankListBean.ResultBean> RankResult;//排行榜的信息

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.home_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);

        WindowManager wm = (WindowManager) getActivity()
                .getSystemService(Context.WINDOW_SERVICE);
        ScreenWidth = wm.getDefaultDisplay().getWidth();


        image = (ImageView) getActivity().findViewById(R.id.top_logo);
        title = (TextView) getActivity().findViewById(R.id.title);
        search = (TextView) view.findViewById(R.id.search);
        if (image != null) {
            image.setVisibility(View.VISIBLE);
        }
        if (title != null) {
            title.setVisibility(View.GONE);
        }

        //判断网络状态
        boolean networkAvailable = isNetworkAvailable(getActivity());
        if (networkAvailable) {


            //显示Progress,隐藏homeView
            progress.setVisibility(View.VISIBLE);
            homeView.setVisibility(View.GONE);
            requestData(view);

        } else {
            //显示没有网的
            Toast.makeText(getActivity(), "网络不顺畅...", Toast.LENGTH_SHORT).show();
        }

        tvMoveRank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 打开更多排行榜
                String state = (String) SPUtils.get(getActivity(),MyApplication.LOGINSTATUES,"0");
                if(state.equals("1")){
                    Bundle bundle=new Bundle();
                    Intent intent = new Intent(getActivity(), MoreRankActivity.class);
                    bundle.putSerializable("ranklist", (Serializable) RankResult);
                    intent.putExtra("bundle",bundle);
                    startActivity(intent);
                }else{
                    startActivity(new Intent(getActivity(), LoginActivity.class));

                }


            }
        });

         currentVersion=getVerCode(getActivity());
         Log.e("TAG","版本66666Version="+currentVersion);
        if( NetworkUtils.isNetworkAvailable(getActivity())){

            apkUndate();



        }



        return view;
    }

    private void apkUndate() {
        //进行版本更新检查
        OkHttpUtils.get().url("http://g.mayinews.com/api/getversion/client/"+currentVersion).build()
                .execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                   Log.e("TAG","版本更新error"+e.getMessage());
            }

            @Override
            public void onResponse(String response, int id) {
                Log.e("TAG","版本更新response   "+response);
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if(jsonObject!=null){

                    int current = jsonObject.optInt("current");//得到服务器当前的最新版本
                    JSONObject apk = jsonObject.optJSONObject("apk");
                    String apkUrl = apk.optString(current+"");  //apk的下载地址

                    //若是有最新版,请求下载和安装apk
                    if(currentVersion<current ){

                        requestDownloadApk(apkUrl);
                    }
                }




            }
        });
    }


    /*
          来下来最新版本的apk
     */
    private void requestDownloadApk(String apkUrl) {

        //判断网络状态
        if (isNetworkAvailable(getActivity())) {
            if (NetworkUtils.isWifi(getActivity())) {  //是无线网络直接更新
                //显示下载的Dialog
                downloadApk(apkUrl);
            } else {
                //是4G提示是否下载
                //弹出不是wifi状态的dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                View view = LayoutInflater.from(getActivity()).inflate(R.layout.netstatus_dialog, null, false);
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                alertDialog.setContentView(view);
                TextView cancle = (TextView) view.findViewById(R.id.tv_cancle);
                TextView sure = (TextView) view.findViewById(R.id.tv_sure);
                cancle.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();

                    }
                });
                sure.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                        downloadApk(apkUrl);
                    }
                });
            }
        }


    }

    private void downloadApk(String apkUrl) {
        Log.e("TAG","apk地址"+apkUrl);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.download_dialog, null, false);
        AlertDialog alertDialog = builder.create();

        ProgressBar pb =  (ProgressBar) view.findViewById(R.id.pb_download);
        alertDialog.show();
        alertDialog.setCancelable(false);  //点击外部不消失
        alertDialog.setContentView(view);
        OkHttpUtils.get().url(apkUrl).
                addHeader("Referer", "http://m.mayinews.com").
                build().execute(new FileCallBack(Environment.getExternalStorageDirectory().getAbsolutePath(), "meiyouquan.apk") {
            @Override
            public void onError(Call call, Exception e, int id) {
                Log.e("TAG","下载失败error="+e.getMessage());
//                Toast.makeText(getActivity(), "下载失败", Toast.LENGTH_SHORT).show();
                alertDialog.dismiss();
            }

            @Override
            public void onResponse(File response, int id) {
                //下载完成后去安装
                Log.e("TAG","下载apkResponse"+response);
                alertDialog.dismiss();
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addCategory(Intent.CATEGORY_DEFAULT);
                intent.setDataAndType(Uri.fromFile(new File(Environment
                                .getExternalStorageDirectory(), "meiyouquan.apk")),
                        "application/vnd.android.package-archive");

                startActivity(intent);
            }

            @Override
            public void inProgress(float progress, long total, int id) {
                super.inProgress(progress, total, id);
//                  pb.setMax((int) total);

                  //显示进度 progress百分比
                  pb.setProgress((int) (100*progress));

            }


        });
    }

    private void requestData(View view) {
//        imageViews.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1508335215839&di=05bd2b18c8a8974915f41c16a34865ee&imgtype=0&src=http%3A%2F%2Fdesk.fd.zol-img.com.cn%2Ft_s960x600c5%2Fg4%2FM01%2F01%2F0B%2FCg-4WVPgP_mIBQYXAAb9Cbf54i0AAQOJgOduL0ABv0h477.jpg");
//        imageViews.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1508335738487&di=c9e3a000c90a38e87ce0078b9e24220c&imgtype=jpg&src=http%3A%2F%2Fimg2.imgtn.bdimg.com%2Fit%2Fu%3D4082926650%2C2115755766%26fm%3D214%26gp%3D0.jpg");
           imageViews.add(R.drawable.banner1);
           imageViews.add(R.drawable.banner2);


        //设置Banner轮播图
        setBanner(view);

//      setViewPager();
        mViewPager.setPageMargin(DisplayUtil.dp2px(getActivity(), 2));
        ViewGroup.LayoutParams layoutParams = mViewPager.getLayoutParams();

        int i = ScreenWidth - 2 * DisplayUtil.dp2px(getActivity(), 60) - 2 * DisplayUtil.dp2px(getActivity(), 2) - 3 * DisplayUtil.dp2px(getActivity(), 10);
        layoutParams.height=i*3/2+4* DisplayUtil.dp2px(getActivity(), 10);
        mViewPager.setLayoutParams(layoutParams);
        Log.e("TAG","ScreenWidth"+ScreenWidth+"   hei="+layoutParams.height+"  i"+i);
        //设置缓存的页面数量
        mViewPager.setOffscreenPageLimit(3);
        mViewPager.setPageTransformer(true, new ScaleInTransformer());

        setRecyclerView(view);
        RequestData();//请求数据
//        setOnListener();//点击事件
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //进入搜索的页面
                startActivity(new Intent(getActivity(), SearchActivity.class));


            }
        });


        //请求排行榜

        OkHttpUtils.get().url("http://meiyou.130game.com/api/hot").build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                RankListBean rankListBean = JSON.parseObject(response, RankListBean.class);
                int status = rankListBean.getStatus();
                if(status==200){
                    RankResult = rankListBean.getResult();
                    Glide.with(getActivity()).load(buildGlideUrl("http://static.mayinews.com"+RankResult.get(0).getAvatar())).into((ImageView) view.findViewById(R.id.image_one));
                    Glide.with(getActivity()).load(buildGlideUrl("http://static.mayinews.com"+RankResult.get(1).getAvatar())).into((ImageView) view.findViewById(R.id.image_two));
                    Glide.with(getActivity()).load(buildGlideUrl("http://static.mayinews.com"+RankResult.get(2).getAvatar())).into((ImageView) view.findViewById(R.id.image_three));

                }


            }
        });


//        Glide.with(this).load("http://img2.3lian.com/2014/gif/11/4/38.jpg").into((ImageView) view.findViewById(R.id.image_one));
//        Glide.with(this).load("http://img2.3lian.com/2014/gif/11/4/34.jpg").into((ImageView) view.findViewById(R.id.image_two));
//        Glide.with(this).load("http://a.hiphotos.baidu.com/zhidao/wh%3D600%2C800/sign=8b393b17a61ea8d38a777c02a73a1c76/5882b2b7d0a20cf4896e727376094b36adaf99a4.jpg").into((ImageView) view.findViewById(R.id.image_three));


    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        if (!hidden) {

            if (image != null) {
                image.setVisibility(View.VISIBLE);
            }
            if (title != null) {
                title.setVisibility(View.GONE);
            }
        }

    }

    private void setOnListener() {


        //1 由于ViewPager 没有点击事件，可以通过对VIewPager的setOnTouchListener进行监听已达到实现点击事件的效果
        mViewPager.setOnTouchListener(new View.OnTouchListener() {
            int flage = 0;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        flage = 0;
                        break;
                    case MotionEvent.ACTION_MOVE:
                        flage = 1;
                        break;
                    case MotionEvent.ACTION_UP:
                        if (flage == 0) {
                            int item = mViewPager.getCurrentItem() % datas.size();
                            Toast.makeText(getActivity(), "" + item, Toast.LENGTH_SHORT).show();
                            if (item == 0) {
                                //vip

                            } else if (item == 1) {


                            } else if (item == 2) {


                            } else if (item == 3) {


                            } else if (item == 4) {


                            } else if (item == 4) {


                            } else if (item == 5) {


                            } else if (item == 6) {


                            } else if (item == 7) {


                            } else if (item == 8) {


                            }
                        }
                        break;


                }
                return false;
            }
        });

        //2 banner的点击事件

        banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {

                Toast.makeText(getActivity(), "" + position, Toast.LENGTH_SHORT).show();
            }
        });


    }


    private void setRecyclerView(View view) {


        //绑定VirtualLayoutManager
        vRcAdapter = new VRcAdapter(getActivity());
        recyclerView.setLayoutManager(new FullyGridLayoutManager(getActivity(), 2));
        vRcAdapter.setOnItemClickLitener(this);
        LRecyclerViewAdapter lRecyclerViewAdapter = new LRecyclerViewAdapter(vRcAdapter);

        recyclerView.setAdapter(lRecyclerViewAdapter);
        int spacing = getResources().getDimensionPixelSize(R.dimen.dp_4);
        recyclerView.addItemDecoration(SpacesItemDecoration.newInstance(spacing, spacing, 2, Color.WHITE));

        recyclerView.setLoadMoreEnabled(false);
        recyclerView.setPullRefreshEnabled(false);


    }

    //分类请求数据
    private void RequestData() {
        OkHttpUtils.get().url(Constant.GETTYPE)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                ModelTagBean modelTagBean = JSON.parseObject(response, ModelTagBean.class);
                datas =  modelTagBean.getResult();
                Log.e("Tag","分类数据="+datas.toString());
                setViewPager(datas);


            }
        });

        //请求Recycler的数据

        OkHttpUtils.get().url(Constant.PULLUPURL)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                HomeReBean homeReBean = JSON.parseObject(response, HomeReBean.class);

                data = homeReBean.getResult();

                vRcAdapter.addData(data);
                vRcAdapter.notifyDataSetChanged();
                //隐藏Progress,显示homeView
                progress.setVisibility(View.GONE);
                homeView.setVisibility(View.VISIBLE);
            }
        });

    }

    private void setViewPager( List<ModelTagBean.ResultBean> datas) {


        mAdapter = new VAdapter(getFragmentManager(), datas);
        mViewPager.setAdapter(mAdapter);
        //设置中间位置
        int item = datas.size() * 40 / 2 - datas.size() * 40 / 2 % datas.size() + 1;//要保证imageViews的整数倍

        mViewPager.setCurrentItem(item);

    }

    private void setBanner(View view) {
        banner = (Banner) view.findViewById(R.id.banner);
        //设置banner样式
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        //设置图片加载器
        banner.setImageLoader(new GlideImageLoader());
        //设置图片集合

        banner.setImages(imageViews);
        //设置banner动画效果
        banner.setBannerAnimation(Transformer.Default);

        //设置自动轮播，默认为true
        banner.isAutoPlay(true);
        //设置轮播时间
        banner.setDelayTime(3000);
        //设置指示器位置（当banner模式中有指示器时）
        banner.setIndicatorGravity(BannerConfig.CENTER);//指示器居中
        //banner设置方法全部调用完毕时最后调用

        //设置点击事件
        banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
//                Toast.makeText(getActivity(), position + "", Toast.LENGTH_SHORT).show();
            }
        });
        banner.start();


    }


    //RecyclerView的item点击事件
    @Override
    public void onItemClick(View view, int position) {


//     Toast.makeText(getActivity(), position + "", Toast.LENGTH_SHORT).show();
        HomeReBean.ResultBean pData = data.get(position);
        String aid = pData.getActor_id();

        Intent intent = new Intent(getActivity(), ModelDetailActivity.class);
        intent.putExtra("aid", aid);
        startActivity(intent);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

	public static int getVerCode(Context context) {
        	        int verCode = -1;
                try {
            	            verCode = context.getPackageManager().getPackageInfo(
                                       "com.mayinews.g", 0).versionCode;
            	        } catch (PackageManager.NameNotFoundException e) {
                        Log.e(TAG, e.getMessage());
                    }
        	        return verCode;
        	    }
    //不强制升级的dialog
    public void  showDialogNoMust(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("软件更新")
                .setMessage("当前版本："+getVerCode(getActivity())+"\n\r"+"当前版本:"+2.0);
        builder.setPositiveButton("更新", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                            //判断网络的状态，
                            //显示下载进度的自定义Dialog

                    }
                });
        builder.setNegativeButton("暂不更新", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                      //什么事也不做


            }
        });
        builder.show();  //显示




    }

    //强制升级的dialog
    public void showDialogMust(){
          //直接判断网络状态，然后直接下载


    }

    private GlideUrl buildGlideUrl(String url) {
        if (TextUtils.isEmpty(url)) {
            return null;
        } else {
            return new GlideUrl(url, new LazyHeaders.Builder().addHeader("Referer", "http://m.mayinews.com").build());
        }
    }


}
