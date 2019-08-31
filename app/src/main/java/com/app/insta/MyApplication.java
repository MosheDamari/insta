package com.app.insta;

import android.content.Context;
import androidx.multidex.MultiDexApplication;

import com.app.insta.Model.Profile;
import com.google.firebase.auth.FirebaseAuth;

public class MyApplication extends MultiDexApplication {
    static Context context;
    static Profile currentProfile;
    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

    public static Context getContext(){
        return context;
    }

    public static Profile getCurrentProfile() {
        return currentProfile;
    }
}
