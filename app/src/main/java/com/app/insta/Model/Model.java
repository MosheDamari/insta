package com.app.insta.Model;

import android.graphics.Bitmap;
import android.widget.ImageView;


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

    public void addProfile(final Profile profile, AddProfileListener listener) {
        modelFirebase.addProfile(profile, listener);
        PostAsyncDao.getAllPosts(new GetAllPostsListener() {
            @Override
            public void onComplete(List<Post> data) {
                for(Post post : data){
                    if(post.getAuthorID().equalsIgnoreCase(profile.getId())){
                        post.setAuthorAvatar(profile.getAuthorImage());
                        post.setAuthor(profile.getAuthorName());
                    }
                }
                PostAsyncDao.addAllPost(data, new AddAllPostListener() {
                    @Override
                    public void onComplete(boolean success) {
                    }
                });
                modelFirebase.addAllPosts(data, new AddAllPostListener() {
                    @Override
                    public void onComplete(boolean success) {
                    }
                });

            }
        });
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
        modelFirebase.getAllPosts(new GetAllPostsListener() {
            @Override
            public void onComplete(List<Post> data) {
                addAllPost(data, new AddAllPostListener() {
                    @Override
                    public void onComplete(boolean success) {

                    }
                });
            }
        });
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
        PostAsyncDao.addPost(post, listener);
        modelFirebase.addPost(post, listener);
    }
    public interface AddAllPostListener{
        void onComplete(boolean success);
    }
    public void addAllPost(List<Post> posts, AddAllPostListener listener) {
        PostAsyncDao.addAllPost(posts, listener);
    }
    public interface EditPostListener{
        void onComplete(boolean success);
    }
    public void editPost(String postID, String desc ,EditPostListener listener){
        PostAsyncDao.editPost(postID, desc, listener);
    }

    public interface DeletePostListener{
        void onComplete(boolean success);
    }
    public void deletePost(String post, DeletePostListener listener){
        PostAsyncDao.deletePost(post, listener);
        modelFirebase.deletePost(post,listener);
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
