/*
 * 官网地站:http://www.mob.com
 * 技术支持QQ: 4006852216
 * 官方微信:ShareSDK   （如果发布新版本的话，我们将会第一时间通过微信将版本更新内容推送给您。如果使用过程中有任何问题，也可以通过微信与我们取得联系，我们将会在24小时内给予回复）
 *
 * Copyright (c) 2013年 mob.com. All rights reserved.
 */

package com.mayinews.g.wxapi;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.mayinews.g.app.MyApplication;
import com.mayinews.g.user.bean.UserInfoBean;
import com.mayinews.g.utils.Constant;
import com.mayinews.g.utils.SPUtils;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Call;


/**
 * Created by Administrator on 2016/8/16.
 */
public class WXEntryActivity extends Activity implements IWXAPIEventHandler {
	private static final int RETURN_MSG_TYPE_LOGIN = 1;
	private static final int RETURN_MSG_TYPE_SHARE = 2;
	private IWXAPI api;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//注册API
		api = WXAPIFactory.createWXAPI(this, Constant.APPID);
		api.handleIntent(getIntent(), this);
		Log.i("savedInstanceState"," sacvsa"+api.handleIntent(getIntent(), this));
	}
	@Override
	public void onReq(BaseReq baseReq) {

	}
	//  发送到微信请求的响应结果
//	@Override
//	public void onResp(BaseResp resp) {
//
//			SendAuth.Resp newResp = (SendAuth.Resp) resp;
//			//获取微信传回的code
//			String code = newResp.code;
//		   //请求cookie
//		   Log.e("TAG","code"+code);
//            getWeixinCookie(code);
//
//
////		Intent intent=new Intent(this, CodeActivity.class);
////		intent.putExtra("code",code);
////		startActivity(intent);
//
//
//
//
//		switch (resp.errCode) {
//			case BaseResp.ErrCode.ERR_OK:
//				Log.i("savedInstanceState","发送成功ERR_OKERR_OK");
//				//发送成功
//				break;
//			case BaseResp.ErrCode.ERR_USER_CANCEL:
//				Log.i("savedInstanceState","发送取消ERR_USER_CANCEL");
//				//发送取消
//				break;
//			case BaseResp.ErrCode.ERR_AUTH_DENIED:
//				Log.i("savedInstanceState","发送取消ERR_AUTH_DENIEDERR_AUTH_DENIEDERR_AUTH_DENIED");
//				//发送被拒绝
//				break;
//			default:
//				Log.i("savedInstanceState","发送返回breakbreakbreak");
//				//发送返回
//				break;
//		}
//
//
//
//	}
//



	//发送到微信请求的响应结果
//    @Override
	public void onResp(BaseResp resp) {
		switch (resp.errCode) {
			case BaseResp.ErrCode.ERR_OK: //发送成功
				switch (resp.getType()) {
					case ConstantsAPI.COMMAND_SENDAUTH:
						//登录回调,处理登录成功的逻辑
						String code = ((SendAuth.Resp) resp).code; //即为所需的code
						//请求cookie
						getWeixinCookie(code);
						break;
					case ConstantsAPI.COMMAND_SENDMESSAGE_TO_WX:
						//分享回调,处理分享成功后的逻辑
						Log.e("TA","微信分享成功");
						finish();
						break;
					default:
						break;
				}


				break;
			case BaseResp.ErrCode.ERR_USER_CANCEL: //发送取消
				switch (resp.getType()) {
					case ConstantsAPI.COMMAND_SENDAUTH:
						Log.e("TA","登录取消了");
						break;
					case ConstantsAPI.COMMAND_SENDMESSAGE_TO_WX:
						Log.e("TA","分享取消了");
						break;
				}
				finish();
				break;
			case BaseResp.ErrCode.ERR_AUTH_DENIED: //发送被拒绝
				Toast.makeText(this, "失败", Toast.LENGTH_SHORT).show();
				break;
			default://发送返回

				break;
		}

	}
	private void getWeixinCookie(String code) {

		Log.e("TAG","微信进来了");

		OkHttpUtils.post().url("http://g.mayinews.com/api/loginwexin.html").addParams("code",code).
				build().execute(new StringCallback() {
			@Override
			public void onError(Call call, Exception e, int id) {

			}

			@Override
			public void onResponse(String response, int id) {
				/**
				 * 请求成功
				 */
				Log.i("TAG", "微信请求结果" + response);

					JSONObject jsonObject = null;

				try {
					jsonObject = new JSONObject(response);
					int status = jsonObject.optInt("status");
					if (status == 200){

//
						//获取token
						String token = jsonObject.optString("jwt");
//
//                                               //保存token
						SPUtils.put(WXEntryActivity.this, MyApplication.TOKEN, token);
						String toeknString = token;

						SPUtils.put(WXEntryActivity.this, MyApplication.LOGINSTATUES, "1");
						SaveUserInfo();


					}else{

						Toast.makeText(WXEntryActivity.this, "认证失败，请稍后再试", Toast.LENGTH_SHORT).show();
						finish();
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}


			}
		});


	}
	private void SaveUserInfo() {


		//如果是登录状态状态，就去获取用户的信息
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
                    UserInfoBean wxLoginBean = JSON.parseObject(response, UserInfoBean.class);
                    int status = wxLoginBean.getStatus();
					Log.e("TAG", "status" + status);
					if (status == 200) {
                        UserInfoBean.ResultBean result = wxLoginBean.getResult();
                        String uid = result.getId();
						String avatar = result.getAvatar();
						String nickname = result.getNickname();

//                        Object desc = result.getDesc();

						//保存信息

						SPUtils.put(WXEntryActivity.this, MyApplication.USERID, uid);
						SPUtils.put(WXEntryActivity.this, MyApplication.USERAVATAR, avatar);
						SPUtils.put(WXEntryActivity.this, MyApplication.USERNICKNAME, nickname);
						Toast.makeText(WXEntryActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
						Log.e("TAG", "uid" + uid + " avatar" + avatar + " nickname" + nickname);
						finish();
					}


				}
			});

		}


	}
}