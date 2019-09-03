package com.app.insta;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;
import com.app.insta.Model.Post;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class FeedListAdapter  extends RecyclerView.Adapter<FeedListAdapter.FeedRowViewHolder> {
    List<Post> postList;
    OnItemClickListener mListener;
    FeedRowViewHolder viewHolder;
    public FeedListAdapter(List<Post> postListLiveData) {
        if(postListLiveData == null){
            this.postList = new ArrayList<>();
        }else {
            this.postList = postListLiveData;
        }
    }
    interface OnItemClickListener{
        void onClick(int index);
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mListener = onItemClickListener;
    }

    @NonNull
    @Override
    public FeedRowViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.post_row, viewGroup,false);
        viewHolder = new FeedRowViewHolder(view,mListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull FeedRowViewHolder holder, int position) {
        Post post = postList.get(position);
        holder.bind(post);
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }
    public void setPostList(List<Post> postList) {
        this.postList = postList;
    }


    static class FeedRowViewHolder extends RecyclerView.ViewHolder {
        TextView mPostAuthor;
        ImageView mAuthorImage;
        ImageView mPostImage;
        TextView mPostText;
        String postID, postImage, authorID;


        public FeedRowViewHolder(@NonNull final View itemView, final OnItemClickListener listener) {
            super(itemView);
            mAuthorImage = itemView.findViewById(R.id.authorImage);
            mPostAuthor = itemView.findViewById(R.id.postAuthor);
            mPostImage = itemView.findViewById(R.id.postImage);
            mPostText = itemView.findViewById(R.id.postText);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle postInfo = new Bundle();
                    postInfo.putString("ID",postID);
                    postInfo.putString("aName",mPostAuthor.getText().toString());
                    postInfo.putString("disc",mPostText.getText().toString());
                    postInfo.putString("image",postImage);
                    postInfo.putString("authorID",authorID);
                    Navigation.findNavController(itemView).navigate(R.id.post, postInfo);

                }
            });

        }

        public void bind(Post post){
            mPostAuthor.setText(post.getAuthor());
            mPostText.setText(post.getDescription());
            postImage = post.getPostImage();
            Picasso.get().load(postImage).into(mPostImage);
            postID = post.getId();
            authorID = post.getAuthorID();
            if(post.getAuthorAvatar() != null && post.getAuthorAvatar() != ""){
                Picasso.get().load(post.getAuthorAvatar()).into(mAuthorImage);
            }

        }
    }
}
