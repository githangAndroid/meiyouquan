package com.mayinews.g.user.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.github.jdsjlzx.ItemDecoration.DividerDecoration;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.mayinews.g.R;
import com.mayinews.g.app.MyApplication;
import com.mayinews.g.user.adapter.MyAttenAdapter;
import com.mayinews.g.user.bean.MyAttenBean;
import com.mayinews.g.utils.SPUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;

public class PersonalAttenActivity extends AppCompatActivity {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.lRecyclerView)
    LRecyclerView lRecyclerView;
    private LRecyclerViewAdapter adapter;  //适配器
    private String token;
    private MyAttenAdapter myAttenAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_atten);
        ButterKnife.bind(this);
        title.setText("我的关注");
        token= (String) SPUtils.get(this,MyApplication.TOKEN,"");
      Log.e("TAG","token"+token);
        initRecyclerView();   //初始化LRecyclerView

        requestData(); //请求数据
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void requestData() {

        OkHttpUtils.get().url("http://g.mayinews.com/api/follow/type/following/max/20.html")
                .addHeader("Authorization", "Bearer " + token)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                Log.e("TAG","我的关注error="+e.getMessage());
            }

            @Override
            public void onResponse(String response, int id) {
                Log.e("TAG","我的关注response="+response);
                MyAttenBean myAttenBean = JSON.parseObject(response, MyAttenBean.class);
                int status = myAttenBean.getStatus();
                if(status==200){

                    List<MyAttenBean.ResultBean> result = myAttenBean.getResult();
                    //添加数据
                     myAttenAdapter.addData(result);
                     adapter.notifyDataSetChanged();

                }else if(status==401){

                     startActivity(new Intent(PersonalAttenActivity.this,LoginActivity.class));

                }else{


                }
            }
        });

    }

    private void initRecyclerView() {

        myAttenAdapter = new MyAttenAdapter(this);
         adapter = new LRecyclerViewAdapter(myAttenAdapter);
         lRecyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
         lRecyclerView.setAdapter(adapter);
         lRecyclerView.setLoadMoreEnabled(false);
         lRecyclerView.setPullRefreshEnabled(false);
        DividerDecoration divider = new DividerDecoration.Builder(this)
                .setHeight(2f)
                .setPadding(2f)
                .setColorResource(R.color.divider)
                .build();
        lRecyclerView.addItemDecoration(divider);

    }
}
