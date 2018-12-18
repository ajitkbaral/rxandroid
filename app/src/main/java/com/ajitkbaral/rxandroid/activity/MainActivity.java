package com.ajitkbaral.rxandroid.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.ajitkbaral.rxandroid.R;
import com.jakewharton.rxbinding.view.RxView;
import com.rengwuxian.materialedittext.MaterialEditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscription;
import rx.functions.Action1;
import rx.subscriptions.CompositeSubscription;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.btnPosts)
    Button btnPost;
    @BindView(R.id.btnEmitter)
    Button btnEmitter;
    @BindView(R.id.material_dialog)
    Button btnMaterialDialog;
    @BindView(R.id.material_edit_text)
    Button btnMaterialEditText;
    @BindView(R.id.animated_svg)
    Button btnSVGAnimation;
    @BindView(R.id.material_progress_bar)
    Button btnMaterialProgressBar;
    private CompositeSubscription compositeSubscription = new CompositeSubscription();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        configureViews();
    }

    private void configureViews() {
        ButterKnife.bind(this);
        btnPostClick();
        btnEmitterClick();
        btnMaterialDialogClick();
        btnMaterialEditTextClick();
        btnAnimatedSVGClick();
        btnMaterialProgressBarClick();

    }

    private void btnMaterialProgressBarClick() {
        Subscription btnMaterialProgressBarClick = RxView.clicks(btnMaterialProgressBar).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                Intent intent = new Intent(MainActivity.this, MaterialProgressBarActivity.class);
                startActivity(intent);
            }
        });
        compositeSubscription.add(btnMaterialProgressBarClick);
    }

    private void btnAnimatedSVGClick() {
        Subscription btnSVGAnimationClick = RxView.clicks(btnSVGAnimation).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                Intent intent = new Intent(MainActivity.this, SVGAnimationActivity.class);
                startActivity(intent);
            }
        });
        compositeSubscription.add(btnSVGAnimationClick);
    }

    private void btnMaterialEditTextClick() {
        Subscription btnMaterialEditTextClick = RxView.clicks(btnMaterialEditText).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                Intent intent = new Intent(MainActivity.this, MaterialEditTextActivity.class);
                startActivity(intent);
            }
        });
        compositeSubscription.add(btnMaterialEditTextClick);
    }

    private void btnMaterialDialogClick() {
        Subscription btnMaterialDialogClick = RxView.clicks(btnMaterialDialog).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                new MaterialDialog.Builder(MainActivity.this)
                        .title(R.string.dialog_title)
                        .content(R.string.dialog_content)
                        .positiveText(R.string.dialog_agree)
                        .negativeText(R.string.dialog_disagree)
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                Toast.makeText(MainActivity.this, "You clicked Yes", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .onNegative(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                Toast.makeText(MainActivity.this, "You clicked No", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .show();
            }
        });
        compositeSubscription.add(btnMaterialDialogClick);
    }

    private void btnEmitterClick() {
        Subscription btnEmitterClick = RxView.clicks(btnEmitter).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                Intent intent = new Intent(MainActivity.this, EmitterActivity.class);
                startActivity(intent);
            }
        });
        compositeSubscription.add(btnEmitterClick);
    }

    private void btnPostClick() {
        Subscription btnPostClick = RxView.clicks(btnPost).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                Intent intent = new Intent(MainActivity.this, PostsActivity.class);
                startActivity(intent);
            }
        });

        compositeSubscription.add(btnPostClick);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (compositeSubscription !=null && !compositeSubscription.isUnsubscribed()) {
            compositeSubscription.unsubscribe();
        }
    }
}
