package com.minoon.disco.sample;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class CollapsingHeaderSampleActivity extends AppCompatActivity {

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, CollapsingHeaderSampleActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_collapsing_header_sample);
    }
}
