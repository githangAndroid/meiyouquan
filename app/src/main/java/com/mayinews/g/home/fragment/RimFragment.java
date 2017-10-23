package com.mayinews.g.home.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mayinews.g.R;


/**
 * Created by Administrator on 2017/9/30 0030.
 */

public class RimFragment extends Fragment {

    private TextView title;
    private ImageView top_logo;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

         title = (TextView) getActivity().findViewById(R.id.title);
         top_logo = (ImageView) getActivity().findViewById(R.id.top_logo);
        if(title!=null){
            title.setVisibility(View.VISIBLE);
            title.setText("周边");
        }
        if(top_logo!=null){
            top_logo.setVisibility(View.GONE);
        }
        TextView textView = new TextView(getActivity());
        textView.setText("请尽请期待");
        textView.setGravity(Gravity.CENTER);
        return textView;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        if (!hidden) {
            if(title!=null){
                title.setVisibility(View.VISIBLE);
                title.setText("周边");
            }
            if(top_logo!=null){
                top_logo.setVisibility(View.GONE);
            }
        }

    }
}
