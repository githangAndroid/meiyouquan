package com.mayinews.g;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.IdRes;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.mayinews.g.album.fragment.AlbumFragment;
import com.mayinews.g.app.MyApplication;
import com.mayinews.g.home.activity.SearchActivity;
import com.mayinews.g.home.fragment.HomeFragment;
import com.mayinews.g.home.fragment.RimFragment;
import com.mayinews.g.user.activity.LoginActivity;
import com.mayinews.g.user.activity.PersonalDataActivity;
import com.mayinews.g.user.activity.SettingActivity;
import com.mayinews.g.user.fragment.UserFragment;
import com.mayinews.g.utils.Constant;
import com.mayinews.g.utils.SPUtils;
import com.mayinews.g.utils.Util;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXImageObject;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static cn.sharesdk.yixin.utils.YXMessage.THUMB_SIZE;
import static com.mayinews.g.utils.SPUtils.get;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.frameLayout)
    FrameLayout frameLayout;
    @BindView(R.id.rgMain)
    RadioGroup rgMain;
    @BindView(R.id.rb_home)
    RadioButton rbHome;
    @BindView(R.id.rb_album)
    RadioButton rbAlbum;
    @BindView(R.id.rb_user)
    RadioButton rbUser;
    @BindView(R.id.top_logo)
    ImageView topLogo;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.nav_view)
    NavigationView navView;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.search)
    ImageView search;
    @BindView(R.id.rb_nb)
    RadioButton rbNb;


    private List<Fragment> mFragmetns;
    private static final int THUMB_SIZE = 150;
    private int position;

    /**
     * 上次切换的Fragment
     */
    private Fragment mContent;
    private boolean isExit;

    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            isExit = false;
        }
    };
    private PopupWindow SharedPopupWindow;
    private IWXAPI api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        api = WXAPIFactory.createWXAPI(this, Constant.APPID);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        toolbar.setNavigationIcon(R.mipmap.ic_launcher);
//         if(topLogo!=null){
//
//             topLogo.setVisibility(View.VISIBLE);
//         }
//            if(title!=null){
//                title.setVisibility(View.GONE);
//            }

        setSupportActionBar(toolbar);


        //页面悬浮的小图标
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                //小图标的点击事件
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
        //整个的整体页面布局
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        //侧边栏下半部分
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //可以通过navigationView来加载侧边栏上半部分的view
        View head = navigationView.inflateHeaderView(R.layout.nav_header_main);
        //根据头部有获取里面的view
        ImageView iv = (ImageView) head.findViewById(R.id.imageView);
        TextView tvNickname = (TextView) head.findViewById(R.id.tv_nickname);

        head.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                 if(getLoginState()){
                     //打开个人资料页面
                     startActivity(new Intent(MainActivity.this, PersonalDataActivity.class));
                 }else{
                     //打开登录页面
                     startActivity(new Intent(MainActivity.this, LoginActivity.class));
                 }
            }
        });
        drawer.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View view, float v) {

            }

            @Override
            public void onDrawerOpened(View view) {
                    Log.e("TAG","open");
                if(getLoginState()){
                    String nickname = (String) get(MainActivity.this, MyApplication.USERNICKNAME, "");
                    String avatar = (String) get(MainActivity.this, MyApplication.USERAVATAR, "");
                    tvNickname.setText(nickname);
                    Glide.with(MainActivity.this).load(buildGlideUrl(avatar)).into(iv);
                }else{

                    tvNickname.setText("立即登录");

                }

            }

            @Override
            public void onDrawerClosed(View view) {
                Log.e("TAG","close");
            }

            @Override
            public void onDrawerStateChanged(int i) {

            }
        });

        initFragment();
        setListener();

        rgMain.check(R.id.rb_home);


    }

    private void initFragment() {
        mFragmetns = new ArrayList<>();
        mFragmetns.add(new HomeFragment());
        mFragmetns.add(new AlbumFragment());
        mFragmetns.add(new RimFragment());
        mFragmetns.add(new UserFragment());

    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);     //菜单
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    //侧边栏下半部分的点击事件
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.pay) {
            if(getLoginState()){
                Toast.makeText(MainActivity.this, "充值", Toast.LENGTH_SHORT).show();
            }else{
                startActivity(new Intent(this,LoginActivity.class));
            }

        } else if (id == R.id.haved) {
            if(getLoginState()){
                Toast.makeText(MainActivity.this, "已购买的", Toast.LENGTH_SHORT).show();
            }else{

                startActivity(new Intent(this,LoginActivity.class));
            }
        } else if (id == R.id.share) {
            if(getLoginState()){
                showSharedPop();
            }else{
                startActivity(new Intent(this,LoginActivity.class));
            }
        } else if (id == R.id.setting) {
            startActivity(new Intent(this, SettingActivity.class));
        }


        //关闭侧边栏
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    private void setListener() {

        rgMain.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId) {
                    case R.id.rb_home:
                        position = 0;
                        scaleAnimation(rbHome);
                        break;
                    case R.id.rb_album:
                        position = 1;
                        scaleAnimation(rbAlbum);
                        break;
                    case R.id.rb_nb:
                        position = 2;
                        scaleAnimation(rbNb);
                        break;
                    case R.id.rb_user:
                        position = 3;
                        scaleAnimation(rbUser);
                        break;
                    default:
                        position = 0;
                        break;
                }

                Fragment fragment = getFragment(position);
                switchFrament(mContent, fragment);
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, SearchActivity.class));
            }
        });
    }

    private void scaleAnimation(RadioButton rb) {
        ScaleAnimation scaleAnimation = new ScaleAnimation(1.0f, 1.2f, 1.0f, 1.2f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scaleAnimation.setDuration(100);
        scaleAnimation.setFillAfter(false);
        rb.startAnimation(scaleAnimation);
    }

    private Fragment getFragment(int position) {

        return mFragmetns.get(position);

    }


    /**
     * @param from 刚显示的Fragment,马上就要被隐藏了
     * @param to   马上要切换到的Fragment，一会要显示
     */
    private void switchFrament(Fragment from, Fragment to) {
        if (from != to) {
            mContent = to;
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            //才切换
            //判断有没有被添加
            if (!to.isAdded()) {
                //to没有被添加
                //from隐藏
                if (from != null) {
                    ft.hide(from);
                }
                //添加to
                if (to != null) {
                    ft.add(R.id.frameLayout, to).commit();
                }
            } else {
                //to已经被添加
                // from隐藏
                if (from != null) {
                    ft.hide(from);
                }
                //显示to
                if (to != null) {
                    ft.show(to).commit();
                }

            }
        }

    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void exit() {
        if (!isExit) {
            isExit = true;
            Toast.makeText(getApplicationContext(), "再按一次退出程序",
                    Toast.LENGTH_SHORT).show();
            // 利用handler延迟发送更改状态信息
            mHandler.sendEmptyMessageDelayed(0, 2000);
        } else {
            finish();
            System.exit(0);
        }
    }

    private GlideUrl buildGlideUrl(String url) {
        if (TextUtils.isEmpty(url)) {
            return null;
        } else {
            return new GlideUrl(url, new LazyHeaders.Builder().addHeader("Referer", "http://m.mayinews.com").build());
        }
    }


    public boolean getLoginState(){

        String state = (String) SPUtils.get(MainActivity.this, MyApplication.LOGINSTATUES, "0");
         if(state.equals("1")){
             return  true;
         }


         return false;

    }
    public void   showSharedPop(){
        View view =  LayoutInflater.from(this).inflate(R.layout.shared_dialog,null);
        View parent =  LayoutInflater.from(this).inflate(R.layout.act_main,null);
        SharedPopupWindow = new PopupWindow(view,ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);


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

        SharedPopupWindow.setOutsideTouchable(true);
        SharedPopupWindow.setFocusable(true);
        //让pop可以点击外面消失掉
        SharedPopupWindow.setBackgroundDrawable(new ColorDrawable(0));
        SharedPopupWindow.showAtLocation(parent, Gravity.BOTTOM,0,0);

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
        byte[] bytes = bitmap2Bytes(bitmap, 30);
        Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        WXImageObject imgObj = new WXImageObject(bmp);


        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = imgObj;
        Bitmap thumbBmp = Bitmap.createScaledBitmap(bmp, 80, THUMB_SIZE, true);
        bmp.recycle();
        msg.thumbData = Util.bmpToByteArray(thumbBmp, true);
        //构造一个req
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("img2");
        req.message = msg;
        if(type.equals("haoyou")){
            req.scene = SendMessageToWX.Req.WXSceneSession;
        }else if(type.equals("pengyouquan")){
            req.scene = SendMessageToWX.Req.WXSceneTimeline;
        }

        api.sendReq(req);
        //dialog消失
        SharedPopupWindow.dismiss();
        backgroundAlpha(1);
//        finish();


    }
    private String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }
    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        getWindow().setAttributes(lp);
    }

    /**
     * Bitmap转换成byte[]并且进行压缩,压缩到不大于maxkb
     * @param bitmap
     * @param
     * @return
     */
    public static byte[] bitmap2Bytes(Bitmap bitmap, int maxkb) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, output);
        int options = 100;
        while (output.toByteArray().length > maxkb&& options != 10) {
            output.reset(); //清空output
            bitmap.compress(Bitmap.CompressFormat.JPEG, options, output);//这里压缩options%，把压缩后的数据存放到output中
            options -= 10;
        }
        return output.toByteArray();
    }


}
