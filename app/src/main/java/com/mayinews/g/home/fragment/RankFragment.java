package com.mayinews.g.home.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.alibaba.fastjson.JSON;
import com.mayinews.g.R;
import com.mayinews.g.home.activity.ModelDetailActivity;
import com.mayinews.g.home.adapter.RankListAdapter;
import com.mayinews.g.home.bean.RankBean;
import com.mayinews.g.home.bean.RankListBean;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.Call;

/**
 * Created by Administrator on 2017/9/28 0028.
 */

public class RankFragment extends Fragment {


    @BindView(R.id.listView)
    ListView listView;
    Unbinder unbinder;
    private Context context;
    List<RankListBean.ResultBean> ranklist;


    public RankFragment(Context context, List<RankListBean.ResultBean> ranklist) {
        this.context = context;
        this.ranklist=ranklist;

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.rank_fragment, container, false);

        //请求数据，显示排行
        unbinder = ButterKnife.bind(this, view);

//        OkHttpUtils.get().url("http://fns.mozu123.com:8080/mcFnsInterface/ranking/getRanking.app?code=0&page=0&pageSize=12")
//                .build().execute(new StringCallback() {
//            @Override
//            public void onError(Call call, Exception e, int id) {
//
//            }
//
//            @Override
//            public void onResponse(String response, int id) {
//
//                RankBean rankBean = JSON.parseObject(response, RankBean.class);
//                List<RankBean.DataBean> data = rankBean.getData();
//                //设置list的数据源
//
//                listView.setAdapter(new RankListAdapter(getActivity(),data));
//
//            }
//        });
        listView.setAdapter(new RankListAdapter(getActivity(),ranklist));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                RankListBean.ResultBean resultBean = ranklist.get(position);
                String aid = resultBean.getId();

                Intent intent=new Intent(getActivity(), ModelDetailActivity.class);
                intent.putExtra("aid",aid);
                startActivity(intent);

            }
        });
        return view;


         }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }



}
