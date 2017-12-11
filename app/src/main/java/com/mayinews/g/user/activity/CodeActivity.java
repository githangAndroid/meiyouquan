package com.mayinews.g.user.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.mayinews.g.R;

import butterknife.BindView;
import butterknife.ButterKnife;


public class CodeActivity extends AppCompatActivity {

    @BindView(R.id.editText)
    TextView editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code);
        ButterKnife.bind(this);
        String info = getIntent().getStringExtra("code");

        editText.setText(info);




    }
}
