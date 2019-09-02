package com.app.insta;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.insta.Model.FeedViewModel;
import com.app.insta.Model.Post;
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
    TextView authorName, description;
    Button edit;
    private static final String ARG_PARAM1 = "ID";
    private static final String ARG_PARAM2 = "aName";
    private static final String ARG_PARAM3 = "disc";
    private static final String ARG_PARAM4 = "image";


    // TODO: Rename and change types of parameters
    private String postID;


    private OnFragmentInteractionListener mListener;

    public fragment_post() {
        // Required empty public constructor
    }

    public static fragment_post newInstance(String postID) {
        fragment_post fragment = new fragment_post();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, postID);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            postID = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_post, container, false);
        authorImage = v.findViewById(R.id.post_author_avatar);
        postImage = v.findViewById(R.id.post_image);
        authorName = v.findViewById(R.id.post_author_name);
        description = v.findViewById(R.id.post_description);
        postID = getArguments().getString(ARG_PARAM1);
        authorName.setText(getArguments().getString(ARG_PARAM2));
        description.setText(getArguments().getString(ARG_PARAM3));
        Picasso.get().load(getArguments().getString(ARG_PARAM4)).into(postImage);
        edit = v.findViewById(R.id.post_edit_button);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        return v;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

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
