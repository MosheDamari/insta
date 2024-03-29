package com.app.insta;

import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.app.insta.Model.FeedViewModel;
import com.app.insta.Model.Post;

import java.util.List;



/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link fragment_feed.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link fragment_feed#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragment_feed extends Fragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    FeedViewModel viewModel;

    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLyoutManager;
    FeedListAdapter mAdapter;
    LiveData<List<Post>> postListLiveData;
    public fragment_feed() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment fragment_feed.
     */

    public static fragment_feed newInstance(String param1, String param2) {
        fragment_feed fragment = new fragment_feed();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }


    void updateDisplay(){
        List<Post> newPostList = postListLiveData.getValue();
        this.mAdapter.setPostList(newPostList);
        this.mAdapter.notifyDataSetChanged();
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        viewModel = ViewModelProviders.of(this).get(FeedViewModel.class);
        postListLiveData = viewModel.getPostList();
        postListLiveData.observe(this, new Observer<List<Post>>() {
            @Override
            public void onChanged(List<Post> posts) {
                updateDisplay();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_feed, container, false);
        mRecyclerView = v.findViewById(R.id.feed_recyclerview);
        mRecyclerView.setHasFixedSize(true);

        mLyoutManager = new LinearLayoutManager(this.getContext());
        mRecyclerView.setLayoutManager(mLyoutManager);
        postListLiveData = viewModel.getPostList();
        mAdapter = new FeedListAdapter(postListLiveData.getValue());
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new FeedListAdapter.OnItemClickListener() {
            @Override
            public void onClick(int index) {

            }
        });
        return v;
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
