package com.app.insta.Model;

import android.os.AsyncTask;
import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
interface PostDao {
    @Query("select * from Post")
    List<Post> getAll();

    @Query("select * from Post")
    LiveData<List<Post>> getAllPosts();

    @Query("select * from Post where id = :id")
    Post getPost(String id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Post> posts);

    @Insert
    void insert(Post posts);

    @Delete
    void delete(Post post);

    @Update
    void updatePost(Post post);
}

public class PostAsyncDao{

    public static void getAllPosts(final Model.GetAllPostsListener listener) {
        new AsyncTask<String,String,List<Post>>(){

            @Override
            protected List<Post> doInBackground(String... strings) {
                List<Post> list = ModelSql.db.postDao().getAll();
                return list;
            }

            @Override
            protected void onPostExecute(List<Post> data) {
                super.onPostExecute(data);
                listener.onComplete(data);

            }
        }.execute();

    }
    public static void addPost(final Post post, final Model.AddPostListener listener) {
        new AsyncTask<Post, Void, Boolean>(){
            @Override
            protected Boolean doInBackground(Post... posts) {
                ModelSql.db.postDao().insert(post);
                return true;
            }
            @Override
            protected void onPostExecute(Boolean task) {
                super.onPostExecute(task);
                listener.onComplete(task);

            }
        }.execute();

    }
    public static void getPost(final String postID,final Model.GetPostListener listener) {
        new AsyncTask<String,String,Post>(){

            @Override
            protected Post doInBackground(String... strings) {
                Post post = ModelSql.db.postDao().getPost(postID);
                return post;
            }

            @Override
            protected void onPostExecute(Post post) {
                super.onPostExecute(post);
                listener.onComplete(post);

            }
        }.execute();

    }
    public static void editPost(final String postID,final String desc, final Model.EditPostListener listener) {
        new AsyncTask<String,String,Boolean>(){
            @Override
            protected Boolean doInBackground(String... strings) {
                Post post = ModelSql.db.postDao().getPost(postID);
                post.setDescription(desc);
                ModelSql.db.postDao().updatePost(post);
                return true;
            }

            @Override
            protected void onPostExecute(Boolean success) {
                super.onPostExecute(success);
                listener.onComplete(success);
            }
        }.execute();
    }
    public static void addAllPost(final List<Post> postList, final Model.AddAllPostListener listener) {
        new AsyncTask<Post,String,Boolean>(){
            @Override
            protected Boolean doInBackground(Post... posts) {
                ModelSql.db.postDao().insertAll(postList);
                return true;
            }

            @Override
            protected void onPostExecute(Boolean success) {
                super.onPostExecute(success);
                listener.onComplete(success);
            }
        }.execute();
    }
    public static void deletePost(final String postID, final Model.DeletePostListener listener) {
        new AsyncTask<String,String,Boolean>(){
            @Override
            protected Boolean doInBackground(String... strings) {
                ModelSql.db.postDao().delete(ModelSql.db.postDao().getPost(postID));
                return true;
            }

            @Override
            protected void onPostExecute(Boolean success) {
                super.onPostExecute(success);
                listener.onComplete(success);
            }
        }.execute();
    }
}
