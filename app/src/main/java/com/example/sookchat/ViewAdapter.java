package com.example.sookchat;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import java.util.List;

import static com.example.sookchat.CardClickActivity.ccContext;
import static com.example.sookchat.RetroFitApiClient.IMAGE_DIR;

public class ViewAdapter extends RecyclerView.Adapter<ViewAdapter.ViewHolder> {

    private final List<ImageItem> imageList;
    private Context vContext;



    public ViewAdapter(Context vContext, List<ImageItem> imagelist){

        this.vContext=vContext;
        imageList = imagelist;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView memo;
        ImageView viewimage;
        ImageButton btnClose;
        Button btnLike;
        Button btnComment;


        public ViewHolder(View itemView){
            super(itemView);
            memo = itemView.findViewById(R.id.view_memo);
            viewimage = itemView.findViewById(R.id.view_image);
            btnClose = itemView.findViewById(R.id.btn_close);
            btnLike=itemView.findViewById(R.id.btn_like);
            btnComment=itemView.findViewById(R.id.btn_comment);

        }
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_card,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        ImageItem imageitem = imageList.get(position);
        holder.memo.setText(imageitem.getMemo());
        Glide.with(holder.itemView.getContext())
                .load( IMAGE_DIR+ imageitem.getFilename() + ".jpg")
                .apply(RequestOptions.circleCropTransform()
                        .diskCacheStrategy(DiskCacheStrategy.NONE))
                .centerCrop()
                .into(holder.viewimage);

        holder.btnClose.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                ((Activity)ccContext).finish();
            }
        });

        holder.btnLike.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                Toast.makeText(vContext, "LIKE!", Toast.LENGTH_LONG).show();

            }
        });

    }

    @Override
    public int getItemCount() {
        return imageList.size();
    }

}
