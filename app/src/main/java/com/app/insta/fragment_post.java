package com.app.insta;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.insta.Model.FeedViewModel;
import com.app.insta.Model.Model;
import com.squareup.picasso.Picasso;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link fragment_post.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link fragment_post#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragment_post extends Fragment {
    ImageView authorImage, postImage;
    TextView authorName, description, descriptionEdit;
    Button edit, delete;
    String authorID;
    FeedViewModel model;
    NavController myNav;
    private static final String ID = "ID";
    private static final String A_NAME = "aName";
    private static final String DISC = "disc";
    private static final String IMAGE = "image";
    private static final String AUTHOR_ID = "authorID";



    // TODO: Rename and change types of parameters
    private String postID;


    private OnFragmentInteractionListener mListener;

    public fragment_post() {
        // Required empty public constructor
    }

    public static fragment_post newInstance(String postID) {
        fragment_post fragment = new fragment_post();
        Bundle args = new Bundle();
        args.putString(ID, postID);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            postID = getArguments().getString(ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        model = ViewModelProviders.of(this).get(FeedViewModel.class);
        View v = inflater.inflate(R.layout.fragment_post, container, false);
        authorImage = v.findViewById(R.id.post_author_avatar);
        postImage = v.findViewById(R.id.post_image);
        authorName = v.findViewById(R.id.post_author_name);
        description = v.findViewById(R.id.post_description);
        descriptionEdit = v.findViewById(R.id.post_edit_description);
        descriptionEdit.setVisibility(View.GONE);
        delete = v.findViewById(R.id.post_delete_button);
        delete.setVisibility(View.GONE);
        postID = getArguments().getString(ID);
        authorName.setText(getArguments().getString(A_NAME));
        description.setText(getArguments().getString(DISC));
        Picasso.get().load(getArguments().getString(IMAGE)).into(postImage);
        authorID = getArguments().getString(AUTHOR_ID);
        edit = v.findViewById(R.id.post_edit_button);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(edit.getText().toString().equalsIgnoreCase("edit")){
                    description.setVisibility(View.GONE);
                    descriptionEdit.setText(description.getText());
                    descriptionEdit.setVisibility(View.VISIBLE);
                    delete.setVisibility(View.VISIBLE);
                    delete.setText(R.string.delete);
                    delete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            model.deletePost(postID, new Model.DeletePostListener() {
                                @Override
                                public void onComplete(boolean success) {
                                    if(success){
                                        Toast.makeText(MyApplication.getContext(), "Post Deleted", Toast.LENGTH_SHORT).show();
                                        postDelete();
                                    }else {
                                        Toast.makeText(MyApplication.getContext(), "Post Failed to Deleted", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                    });
                    edit.setText(R.string.done);
                }else{
                    if(description.getText() != descriptionEdit.getText()){
                        model.editPost(postID, descriptionEdit.getText().toString(), new Model.EditPostListener() {
                            @Override
                            public void onComplete(boolean success) {
                                if(success){
                                    Toast.makeText(MyApplication.getContext(), "Post Update", Toast.LENGTH_SHORT).show();
                                    descriptionEdit.setVisibility(View.GONE);
                                    description.setText(descriptionEdit.getText());
                                    description.setVisibility(View.VISIBLE);
                                    edit.setText(R.string.edit);
                                }else {
                                    Toast.makeText(MyApplication.getContext(), "Failed To Update", Toast.LENGTH_SHORT).show();
                                    descriptionEdit.setVisibility(View.GONE);
                                    description.setVisibility(View.VISIBLE);
                                    edit.setText(R.string.edit);
                                }
                            }

                        });
                    }
                }
            }
        });
        if(!authorID.equalsIgnoreCase(MyApplication.getCurrentProfile().getId())){
            edit.setVisibility(View.GONE);
        }
        return v;
    }

    public void postDelete(){
        NavHostFragment.findNavController(this).navigate(R.id.feed);
    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
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
