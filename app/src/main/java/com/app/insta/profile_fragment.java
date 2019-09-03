package com.app.insta;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.insta.Model.Model;
import com.app.insta.Model.Post;
import com.app.insta.Model.Profile;
import com.squareup.picasso.Picasso;

import java.util.Date;

import static android.app.Activity.RESULT_OK;


public class profile_fragment extends Fragment {
    private Button edit, camera, gallery;
    private TextView name, disc, email, discEdit, nameEdit, emailDisc;
    private ImageView profilePic;
    private Profile profile;
    private static final int IMAGE_PICK_CODE = 1000;
    private static final int PERMISSION_CODE = 1001;
    private static final int CAMERA_REQUEST = 1888;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;

    private OnFragmentInteractionListener mListener;

    public profile_fragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_profile, container, false);
        profile = MyApplication.getCurrentProfile();
        name = v.findViewById(R.id.profile_author_name);
        name.setText(profile.getAuthorName());
        disc = v.findViewById(R.id.profile_author_disc);
        disc.setText(profile.getAuthorDesc());
        email = v.findViewById(R.id.profile_author_email);
        email.setText(profile.getAuthorEmail());
        profilePic = v.findViewById(R.id.profile_avatar);
        if(profile.getAuthorImage() != null && profile.getAuthorImage() != ""){
            Picasso.get().load(profile.getAuthorImage()).into(profilePic);
        }
        discEdit = v.findViewById(R.id.profile_edit_desc);
        discEdit.setVisibility(View.GONE);
        gallery = v.findViewById(R.id.profile_gallery_button);
        gallery.setVisibility(View.GONE);
        camera = v.findViewById(R.id.profile_camera_button);
        camera.setVisibility(View.GONE);
        nameEdit = v.findViewById(R.id.profile_edit_name);
        nameEdit.setVisibility(View.GONE);
        emailDisc = v.findViewById(R.id.profile_edit_email);
        emailDisc.setVisibility(View.GONE);

        edit = v.findViewById(R.id.profile_edit_button);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(edit.getText().toString().equalsIgnoreCase("edit")){
                    edit.setText(R.string.done);
                    disc.setVisibility(View.GONE);
                    name.setVisibility(View.GONE);
                    email.setVisibility(View.GONE);
                    discEdit.setText(disc.getText());
                    discEdit.setVisibility(View.VISIBLE);
                    nameEdit.setText(name.getText());
                    nameEdit.setVisibility(View.VISIBLE);
                    emailDisc.setVisibility(View.VISIBLE);
                    camera.setVisibility(View.VISIBLE);
                    camera.setText(R.string.camera);
                    camera.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                if (getActivity().checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) ==
                                        PackageManager.PERMISSION_DENIED) {
                                    String[] permissions = {Manifest.permission.CAMERA};
                                    requestPermissions(permissions, MY_CAMERA_PERMISSION_CODE);
                                } else {
                                    getImageFromCamera();
                                }
                            }
                            else{
                                getImageFromCamera();
                            }
                        }
                    });
                    gallery.setVisibility(View.VISIBLE);
                    gallery.setText(R.string.gallery);
                    gallery.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                if (getActivity().checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) ==
                                        PackageManager.PERMISSION_DENIED) {
                                    String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
                                    requestPermissions(permissions, PERMISSION_CODE);
                                } else {
                                    pickImageFromGallery();
                                }
                            }
                            else{
                                pickImageFromGallery();
                            }
                        }
                    });
                }else {
                    edit.setText(R.string.edit);
                    nameEdit.setVisibility(View.GONE);
                    name.setText(nameEdit.getText());
                    name.setVisibility(View.VISIBLE);
                    discEdit.setVisibility(View.GONE);
                    disc.setVisibility(View.VISIBLE);
                    disc.setText(discEdit.getText());
                    email.setVisibility(View.VISIBLE);
                    emailDisc.setVisibility(View.GONE);
                    email.setText(emailDisc.getText());
                    profile.setAuthorDesc(disc.getText().toString());
                    profile.setAuthorName(name.getText().toString());
                    profile.setAuthorEmail(email.getText().toString());
                    profile.setAuthorImage(disc.getText().toString());
                    saveProfile();
                }
            }
        });
        return v;
    }
    public void saveProfile(){
        if(profilePic != null && disc.getText() != null){
            Bitmap bitmap = ((BitmapDrawable) profilePic.getDrawable()).getBitmap();
            Model.instance.saveImage(bitmap, new Model.SaveImageListener() {
                @Override
                public void onComplete(String url) {
                    profile.setAuthorImage(url);
                    Model.instance.addProfile(profile, new Model.AddProfileListener() {
                        @Override
                        public void onComplete(boolean success) {
                            saveStatus(success);
                        }
                    });
                }
            });

        }
    }
    public void saveStatus(boolean success){
        if(success) {
            Toast.makeText(this.getContext(), "Updated", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this.getContext(), "Failed To Updated", Toast.LENGTH_SHORT).show();
        }
    }

    private void getImageFromCamera(){
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAMERA_REQUEST);
    }

    private void pickImageFromGallery(){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent,IMAGE_PICK_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(resultCode == RESULT_OK && requestCode == IMAGE_PICK_CODE){
            profilePic.setImageURI(data.getData());
        }else if(requestCode == CAMERA_REQUEST && resultCode == RESULT_OK){
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            profilePic.setImageBitmap(photo);

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            switch (requestCode) {
                case PERMISSION_CODE: {
                    pickImageFromGallery();
                }
                case MY_CAMERA_PERMISSION_CODE: {
                    Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, CAMERA_REQUEST);
                }
            }
        }
        else {
            Toast.makeText(this.getContext(), "Permission Denied!", Toast.LENGTH_SHORT).show();
        }
    }
    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
