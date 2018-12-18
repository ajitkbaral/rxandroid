package com.ajitkbaral.rxandroid.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.CycleInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.Button;

import com.ajitkbaral.rxandroid.R;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.rengwuxian.materialedittext.validation.RegexpValidator;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MaterialEditTextActivity extends AppCompatActivity {
    @BindView(R.id.validateBt)
    Button validateBt;
    @BindView(R.id.validationEt)
    MaterialEditText validationEt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_material_edit_text);
        ButterKnife.bind(this);
        initValidationEt();
    }

    private void initValidationEt() {
        final MaterialEditText materialEditText = validationEt.addValidator(new RegexpValidator("Only Integer Valid!", "\\d+"));
        validateBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // validate
                if (!validationEt.validate()) {
                    materialEditText.startAnimation(shakeError());
                }
            }
        });
    }

    public TranslateAnimation shakeError() {
        TranslateAnimation shake = new TranslateAnimation(0, 10, 0, 0);
        shake.setDuration(300);
        shake.setInterpolator(new CycleInterpolator(7));
        return shake;
    }
}
