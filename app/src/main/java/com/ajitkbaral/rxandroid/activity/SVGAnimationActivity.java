package com.ajitkbaral.rxandroid.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.ajitkbaral.rxandroid.R;
import com.jaredrummler.android.widget.AnimatedSvgView;

public class SVGAnimationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_svg_animation);
        AnimatedSvgView svgView = findViewById(R.id.animated_svg_view);
        svgView.start();
    }
}
