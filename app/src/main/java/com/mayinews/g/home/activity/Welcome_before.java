package com.mayinews.g.home.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.mayinews.g.MainActivity;
import com.mayinews.g.R;
import com.mayinews.g.utils.NetworkUtils;


public class Welcome_before extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_before);

        if (NetworkUtils.isNetworkAvailable(this)){
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }else{
            Toast.makeText(this, "请检查网络", Toast.LENGTH_SHORT).show();
        }
    }
}
