package com.ajitkbaral.rxandroid.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.ajitkbaral.rxandroid.R;
import com.ajitkbaral.rxandroid.adapter.PostAdapter;
import com.ajitkbaral.rxandroid.entity.Post;
import com.ajitkbaral.rxandroid.rest.ApiClient;
import com.ajitkbaral.rxandroid.rest.ApiInterface;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class PostsActivity extends AppCompatActivity {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    private ApiInterface mApiInterface;
    private PostAdapter mPostAdapter;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        ButterKnife.bind(this);
        configureApiInterface();
        configureViews();
        createObservable();
    }

    private void createObservable() {
        /*mApiInterface.getPosts()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<Post>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onSuccess(List<Post> posts) {
                        displayPosts(posts);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
                    }
                });*/
        mApiInterface.getPosts()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Post>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(List<Post> posts) {
                        displayPosts(posts);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(PostsActivity.this, "onError", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete() {
                        Toast.makeText(PostsActivity.this, "onComplete", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void displayPosts(List<Post> posts) {
        mPostAdapter = new PostAdapter(this, posts);
        recyclerView.setAdapter(mPostAdapter);
        progressBar.setVisibility(View.GONE);
    }

    private void configureApiInterface() {
        mApiInterface = ApiClient.getApiClient().create(ApiInterface.class);
    }

    private void configureViews() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (compositeDisposable != null && !compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
            if (compositeDisposable.isDisposed())
                Toast.makeText(this, "Disposed", Toast.LENGTH_SHORT).show();
        }
    }
}
