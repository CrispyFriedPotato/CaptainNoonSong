package com.example.sookchat;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.sookchat.Data.ImageItem;

import java.util.List;

import static com.example.sookchat.Retrofit.RetroFitApiClient.IMAGE_DIR;

public class ViewAdapter extends RecyclerView.Adapter<ViewAdapter.ViewHolder> {

    private static final String TAG = "ViewAdapter";
    ;    private final List<ImageItem> imageList;
    private Context vContext;
    private OnCardListener mOnCardListener;





    public ViewAdapter(Context vContext, List<ImageItem> imagelist){
        Log.e(TAG,"ViewAdapter: called.");

        this.vContext=vContext;
        this.imageList = imagelist;


    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView memo;
        ImageView viewimage;
        ImageButton btnClose;
        Button btnLike;
        Button btnComment;
        OnCardListener onCardListener;

        public ViewHolder(View itemView){

            super(itemView);
            Log.e(TAG,"ViewHolder: called.");
            memo = itemView.findViewById(R.id.view_memo);
            viewimage = itemView.findViewById(R.id.view_image);
            btnClose = itemView.findViewById(R.id.btn_close);
            btnLike=itemView.findViewById(R.id.btn_like);
            btnComment=(Button)itemView.findViewById(R.id.btn_comment);


        }

    }


    public void setOnClickListener(OnCardListener onCardListener){
        mOnCardListener = onCardListener;
    }

    public interface OnCardListener{
        void onCardClick(int position);

        void onCardDelete();
        void onClickLike();
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.e(TAG,"onCreateViewHolder: called.");
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_card,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Log.e(TAG,"onBindViewHolder: called.");

        final ImageItem imageitem = imageList.get(position);
        holder.memo.setText(imageitem.getMemo());
        Glide.with(holder.itemView.getContext())
                .load( IMAGE_DIR+ imageitem.getFilename() + ".jpg")
                .apply(RequestOptions.circleCropTransform()
                        .diskCacheStrategy(DiskCacheStrategy.NONE))
                .centerCrop()
                .into(holder.viewimage);

        holder.btnClose.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                //((Activity)ccContext).finish();
                mOnCardListener.onCardDelete();
            }
        });

        holder.btnLike.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                mOnCardListener.onClickLike();

            }
        });

        holder.btnComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnCardListener.onCardClick(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return imageList.size();
    }



}