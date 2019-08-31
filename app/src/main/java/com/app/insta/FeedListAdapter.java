package com.app.insta;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.app.insta.Model.Post;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

class FeedListAdapter  extends RecyclerView.Adapter<FeedListAdapter.FeedRowViewHolder> {
    List<Post> postList;
    AdapterView.OnItemClickListener mListener;
    FeedRowViewHolder viewHolder;
    public FeedListAdapter(List<Post> postListLiveData) {
        if(postListLiveData == null){
            this.postList = new ArrayList<>();
        }else {
            this.postList = postListLiveData;
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
    }

    interface OnItemClickListener{
        void onClick(int index);
    }
    public void setOnItemClickListener(AdapterView.OnItemClickListener listener){
        mListener = listener;
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

    public void updateDisplay(List<Post> posts){
       //viewHolder.bind(posts.get(0));
    }
    static class FeedRowViewHolder extends RecyclerView.ViewHolder {
        TextView mPostAuthor;
        ImageView mAuthorImage;
        ImageView mPostImage;
        TextView mPostText;

        public void updateDisplay(List<Post> posts){
            bind(posts.get(0));
        }

        public FeedRowViewHolder(@NonNull final View itemView, final AdapterView.OnItemClickListener listener) {
            super(itemView);
            mAuthorImage = itemView.findViewById(R.id.authorImage);
            mPostAuthor = itemView.findViewById(R.id.postAuthor);
            mPostImage = itemView.findViewById(R.id.postImage);
            mPostText = itemView.findViewById(R.id.postText);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int index = getAdapterPosition();
                    if (listener != null){
                        if (index != RecyclerView.NO_POSITION) {
                            //listener.onItemClick(, itemView, index);
                        }
                    }
                }
            });

        }

        public void bind(Post post){
            mPostAuthor.setText(post.getAuthor());
            mPostText.setText(post.getDescription());
            Picasso.get().load(post.getPostImage()).into(mPostImage);
        }
    }
}
