package com.ajitkbaral.rxandroid.rest;

import com.ajitkbaral.rxandroid.entity.Post;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {
    @GET("posts")
    Observable<List<Post>> getPosts();
}
