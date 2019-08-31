package com.app.insta.Model;

import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfileViewMdoel extends ViewModel {

    private FirebaseAuth mAuth;

    public ProfileViewMdoel(){
        mAuth = FirebaseAuth.getInstance();
    }

    public FirebaseAuth getmAuth() {
        return mAuth;
    }

    public FirebaseUser getCurrentUser() {
        return mAuth.getCurrentUser();
    }

    public void addProfile(Profile profile, Model.AddProfileListener listener){
        Model.instance.addProfile(profile,listener);
    }

    public void getProfile(String id, Model.GetProfileListener listener){
        Model.instance.getProfile(id,listener);
    }
}
