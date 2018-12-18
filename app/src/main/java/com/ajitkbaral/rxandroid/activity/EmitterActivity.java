package com.ajitkbaral.rxandroid.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.ajitkbaral.rxandroid.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.PublishSubject;

public class EmitterActivity extends AppCompatActivity implements View.OnClickListener{

    int mCounter = 0;
    @BindView(R.id.textCounter)
    TextView textCounter;
    @BindView(R.id.buttonCounterIncrement)
    Button btnCounterIncrement;
    @BindView(R.id.buttonCounterDecrement)
    Button btnCounterDecrement;
    private PublishSubject<Integer> mCounterEmitter;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emitter);
        ButterKnife.bind(this);
        configureViews();
        counterObservable();
    }

    private void configureViews() {
        textCounter.setText(String.valueOf(mCounter));
        btnCounterIncrement.setOnClickListener(this);
        btnCounterDecrement.setOnClickListener(this);
    }

    private void counterObservable() {
        mCounterEmitter = PublishSubject.create();
        mCounterEmitter.subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                compositeDisposable.add(d);
            }

            @Override
            public void onNext(Integer integer) {
                textCounter.setText(String.valueOf(integer));
            }

            @Override
            public void onError(Throwable e) {
                Toast.makeText(EmitterActivity.this, "onError", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onComplete() {
                Toast.makeText(EmitterActivity.this, "onComplete", Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (compositeDisposable !=null && !compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
            if (compositeDisposable.isDisposed())
                Toast.makeText(this, "Disposed", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.buttonCounterIncrement && mCounter >= 0)
            mCounter++;
        else if (v.getId() == R.id.buttonCounterDecrement && mCounter > 0)
            mCounter--;
        mCounterEmitter.onNext(mCounter);
    }
}
