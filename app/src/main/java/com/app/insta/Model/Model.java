package com.app.insta.Model;

import android.graphics.Bitmap;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class Model {
    final public static Model instance = new Model();

    ModelSql modelSql;
    ModelFirebase modelFirebase;
    private Model() {
        modelSql = new ModelSql();
        modelFirebase = new ModelFirebase();

    }

    /**
     * Profile
     */
    public interface AddProfileListener{
        void onComplete(boolean success);
    }

    public void addProfile(Profile profile, AddProfileListener listener) {
        modelFirebase.addProfile(profile, listener);

    }

    public interface GetProfileListener{
        void onComplete(Profile profile);
    }

    public void getProfile(String id, GetProfileListener listener) {
        modelFirebase.getProfile(id, listener);

    }
    /**
     * Post
     */
    public interface GetAllPostsListener {
        void onComplete(List<Post> data);
    }
    public void getAllPosts(final GetAllPostsListener listener) {
        PostAsyncDao.getAllPosts(listener);
        modelFirebase.getAllPosts(listener);
    }
    public interface GetPostListener {
        void onComplete(Post post);
    }
    public void getPost(String postID, final GetPostListener listener) {
        PostAsyncDao.getPost(postID,listener);

    }
    public interface AddPostListener{
        void onComplete(boolean success);
    }
    public void addPost(Post post, AddPostListener listener) {

        PostAsyncDao.AddPost(post, listener);
        modelFirebase.addPost(post, listener);

    }

    public interface EditPostListener{
        void onComplete(boolean success);
    }
    public void editPost(Post post, EditPostListener listener){

    }

    public interface DeletePostListener{
        void onComplete(boolean success);
    }
    public void deletePost(Post post, DeletePostListener listener){

    }

    public interface SaveImageListener{
        void onComplete(String url);
    }
    public void saveImage(Bitmap imageBitmap, SaveImageListener listener) {
        modelFirebase.saveImage(imageBitmap, listener);
    }

    public interface GetImageListener {
        void onComplete(ImageView image);
    }
}
