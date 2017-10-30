package com.mayinews.g.user.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.mayinews.g.R;
import com.mayinews.g.app.MyApplication;
import com.mayinews.g.user.activity.HaveBuyActivity;
import com.mayinews.g.user.activity.LoginActivity;
import com.mayinews.g.user.activity.MyCollectionActivity;
import com.mayinews.g.user.activity.OrderActivity;
import com.mayinews.g.user.activity.PayActivity;
import com.mayinews.g.user.activity.PersonalDataActivity;
import com.mayinews.g.user.activity.SettingActivity;
import com.mayinews.g.user.activity.UnReadActivity;
import com.mayinews.g.utils.Constant;
import com.mayinews.g.utils.SPUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.mayinews.g.R.id.ll_noLogin;
import static com.mayinews.g.R.id.ll_setData;

/**
 * Created by 盖航_ on 2017/8/29.
 */

public class UserFragment extends Fragment {


    @BindView(R.id.login)
    TextView login;
    @BindView(R.id.item_setting)
    RelativeLayout itemSetting;
    @BindView(R.id.rl_pay)
    RelativeLayout rlPay;
    @BindView(R.id.rl_vip)
    RelativeLayout rlVip;
    @BindView(R.id.rl_payed)
    RelativeLayout rlPayed;
    @BindView(R.id.rl_collection)
    RelativeLayout rlCollection;
    @BindView(R.id.rl_unread)
    RelativeLayout rlUnread;
    @BindView(R.id.rl_order)
    RelativeLayout rlOrder;
    @BindView(R.id.rl_shared)
    RelativeLayout rlShared;
    @BindView(R.id.headView)
    ImageView headView;
    @BindView(ll_noLogin)
    LinearLayout llNoLogin;
    @BindView(R.id.ll_yesLogin)
    RelativeLayout llYesLogin;
    @BindView(R.id.tv_nickName)
    TextView tvNickName;
    @BindView(ll_setData)
    LinearLayout llSetData;
    @BindView(R.id.iv_pay)
    ImageView ivPay;
    @BindView(R.id.iv_vip)
    ImageView ivVip;
    @BindView(R.id.iv_payed)
    ImageView ivPayed;
    @BindView(R.id.iv_collection)
    ImageView ivCollection;
    @BindView(R.id.iv_mession)
    ImageView ivMession;
    @BindView(R.id.iv_order_form)
    ImageView ivOrderForm;
    @BindView(R.id.iv_share)
    ImageView ivShare;
    @BindView(R.id.iv_setting)
    ImageView ivSetting;
    @BindView(R.id.login_head)
    CircleImageView loginHead;
    private Unbinder unbinder;
    private ImageView image;
    private TextView title;
    private String userName;
    private String loginStatues;  //登录状态

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.user_fragment, container, false);
        image = (ImageView) getActivity().findViewById(R.id.top_logo);
        title = (TextView) getActivity().findViewById(R.id.title);
        if (image != null) {
            image.setVisibility(View.GONE);
        }
        if (title != null) {
            title.setVisibility(View.VISIBLE);
            title.setText("个人中心");
        }
        unbinder = ButterKnife.bind(this, view);
//        Glide.with(this).load("http://fns-userimg-public.oss-cn-hangzhou.aliyuncs.com/150667638881673806a.jpg").into(headView);
        return view;
    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        if (!hidden) {
            if (image != null) {
                image.setVisibility(View.GONE);
            }
            if (title != null) {
                title.setVisibility(View.VISIBLE);
                title.setText("个人中心");
            }
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.login, R.id.item_setting, R.id.rl_pay, R.id.rl_vip, R.id.rl_collection, R.id.rl_payed, R.id.rl_unread, R.id.rl_order, R.id.rl_shared,
            R.id.ll_setData
    })
    public void Onclick(View view) {
        switch (view.getId()) {
            case R.id.login:
                //打开登陆页面
                startActivity(new Intent(getActivity(), LoginActivity.class));
                break;
            case R.id.item_setting:
                //进入设置页面
                startActivity(new Intent(getActivity(), SettingActivity.class));
                break;
            case R.id.rl_pay:
                if(isLogin()){
                    startActivity(new Intent(getActivity(), PayActivity.class));
                }else{
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                }


                break;
            case R.id.rl_payed:
                if(isLogin()){
                    startActivity(new Intent(getActivity(), HaveBuyActivity.class));
                }else{
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                }


                break;
            case R.id.rl_vip:

                Toast.makeText(getActivity(), "VIP功能马上开放", Toast.LENGTH_SHORT).show();
                break;
            //收藏页面
            case R.id.rl_collection:
                if(isLogin()){
                    startActivity(new Intent(getActivity(), MyCollectionActivity.class));
                }else{
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                }


                break;
            case R.id.rl_unread:
                if(isLogin()){
                    startActivity(new Intent(getActivity(), UnReadActivity.class));
                }else{
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                }

                break;
            case R.id.rl_order:
                if(isLogin()){
                    startActivity(new Intent(getActivity(), OrderActivity.class));
                }else{
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                }

                break;
            case R.id.rl_shared:
                if(isLogin()){
                    Toast.makeText(getActivity(), "分享", Toast.LENGTH_SHORT).show();
                }else{
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                }


                break;
            case ll_setData:
                //进入个人资料设置页面
                startActivity(new Intent(getActivity(), PersonalDataActivity.class));
                break;

        }

    }


    @Override
    public void onResume() {
        super.onResume();
        loginStatues = (String) SPUtils.get(getActivity(), MyApplication.LOGINSTATUES, "0");
        if (loginStatues.equals("1")) {   //登录状态

            llYesLogin.setVisibility(View.VISIBLE);
            llNoLogin.setVisibility(View.GONE);
//            login.setText("默认用户");
            //设置昵称
            String nickname = (String) SPUtils.get(getActivity(), MyApplication.USERNICKNAME, "");
            String avatar = (String) SPUtils.get(getActivity(), MyApplication.USERAVATAR, "");
            tvNickName.setText(nickname);
            Glide.with(getActivity()).load(buildGlideUrl(avatar)).into(loginHead);
            Log.e("TAG","RRESUME="+"nickname="+nickname+"  avatar"+avatar);

        } else {//非登录状态
            llNoLogin.setVisibility(View.VISIBLE);
            llYesLogin.setVisibility(View.GONE);
            login.setText("登录");
        }
    }


    private GlideUrl buildGlideUrl(String url) {
        if (TextUtils.isEmpty(url)) {
            return null;
        } else {
            return new GlideUrl(url, new LazyHeaders.Builder().addHeader("Referer", "http://m.mayinews.com").build());
        }
    }

    //判断当前是否为登录状态
    public boolean isLogin(){
          String state = (String) SPUtils.get(getActivity(),MyApplication.LOGINSTATUES,"0");
        if (state.equals("1")){
             return  true;
        }else{
            return  false;
        }

    }
}
