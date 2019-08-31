package com.app.insta.Model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Profile {
    @PrimaryKey
    @NonNull
    public String id;
    public String authorName;
    public String authorImage;
    public String authorEmail;

    @NonNull
    public String getId() {
        return id;
    }

    public String getAuthorEmail() {
        return authorEmail;
    }

    public void setAuthorEmail(String authorEmail) {
        this.authorEmail = authorEmail;
    }

    public String getAuthorName() {
        return authorName;
    }

    public String getAuthorImage() {
        return authorImage;
    }

    public Profile(){

    }
    public Profile(String id,String authorName,String authorImage, String authorEmail) {
        this.id = id;
        this.authorImage = authorImage;
        this.authorName = authorName;
        this.authorEmail = authorEmail;

    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public void setAuthorImage(String authorImage) {
        this.authorImage = authorImage;
    }
}
