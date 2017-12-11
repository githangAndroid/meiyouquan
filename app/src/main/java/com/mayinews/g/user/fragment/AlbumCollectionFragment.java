package com.mayinews.g.user.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.mayinews.g.R;
import com.mayinews.g.app.MyApplication;
import com.mayinews.g.user.adapter.MyCollectionAlbumAdapter;
import com.mayinews.g.user.bean.MyAlbumCollectionBean;
import com.mayinews.g.utils.SPUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.Call;

/**
 *
 */

public class AlbumCollectionFragment extends Fragment {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    Unbinder unbinder;
    @BindView(R.id.delete)
    Button delete;
    private Context context;
    private MyCollectionAlbumAdapter adapter;
    private String loginState;//是否登录状态
    private String token;//token
    private boolean isChooseClick = false;
    private List<Integer>  canList=new ArrayList<>();//需要取消订阅的的数据位置
    private List<MyAlbumCollectionBean.ResultBean> result;//获取当前用户订阅的专辑数据

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.my_album_collection, container, false);
        unbinder = ButterKnife.bind(this, view);
        context = getActivity();
        initRecycler(); //初始化RecyclerView
        initData();//请求数据
        TextView choose = (TextView) getActivity().findViewById(R.id.choose);

        choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isChooseClick) {
                    choose.setText("选择");
                    delete.setVisibility(View.GONE);
                } else {
                    choose.setText("取消");
                    delete.setVisibility(View.VISIBLE);
                }
                isChooseClick = !isChooseClick;
                adapter.setEditMode(isChooseClick);


            }
        });

        adapter.setCancleAttenListener(new MyCollectionAlbumAdapter.CancleAttenListener() {
            @Override
            public void deleteData(boolean isChecked, int position) {
                Integer integer = Integer.valueOf(position);
                if(isChecked){
                       canList.add(integer);
                   }else{
                        canList.remove(integer);
                }
            }
        });


        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 List< MyAlbumCollectionBean.ResultBean> cans=new ArrayList<MyAlbumCollectionBean.ResultBean>();


                if(canList!=null && canList.size()>0){

                    //取消订阅
                    for(Integer i:canList){

                        //执行取消操作
                        String id = result.get(i).getId();


                        doCancleAtten(id);
                    }


                    for(Integer i:canList){

                        //执行取消操作
                        MyAlbumCollectionBean.ResultBean resultBean = result.get(i);
                        cans.add(resultBean);


                    }
                    result.removeAll(cans);
                    Log.e("TAG","result.size"+result.size());
                    adapter.notifyDataSetChanged();


                }
                adapter.setEditMode(false);
                canList.clear();
                choose.setText("选择");
                delete.setVisibility(View.GONE);
                isChooseClick=false;


            }
        });
        return view;
    }

    private void doCancleAtten(String gid) {


        token = (String) SPUtils.get(context, MyApplication.TOKEN, "");
        OkHttpUtils.post().url("http://g.mayinews.com/api/favor.html")
                .addHeader("Authorization", "Bearer " + token)
                .addParams("gid",gid)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                Log.e("TAG", "收藏error" + e.getMessage());
            }

            @Override
            public void onResponse(String response, int id) {
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                int status = jsonObject.optInt("status");
                if(status==200){
                    Toast.makeText(context, "删除成功", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    private void initData() {
        token = (String) SPUtils.get(context, MyApplication.TOKEN, "");
        OkHttpUtils.get().url("http://g.mayinews.com/api/favor/max/20.html").addHeader("Authorization", "Bearer " + token)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                Log.e("TAG", "收藏error" + e.getMessage());
            }

            @Override
            public void onResponse(String response, int id) {
                Log.e("TAG", "收藏" + response);
                MyAlbumCollectionBean myAlbumCollectionBean = JSON.parseObject(response, MyAlbumCollectionBean.class);
                int status = myAlbumCollectionBean.getStatus();
                if (status == 200) {

                    result = myAlbumCollectionBean.getResult();

                    adapter.addData(result);
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }

    private void initRecycler() {
        recyclerView.setLayoutManager(new GridLayoutManager(context, 3));
        adapter = new MyCollectionAlbumAdapter(context);
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
