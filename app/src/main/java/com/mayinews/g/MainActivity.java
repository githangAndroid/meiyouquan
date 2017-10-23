package com.mayinews.g;

import android.content.Intent;
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
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.mayinews.g.album.fragment.AlbumFragment;
import com.mayinews.g.home.activity.SearchActivity;
import com.mayinews.g.home.fragment.HomeFragment;
import com.mayinews.g.home.fragment.RimFragment;
import com.mayinews.g.user.activity.LoginActivity;
import com.mayinews.g.user.activity.SettingActivity;
import com.mayinews.g.user.fragment.UserFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
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

        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //打开登录页面
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
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
            Toast.makeText(MainActivity.this, "充值", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.haved) {
            Toast.makeText(MainActivity.this, "已购买", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.share) {
            Toast.makeText(MainActivity.this, "分享", Toast.LENGTH_SHORT).show();
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


}
