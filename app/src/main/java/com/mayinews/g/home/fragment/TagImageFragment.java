package com.mayinews.g.home.fragment;

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
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.mayinews.g.R;
import com.mayinews.g.home.activity.TagDetailActivity;
import com.mayinews.g.home.bean.ModelTagBean;
import com.mayinews.g.home.bean.TagDatailBean;

import java.util.List;

import jp.wasabeef.glide.transformations.BlurTransformation;

import static com.mayinews.g.R.id.imageView2;
import static com.mayinews.g.R.id.imageView3;

/**
 * Created by Administrator on 2017/8/30 0030.
 */

public class TagImageFragment extends Fragment {


    ModelTagBean.ResultBean dataBean;


    public TagImageFragment() {
    }

    public TagImageFragment( ModelTagBean.ResultBean dataBean) {

        this.dataBean = dataBean;

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=null;
        if(dataBean!=null){
            List<String> images = dataBean.getImages();
            Log.e("TAG","images="+images);
            if(images!=null && images.size()>0){






              switch (images.size()) {

                  case  1:
                      view= inflater.inflate(R.layout.one_image_fragment, container, false);

                      setData01(view,dataBean);

                      break;
                  case  2:
                      view= inflater.inflate(R.layout.two_image_fragment, container, false);

                      setData02(view,dataBean);

                      break;
                  case  3:
                      view= inflater.inflate(R.layout.three_image_fragment, container, false);

                      setData1(view,dataBean);
                      break;
                  case  4:
                      view= inflater.inflate(R.layout.four_image_fragment, container, false);
                      setData2(view,dataBean);
                      break;
                  case  5:
                      view= inflater.inflate(R.layout.five_image_fragment, container, false);
                      setData4(view,dataBean);
                      break;
                  case  6:
                      view= inflater.inflate(R.layout.six_image_fragment, container, false);
                      setData3(view,dataBean);
                      break;
              }


            }

        }
            if(view!=null){
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        TextView tv_title = (TextView) v.findViewById(R.id.tv_title);
                        String title = tv_title.getText().toString();

                        if("VIP专区".equals(title)){
                            Toast.makeText(getActivity(), "VIP专区", Toast.LENGTH_SHORT).show();
                        } else if("最新".equals(title)){
                            Toast.makeText(getActivity(), "最新", Toast.LENGTH_SHORT).show();
                        }else if("热门模特".equals(title)){
                            Toast.makeText(getActivity(), "热门模特", Toast.LENGTH_SHORT).show();
                        }else{
                             Intent intent = new Intent(getActivity(), TagDetailActivity.class);
                             intent.putExtra("title",title);
                             startActivity(intent);

                        }
                    }
                });
            }

           return view;
    }

    private void setData02(View view, ModelTagBean.ResultBean tagDatailBeen) {
        TextView title = (TextView) view.findViewById(R.id.tv_title);
        ImageView imageView1 = (ImageView) view.findViewById(R.id.image1);
        ImageView imageView2 = (ImageView) view.findViewById(R.id.image2);
        title.setText(dataBean.getTitle());
        Glide.with(getActivity()).load(buildGlideUrl("http://static.mayinews.com"+tagDatailBeen.getImages().get(0))).
                into(imageView1);
        Glide.with(getActivity()).load(buildGlideUrl("http://static.mayinews.com"+tagDatailBeen.getImages().get(1))).
                into(imageView2);
    }

    private void setData01(View view, ModelTagBean.ResultBean tagDatailBeen) {

        TextView title = (TextView) view.findViewById(R.id.tv_title);
        ImageView imageView1 = (ImageView) view.findViewById(R.id.image1);
        title.setText(dataBean.getTitle());
        Glide.with(getActivity()).load(buildGlideUrl("http://static.mayinews.com"+tagDatailBeen.getImages().get(0))).
                into(imageView1);

    }

    private void setData4(View view,  ModelTagBean.ResultBean tagDatailBeen) {

        TextView title = (TextView) view.findViewById(R.id.tv_title);
        ImageView imageView1 = (ImageView) view.findViewById(R.id.image1);
        ImageView imageView2 = (ImageView) view.findViewById(R.id.image2);
        ImageView imageView3 = (ImageView) view.findViewById(R.id.image3);
        ImageView imageView4 = (ImageView) view.findViewById(R.id.image4);
        ImageView imageView5 = (ImageView) view.findViewById(R.id.image5);
        title.setText(dataBean.getTitle());
        Glide.with(getActivity()).load(buildGlideUrl("http://static.mayinews.com"+tagDatailBeen.getImages().get(0))).
                into(imageView1);
        Glide.with(getActivity()).load(buildGlideUrl("http://static.mayinews.com"+tagDatailBeen.getImages().get(1))).into(imageView2);
        Glide.with(getActivity()).load(buildGlideUrl("http://static.mayinews.com"+tagDatailBeen.getImages().get(2))).into(imageView3);
        Glide.with(getActivity()).load(buildGlideUrl("http://static.mayinews.com"+tagDatailBeen.getImages().get(3))).into(imageView4);
        Glide.with(getActivity()).load(buildGlideUrl("http://static.mayinews.com"+tagDatailBeen.getImages().get(4))).into(imageView5);


    }

    private void setData3(View view,  ModelTagBean.ResultBean tagDatailBeen) {

        TextView title = (TextView) view.findViewById(R.id.tv_title);
        ImageView imageView1 = (ImageView) view.findViewById(R.id.image1);
        ImageView imageView2 = (ImageView) view.findViewById(R.id.image2);
        ImageView imageView3 = (ImageView) view.findViewById(R.id.image3);
        ImageView imageView4 = (ImageView) view.findViewById(R.id.image4);
        ImageView imageView5 = (ImageView) view.findViewById(R.id.image5);
        ImageView imageView6 = (ImageView) view.findViewById(R.id.image6);
        title.setText(dataBean.getTitle());
//        if("VIP专区".equals(dataBean.getTitle())){
//            //高斯出路 .bitmapTransform(new BlurTransformation(context,23,4))
//
//            Glide.with(getActivity()).load(buildGlideUrl("http://static.mayinews.com")+tagDatailBeen.getImages().get(0)).
//                     bitmapTransform(new BlurTransformation(getActivity(),25))
//                    .into(imageView1);
//            Glide.with(getActivity()).load(buildGlideUrl("http://static.mayinews.com")+tagDatailBeen.getImages().get(1)).
//                     bitmapTransform(new BlurTransformation(getActivity(),25))
//                    .into(imageView2);
//            Glide.with(getActivity()).load(buildGlideUrl("http://static.mayinews.com")+tagDatailBeen.getImages().get(2)).
//                     bitmapTransform(new BlurTransformation(getActivity(),25))
//                    .into(imageView3);
//            Glide.with(getActivity()).load(buildGlideUrl("http://static.mayinews.com")+tagDatailBeen.getImages().get(3)).
//                     bitmapTransform(new BlurTransformation(getActivity(),25))
//                    .into(imageView4);
//            Glide.with(getActivity()).load(buildGlideUrl("http://static.mayinews.com")+tagDatailBeen.getImages().get(4)).
//                     bitmapTransform(new BlurTransformation(getActivity(),25))
//                    .into(imageView5);
//            Glide.with(getActivity()).load(buildGlideUrl("http://static.mayinews.com")+tagDatailBeen.getImages().get(5)).
//                     bitmapTransform(new BlurTransformation(getActivity(),25))
//                    .into(imageView6);



            Glide.with(getActivity()).load(buildGlideUrl("http://static.mayinews.com"+tagDatailBeen.getImages().get(0))).
                    into(imageView1);
            Glide.with(getActivity()).load(buildGlideUrl("http://static.mayinews.com"+tagDatailBeen.getImages().get(1))).into(imageView2);
            Glide.with(getActivity()).load(buildGlideUrl("http://static.mayinews.com"+tagDatailBeen.getImages().get(2))).into(imageView3);
            Glide.with(getActivity()).load(buildGlideUrl("http://static.mayinews.com"+tagDatailBeen.getImages().get(3))).into(imageView4);
            Glide.with(getActivity()).load(buildGlideUrl("http://static.mayinews.com"+tagDatailBeen.getImages().get(4))).into(imageView5);
            Glide.with(getActivity()).load(buildGlideUrl("http://static.mayinews.com"+tagDatailBeen.getImages().get(5))).into(imageView6);




    }

    private void setData2(View view,  ModelTagBean.ResultBean tagDatailBeen) {
        TextView title = (TextView) view.findViewById(R.id.tv_title);
        ImageView imageView1 = (ImageView) view.findViewById(R.id.image1);
        ImageView imageView2 = (ImageView) view.findViewById(R.id.image2);
        ImageView imageView3 = (ImageView) view.findViewById(R.id.image3);
        ImageView imageView4 = (ImageView) view.findViewById(R.id.image4);
        title.setText(dataBean.getTitle());

            Glide.with(getActivity()).load(buildGlideUrl("http://static.mayinews.com"+tagDatailBeen.getImages().get(0))).
                    into(imageView1);
            Glide.with(getActivity()).load(buildGlideUrl("http://static.mayinews.com"+tagDatailBeen.getImages().get(1))).into(imageView2);
            Glide.with(getActivity()).load(buildGlideUrl("http://static.mayinews.com"+tagDatailBeen.getImages().get(2))).into(imageView3);
            Glide.with(getActivity()).load(buildGlideUrl("http://static.mayinews.com"+tagDatailBeen.getImages().get(3))).into(imageView4);







    }

    private void setData1(View view, ModelTagBean.ResultBean tagDatailBeen) {
        TextView title = (TextView) view.findViewById(R.id.tv_title);
        ImageView imageView1 = (ImageView) view.findViewById(R.id.image1);
        ImageView imageView2 = (ImageView) view.findViewById(R.id.image2);
        ImageView imageView3 = (ImageView) view.findViewById(R.id.image3);
        title.setText(dataBean.getTitle());

            Glide.with(getActivity()).load(buildGlideUrl("http://static.mayinews.com"+tagDatailBeen.getImages().get(0))).
                    into(imageView1);
            Glide.with(getActivity()).load(buildGlideUrl("http://static.mayinews.com"+tagDatailBeen.getImages().get(1))).into(imageView2);
            Glide.with(getActivity()).load(buildGlideUrl("http://static.mayinews.com"+tagDatailBeen.getImages().get(2))).into(imageView3);



    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
      }



    private GlideUrl buildGlideUrl(String url) {
        if (TextUtils.isEmpty(url)) {
            return null;
        } else {
            return new GlideUrl(url, new LazyHeaders.Builder().addHeader("Referer", "http://m.mayinews.com").build());
        }
    }
}
