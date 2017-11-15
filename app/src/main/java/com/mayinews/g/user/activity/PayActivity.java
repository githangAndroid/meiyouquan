package com.mayinews.g.user.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.mayinews.g.R;
import com.mayinews.g.user.adapter.PayListAdapter1;
import com.mayinews.g.user.adapter.PayListAdapter2;
import com.mayinews.g.user.adapter.PayListAdapter3;
import com.mayinews.g.user.bean.PayDataBean;
import com.mayinews.g.user.bean.PayListViewBean;
import com.mayinews.g.user.bean.YouBiPayBean;
import com.mayinews.g.view.CustomExpandableListView;
import com.mayinews.g.view.MyListView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

public class PayActivity extends AppCompatActivity {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.tv_balance)
    TextView tvBalance;
    @BindView(R.id.listView1)
    CustomExpandableListView listView1;
    @BindView(R.id.listView2)
    CustomExpandableListView listView2;
    @BindView(R.id.listView3)
    MyListView listView3;
    private PayListAdapter1 payListAdapter1;
    private PayListAdapter2 payListAdapter2;
    private PayListAdapter3 payListAdapter3;
    private List<PayDataBean> dataBean;  //类型一的数据
    private boolean isExpand1=false;  //默认是关闭的

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);
        ButterKnife.bind(this);
        title.setText("充值");
        initListVive1();
        initListVive2();
        initListVive3();
        requestData1();
        requestData2();
        requestData3();
    }

    private void requestData3() {
        OkHttpUtils.get().url("http://fnsandroid4.mozu123.com/mcFnsInterface/v2_recharge/getRechargeByPage.app").build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        YouBiPayBean youBiPayBean = JSON.parseObject(response, YouBiPayBean.class);

                        List<YouBiPayBean.DataBean> data = youBiPayBean.getData();
                        payListAdapter3.addData(data);
                        payListAdapter3.notifyDataSetChanged();
                    }
                });



    }

    private void requestData2() {
        OkHttpUtils.get().url("http://fnsandroid4.mozu123.com/mcFnsInterface/vip/getVIPByType.app?type=0").build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        PayListViewBean exListViewBean1 = JSON.parseObject(response, PayListViewBean.class);
                        String data = exListViewBean1.getData();
                        List<PayDataBean> dataBean = JSON.parseArray(data, PayDataBean.class);
                        payListAdapter2.addData(dataBean);
                        payListAdapter2.notifyDataSetChanged();
                    }
                });

    }

    private void initListVive3() {
         payListAdapter3 = new PayListAdapter3(this);
         listView3.setAdapter(payListAdapter3);

    }

    private void requestData1() {
        OkHttpUtils.get().url("http://fnsandroid4.mozu123.com/mcFnsInterface/vip/getVIPByType.app?type=1").build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        PayListViewBean exListViewBean1 = JSON.parseObject(response, PayListViewBean.class);
                        String data = exListViewBean1.getData();
                        dataBean = JSON.parseArray(data, PayDataBean.class);
                        payListAdapter1.addData(dataBean);
                        payListAdapter1.notifyDataSetChanged();
                    }
                });
    }

    private void initListVive1() {
         payListAdapter1 = new PayListAdapter1(this);
         listView1.setAdapter(payListAdapter1);
        //设置父节点(章目录)不可点击
        listView1.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                return true;//返回true,表示不可点击
            }
        });

        listView1.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
                for (int i = 0; i < dataBean.size(); i++) {
                    if (groupPosition != i) {
                        listView1.collapseGroup(i);

                    }
                }

            }

        });

        payListAdapter1.setGroupExpand1(new PayListAdapter1.GroupExpand1() {
            @Override
            public void groupExpand(int position) {
                if(isExpand1){
                    listView1.collapseGroup(position);
                }else{
                    listView1.expandGroup(position);
                }
                isExpand1=!isExpand1;
            }
        });
    }
    private void initListVive2() {
        payListAdapter2 = new PayListAdapter2(this);
        listView2.setAdapter(payListAdapter2);
    }

    @OnClick(R.id.iv_back)
    public void OnClick(View view) {
        switch (view.getId()) {

            case R.id.iv_back:
                finish();
                break;
        }

    }

}
