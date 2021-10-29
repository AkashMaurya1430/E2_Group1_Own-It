package com.example.ownit;

import android.content.Context;
import android.content.Intent;
import android.graphics.ImageDecoder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.makeramen.roundedimageview.RoundedImageView;
import java.util.List;


public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.PostViewHolder>{
    private List<postitem> postItems;
    private OnImageListener onImageListener;


    public PostsAdapter(List<postitem> postItems,OnImageListener onImageListener) {
        this.postItems = postItems;
        this.onImageListener = onImageListener;

    }


    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_item_container, parent, false);
        return new PostViewHolder(view,onImageListener);
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
       holder.setPostImage(postItems.get(position));
       holder.textView.setText("caption" + position);

    }




    @Override
    public int getItemCount() {
        return postItems.size();
    }

    public class PostViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        OnImageListener onImageListener;
        RoundedImageView postImageView;
        TextView textView;


        public PostViewHolder(@NonNull View itemView,OnImageListener onImageListener) {
            super(itemView);
            postImageView = itemView.findViewById(R.id.imagePost);
            textView = itemView.findViewById(R.id.caption);
            this.onImageListener = onImageListener;

            itemView.setOnClickListener(this);
        }

        public PostViewHolder(View inflate) {
            super(inflate);
        }

        void setPostImage(postitem postItem){
//            if you want to display image from internet
//            here you can put code for loading image using glide or picasso
            postImageView.setImageResource(postItem.getImage());

        }


        @Override
        public void onClick(View view) {
            onImageListener.onImageClick(getAdapterPosition());

        }
    }
    public interface OnImageListener{
        void onImageClick(int position);
    }

}
