package com.zaga.oneway;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PostsViewAdapter extends RecyclerView.Adapter<PostsViewAdapter.PostViewHolder>{

    private ArrayList<Post> posts;
    // create OnItemClickListener
    private OnItemClickListener listener;
    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public PostsViewAdapter(ArrayList<Post> posts) {
        this.posts = posts;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public static class PostViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        private TextView postMsg;

        private PostViewHolder(View itemView, final OnItemClickListener postListener) {
            super(itemView);
            // R.id.gameName is in the """card_game.xml"""
            postMsg = itemView.findViewById(R.id.tv_post_msg);

            // set onItemClick
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (postListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            postListener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }

    // Create new views (invoked by the layout manager)
    @Override
    public PostViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.post_crad, parent, false);

        PostViewHolder gvh = new PostViewHolder(view, listener);
        return gvh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(PostViewHolder holder, int position) {
        // - get element from your data set at this position
        Post card = posts.get(position);
        // - replace the contents of the view with that element
        holder.postMsg.setText(card.toString());
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public void updateData(ArrayList<Post> post) {
        posts.clear();
        posts.addAll(post);
        notifyDataSetChanged();
    }

    public void addItem(int position, Post post) {
        posts.add(position, post);
        notifyItemInserted(position);
    }

    public void removeItem(int position) {
        posts.remove(position);
        notifyItemRemoved(position);
    }

}
