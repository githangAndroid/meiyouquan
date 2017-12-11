package com.mayinews.g.utils;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.mayinews.g.R;

/**
 * Created by Administrator on 2017/11/1 0001.
 */

public class ShowPayDialog {

    public static void show(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.pay_dialog, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(view);

        AlertDialog alertDialog = builder.show();
        Window window = alertDialog.getWindow();
        window.setBackgroundDrawableResource(android.R.color.transparent);

       LinearLayout payAli = (LinearLayout) view.findViewById(R.id.pay_ali);
       LinearLayout payWei = (LinearLayout) view.findViewById(R.id.pay_weichat);
        payAli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "支付吧支付", Toast.LENGTH_SHORT).show();
            }
        });
        payWei.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "微信支付", Toast.LENGTH_SHORT).show();
            }
        });


    }





}
