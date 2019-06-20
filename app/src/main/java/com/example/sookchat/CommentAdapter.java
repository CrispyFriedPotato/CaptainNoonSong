package com.example.sookchat;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;



class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {

    private static final String TAG = "CommentAdapter";

    private Context mContext;
    private List<Comment> mComment;
    int postid;


    public CommentAdapter(Context mContext, List<Comment> mComment){
        Log.e(TAG,"CommentAdapter: called.");
        this.mContext = mContext;
        this.mComment = mComment;

    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        public ImageView deletebtn;
        public ImageView image_profile;
        public TextView comment;



        public ViewHolder(View itemView){
            super(itemView);
            Log.e(TAG,"ViewHolder: called.");
            image_profile = itemView.findViewById(R.id.image_profile);
            comment = itemView.findViewById(R.id.comment);
            deletebtn = itemView.findViewById(R.id.delete_btn);
        }

    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        Log.e(TAG,"onCreateViewHolder: called.");
        View view = LayoutInflater.from(mContext).inflate(R.layout.comment_item, viewGroup, false);
        return new CommentAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Log.e(TAG,"onBindViewHolder: called.");
        Comment comment = mComment.get(position);
        holder.comment.setText(comment.getComment());

    }

    @Override
    public int getItemCount() {
        return mComment.size();
    }

}
