package com.mayinews.g.user.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
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
import com.mayinews.g.user.activity.PersonalAttenActivity;
import com.mayinews.g.user.activity.PersonalDataActivity;
import com.mayinews.g.user.activity.SettingActivity;
import com.mayinews.g.user.activity.UnReadActivity;
import com.mayinews.g.utils.Constant;
import com.mayinews.g.utils.SPUtils;
import com.mayinews.g.utils.Util;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXImageObject;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.io.ByteArrayOutputStream;

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
    @BindView(R.id.rl_atten)
    RelativeLayout rlAtten;
    private Unbinder unbinder;
    private ImageView image;
    private TextView title;
    private String userName;
    private String loginStatues;  //登录状态
    private static final int THUMB_SIZE = 150;
    private int mTargetScene = SendMessageToWX.Req.WXSceneSession;
    private IWXAPI api;
    private AlertDialog sharedDialog;
    private PopupWindow SharedPopupWindow;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.user_fragment, container, false);
        image = (ImageView) getActivity().findViewById(R.id.top_logo);
        title = (TextView) getActivity().findViewById(R.id.title);


        api = WXAPIFactory.createWXAPI(getActivity(), Constant.APPID);
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
            R.id.ll_yesLogin,R.id.rl_atten
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
                if (isLogin()) {
                    startActivity(new Intent(getActivity(), PayActivity.class));
                } else {
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                }


                break;
            case R.id.rl_payed:
                if (isLogin()) {
                    startActivity(new Intent(getActivity(), HaveBuyActivity.class));
                } else {
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                }


                break;
            case R.id.rl_vip:

                Toast.makeText(getActivity(), "VIP功能马上开放", Toast.LENGTH_SHORT).show();
                break;
            //收藏页面
            case R.id.rl_collection:
                if (isLogin()) {
                    startActivity(new Intent(getActivity(), MyCollectionActivity.class));
                } else {
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                }


                break;
            case R.id.rl_unread:
                if (isLogin()) {
                    startActivity(new Intent(getActivity(), UnReadActivity.class));
                } else {
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                }

                break;
            case R.id.rl_order:
                if (isLogin()) {
                    startActivity(new Intent(getActivity(), OrderActivity.class));
                } else {
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                }

                break;
            case R.id.rl_shared:
                if (isLogin()) {
                    //分享图片
                    //显示Dialog
                    showSharedPop();
//                    showSharedDialog();
//                    sharePicture();
                } else {
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                }


                break;
            case R.id.ll_yesLogin:
                //进入个人资料设置页面
                startActivity(new Intent(getActivity(), PersonalDataActivity.class));

                break;
            case R.id.rl_atten:
                //进入我的关注
                if (isLogin()) {
                    startActivity(new Intent(getActivity(), PersonalAttenActivity.class));
                } else {
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                }


        }

    }

    private void showSharedDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        sharedDialog = builder.create();
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.shared_dialog, null);
        sharedDialog.setContentView(view);

        LinearLayout llWx = (LinearLayout) view.findViewById(R.id.ll_wx);//微信好友
        LinearLayout llPengyou = (LinearLayout) view.findViewById(R.id.ll_pengyou);//微信朋友圈
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
        sharedDialog.show();

    }

    public void showSharedPop() {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.shared_dialog, null);
        View parent = LayoutInflater.from(getActivity()).inflate(R.layout.user_fragment, null);
        SharedPopupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);


        LinearLayout llWx = (LinearLayout) view.findViewById(R.id.ll_wx);//微信好友
        LinearLayout llPengyou = (LinearLayout) view.findViewById(R.id.ll_pengyou);//微信朋友圈
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
        TextView cancle = (TextView) view.findViewById(R.id.cancle);//微信朋友圈
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
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.shared);
        byte[] bytes = bitmap2Bytes(bitmap, 32);
        Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        WXImageObject imgObj = new WXImageObject(bmp);


        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = imgObj;
        Bitmap thumbBmp = Bitmap.createScaledBitmap(bmp, 80, THUMB_SIZE, true);
        bmp.recycle();
        msg.thumbData = Util.bmpToByteArray(thumbBmp, true);
        //构造一个req
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("img");
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
//        finish();


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
            if (avatar != null && !avatar.equals("")) {
                Glide.with(getActivity()).load(buildGlideUrl(avatar)).into(loginHead);
            }

            Log.e("TAG", "RRESUME=" + "nickname=" + nickname + "  avatar" + avatar);

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
    public boolean isLogin() {
        String state = (String) SPUtils.get(getActivity(), MyApplication.LOGINSTATUES, "0");
        if (state.equals("1")) {
            return true;
        } else {
            return false;
        }

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
        WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        getActivity().getWindow().setAttributes(lp);
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
}
