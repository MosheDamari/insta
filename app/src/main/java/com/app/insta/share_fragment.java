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


import java.util.Date;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link share_fragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link share_fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class share_fragment extends Fragment {

    ImageView shareImageView;
    Button galleryButton, cameraButton, shareButton;
    TextView disc;
    Profile currentProfile;

    private static final int IMAGE_PICK_CODE = 1000;
    private static final int PERMISSION_CODE = 1001;
    private static final int CAMERA_REQUEST = 1888;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public share_fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment share_fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static share_fragment newInstance(String param1, String param2) {
        share_fragment fragment = new share_fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_share, container, false);
        currentProfile =MyApplication.getCurrentProfile();
        shareImageView = v.findViewById(R.id.image_view);
        galleryButton = v.findViewById(R.id.share_gallery_button);
        disc = v.findViewById(R.id.share_description_text);
        galleryButton.setOnClickListener(new View.OnClickListener() {
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
        cameraButton = v.findViewById(R.id.share_camera_button);
        cameraButton.setOnClickListener(new View.OnClickListener() {
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
        shareButton = v.findViewById(R.id.share_toshare_button);
        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sharePost();
            }
        });
        return v;
    }

    public void sharePost(){
        if(shareImageView != null && disc.getText() != null){
            boolean upload = false;
            Bitmap bitmap = ((BitmapDrawable) shareImageView.getDrawable()).getBitmap();
            Model.instance.saveImage(bitmap, new Model.SaveImageListener() {
                @Override
                public void onComplete(String url) {
                    Date d = new Date();
                    Post myPost = new Post(MyApplication.getCurrentProfile().getId()+ d.getTime(), disc.getText().toString(), url,currentProfile.getAuthorName(),currentProfile.getAuthorImage());
                    Model.instance.addPost(myPost, new Model.AddPostListener() {
                        @Override
                        public void onComplete(boolean success) {
                            onShareSuccess(success);
                        }

                    });
                }
            });

        }
    }

    public void onShareSuccess(boolean share){
        if(share){
            Toast.makeText(this.getContext(), "Uploaded", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this.getContext(), "Failed Uploaded", Toast.LENGTH_SHORT).show();
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
            shareImageView.setImageURI(data.getData());
        }else if(requestCode == CAMERA_REQUEST && resultCode == RESULT_OK){
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            shareImageView.setImageBitmap(photo);

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
            Toast.makeText(this.getContext(), "Permission Denied!", Toast.LENGTH_SHORT);
        }
     }


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
