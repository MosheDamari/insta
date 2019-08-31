package com.app.insta.Model;

import android.os.AsyncTask;
import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
interface PostDao {
    @Query("select * from Post")
    List<Post> getAll();

    @Query("select * from Post")
    LiveData<List<Post>> getAllPosts();

    @Query("select * from Post where id = :id")
    Post get(String id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Post... posts);

    @Insert
    void insert(Post posts);

    @Delete
    void delete(Post Post);
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
    public static void AddPost(final Post post, final Model.AddPostListener listener) {
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
                Post Post = ModelSql.db.postDao().get(postID);
                return Post;
            }

            @Override
            protected void onPostExecute(Post post) {
                super.onPostExecute(post);
                listener.onComplete(post);

            }
        }.execute();

    }
}
