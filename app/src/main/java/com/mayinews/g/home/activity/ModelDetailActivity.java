package com.mayinews.g.home.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;

import com.markmao.pullscrollview.ui.widget.PullScrollView;

import com.mayinews.g.R;
import com.mayinews.g.home.adapter.ModelDetailAdapter;
import com.mayinews.g.home.bean.HomeReBean;
import com.mayinews.g.home.bean.ModelDetailBean1;
import com.mayinews.g.home.bean.ModelDetailBean2;
import com.mayinews.g.utils.Constant;
import com.mayinews.g.view.FullyLinearLayoutManager;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;
import com.mayinews.g.utils.NetworkUtils;

import static android.R.attr.data;

/**
 * 主页下面的模特点击后的页面
 */
public class ModelDetailActivity extends AppCompatActivity{

    private PullScrollView mScrollView;
//    private ImageView topHeadView;




    ImageView mHeadImg;//可拉伸的顶部图片
    TextView username;
    TextView city;
    TextView threeDimensional;
    TextView height;
    CircleImageView userAvatar;

    TextView followCount;

    TextView attention;

     ImageView iv_back;
    @BindView(R.id.lRecyclerView)
    LRecyclerView lRecyclerView;
//    @BindView(R.id.rootView)
//
//    RelativeLayout rootView;
    private LRecyclerViewAdapter mLRecyclerViewAdapter;
    private ModelDetailAdapter detailAdapter;
    private View headView;//头部view
    private boolean isAtten=false;  //标记是否关注
    private String aid; //模特ID

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_model_detail);
        ButterKnife.bind(this);

        initView();
        headView = LayoutInflater.from(this).inflate(R.layout.recycler_head, null, false);
        username= (TextView) headView.findViewById(R.id.username);
        city= (TextView) headView.findViewById(R.id.city);
        threeDimensional= (TextView) headView.findViewById(R.id.three_dimensional);
        height= (TextView) headView.findViewById(R.id.tv_height);
        userAvatar= (CircleImageView) headView.findViewById(R.id.user_avatar);
        followCount= (TextView) headView.findViewById(R.id.followCount);
        attention= (TextView) headView.findViewById(R.id.attention);
//        rootView.setVisibility(View.GONE);
        iv_back= (ImageView) findViewById(R.id.back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    finish();
            }
        });

        attention.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!isAtten){
                    attention.setText("取消成功");
                    Toast.makeText(ModelDetailActivity.this, "关注成功", Toast.LENGTH_SHORT).show();
                }else{
                     attention.setText("关注Ta");
                    Toast.makeText(ModelDetailActivity.this, "取消成功", Toast.LENGTH_SHORT).show();
                }
                   isAtten=!isAtten;

            }
        });


        initLrecyclerView();


        Intent intent = getIntent();
        aid = intent.getStringExtra("aid");

//        //设置上班半部分的信息

//          setUpInfo(data);

          RequestInfoData(aid);// 请求页面上半部分的信息数据
//        RequestLreData(); //请求下面的数据
    }
    protected void initView() {
        mScrollView = (PullScrollView) findViewById(com.markmao.pullscrollview.R.id.scroll_view);
        mHeadImg = (ImageView) findViewById(com.markmao.pullscrollview.R.id.background_img);
        mScrollView.setHeader(mHeadImg);


    }
    private void setUpInfo(HomeReBean.ResultBean data) {
        Glide.with(this).load(buildGlideUrl("http://static.mayinews.com"+ data.getActor_avatar())).into(userAvatar);
        username.setText(data.getActor());
        //设置背景
//        Glide.with(this).load(buildGlideUrl("http://static.mayinews.com"+ data.getActor_image())).into(mHeadImg);
         Glide.with(this).load(buildGlideUrl("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1508390658035&di=74c13488c80ed535a57b50915b305723&imgtype=jpg&src=http%3A%2F%2Fimg3.imgtn.bdimg.com%2Fit%2Fu%3D3884961763%2C365552436%26fm%3D214%26gp%3D0.jpg")).into(mHeadImg);

        //设置城市，三维，身高
        city.setText(data.getActor_city());
        threeDimensional.setText(data.getActor_size());
        height.setText(data.getActor_height());
        followCount.setText("30021人已关注她");
     

    }

    private void RequestLreData(final ModelDetailBean1.ResultBean data) {
        boolean status = NetworkUtils.isNetworkAvailable(this);
        if(status){

            OkHttpUtils.get()
                    .url("http://g.mayinews.com/api/getdoc/aid/"+aid)
                    .build().execute(new StringCallback() {
                @Override
                public void onError(Call call, Exception e, int id) {

                }

                @Override
                public void onResponse(String response, int id) {


                    ModelDetailBean2 bean = JSON.parseObject(response, ModelDetailBean2.class);
                    if( bean.getStatus()==200){
                        List<ModelDetailBean2.ResultBean> result = bean.getResult();
                         detailAdapter.addData(data,result);
                         detailAdapter.notifyDataSetChanged();
                         mLRecyclerViewAdapter.notifyDataSetChanged();
//                       rootView.setVisibility(View.VISIBLE);
                         lRecyclerView.setBackgroundColor(getResources().getColor(R.color.transparent));

                    }


                }
            });
        }else{
            //没有网





        }
    }

    private void RequestInfoData(final String aid ) {
        boolean status = NetworkUtils.isNetworkAvailable(this);
        if (status) {
            //有网络连接
            OkHttpUtils.get()
                    .url(Constant.GETACTORINFO+aid)
                    .build().execute(new StringCallback() {
                @Override
                public void onError(Call call, Exception e, int id) {

                }

                @Override
                public void onResponse(String response, int id) {
                    ModelDetailBean1 bean = JSON.parseObject(response, ModelDetailBean1.class);
                    ModelDetailBean1.ResultBean result = bean.getResult();
                    Log.e("TAG","模特=="+result.toString());
                    Log.e("TAG","response="+response+"      aid"+aid);
                    lRecyclerView.setBackgroundColor(getResources().getColor(R.color.white));
                    Glide.with(ModelDetailActivity.this).load(buildGlideUrl("http://static.mayinews.com"+ result.getAvatar())).into(userAvatar);
                    username.setText(result.getNickname());  //还有个realName
                    //设置背景
//        Glide.with(this).load(buildGlideUrl("http://static.mayinews.com"+ data.getActor_image())).into(mHeadImg);
//                    Glide.with(ModelDetailActivity.this).load(buildGlideUrl("http://fns-userimg-public.oss-cn-hangzhou.aliyuncs.com/150417333338505ef39.jpg")).into(mHeadImg);
                    Glide.with(ModelDetailActivity.this).load(buildGlideUrl("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1508390658035&di=74c13488c80ed535a57b50915b305723&imgtype=jpg&src=http%3A%2F%2Fimg3.imgtn.bdimg.com%2Fit%2Fu%3D3884961763%2C365552436%26fm%3D214%26gp%3D0.jpg")).into(mHeadImg);
                    //设置城市，三维，身高

                    Log.e("TAG","城市="+result.getCity()+"  三维="+ result.getSize()+"  +高="+result.getHeight());
                    city.setText(result.getCity());
                    threeDimensional.setText(result.getSize());
                    height.setText(result.getHeight());
                    followCount.setText("30021人已关注她");
                    lRecyclerView.setBackgroundColor(getResources().getColor(R.color.transparent));
                    RequestLreData(result);









                }
            });
        } else {
            //没有网络。显示没有网的信息
            Toast.makeText(ModelDetailActivity.this, "网络不给力", Toast.LENGTH_SHORT).show();
        }
    }

    private void initLrecyclerView() {
        lRecyclerView.setPullRefreshEnabled(false);
//
        //绑定VirtualLayoutManager
        detailAdapter = new ModelDetailAdapter(this);
        lRecyclerView.setLayoutManager(new FullyLinearLayoutManager(this));
        lRecyclerView.setNestedScrollingEnabled(false);
//        vRcAdapter.setOnItemClickLitener(this);
        mLRecyclerViewAdapter = new LRecyclerViewAdapter(detailAdapter);
        mLRecyclerViewAdapter.addHeaderView(headView);
        lRecyclerView.setAdapter(mLRecyclerViewAdapter);
        lRecyclerView.setLoadMoreEnabled(false);
        lRecyclerView.setPullRefreshEnabled(false);
        lRecyclerView.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {


                Toast.makeText(ModelDetailActivity.this, "123", Toast.LENGTH_SHORT).show();
            }
        });

    }





//    private void initPullView() {
//
//        mScrollView.setOnTurnListener(this);
//        mScrollView.setHeader(mHeadImg);
//    }


    //点击事件
    public void OnClick(View view) {


    }


    private GlideUrl buildGlideUrl(String url) {
        if (TextUtils.isEmpty(url)) {
            return null;
        } else {
            return new GlideUrl(url, new LazyHeaders.Builder().addHeader("Referer", "http://m.mayinews.com").build());
        }
    }



}
