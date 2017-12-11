package com.mayinews.g.home.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.mayinews.g.R;
import com.mayinews.g.home.adapter.ListSearchAdapter;
import com.mayinews.g.home.bean.SearchDataBean;
import com.mayinews.g.utils.Constant;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

public class SearchActivity extends AppCompatActivity {

    @BindView(R.id.cancle)
    TextView cancle;
    @BindView(R.id.searchContent)
    EditText searchContent;
    @BindView(R.id.clear)
    ImageView clear;
    @BindView(R.id.search)
    ImageView search;
    @BindView(R.id.listView)
    ListView listView;
    @BindView(R.id.tv_nodata)
    TextView tvNodata;
    List<SearchDataBean> data; //请求的搜索结果
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        searchContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.e("TAG", "start" + start + "  before" + before);
            }

            @Override
            public void afterTextChanged(Editable s) {


            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SearchDataBean bean = data.get(position);
                String type =   bean.getType();
                if(type.equals("actor")){   //进入模特
                    Intent intent = new Intent(SearchActivity.this, ModelDetailActivity.class);
                    intent.putExtra("aid",bean.getId());
                    Log.e("TAG","id为==="+bean.getId());
                    startActivity(intent);


                }else{
                    //进入专辑
                    Intent intent = new Intent(SearchActivity.this, PhotosActivity.class);
                    intent.putExtra("id",bean.getCid());
                    startActivity(intent);
                }
            }
        });
    }


    @OnClick({R.id.cancle, R.id.clear, R.id.search})
    public void OnClick(View view) {

        switch (view.getId()) {
            case R.id.cancle:
                finish();
                break;
            case R.id.clear:
                searchContent.setText("");
                break;
            case R.id.search:
                requestSearch();
                break;
        }

    }

    private void requestSearch() {
        //请求搜索的接口


        OkHttpUtils.get().url(Constant.SEARCH + searchContent.getText().toString()).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                 data = JSON.parseArray(response, SearchDataBean.class);
                Log.e("TAG","dataaaa"+data);
                if (data != null && data.size() > 0) {
                    tvNodata.setVisibility(View.GONE);
                    listView.setVisibility(View.VISIBLE);
                    //显示listView
                    ListSearchAdapter adapter = new ListSearchAdapter(SearchActivity.this, data);
                    listView.setAdapter(adapter);

                }else {
                    tvNodata.setVisibility(View.VISIBLE);
                    listView.setVisibility(View.GONE);
                }

            }
        });
    }
}
