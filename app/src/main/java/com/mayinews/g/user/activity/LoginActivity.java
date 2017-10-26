package com.mayinews.g.user.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.mayinews.g.R;
import com.mayinews.g.app.MyApplication;
import com.mayinews.g.app.bean.UserInfoBean;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

import com.mayinews.g.utils.Constant;
import com.mayinews.g.utils.NetworkUtils;
import com.mayinews.g.utils.SPUtils;
import com.mayinews.g.utils.StringUtil;

public class
LoginActivity extends AppCompatActivity {


    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.ll_qq)
    LinearLayout llQq;
    @BindView(R.id.WeChat)
    LinearLayout WeChat;
    @BindView(R.id.et_phoneNumber)
    EditText etPhoneNumber;
    @BindView(R.id.et_author_code)
    EditText etAuthorCode;
    @BindView(R.id.tv_getcode)
    TextView tvGetcode;
    @BindView(R.id.bt_login)
    Button btLogin;

    private static final int TIME = 1;
    private static int recentTime = 299;

    Handler handler = new Handler() {


        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case TIME:
                    recentTime--;

                    if (recentTime > 0) {
                        tvGetcode.setText(recentTime + "秒");
                        this.sendEmptyMessageDelayed(TIME, 1000);
                    } else {

                        tvGetcode.setEnabled(true);
                        this.removeCallbacksAndMessages(null);
                        tvGetcode.setText("获取验证码");
                    }
                    break;
            }

        }

    };
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_login);
            ButterKnife.bind(this);
        }


        @OnClick({R.id.iv_back, R.id.ll_qq, R.id.WeChat, R.id.tv_getcode, R.id.bt_login})
        public void OnClick(View v) {
            switch (v.getId()) {
                case R.id.iv_back:
                    finish();
                    break;
                case R.id.ll_qq:
                    //qq登录
                    break;

                case R.id.WeChat:
                    //微信登录
                    break;
                case R.id.tv_getcode: //获取验证码
                    getVerCode();

                    break;
                case R.id.bt_login:
                    //登录
                    String mobile = etPhoneNumber.getText().toString();   //获取手机号
                    String number = etAuthorCode.getText().toString();  //获取验证码
                    if (!StringUtil.isEmpty(number)) {   //填写了验证码
                        if (!StringUtil.isEmpty(mobile)) {
                            if (StringUtil.isMobile(mobile)) {


                                //判断网络状态

                                if (NetworkUtils.isNetworkAvailable(LoginActivity.this)) {

                                    Log.e("TAG", "mobile" + mobile + "    pho" + number);
                                    /**
                                     *    发送登录请求
                                     */
                                    OkHttpUtils.post().url(Constant.LOGINURL)
                                            .addParams("mobile", mobile)
                                            .addParams("number", number)
                                            .build().execute(new StringCallback() {
                                        @Override
                                        public void onError(Call call, Exception e, int id) {
                                            /**
                                             * 请求失败
                                             */
                                            Log.e("TAG", "错误" + e.getMessage());
                                            Toast.makeText(LoginActivity.this, "系统错误，请稍后再试", Toast.LENGTH_SHORT).show();

                                        }

                                        @Override
                                        public void onResponse(String response, int id) {
                                            /**
                                             * 请求成功
                                             */
                                            Log.i("TAG", "请求结果" + response);
                                            try {
                                                JSONObject jsonObject = null;
                                                jsonObject = new JSONObject(response);

                                                int status = jsonObject.optInt("status");
                                                if (status == 200) {
//
//
//
                                                    //获取token
                                                    String token = jsonObject.optString("jwt");
//
//                                               //保存token
                                                    SPUtils.put(LoginActivity.this, MyApplication.TOKEN, token);
//
//                                                /**
//                                                 *   解析token的得到客户信息
//                                                 */
//                                                //1 截取token字符串，以 “，“分开
                                                    String toeknString = token;

//                                                String[] split = toeknString.split("\\.");

                                                    //使用base64解析
//                                                byte[] data = Base64.decode(split[1].toString(), Base64.DEFAULT);
//                                                String dataJson = new String(data);
//                                                Log.i("TAG", "用户信息JSON字符串==" + dataJson);
//                                                JSONObject jsonObject1 = new JSONObject(dataJson);
//                                                JSONObject userInfo = jsonObject1.optJSONObject("data");
                                                    //保存登录成功的字段
                                                    SPUtils.put(LoginActivity.this, MyApplication.LOGINSTATUES, "1");
                                                    SaveUserInfo();


                                                } else {

                                                    handler.removeCallbacksAndMessages(null);
                                                    etAuthorCode.setText("获取验证码");
                                                    etAuthorCode.setEnabled(true);
                                                    Toast.makeText(LoginActivity.this, "系统错误,请稍后重试", Toast.LENGTH_SHORT).show();

                                                }
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }

                                        }
                                    });
                                } else {

                                    Toast.makeText(this, "您的网络状况不好", Toast.LENGTH_SHORT).show();
                                }

                            } else {
                                Toast.makeText(this, "请输入正确的手机号", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(this, "请输入手机号", Toast.LENGTH_SHORT).show();
                        }


                    } else {
                        Toast.makeText(this, "请输入验证码", Toast.LENGTH_SHORT).show();
                    }


                    break;

            }


        }

        private void SaveUserInfo() {


            //如果死登录状态状态，就去获取用户的信息
            String token = (String) SPUtils.get(this, MyApplication.TOKEN, "");
            Log.e("TAG", "token222" + token);
            if (token != null && !token.equals("")) {
                //获取用户的信息
                OkHttpUtils.get().url(Constant.GETUSERINFO).addHeader("authorization", "Bearer " + token)
                        .build().execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        UserInfoBean userInfoBean = JSON.parseObject(response, UserInfoBean.class);
                        int status = userInfoBean.getStatus();
                        Log.e("TAG", "status" + status);
                        if (status == 200) {
                            UserInfoBean.ResultBean result = userInfoBean.getResult();
                            String uid = result.getUid();
                            String avatar = result.getAvatar();
                            String nickname = result.getNickname();
                            String sex = result.getSex();
//                        Object desc = result.getDesc();

                            //保存信息

                            SPUtils.put(LoginActivity.this, Constant.USERID, uid);
                            SPUtils.put(LoginActivity.this, Constant.USERAVATAR, avatar);
                            SPUtils.put(LoginActivity.this, Constant.USERNICKNAME, nickname);
                            SPUtils.put(LoginActivity.this, Constant.USERSEX, sex);
                            Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                            Log.e("TAG", "uid" + uid + " avatar" + avatar + " nickname" + nickname + " sex" + sex);
                            finish();
                        }


                    }
                });

            }


        }

        private void getVerCode() {
            //网络连接
            final String phoneNumber = etPhoneNumber.getText().toString();

            if (!StringUtil.isEmpty(phoneNumber)) {
                if (StringUtil.isMobile(phoneNumber)) {

                    if (NetworkUtils.isNetworkAvailable(LoginActivity.this)) {

                        //发送消息
                        tvGetcode.setEnabled(false);
                        recentTime = 299;
                        tvGetcode.setText(recentTime + "秒");
                        handler.sendEmptyMessageDelayed(TIME, 1000);
                        /**
                         * 请求验证码
                         */
                        OkHttpUtils.post().url(Constant.GETCODE).addParams("mobile", phoneNumber)
                                .build().execute(new StringCallback() {
                            @Override
                            public void onError(Call call, Exception e, int id) {
                                //请求失败
                                Log.i("TAG", "验证码请求失败" + e.getMessage());
                            }

                            @Override
                            public void onResponse(String response, int id) {
//                            //请求成功
//                            Log.i("TAG", "验证码请求成功" + response);
                                String substring = phoneNumber.substring(7);
                                //保存用户的手机号
                                SPUtils.put(LoginActivity.this, MyApplication.PHONENUMBER, phoneNumber);
////                                    ToastUtil.showToast(LoginActivity.this, "验证码已发送至尾号为"+substring+"的手机号");
                                Toast.makeText(LoginActivity.this, "验证码已发送至尾号为" + substring + "的手机号", Toast.LENGTH_SHORT).show();
                                Log.e("TAG", "验证码" + response);
                            }
                        });
                    } else {
                        Toast.makeText(this, "获取验证码失败", Toast.LENGTH_SHORT).show();

                    }

                } else {
                    Toast.makeText(this, "请输入正确的手机号", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "手机号不能为空", Toast.LENGTH_SHORT).show();
//                    ToastUtil.showToast(LoginActivity.this, "手机号不能为空");
            }
        }


    }




