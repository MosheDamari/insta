package com.app.insta.Model;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Post {
    @PrimaryKey
    @NonNull
    public String id;
    public String author;
    public String authorAvatar;
    public String description;
    public String postImage;


    public void setId(@NonNull String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.description = name;
    }

    public void setPostImage(String image) {
        this.postImage = image;
    }


    public void setAuthor(String author) {
        this.author = author;
    }

    public void setAuthorAvatar(String authorAvatar) {
        this.authorAvatar = authorAvatar;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public Post(){

    }
    public Post(String id, String description, String image, String author, String authorAvatar) {
        this.id = id;
        this.description = description;
        this.postImage = image;
        this.author = author;
        this.authorAvatar = authorAvatar;

    }

    public String getId() {
        return id;
    }

    public String getName() { return description;}

    public String getPostImage() {
        return postImage;
    }


    public String getAuthorAvatar() { return authorAvatar;}

    public String getAuthor() {
        return author;
    }


    public String getDescription() {
        return description;
    }



}
