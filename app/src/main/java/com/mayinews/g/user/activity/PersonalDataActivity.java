package com.mayinews.g.user.activity;

import android.Manifest;
import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.mayinews.g.R;
import com.mayinews.g.app.MyApplication;
import com.mayinews.g.user.bean.PostAvaterResultBean;
import com.mayinews.g.utils.Constant;
import com.mayinews.g.utils.SPUtils;
import com.yalantis.ucrop.UCrop;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;


import java.io.File;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;


public class PersonalDataActivity extends AppCompatActivity {

    private static final int REQUESTEXTRASTORAGE = 0;
    private static final int CHOOSE_PHOTO = 1;
    private static final int TAKE_PHOTO = 2;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.headView)
    CircleImageView headView;
    @BindView(R.id.et_nickname)
    EditText etNickname;
    @BindView(R.id.clear)
    ImageView clear;
    @BindView(R.id.tv_sex)
    TextView tvSex;
    @BindView(R.id.et_address)
    EditText etAddress;
    @BindView(R.id.btn_finish)
    Button btnFinish;

    AlertDialog dialog;//选择图像的alertDialog

    File outputImage;
    private Uri imageUrl;
    private AlertDialog sexDialog;  //选择性别的
    String token;//token
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_data);

        ButterKnife.bind(this);
        title.setText("我的");


//        File file = new File(getExternalCacheDir(), "head.jpg");
//
//
//        boolean exists = file.exists();
//        Log.e("TAG", "exists===" + exists);
//        if (exists) {
//            displayImage(file.getAbsolutePath());
//        }



        //设置昵称
        String nickname = (String) SPUtils.get(this, MyApplication.USERNICKNAME, "");
        //1获取token
       token = (String) SPUtils.get(this, MyApplication.TOKEN, "");
        String sex = (String) SPUtils.get(this, MyApplication.USERSEX, "");
        if (nickname != null && !nickname.equals("")) {
            etNickname.setText(nickname);
        }
        if(sex.equals("0")){
            tvSex.setText("男");
        }else if(sex.equals("1")){
            tvSex.setText("女");
        }


        String avatar = (String) SPUtils.get(this, MyApplication.USERAVATAR, "");
        Log.e("TAG","姓名"+nickname+"   头像"+avatar);
        if(avatar!=null && !avatar.equals("")){
            Glide.with(this).load(buildGlideUrl(avatar)).into(headView);
        }


    }


    @OnClick({R.id.iv_back, R.id.headView, R.id.tv_sex, R.id.btn_finish,R.id.clear})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();

                break;
            case R.id.headView:
                //打开头像选择
                showHeadDialog();
                break;
            case R.id.tv_sex:
                //打开性别选择框
                showChooseSexDialog();

                break;
            case R.id.btn_finish:
                //执行保存接口
                //1 判断昵称是否为空
                if (etNickname.getText().toString().isEmpty()) {
                    Toast.makeText(this, "昵称不能为空", Toast.LENGTH_SHORT).show();
                } else {
                    //保存昵称


                    OkHttpUtils.post().url(Constant.SETNICKNAME).addHeader("authorization", "Bearer " + token)
                            .addParams("nickname", etNickname.getText().toString())
                            .build().execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e, int id) {

                            Log.e("TAG","昵称报错出错"+e.getMessage());
                            Toast.makeText(PersonalDataActivity.this, "设置失败请稍后再试", Toast.LENGTH_SHORT).show();

                        }

                        @Override
                        public void onResponse(String response, int id) {

                            Log.e("TAG","保存昵称成功resonse"+response+"    outFile="+outputImage);
                            //保存信息
                            SPUtils.put(PersonalDataActivity.this,MyApplication.USERNICKNAME,etNickname.getText().toString());
                            //执行保存头像功能
                            saveHeanIcon();

                        }
                    });
                }

                break;

               case R.id.clear:

                    etNickname.setText("");

                break;


        }


    }

    private void saveHeanIcon() {
        if(outputImage!=null){


        OkHttpUtils.post()
                .url(Constant.SETUSERAVATAR)
                .addHeader("Authorization","Bearer "+token)
                .addFile("file","avatar.jpg",outputImage)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                           Log.e("TAG","保存头像错误="+e.getMessage());
                        Toast.makeText(PersonalDataActivity.this, "设置失败请稍后再试", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String response, int id) {


                        Log.e("TAG","保存成功后的头像response="+response);
                        PostAvaterResultBean resultBean = JSON.parseObject(response, PostAvaterResultBean.class);
                        if(resultBean.getStatus()==200){

                            PostAvaterResultBean.ResultBean result = resultBean.getResult();
                            String path = result.getPath();
                            //保存更新后的头像地址
                            SPUtils.put(PersonalDataActivity.this,MyApplication.USERAVATAR,path);
                            Toast.makeText(PersonalDataActivity.this, "信息保存成功", Toast.LENGTH_SHORT).show();
                            finish();
                        }else{
                            Toast.makeText(PersonalDataActivity.this, "设置失败请稍后再试", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
        }else{

            Toast.makeText(PersonalDataActivity.this, "信息保存成功", Toast.LENGTH_SHORT).show();
            finish();
        }

    }

    private void showChooseSexDialog() {


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View choseHeadView = View.inflate(this, R.layout.chose_sex_dialog, null);

        sexDialog = builder.create();
        sexDialog.setView(choseHeadView);
        Window window = sexDialog.getWindow();
        window.setGravity(Gravity.BOTTOM);
        window.setBackgroundDrawableResource(android.R.color.transparent);
        sexDialog.show();
        WindowManager windowManager = getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = sexDialog.getWindow().getAttributes();
        lp.width = (int) (display.getWidth() - 4); //设置宽度
        sexDialog.getWindow().setAttributes(lp);

        TextView tvMan = (TextView) choseHeadView.findViewById(R.id.tv_man);
        TextView tvWoman = (TextView) choseHeadView.findViewById(R.id.tv_woman);
        TextView tvCancle = (TextView) choseHeadView.findViewById(R.id.tv_cancle);

        tvMan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //设置相别为男
                tvSex.setText("男");
                sexDialog.dismiss();
            }
        });

        tvWoman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvSex.setText("女");
                sexDialog.dismiss();
            }
        });
        tvCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sexDialog.dismiss();
            }
        });


    }

    private void showHeadDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View choseHeadView = View.inflate(this, R.layout.chose_head_dialog, null);

        dialog = builder.create();
        dialog.setView(choseHeadView);
        Window window = dialog.getWindow();
        window.setGravity(Gravity.BOTTOM);
        window.setBackgroundDrawableResource(android.R.color.transparent);
        dialog.show();
        WindowManager windowManager = getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.width = (int) (display.getWidth() - 4); //设置宽度
        dialog.getWindow().setAttributes(lp);

        TextView tvPhoto = (TextView) choseHeadView.findViewById(R.id.tv_photo);
        TextView tvCamera = (TextView) choseHeadView.findViewById(R.id.tv_camera);
        TextView tvCancle = (TextView) choseHeadView.findViewById(R.id.tv_cancle);
        tvCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File file = new File(getExternalCacheDir(), "take_photo.jpg");
                try {
                    if (file.exists()) {
                        file.delete();
                    } else {
                        file.createNewFile();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }


                //打开相机
//                CreateFile();
                if (Build.VERSION.SDK_INT >= 24) {
                    imageUrl = FileProvider.getUriForFile(PersonalDataActivity.this, "com.mayinews.g", file);

                } else {
                    imageUrl = Uri.fromFile(file);
                }

                //启动相机

                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUrl);
                startActivityForResult(intent, TAKE_PHOTO);
                dialog.dismiss();
            }
        });
        tvPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //打开相册选择图片
                getPicture();  //打开图库
            }
        });


        tvCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


    }

    private void getPicture() {
        //检查权限
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            //没有授权
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUESTEXTRASTORAGE);

        } else {

            openAlbum();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {

            case REQUESTEXTRASTORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //已经授予权限，打开相册
                    openAlbum();
                } else {
                    //拒绝授权


                }
                break;


        }
    }

    private void openAlbum() {
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent, CHOOSE_PHOTO);
        dialog.dismiss();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case CHOOSE_PHOTO:
//


                    startCropActivity(data.getData());
                    break;

                case UCrop.REQUEST_CROP:

                    Uri resultUri = UCrop.getOutput(data);
                    Log.e("TAG", "回来啦" + resultUri);
//                if (Build.VERSION.SDK_INT >=19) {
//                    //4.4以上使用这个方法a
//                    handleImageOnKitKat(resultUri);
//
//                } else {
//                    //4.4以下使用这个方法
//                    handleImageBeforeKitKat(resultUri);
//
//                }



                    displayImage(outputImage.getAbsolutePath());

                    break;

                case TAKE_PHOTO:
                    //进入裁剪
                    startCropActivity(imageUrl);
                    break;
            }

        }
    }


    public void startCropActivity(Uri uri) {
        CreateFile();

        UCrop crop = UCrop.of(uri, Uri.fromFile(outputImage));
        crop.withAspectRatio(1, 1);
        crop.withMaxResultSize(512, 512);

        crop.start(this);


    }

    private void CreateFile() {
        outputImage = new File(getExternalCacheDir(), "head.jpg");//保存裁剪图片的File
        try {
            if (outputImage.exists()) {
                outputImage.delete();
            } else {
                outputImage.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleImageBeforeKitKat(Uri uri) {
//        Uri uri = data.getData();
        String path = getImagePath(uri, null);
        Log.e("TAG", "path" + path);
        displayImage(path);
    }

    private void displayImage (String imagePath) {
         Log.e("tag","display"+imagePath);
        if (imagePath != null) {
//            outputImage = new File(imagePath);   //保存相册的地址


//            File newFile = CompressHelper.getDefault(getApplicationContext()).compressToFile(outputImage);
            Bitmap testbitmap = BitmapFactory.decodeFile(imagePath);//解析为bitmap
            Log.e("tag","testbitmap"+testbitmap);
            Glide.with(this).load(imagePath).into(headView);
//            headView.setImageBitmap(testbitmap);
        } else {
            Toast.makeText(this, "failed to get image", Toast.LENGTH_SHORT).show();
        }

    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void handleImageOnKitKat(Uri uri) {
        String imagePath = null;
//        Uri uri = data.getData();
        boolean documentUri = DocumentsContract.isDocumentUri(this, uri);
        if (documentUri) {

            //如果是document类型的uri，则通过是document id处理
            String docId = DocumentsContract.getDocumentId(uri);
            if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
                String id = docId.split(":")[1];//解析出数字格式的id
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
            } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"),
                        Long.valueOf(docId));
                imagePath = getImagePath(contentUri, null);
            } else if ("content".equalsIgnoreCase(uri.getScheme())) {
                //如果是content类型的uri，则使用普通方式处理
                imagePath = getImagePath(uri, null);
            } else if ("file".equalsIgnoreCase(uri.getScheme())) {
                //如果是file类型的uri，直接获取图片路径即可
                imagePath = uri.getPath();
            }

            Log.e("TAG", "imagePath=" + imagePath);
            displayImage(imagePath);//根据图片路径显示图片
        }


    }


    private String getImagePath(Uri uri, String selection) {
        String path = null;
        Cursor cursor = getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToNext()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }

            cursor.close();
        }


        return path;


    }
    private GlideUrl buildGlideUrl(String url) {
        if (TextUtils.isEmpty(url)) {
            return null;
        } else {
             return new GlideUrl(url, new LazyHeaders.Builder().addHeader("Referer", "http://m.mayinews.com").build());
        }



    }



}
