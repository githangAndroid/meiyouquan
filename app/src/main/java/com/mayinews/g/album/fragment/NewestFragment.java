package com.mayinews.g.album.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.fastjson.JSON;
import com.github.jdsjlzx.ItemDecoration.SpacesItemDecoration;
import com.github.jdsjlzx.interfaces.OnItemClickListener;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.mayinews.g.R;
import com.mayinews.g.album.adapter.MyLrecyclerAdapter;

import com.mayinews.g.home.activity.ModelDetailActivity;
import com.mayinews.g.home.activity.PhotosActivity;
import com.mayinews.g.home.bean.HomeReBean;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.Call;

/**
 * Created by Administrator on 2017/8/31 0031.
 * 最新上架
 */

public class NewestFragment extends Fragment {

    @BindView(R.id.lRecyclerView)
    com.github.jdsjlzx.recyclerview.LRecyclerView lRecyclerView;
    Unbinder unbinder;
    private MyLrecyclerAdapter adapter;
    private LRecyclerViewAdapter mLRecyclerViewAdapter;
    private String id;
     List<HomeReBean.ResultBean> data;
    public NewestFragment(String id) {
        this.id = id;
    }


    public NewestFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.album_item, container, false);
        unbinder = ButterKnife.bind(this, view);
        setLRecyclerView();//设置LRecyclerView
        RequestData();   //请求数据
        return view;
    }


    private void setLRecyclerView() {
        adapter = new MyLrecyclerAdapter(getActivity());
        lRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        mLRecyclerViewAdapter = new LRecyclerViewAdapter(adapter);
        lRecyclerView.setAdapter(mLRecyclerViewAdapter);
        lRecyclerView.setLoadMoreEnabled(false);
        lRecyclerView.setPullRefreshEnabled(false);
        int spacing = getResources().getDimensionPixelSize(R.dimen.dp_4);
        lRecyclerView.addItemDecoration(SpacesItemDecoration.newInstance(spacing, spacing, 2, Color.WHITE));
        adapter.setAvaListener(new MyLrecyclerAdapter.AvaListener() {

            @Override
            public void onAvaListener(int position) {
                HomeReBean.ResultBean pData = data.get(position);
                final String aid = pData.getActor_id();
                Intent intent = new Intent(getActivity(), ModelDetailActivity.class);
                intent.putExtra("aid",aid);
                startActivity(intent);
            }

        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    private void RequestData() {
        //请求Recycler的数据

        OkHttpUtils.get().url("http://g.mayinews.com/api/pullup/cid/" + id)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                final HomeReBean homeReBean = JSON.parseObject(response, HomeReBean.class);
                data = homeReBean.getResult();
                adapter.addData(data);
                mLRecyclerViewAdapter.notifyDataSetChanged();

                mLRecyclerViewAdapter.setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
//                        HomeReBean.ResultBean pData = data.get(position);
//                        Intent intent = new Intent(getActivity(), ModelDetailActivity.class);
//                        intent.putExtra("data",pData);
//                        startActivity(intent);
                        String id = data.get(position).getId();
                        Intent intent = new Intent(getActivity(), PhotosActivity.class);
                        intent.putExtra("id",id);
                        startActivity(intent);


                    }
                });

            }


        });
    }
}
