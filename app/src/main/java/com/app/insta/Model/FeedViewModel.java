package com.app.insta.Model;

import android.widget.ImageView;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

public class FeedViewModel extends ViewModel {
    MutableLiveData<List<Post>> postListLiveData = new MutableLiveData<List<Post>>();
    public FeedViewModel(){
        Model.instance.getAllPosts(new Model.GetAllPostsListener() {
            @Override
            public void onComplete(List<Post> data) {
                postListLiveData.setValue(data);
            }
        });
    }

    public LiveData<List<Post>> getPostList() {
        return postListLiveData;
    }
}
